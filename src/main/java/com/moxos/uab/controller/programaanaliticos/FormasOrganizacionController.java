package com.moxos.uab.controller.programaanaliticos;

import com.moxos.uab.model.jsonmodels.MessageResult;
import com.moxos.uab.model.models.programaanalitico.ParametroBusquedaProgramaAnaliticoRequest;
import com.moxos.uab.model.models.programaanalitico.bibliografia.EliminarBibliografiaRequest;
import com.moxos.uab.model.models.programaanalitico.formasorganizacion.*;
import com.moxos.uab.model.models.utility.ListGroupViewItem;
import com.moxos.uab.model.models.utility.ListViewItemSelected;
import com.moxos.uab.mybatis.entity.*;
import com.moxos.uab.mybatis.logic.MiFacade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class FormasOrganizacionController {
    @Autowired
    private MiFacade mi;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ModelMapper modelMapper;

    private Clientes getUsuario() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        Clientes cliente = (Clientes) attr.getRequest().getSession().getAttribute("__sess_cliente");
        return cliente;
    }

    @GetMapping(value = "/programaanaanalitico/formasorganizacion/listarDistribucionTiempos")
    public String listarDistribucionTiempos(@ModelAttribute("model") ParametroBusquedaProgramaAnaliticoRequest model, Model modelo) {
        Contenidos _contenido = new Contenidos();
        _contenido.setId_dct_programa_analitico(model.getId_dct_programa_analitico());
        List<Contenidos> contenidos = mi.getListarContenido(_contenido);
        HorasMaterias horas = mi.getCantidadHorasAsignatura(model.getId_dct_programa_analitico());
        List<FormasOrganizacionDistribucion> distribuciones = mi.getListarFormasOrganizacionDistribucion(model.getId_dct_programa_analitico());
        List<FormasOrganizacionDistribucion> formasdistribucion = mi.getListarFormasPorDistribucion(model.getId_dct_programa_analitico());
        List<ContenidosFormasRequest> formas = new ArrayList<>();
        for (Contenidos contenido : contenidos) {
            ContenidosFormasRequest forma = new ContenidosFormasRequest();
            forma.setContenido(contenido.getContenido());
            forma.setId_prg_a_contenido(contenido.getId_prg_a_contenido());
            List<FormasOrganizacionDistribucion> distribucion = distribuciones.stream().filter(p -> p.getId_prg_a_contenido().equals(contenido.getId_prg_a_contenido())).collect(Collectors.toList());
            forma.setDistribuciones(distribucion);
            formas.add(forma);
        }
        FormasDistribucionRequest formasorganizacion = new FormasDistribucionRequest();
        formasorganizacion.setTotalHoras(horas.getTotal());
        formasorganizacion.setDistribucion(formas);
        formasorganizacion.setFormasdistribucion(formasdistribucion);
        formasorganizacion.setId_dct_programa_analitico(model.getId_dct_programa_analitico());
        modelo.addAttribute("formasorganizacion", formasorganizacion);
        return "ProgramasAnaliticos/FormasOrganizacion/DetalleDistribucionTiempos";
    }

    @GetMapping(value = "/programaanaanalitico/formasorganizacion/agregar")
    public String agregar(@ModelAttribute("model") ParametroBusquedaDistribucionRequest model, Model modelo) {
        FormasOrganizacionRequest formas = new FormasOrganizacionRequest();
        formas.setId_dct_programa_analitico(model.getId_dct_programa_analitico());
        formas.setId_prg_a_contenido(model.getId_prg_a_contenido());
        formas.setId_estado("A");
        formas.setTotalHoras(model.getTotalHoras());
        formas.setUlt_usuario(1);
        formas.setTipo_forma("PRESENCIAL");
        List<ListViewItemSelected> group = new ArrayList<>();
        group.add(new ListViewItemSelected("PRESENCIAL", "PRESENCIAL", formas.getTipo_forma().equals("PRESENCIAL")));
        group.add(new ListViewItemSelected("VIRTUAL DISTANCIA", "VIRTUAL DISTANCIA", formas.getTipo_forma().equals("VIRTUAL DISTANCIA")));
        List<FormasOrganizacion> tipoFormas = mi.getListaFormasPorTipo(formas.getTipo_forma());
        formas.setTipo(group);
        formas.setItems(getGrooups(tipoFormas, -1));
        modelo.addAttribute("model", formas);
        return "ProgramasAnaliticos/FormasOrganizacion/Agregar";
    }

    @RequestMapping(value = "/programaanaanalitico/formasorganizacion/formas", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<ListGroupViewItem> getListaFormas(@ModelAttribute("tipo") String tipo) {
        List<ListGroupViewItem> formas = new ArrayList<>();
        List<FormasOrganizacion> tipoFormas = mi.getListaFormasPorTipo(tipo);
        formas = getGrooups(tipoFormas, -1);
        return formas;
    }

    @RequestMapping(value = "/programaanaanalitico/formasorganizacion/agregar", method = RequestMethod.POST)
    public String agregar(@ModelAttribute("model") @Validated FormasOrganizacionRequest model, BindingResult result, Model modelo) {

        FormasDistribucion formasdistribucion = new FormasDistribucion();
        formasdistribucion.setId_prg_a_contenido(model.getId_prg_a_contenido());
        formasdistribucion.setId_prg_a_formas(model.getId_prg_a_formas());
        formasdistribucion.setId_dct_programa_analitico(model.getId_dct_programa_analitico());
        Integer idDistribucion = mi.getidDistribucion(formasdistribucion);
        if (idDistribucion != null)
            result.addError(new FieldError("model", "horas", "ya se ha registrado esta forma de organizacion en esta unidad"));
        if (model.getHoras() != null)
            if (model.getHoras() <= 0)
                result.addError(new FieldError("model", "horas", "Debe ser mayor a 0 horas"));

        if (model.getHoras() != null)
            if (model.getHoras() > model.getTotalHoras())
                result.addError(new FieldError("model", "horas", "ha superado la cantidad de horas establecida de " + model.getTotalHoras()));
        Integer total = mi.getTotalHorasdistribucion(model.getId_dct_programa_analitico());
        if (total != null)
            if (total > model.getTotalHoras())
                result.addError(new FieldError("model", "horas", "ha superado la cantidad de horas establecida de " + model.getTotalHoras()));
        if (result.hasErrors()) {
            List<ListViewItemSelected> group = new ArrayList<>();
            group.add(new ListViewItemSelected("PRESENCIAL", "PRESENCIAL", model.getTipo_forma().equals("PRESENCIAL")));
            group.add(new ListViewItemSelected("VIRTUAL DISTANCIA", "VIRTUAL DISTANCIA", model.getTipo_forma().equals("VIRTUAL DISTANCIA")));
            List<FormasOrganizacion> tipoFormas = mi.getListaFormasPorTipo(model.getTipo_forma());
            model.setTipo(group);
            model.setItems(getGrooups(tipoFormas, model.getId_prg_a_formas()));
            modelo.addAttribute("model", model);
            return "ProgramasAnaliticos/FormasOrganizacion/Agregar";
        }
        FormasDistribucion distribucion = modelMapper.map(model, FormasDistribucion.class);
        mi.insertarDistribucion(distribucion);
        String params = "?id_dct_programa_analitico=" + model.getId_dct_programa_analitico();
        return "redirect:/programaanaanalitico/formasorganizacion/listarDistribucionTiempos" + params;
    }

    @GetMapping(value = "/programaanaanalitico/formasorganizacion/editar")
    public String editar(@ModelAttribute("model") FormasDistribucionEditarRequest model, Model modelo) {
        FormasDistribucion distribucion = mi.getFormasDistribucion(model.getId_prg_a_distribucion());
        FormasOrganizacionHorasRequest formas = new FormasOrganizacionHorasRequest();
        formas.setHoras(distribucion.getHoras());
        formas.setId_prg_a_distribucion(distribucion.getId_prg_a_distribucion());
        formas.setTotalHoras(model.getTotalHoras());
        formas.setId_dct_programa_analitico(distribucion.getId_dct_programa_analitico());
        formas.setHorasAnterior(model.getHorasAnterior());
        modelo.addAttribute("model", formas);
        return "ProgramasAnaliticos/FormasOrganizacion/Editar";
    }

    @RequestMapping(value = "/programaanaanalitico/formasorganizacion/editar", method = RequestMethod.POST)
    public String editar(@ModelAttribute("model") @Validated FormasOrganizacionHorasRequest model, BindingResult result, Model modelo) {

        if (model.getHoras() != null)
            if (model.getHoras() > model.getTotalHoras())
                result.addError(new FieldError("model", "horas", "ha superado la cantidad de horas establecida de " + model.getTotalHoras()));
        Integer diferencia = model.getHorasAnterior() - model.getHoras();
        Integer totalFormasDistribucion = mi.getTotalHorasdistribucion(model.getId_dct_programa_analitico());
        Integer total = 0;
        if (diferencia > 0)
            total = totalFormasDistribucion - diferencia;
        else
            total = totalFormasDistribucion + model.getHoras() - model.getHorasAnterior();
        if (total != null)
            if (total > model.getTotalHoras())
                result.addError(new FieldError("model", "horas", "ha superado la cantidad de horas establecida de " + model.getTotalHoras()));
        if (result.hasErrors()) {
            modelo.addAttribute("model", model);
            return "ProgramasAnaliticos/FormasOrganizacion/Editar";
        }
        FormasOrganizacionDistribucion distribucion = modelMapper.map(model, FormasOrganizacionDistribucion.class);
        mi.actualizarHorasDistribucion(distribucion);
        String params = "?id_dct_programa_analitico=" + model.getId_dct_programa_analitico();
        return "redirect:/programaanaanalitico/formasorganizacion/listarDistribucionTiempos" + params;
    }

    @GetMapping(value = "/programaanaanalitico/formasorganizacion/eliminar")
    public String eliminar(@ModelAttribute("model") EliminarFormasOrganizacionRequest model, Model modelo) {
        mi.eliminarDistribucion(model.getId_prg_a_distribucion());
        String params = "?id_dct_programa_analitico=" + model.getId_dct_programa_analitico();
        return "redirect:/programaanaanalitico/formasorganizacion/listarDistribucionTiempos" + params;
    }

    private List<ListGroupViewItem> getGrooups(List<FormasOrganizacion> tipoFormas, int id) {
        List<ListGroupViewItem> groupitems = new ArrayList<>();
        if (!tipoFormas.isEmpty()) {
            String aux = "";
            List<ListGroupViewItem> groups = new ArrayList<>();
            ListGroupViewItem group = new ListGroupViewItem();
            for (FormasOrganizacion item : tipoFormas) {
                if (!aux.equals(item.getGrupo_forma())) {
                    aux = item.getGrupo_forma();
                    group = new ListGroupViewItem();
                    group.setGroup(aux);
                    List<FormasOrganizacion> formasorganizacion = tipoFormas.stream().filter(p -> p.getGrupo_forma().equals(item.getGrupo_forma())).collect(Collectors.toList());
                    List<ListViewItemSelected> listitems = new ArrayList<>();
                    for (FormasOrganizacion itemview : formasorganizacion)
                        listitems.add(new ListViewItemSelected(itemview.getId_prg_a_formas().toString(), itemview.getFormas(), itemview.getId_prg_a_formas() == id));
                    group.getItems().addAll(listitems);
                    groups.add(group);
                }
            }
            groupitems = groups;
        }
        return groupitems;
    }

}
