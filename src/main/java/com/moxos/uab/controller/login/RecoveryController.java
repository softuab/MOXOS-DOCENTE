package com.moxos.uab.controller.login;

import com.moxos.uab.model.models.login.*;
import com.moxos.uab.mybatis.entity.Docentes;
import com.moxos.uab.mybatis.entity.Tokens;
import com.moxos.uab.mybatis.logic.MiFacade;
import com.moxos.uab.service.EmailService;
import com.moxos.uab.util.Mail;
import org.jboss.aerogear.security.otp.Totp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@Controller
public class RecoveryController {
    @Autowired
    private MiFacade mi;

    @Autowired
    private EmailService emailservice;
    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @RequestMapping(value = "/recovery", method = RequestMethod.GET)
    public String Recovery(@ModelAttribute("model") RecoveryPasswordRequest model, ModelMap modelo) throws IOException {
        Docentes datosdocente = mi.getBuscarListaDocentesCorreo(model.getCorreo()).stream().findFirst().get();
        String tokens = UUID.randomUUID().toString();
        Tokens token = new Tokens();
        token.setExpiryDate(30);
        token.setId_docente(datosdocente.getId_docente());
        token.setId_estado("A");
        token.setToken(tokens.toUpperCase());
        Mail mail = new Mail();
        mail.setFrom("soporte@uabjb.edu.bo");
        mail.setTo(model.getCorreo());
        mail.setSubject("RECUPERA TU CONTRASEÑA");
        modelo.addAttribute("nombrecompleto", datosdocente.getNombre_completo());
        modelo.addAttribute("token", tokens);
        mail.setModel(modelo);
        if (emailservice.sendEmail(mail)) {
            mi.setGenerarToken(token);
            modelo.put("nombrecompleto", datosdocente.getNombre_completo());
            TokenRecoveryRequest modeltoken = new TokenRecoveryRequest();
            modeltoken.setId_docente(datosdocente.getId_docente());
            modeltoken.setNombre_completo(datosdocente.getNombre_completo());
            modelo.addAttribute("model", modeltoken);
            return "Recovery/CodigoVerificacion";
        } else {
            modelo.addAttribute("model", model);
            modelo.addAttribute("mensaje", "No se logro enviar el correo de recuperacion intente nuevamente");
            return "Recovery/error";
        }
    }

    @RequestMapping(value = "/recovery", method = RequestMethod.POST)
    public String Recovery(@ModelAttribute("model") @Validated TokenRecoveryRequest model, BindingResult result, ModelMap modelo) {
        if (result.hasErrors()) {
            modelo.addAttribute("nombrecompleto", model.getNombre_completo());
            return "Recovery/CodigoVerificacion";
        } else {
            PasswordResetRequest password = new PasswordResetRequest();
            password.setId_docente(model.getId_docente());
            password.setNombre_completo(model.getNombre_completo());
            modelo.addAttribute("nombres", password.getNombre_completo());
            modelo.addAttribute("model", password);
            modelo.addAttribute("id_rol", 1);
            return "Recovery/CambioPinDocente";
        }
    }

    @RequestMapping(value = "/recovery/registrarNuevoPin", method = RequestMethod.POST)
    public String registrarNuevoPin(@ModelAttribute("model") @Validated PasswordResetRequest model, BindingResult result, Model modelo) {
        if (result.hasErrors()) {
            modelo.addAttribute("nombres", model.getNombre_completo());
            modelo.addAttribute("model", model);
            modelo.addAttribute("id_rol", 1);
            return "Recovery/CambioPinDocente";
        }
        String clave = bcryptEncoder.encode(model.getConf_nueva_clave());
        Docentes docente = new Docentes();
        docente.setId_docente(model.getId_docente());
        docente.setClave(bcryptEncoder.encode(model.getNueva_clave().trim()));
        docente.setId_rol(10);
        docente.setUlt_usuario(model.getId_docente());
        int iValor = this.mi.setCambioPinDocente(docente);
        modelo.addAttribute("nombres", model.getNombre_completo());
        if (iValor == 1) {
            return "redirect:/singin";
        } else {
            modelo.addAttribute("mensaje", "No se realizo el cambio de clave. Intentelo de nuevo");
            return "Recovery/error";
        }
    }

    @GetMapping("/recovery/twofactor")
    public String recoveryTwoFactor(@ModelAttribute("apodo") String apodo, Model modelo) {
        Docentes datosdocente = mi.getUsuarioDocente(apodo);
        TwoFactorRecoveryRequest modeltoken = new TwoFactorRecoveryRequest();
        modeltoken.setId_docente(datosdocente.getId_docente());
        modeltoken.setNombre_completo(datosdocente.getNombre_completo());
        modelo.addAttribute("nombrecompleto", datosdocente.getNombre_completo());
        modelo.addAttribute("model", modeltoken);
        return "Recovery/CodigoTwoFactorVerificacion";
    }

    @PostMapping("/recovery/twofactor")
    public String recoveryTwoFactor(@ModelAttribute("model") @Validated TwoFactorRecoveryRequest model, BindingResult result, Model modelo) {

        if (validarToken(model.getToken(), model.getId_docente())) {
            result.addError(new FieldError("token", "token", "Token invalido vuelva a intentarlo"));
        }

        if (result.hasErrors()) {
            modelo.addAttribute("nombrecompleto", model.getNombre_completo());
            modelo.addAttribute("model", model);
            return "Recovery/CodigoTwoFactorVerificacion";
        }
        PasswordResetRequest password = new PasswordResetRequest();
        password.setId_docente(model.getId_docente());
        password.setNombre_completo(model.getNombre_completo());
        modelo.addAttribute("nombres", password.getNombre_completo());
        modelo.addAttribute("model", password);
        modelo.addAttribute("id_rol", 1);
        return "Recovery/CambioPinDocente";
    }
    private boolean isValidLong(String code) {
        try {
            Long.parseLong(code);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    public boolean validarToken(String token, int idDocente) {
        String claveSecreta = mi.getSecret(idDocente);
        Totp totp = new Totp(claveSecreta);
        return !isValidLong(token) || !totp.verify(token);
    }
}
