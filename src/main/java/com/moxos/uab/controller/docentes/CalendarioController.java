package com.moxos.uab.controller.docentes;

import com.moxos.uab.model.models.calendarios.CalendarioDetalleRequest;
import com.moxos.uab.model.models.utility.ParametroEntradaRequest;
import com.moxos.uab.mybatis.entity.Calendarios;
import com.moxos.uab.mybatis.entity.Clientes;
import com.moxos.uab.mybatis.logic.MiFacade;
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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CalendarioController {
    @Autowired
    private MiFacade mi;
    @Autowired
    private HttpServletRequest request;

    private Clientes getUsuario() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        Clientes cliente = (Clientes) attr.getRequest().getSession().getAttribute("__sess_cliente");
        return cliente;
    }

    @RequestMapping("/EntradaCalendariocalificacion")
    public String Entrada(Model modelo) {
        ParametroEntradaRequest model = new ParametroEntradaRequest();
        modelo.addAttribute("simagen", this.getUsuario().getImagen());
        modelo.addAttribute("usuario", this.getUsuario().getNombres());
        modelo.addAttribute("gestion", Integer.toString(this.getUsuario().getGestion()));
        modelo.addAttribute("periodo", Integer.toString(this.getUsuario().getPeriodo()));
        modelo.addAttribute("model", model);
        return "Calendario/Entrada";
    }

    @RequestMapping(value = "/calendario/ListarDetalleCallendarios", method = RequestMethod.POST)
    public String listarDetalleCallendarios(@ModelAttribute("model") @Validated ParametroEntradaRequest model, BindingResult result, Model modelo) {
        if (result.hasErrors()) {
            modelo.addAttribute("simagen", this.getUsuario().getImagen());
            modelo.addAttribute("usuario", this.getUsuario().getNombres());
            modelo.addAttribute("gestion", Integer.toString(this.getUsuario().getGestion()));
            modelo.addAttribute("periodo", Integer.toString(this.getUsuario().getPeriodo()));
            modelo.addAttribute("model", model);
            return "Calendario/Entrada";
        }

        Calendarios calendario = new Calendarios();
        calendario.setPeriodo(model.getPeriodo());
        calendario.setGestion(model.getGestion());
        calendario.setId_docente(this.getUsuario().getId_usuario());
        List<Calendarios> listacalenario = null;
        listacalenario = mi.getlistarCalendarioDocente(calendario);
        List<CalendarioDetalleRequest> listadetalle = new ArrayList<>();
        for (Calendarios detallecalendario : listacalenario) {
            CalendarioDetalleRequest obj = new CalendarioDetalleRequest(detallecalendario.getFecha_inicio(), detallecalendario.getFecha_limite());
            obj.setCarrera(detallecalendario.getPrograma());
            obj.setGestion(detallecalendario.getGestion());
            obj.setObservacion("");
            obj.setEsAmpliacion(detallecalendario.getTipo().equals("N") ? false : true);
            obj.setPeriodo(detallecalendario.getPeriodo());
            obj.setTipoNota(detallecalendario.getTipo_nota() + " Nro. Nota de evaluacion " + detallecalendario.getNro_tipo_nota());
            obj.setTitulo(detallecalendario.getTipo_evaluacion());
            listadetalle.add(obj);
        }
        List<CalendarioDetalleRequest> listamostrar = listadetalle.stream().sorted(Comparator.comparing(CalendarioDetalleRequest::getCarrera)).collect(Collectors.toList());
        modelo.addAttribute("ListaCarreraDetalle", listamostrar);
        modelo.addAttribute("usuario", this.getUsuario().getNombres());
        modelo.addAttribute("gestion", model.getGestion());
        modelo.addAttribute("periodo", model.getPeriodo());
        return "Calendario/ListarDetalleCallendarios";
    }

}
