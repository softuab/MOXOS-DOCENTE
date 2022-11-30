/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.web.Docentes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.fautapo.domain.Calendarios;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.logic.MiFacade;
import org.fautapo.model.CalendarioDetalleModel;
import org.fautapo.model.ParametrosEntrada;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CalendarioController {

    @Autowired
    private MiFacade mi;
    @Autowired
    private HttpServletRequest request;

    private Clientes GetUsuario() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        Clientes cliente = (Clientes) attr.getRequest().getSession().getAttribute("__sess_cliente");
        return cliente;
    }

    @RequestMapping("/EntradaCalendariocalificacion.fautapo")
    public String Entrada(Model modelo) {
        Clientes cliente = this.GetUsuario();
        if (cliente == null) {
            modelo.addAttribute("mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            return "Aviso";
        }
        ParametrosEntrada model = new ParametrosEntrada();
        modelo.addAttribute("simagen", cliente.getImagen());
        modelo.addAttribute("usuario", cliente.getNombres());
        modelo.addAttribute("gestion", Integer.toString(cliente.getGestion()));
        modelo.addAttribute("periodo", Integer.toString(cliente.getPeriodo()));
        modelo.addAttribute("model", model);
        return "AdministrarCalendariocalificacion/Entrada";
    }

    @RequestMapping(value = "/ListarDetalleCallendarios.fautapo", method = RequestMethod.POST)
    public String ListarDetalleCallendarios(@ModelAttribute("model") @Validated ParametrosEntrada model, BindingResult result, Model modelo) {
        Clientes cliente = this.GetUsuario();
        if (cliente == null) {
            modelo.addAttribute("mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            return "Aviso";
        }
        if (result.hasErrors()) {
            modelo.addAttribute("simagen", cliente.getImagen());
            modelo.addAttribute("usuario", cliente.getNombres());
            modelo.addAttribute("gestion", Integer.toString(cliente.getGestion()));
            modelo.addAttribute("periodo", Integer.toString(cliente.getPeriodo()));
            modelo.addAttribute("model", model);
            return "AdministrarCalendariocalificacion/Entrada";
        }
        String sNombres = cliente.getNombres();
        int iId_docente = cliente.getId_usuario();
        List<String> Listaprograma = new ArrayList<String>();

        Calendarios calendario = new Calendarios();
        calendario.setPeriodo(model.getPeriodo());
        calendario.setGestion(model.getGestion());
        calendario.setId_docente(iId_docente);
        List<Calendarios> listacalenario = null;
        try {
            listacalenario = mi.getlistarCalendarioDocente(calendario);
            for (Calendarios var : listacalenario) {
                Listaprograma.add(var.getPrograma());
            }
            List<CalendarioDetalleModel> listadetalle = new ArrayList<CalendarioDetalleModel>();
            for (Calendarios var : listacalenario) {
                CalendarioDetalleModel obj = new CalendarioDetalleModel(var.getFecha_inicio(), var.getFecha_limite());
                obj.setCarrera(var.getPrograma());
                obj.setGestion(var.getGestion());
                obj.setObservacion("");
                obj.setPeriodo(var.getPeriodo());
                obj.setTitulo(var.getTipo_evaluacion() + "-" + var.getTipo_nota() + " " + var.getNro_tipo_nota());
                listadetalle.add(obj);
            }
            List<String> listamostrar = Listaprograma.stream().distinct().collect(Collectors.toList());
            modelo.addAttribute("ListaCarrera", listamostrar);
            modelo.addAttribute("ListaCarreraDetalle", listadetalle);
            modelo.addAttribute("usuario", sNombres);
            modelo.addAttribute("gestion", model.getGestion());
            modelo.addAttribute("periodo", model.getPeriodo());
        } catch (Exception ex) {
        }

        return "AdministrarCalendariocalificacion/ListarDetalleCallendarios";
    }
}
