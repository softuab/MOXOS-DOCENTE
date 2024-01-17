package com.moxos.uab.controller.programaanaliticos;

import com.moxos.uab.model.jsonmodels.MessageResult;
import com.moxos.uab.model.models.programaanalitico.ParametroBusquedaProgramaAnaliticoRequest;
import com.moxos.uab.model.models.programaanalitico.bibliografia.EliminarBibliografiaRequest;
import com.moxos.uab.model.models.programaanalitico.bibliografia.ReferenciaBibliograficaGrupo;
import com.moxos.uab.model.models.programaanalitico.bibliografia.ReferenciaDocumentos;
import com.moxos.uab.model.models.programaanalitico.cronograma.CronogramaRequest;
import com.moxos.uab.model.models.programaanalitico.cronograma.EliminarCronogramaRequest;
import com.moxos.uab.model.models.programaanalitico.cronograma.ParemetroBusquedaCronograma;
import com.moxos.uab.mybatis.entity.Clientes;
import com.moxos.uab.mybatis.entity.Contenidos;
import com.moxos.uab.mybatis.entity.Cronograma;
import com.moxos.uab.mybatis.entity.ListViewItem;
import com.moxos.uab.mybatis.logic.MiFacade;
import com.moxos.uab.util.Convert;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class CronogramaController {
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

    @GetMapping(value = "/programaanaanalitico/listarCronograma")
    public String listarContenidos(@ModelAttribute("model") ParametroBusquedaProgramaAnaliticoRequest model, Model modelo) {
        List<Cronograma> cronogramas = mi.getListarCronograma(model.getId_dct_programa_analitico());
        modelo.addAttribute("cronograma", cronogramas);
        modelo.addAttribute("id_dct_programa_analitico", model.getId_dct_programa_analitico());
        return "ProgramasAnaliticos/Cronograma/DetalleCronograma";
    }

    @GetMapping(value = "/programaanaanalitico/cronograma/agregar")
    public String agregar(@ModelAttribute("model") ParametroBusquedaProgramaAnaliticoRequest model, Model modelo) {
        List<ListViewItem> items = mi.getListaFormasClases();
        CronogramaRequest cronograma = new CronogramaRequest();
        cronograma.setId_estado("A");
        cronograma.setFecha(new Date());
        cronograma.setUlt_usuario(1);
        cronograma.setId_dct_programa_analitico(model.getId_dct_programa_analitico());
        modelo.addAttribute("model", cronograma);
        modelo.addAttribute("group", items);
        return "ProgramasAnaliticos/Cronograma/Agregar";
    }

    @PostMapping(value = "/programaanaanalitico/cronograma/agregar")
    public String agregar(@ModelAttribute("model") @Validated CronogramaRequest model, BindingResult result, Model modelo) {
        if (result.hasErrors()) {
            List<ListViewItem> items = mi.getListaFormasClases();
            model.setTipos_de_clases(model.getTipo_de_clase());
            model.setFecha(Convert.ToDate(model.getText_fecha(),"yyyy-MM-dd"));
            modelo.addAttribute("model", model);
            modelo.addAttribute("group", items);
            return "ProgramasAnaliticos/Cronograma/Agregar";
        }
        model.setFecha(Convert.ToDate(model.getText_fecha(),"yyyy-MM-dd"));
        Cronograma cronograma = modelMapper.map(model, Cronograma.class);
        mi.registrarCronograma(cronograma);
        String params = "?id_dct_programa_analitico=" + model.getId_dct_programa_analitico();
        return "redirect:/programaanaanalitico/listarCronograma" + params;
    }
    @GetMapping(value = "/programaanaanalitico/cronograma/editar")
    public String editar(@ModelAttribute("model") ParemetroBusquedaCronograma model, Model modelo) {
        List<ListViewItem> items = mi.getListaFormasClases();
        CronogramaRequest cronograma = modelMapper.map(mi.getCronograma(model.getId_prg_a_cronograma()),CronogramaRequest.class);
        cronograma.setTipos_de_clases(cronograma.getTipo_de_clase());
        modelo.addAttribute("model", cronograma);
        modelo.addAttribute("group", items);
        return "ProgramasAnaliticos/Cronograma/Editar";
    }
    @PostMapping(value = "/programaanaanalitico/cronograma/editar")
    public String editar(@ModelAttribute("model") @Validated CronogramaRequest model, BindingResult result, Model modelo) {
        if (result.hasErrors()) {
            List<ListViewItem> items = mi.getListaFormasClases();
            model.setTipos_de_clases(model.getTipo_de_clase());
            model.setFecha(Convert.ToDate(model.getText_fecha(),"yyyy-MM-dd"));
            modelo.addAttribute("model", model);
            modelo.addAttribute("group", items);
            return "ProgramasAnaliticos/Cronograma/Editar";
        }
        model.setFecha(Convert.ToDate(model.getText_fecha(),"yyyy-MM-dd"));
        Cronograma cronograma = modelMapper.map(model, Cronograma.class);
        mi.actualizarCronograma(cronograma);
        String params = "?id_dct_programa_analitico=" + model.getId_dct_programa_analitico();
        return "redirect:/programaanaanalitico/listarCronograma" + params;
    }
    @RequestMapping(value = "/programaanaanalitico/cronograma/eliminar", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public MessageResult eliminar(@RequestBody EliminarCronogramaRequest model) {
        MessageResult Respuesta = new MessageResult();
        try {
            mi.eliminarCronograma(model.getId_prg_a_cronograma());
            Respuesta.setMessage("Se elimino los estudiantes con exito..");
            Respuesta.setStatus("OK");
        } catch (Exception ex) {
            Respuesta.setMessage("Problemas al eliminar:" + ex.getMessage());
            Respuesta.setStatus("Error");
        }
        return Respuesta;
    }
}
