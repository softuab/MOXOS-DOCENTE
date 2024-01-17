package com.moxos.uab.controller.programaanaliticos;

import com.moxos.uab.model.jsonmodels.MessageResult;
import com.moxos.uab.model.models.programaanalitico.ParametroBusquedaProgramaAnaliticoRequest;
import com.moxos.uab.model.models.programaanalitico.contenidos.ContenidosRequest;
import com.moxos.uab.model.models.programaanalitico.contenidos.EliminarContenidoRequest;
import com.moxos.uab.model.models.programaanalitico.contenidos.ParametroBusquedaContenido;
import com.moxos.uab.mybatis.entity.Clientes;
import com.moxos.uab.mybatis.entity.Contenidos;
import com.moxos.uab.mybatis.logic.MiFacade;
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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@Controller
public class ContenidosController {
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

    @GetMapping(value = "/programaanaanalitico/listarContenidos")
    public String listarContenidos(@ModelAttribute("model") ParametroBusquedaProgramaAnaliticoRequest model, Model modelo) {
        Contenidos _contenido = new Contenidos();
        _contenido.setId_dct_programa_analitico(model.getId_dct_programa_analitico());
        List<Contenidos> contenidos = null;
        contenidos = mi.getListarContenido(_contenido);
        String estado = "0";
        if (!contenidos.isEmpty()) {
            estado = "1";
        }
        modelo.addAttribute("contenidos", contenidos);
        modelo.addAttribute("estado", estado);
        modelo.addAttribute("id_dct_programa_analitico", model.getId_dct_programa_analitico());
        modelo.addAttribute("gestion", model.getGestion());
        modelo.addAttribute("periodo", model.getPeriodo());
        modelo.addAttribute("materia", model.getMateria());
        modelo.addAttribute("grupo", model.getGrupo());
        return "ProgramasAnaliticos/Contenidos/Detallecontenidos";
    }

    @GetMapping(value = "/programaanaanalitico/contenidos/agregar")
    public String agregar(@ModelAttribute("model") ParametroBusquedaProgramaAnaliticoRequest model, Model modelo) {
        ContenidosRequest contenido = new ContenidosRequest();
        contenido.setId_estado("A");
        contenido.setId_dct_programa_analitico(model.getId_dct_programa_analitico());
        contenido.setGestion(model.getGestion());
        contenido.setPeriodo(model.getPeriodo());
        contenido.setMateria(model.getMateria());
        contenido.setGrupo(model.getGrupo());
        modelo.addAttribute("nombres", this.getUsuario().getNombres());
        modelo.addAttribute("model", contenido);
        return "ProgramasAnaliticos/Contenidos/Agregar";
    }

    @PostMapping(value = "/programaanaanalitico/contenidos/agregar")
    public String agregar(@ModelAttribute("model") @Validated ContenidosRequest model, BindingResult result, Model modelo) throws UnsupportedEncodingException {
        if (result.hasErrors()) {
            modelo.addAttribute("nombres", this.getUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "ProgramasAnaliticos/Contenidos/Agregar";
        }
        Contenidos contenidos = modelMapper.map(model, Contenidos.class);
        mi.registrarObjetivo_Instructivo(contenidos);
        String params = "?id_dct_programa_analitico=" + model.getId_dct_programa_analitico() +
                "&gestion=" + model.getGestion() +
                "&periodo=" + model.getPeriodo() +
                "&materia=" + URLEncoder.encode(model.getMateria(), "UTF-8") +
                "&grupo=" + URLEncoder.encode(model.getGrupo(), "UTF-8") +
                "&programa=" + URLEncoder.encode("", "UTF-8") +
                "&tab=" + URLEncoder.encode("2", "UTF-8");
        return "redirect:/detalleProgramaAnalitico" + params;
    }

    @GetMapping(value = "/programaanaanalitico/contenidos/editar")
    public String editar(@ModelAttribute("model") ParametroBusquedaContenido model, Model modelo) {
        ContenidosRequest contenido = modelMapper.map(mi.getContenido(model.getId_prg_a_contenido()), ContenidosRequest.class);
        contenido.setGestion(model.getGestion());
        contenido.setPeriodo(model.getPeriodo());
        contenido.setMateria(model.getMateria());
        contenido.setGrupo(model.getGrupo());
        modelo.addAttribute("nombres", this.getUsuario().getNombres());
        modelo.addAttribute("model", contenido);
        return "ProgramasAnaliticos/Contenidos/Editar";
    }

    @PostMapping(value = "/programaanaanalitico/contenidos/editar")
    public String editar(@ModelAttribute("model") @Validated ContenidosRequest model, BindingResult result, Model modelo) throws UnsupportedEncodingException {
        if (result.hasErrors()) {
            modelo.addAttribute("nombres", this.getUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "ProgramasAnaliticos/Contenidos/Agregar";
        }
        Contenidos contenidos = modelMapper.map(model, Contenidos.class);
        mi.actualizarObjetivo_Instructivo(contenidos);
        String params = "?id_dct_programa_analitico=" + model.getId_dct_programa_analitico() +
                "&gestion=" + model.getGestion() +
                "&periodo=" + model.getPeriodo() +
                "&materia=" + URLEncoder.encode(model.getMateria(), "UTF-8") +
                "&grupo=" + URLEncoder.encode(model.getGrupo(), "UTF-8") +
                "&programa=" + URLEncoder.encode("", "UTF-8") +
                "&tab=" + URLEncoder.encode("2", "UTF-8");
        return "redirect:/detalleProgramaAnalitico" + params;
    }

    @GetMapping(value = "/programaanaanalitico/contenidos/eliminar")
    public String eliminar(@ModelAttribute("model") EliminarContenidoRequest model) {
        Contenidos contenido = new Contenidos();
        contenido.setId_prg_a_contenido(model.getId_prg_a_contenido());
        contenido.setUlt_usuario(getUsuario().getId_usuario());
        mi.eliminarObjetivo_Instructivo(contenido);
        String params = "?id_dct_programa_analitico=" + model.getId_dct_programa_analitico();
        return "redirect:/programaanaanalitico/formasorganizacion/listarDistribucionTiempos" + params;
    }

}
