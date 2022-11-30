package org.fautapo.web.cuenta;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import org.fautapo.domain.Docentes;
import org.fautapo.domain.Tokens;
import org.fautapo.domain.logic.MiFacade;
import org.fautapo.model.PasswordRecoveryModel;
import org.fautapo.model.RecoveryPasswordModel;
import org.fautapo.model.TokenRecovery;
import org.fautapo.service.EmailService;
import org.fautapo.util.Mail;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AutenticacionController {

    @Autowired
    private MiFacade mi;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;
    @Autowired
    private EmailService emailservice;

    @RequestMapping("/login.fautapo")
    public String loginPage(@RequestParam(value = "error", required = false) Integer errorCode, Model model, HttpServletRequest servletRequest) {
        if (errorCode != null) {
            if (errorCode.equals(1)) {
                model.addAttribute("authenticationError", "Ingreso usuario y contraseña incorrecto");
                model.addAttribute("login", "");
            }
            if (errorCode.equals(2)) {
                String login = servletRequest.getParameter("apodo");
                model.addAttribute("authenticationError", "Capchat incorrecto");
                model.addAttribute("login", login);
            }
        }
        model.addAttribute("login", "");
        return "login/LoginEntrada";
    }

    @RequestMapping(value = "/recovery.fautapo", method = RequestMethod.POST)
    public String recovery() {
        return "/user/menu";
    }

    @RequestMapping(value = "/ExisteUsuario.fautapo", method = RequestMethod.GET, consumes = "application/json", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> ExisteUsuario(@ModelAttribute("model") @Validated RecoveryPasswordModel model, BindingResult result) {
        String msg = "none";
        if (result.hasErrors()) {
            msg = "";
            msg = result.getFieldErrors().stream().map((p) -> (p.getDefaultMessage() + "<br/>")).reduce(msg, String::concat);
            return new ResponseEntity<>("{\"status\" : \"" + msg + "\"}", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("{\"status\" : \"" + msg + "\"}", HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/Recovery.fautapo", method = RequestMethod.GET)
    public String Recovery(@ModelAttribute("model") RecoveryPasswordModel model, ModelMap modelo) throws IOException, FileNotFoundException, MessagingException {
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
            TokenRecovery modeltoken = new TokenRecovery();
            modeltoken.setId_docente(datosdocente.getId_docente());
            modeltoken.setNombre_completo(datosdocente.getNombre_completo());
            modelo.addAttribute("model", modeltoken);
            return "login/recovery/CodigoVerificacion";
        } else {
            modelo.addAttribute("model", model);
            modelo.addAttribute("mensaje", "No se logro enviar el correo de recuperacion intente nuevamente");
            return "login/recovery/error";
        }
    }

    @RequestMapping(value = "/Recovery.fautapo", method = RequestMethod.POST)
    public String Recovery(@ModelAttribute("model") @Validated TokenRecovery model, BindingResult result, ModelMap modelo) {
        if (result.hasErrors()) {
            modelo.addAttribute("nombrecompleto", model.getNombre_completo());
            return "login/recovery/CodigoVerificacion";
        } else {
            modelo.put("id_docente", model.getId_docente());
            return "login/recovery/CambioPinDocente";
        }
    }

    @RequestMapping("/Logout.fautapo")
    public String Logout() {
        request.getSession().removeAttribute("__sess_cliente");
        request.getSession().invalidate();
        return "redirect:/login.fautapo";
    }

    @RequestMapping(value = "/RegistrarNuevoPinDocente.fautapo", method = RequestMethod.POST)
    public String RegistrarNuevoPinDocente(@ModelAttribute("model") PasswordRecoveryModel model, ModelMap modelo) {
        modelo.addAttribute("id_docente", model.getId_docente());
        modelo.addAttribute("nueva_clave", model.getNueva_clave());
        modelo.addAttribute("conf_nueva_clave", model.getConf_nueva_clave());
        int nrocarac = model.getNueva_clave().length();
        int valor = 6;
        if (("".equals(model.getNueva_clave())) && ("".equals(model.getConf_nueva_clave()))) {
            return "login/recovery/CambioPinDocente";
        }
        if (nrocarac >= valor) {
            if (model.getNueva_clave().trim().equals(model.getConf_nueva_clave().trim())) {
                Docentes docente = new Docentes();
                docente.setId_docente(model.getId_docente());
                docente.setClave(bcryptEncoder.encode(model.getNueva_clave().trim()));
                docente.setId_rol(10);
                docente.setUlt_usuario(model.getId_docente());
                int iValor = this.mi.setCambioPinDocente(docente);
                //return new ModelAndView("cambiopindocente/CambioPinDocenteSalida2", modelo);
                if (iValor == 1) {
                    modelo.addAttribute("mensaje", "Se realizo el cambio de clave");
                    return "login/recovery/Confirmacion";
                } else {
                    modelo.addAttribute("mensaje", "No se realizo el cambio de clave. Intentelo de nuevo");
                    return "login/recovery/Confirmacion";
                }
            } else {
                String mensaje = "No coincide la confirmacion de la clave";
                modelo.put("mensaje", mensaje);
                return "login/recovery/error";
            }
        } else {
            String mensaje = "Digite un minimo de 6 caracteres";
            modelo.addAttribute("mensaje", mensaje);
            return "login/recovery/error";
        }
    }
}
