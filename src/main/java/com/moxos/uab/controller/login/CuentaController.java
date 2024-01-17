package com.moxos.uab.controller.login;

import com.moxos.uab.model.models.login.PasswordRecoveryRequest;
import com.moxos.uab.model.models.utility.ParametroEntradaRequest;
import com.moxos.uab.mybatis.entity.Clientes;
import com.moxos.uab.mybatis.entity.Docentes;
import com.moxos.uab.mybatis.logic.MiFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CuentaController {
    @Autowired
    private MiFacade mi;
    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;
    @Autowired
    private HttpServletRequest request;

    private Clientes getUsuario() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        Clientes cliente = (Clientes) attr.getRequest().getSession().getAttribute("__sess_cliente");
        return cliente;
    }

    @RequestMapping("/cambioPinDocente/entrada")
    public String entrada(Model modelo) {
        Clientes cliente = this.getUsuario();
        ParametroEntradaRequest model = new ParametroEntradaRequest();
        modelo.addAttribute("simagen", cliente.getImagen());
        modelo.addAttribute("nombres", cliente.getNombres());
        modelo.addAttribute("model", model);
        return "Cuenta/CambioPinEntrada";
    }
    @RequestMapping(value = "/cambioPinDocente/avisoCambioPin", method = RequestMethod.POST)
    public String verificarEntrada(@ModelAttribute("model") @Validated ParametroEntradaRequest model, BindingResult result, Model modelo) {
        Clientes cliente = this.getUsuario();
        if (result.hasErrors()) {
            modelo.addAttribute("simagen", cliente.getImagen());
            modelo.addAttribute("nombres", cliente.getNombres());
            modelo.addAttribute("model", model);
            return "Cuenta/CambioPinEntrada";
        }
        int iId_docente = cliente.getId_usuario();
        String nombres = cliente.getNombres();
        String id_rol = Integer.toString(cliente.getId_rol());
        modelo.addAttribute("id_rol", id_rol);
        modelo.addAttribute("nombres", nombres);
        modelo.addAttribute("id_docente", Integer.toString(iId_docente));
        modelo.addAttribute("clave", model.getClave());
        return "Cuenta/CambioPinDocenteAviso";
    }
    @RequestMapping(value = "/cambioPinDocente/ingresarNuevoPin", method = RequestMethod.POST)
    public String ingresarNuevoPin(Model modelo) {
        Clientes cliente = this.getUsuario();
        PasswordRecoveryRequest model = new PasswordRecoveryRequest();
        model.setId_docente(cliente.getId_usuario());
        modelo.addAttribute("nombres", cliente.getNombres());
        modelo.addAttribute("model", model);
        modelo.addAttribute("id_rol", cliente.getId_rol());
        return "Cuenta/CambioPinDocenteRegistro";
    }
    @RequestMapping(value = "/cambioPinDocente/registrarNuevoPin", method = RequestMethod.POST)
    public String registrarNuevoPin(@ModelAttribute("model") @Validated PasswordRecoveryRequest model, BindingResult result, Model modelo) {
        Clientes cliente = this.getUsuario();
        if (result.hasErrors()) {
            modelo.addAttribute("nombres", cliente.getNombres());
            modelo.addAttribute("model", model);
            modelo.addAttribute("id_rol", cliente.getId_rol());
            return "Cuenta/CambioPinDocenteRegistro";
        }
        String clave = bcryptEncoder.encode(model.getConf_nueva_clave());
        Docentes docente = new Docentes();
        docente.setId_docente(model.getId_docente());
        docente.setClave(clave);
        docente.setId_rol(cliente.getId_rol());
        docente.setUlt_usuario(cliente.getId_usuario());
        Integer iValor = this.mi.setCambioPinDocente(docente);
        modelo.addAttribute("nombres", cliente.getNombres());
        if (iValor == 1) {
            cliente.setClave(clave);
            request.getSession().setAttribute("__sess_cliente", cliente);
            modelo.addAttribute("mensaje", "Se realizo el cambio de clave");
            return "cambioPinDocente/docente/Aviso";
        } else {
            modelo.addAttribute("mensaje", "No se realizo el cambio de clave. Intentelo de nuevo");
            return "cambioPinDocente/docente/Aviso";
        }
    }
}
