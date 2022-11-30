/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.web.Docentes;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Docentes;
import org.fautapo.domain.logic.MiFacade;
import org.fautapo.model.ParametrosEntrada;
import org.fautapo.model.PasswordRecoveryModel;
import org.modelmapper.ModelMapper;
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

@Controller
public class SeguridadController {

    @Autowired
    private MiFacade mi;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    private Clientes GetUsuario() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        Clientes cliente = (Clientes) attr.getRequest().getSession().getAttribute("__sess_cliente");
        return cliente;
    }

    @RequestMapping("/cambioPinDocente/entrada.fautapo")
    public String Entrada(Model modelo) {
        Clientes cliente = this.GetUsuario();
        ParametrosEntrada model = new ParametrosEntrada();
        modelo.addAttribute("simagen", cliente.getImagen());
        modelo.addAttribute("nombres", cliente.getNombres());
        modelo.addAttribute("model", model);
        return "cambioPinDocente/docente/CambioPinDocenteEntrada";
    }

    @RequestMapping(value = "/cambioPinDocente/avisoCambioPin.fautapo", method = RequestMethod.POST)
    public String VerificarEntrada(@ModelAttribute("model") @Validated ParametrosEntrada model, BindingResult result, Model modelo) {
        Clientes cliente = this.GetUsuario();
        if (result.hasErrors()) {
            modelo.addAttribute("simagen", cliente.getImagen());
            modelo.addAttribute("nombres", cliente.getNombres());
            modelo.addAttribute("model", model);
            return "cambioPinDocente/docente/CambioPinDocenteEntrada";
        }
        int iId_docente = cliente.getId_usuario();
        String nombres = cliente.getNombres();
        String id_rol = Integer.toString(cliente.getId_rol());
        modelo.addAttribute("id_rol", id_rol);
        modelo.addAttribute("nombres", nombres);
        modelo.addAttribute("id_docente", Integer.toString(iId_docente));
        modelo.addAttribute("clave", model.getClave());
        return "cambioPinDocente/docente/CambioPinDocenteAviso";
    }

    @RequestMapping(value = "/cambioPinDocente/ingresarNuevoPin.fautapo", method = RequestMethod.POST)
    public String ingresarNuevoPin(Model modelo) {
        Clientes cliente = this.GetUsuario();
        PasswordRecoveryModel model = new PasswordRecoveryModel();
        model.setId_docente(cliente.getId_usuario());
        modelo.addAttribute("nombres", cliente.getNombres());
        modelo.addAttribute("model", model);
        modelo.addAttribute("id_rol", cliente.getId_rol());
        return "cambioPinDocente/docente/CambioPinDocenteRegistro";
    }

    @RequestMapping(value = "/cambioPinDocente/registrarNuevoPin.fautapo", method = RequestMethod.POST)
    public String registrarNuevoPin(@ModelAttribute("model") @Validated PasswordRecoveryModel model, BindingResult result, Model modelo) throws IOException {
        Clientes cliente = this.GetUsuario();
        if (result.hasErrors()) {
            modelo.addAttribute("nombres", cliente.getNombres());
            modelo.addAttribute("model", model);
            modelo.addAttribute("id_rol", cliente.getId_rol());
            return "cambioPinDocente/docente/CambioPinDocenteRegistro";
        }
        String clave = bcryptEncoder.encode(model.getConf_nueva_clave());
        Docentes docente = new Docentes();
        docente.setId_docente(model.getId_docente());
        docente.setClave(clave);
        docente.setId_rol(cliente.getId_rol());
        docente.setUlt_usuario(cliente.getId_usuario());
        int iValor = this.mi.setCambioPinDocente(docente);
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
