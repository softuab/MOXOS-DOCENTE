package com.moxos.uab.controller.login;

import com.moxos.uab.model.jsonmodels.MessageResult;
import com.moxos.uab.model.models.twofactor.ParametroBusquedaDocenteRequest;
import com.moxos.uab.model.models.utility.ParametroEntradaRequest;
import com.moxos.uab.mybatis.entity.Clientes;
import com.moxos.uab.mybatis.entity.Docentes;
import com.moxos.uab.mybatis.logic.MiFacade;
import org.jboss.aerogear.security.otp.api.Base32;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
public class TwoFactorController {
    @Autowired
    private MiFacade mi;
    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;
    @Autowired
    private HttpServletRequest request;

    public static String QR_PREFIX = "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=";
    public static String APP_NAME = "AppSIIGAADocente";

    private Clientes getUsuario() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        Clientes cliente = (Clientes) attr.getRequest().getSession().getAttribute("__sess_cliente");
        return cliente;
    }

    @GetMapping(value = "/twofactor/entrada")
    public String entrada(Model modelo) {
        ParametroEntradaRequest model = new ParametroEntradaRequest();
        modelo.addAttribute("simagen", getUsuario().getImagen());
        modelo.addAttribute("nombres", getUsuario().getNombres());
        modelo.addAttribute("model", model);
        return "TwoFactor/Entrada";
    }

    @RequestMapping(value = "/twofactor/activarodesactivar", method = RequestMethod.POST)
    public String verificarEntrada(@ModelAttribute("model") @Validated ParametroEntradaRequest model, BindingResult result, Model modelo) {
        if (result.hasErrors()) {
            modelo.addAttribute("simagen", getUsuario().getImagen());
            modelo.addAttribute("nombres", getUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "TwoFactor/Entrada";
        }
        modelo.addAttribute("twofactor", getUsuario().isUsing2FA());
        modelo.addAttribute("id_rol", getUsuario().getId_rol());
        modelo.addAttribute("nombres", getUsuario().getNombres());
        modelo.addAttribute("id_docente", getUsuario().getId_usuario());
        modelo.addAttribute("clave", model.getClave());
        return "TwoFactor/Activacion";
    }

    @RequestMapping(value = "/twofactor/update/2fa", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public MessageResult modifyUser2FA(@RequestBody ParametroBusquedaDocenteRequest model) throws UnsupportedEncodingException {
        MessageResult message = new MessageResult();
        Docentes docentes = new Docentes();
        String secret = Base32.random();
        docentes.setSecret(secret);
        docentes.setId_docente(getUsuario().getId_usuario());
        docentes.setUsing2FA(model.isUse2FA());
        mi.updateUser2FA(docentes);
        Clientes cliente = getUsuario();
        cliente.setUsing2FA(model.isUse2FA());
        cliente.setSecret(secret);
        request.getSession().setAttribute("__sess_cliente", cliente);
        if (model.isUse2FA()) {
            message.setMessage(generateQRUrl());
            message.setStatus("OK");
            return message;
        }
        message.setStatus("ERROR");
        return message;
    }

    public String generateQRUrl() throws UnsupportedEncodingException {

        return QR_PREFIX + URLEncoder.encode(String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s",
                        APP_NAME, getUsuario().getCorreo(), getUsuario().getSecret(), APP_NAME),
                "UTF-8");
    }


}
