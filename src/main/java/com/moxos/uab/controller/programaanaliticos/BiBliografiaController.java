package com.moxos.uab.controller.programaanaliticos;

import com.moxos.uab.model.jsonmodels.MessageResult;
import com.moxos.uab.model.models.programaanalitico.ParametroBusquedaProgramaAnaliticoRequest;
import com.moxos.uab.model.models.programaanalitico.bibliografia.*;
import com.moxos.uab.mybatis.entity.BiBliografia;
import com.moxos.uab.mybatis.entity.Clientes;
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
public class BiBliografiaController {
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

    @GetMapping(value = "/programaanaanalitico/listarreferenciasbibliograficas")
    public String listarreferenciasbibliograficas(@ModelAttribute("model") ParametroBusquedaProgramaAnaliticoRequest model, Model modelo) {
        BiBliografia bibliografia = new BiBliografia();
        bibliografia.setId_dct_programa_analitico(model.getId_dct_programa_analitico());
        List<BiBliografia> bibliografias = null;
        bibliografias = mi.getListarBibliografia(bibliografia);
        modelo.addAttribute("bibliografias", bibliografias);
        modelo.addAttribute("id_dct_programa_analitico", model.getId_dct_programa_analitico());
        return "ProgramasAnaliticos/Bibliografias/DetalleBibliografia";
    }

    @GetMapping(value = "/programaanaanalitico/bibliografias/agregar")
    public String agregar(@ModelAttribute("model") ParametroBusquedaProgramaAnaliticoRequest model, Model modelo) {
        List<ListViewItem> items = new ArrayList<>();
        items.add(new ListViewItem("1", "REFERENCIA BIBLIOGRAFICA DOCUMENTOS"));
        items.add(new ListViewItem("2", "REFERENCIA BIBLIOGRAFICA REVISTA"));
        items.add(new ListViewItem("3", "REFERENCIA BIBLIOGRAFICA ELECTRONICO"));
        List<ListViewItem> tipos = new ArrayList<>();
        tipos.add(new ListViewItem("Basica", "Basica"));
        tipos.add(new ListViewItem("Complementaria", "Complementaria"));
        ReferenciaBibliograficaGrupo group = new ReferenciaBibliograficaGrupo();
        group.setTipo_referncia(1);
        group.setItems(items);
        ReferenciaDocumentos bibliografia = new ReferenciaDocumentos();
        bibliografia.setTipos(tipos);
        bibliografia.setTipo_referncia(1);
        bibliografia.setId_dct_programa_analitico(model.getId_dct_programa_analitico());
        modelo.addAttribute("model", bibliografia);
        modelo.addAttribute("group", group);
        return "ProgramasAnaliticos/Bibliografias/Agregar";
    }

    @GetMapping(value = "/programaanaanalitico/bibliografias/agregartipo")
    public String agregartipo(@ModelAttribute("model") BibliografiaTipoReferenciaRequest model, Model modelo) {
        String path = "";
        List<ListViewItem> tipos = new ArrayList<>();
        tipos.add(new ListViewItem("Basica", "Basica"));
        tipos.add(new ListViewItem("Complementaria", "Complementaria"));
        if (model.getTipo_referncia() == 1) {
            ReferenciaDocumentos bibliografia = new ReferenciaDocumentos();
            bibliografia.setTipos(tipos);
            bibliografia.setTipo_referncia(model.getTipo_referncia());
            bibliografia.setId_dct_programa_analitico(model.getId_dct_programa_analitico());
            modelo.addAttribute("model", bibliografia);
            path = "ProgramasAnaliticos/Bibliografias/_AgregarDocumento";
        }
        if (model.getTipo_referncia() == 2) {
            ReferenciaRevista bibliografia = new ReferenciaRevista();
            bibliografia.setTipos(tipos);
            bibliografia.setTipo_referncia(model.getTipo_referncia());
            bibliografia.setId_dct_programa_analitico(model.getId_dct_programa_analitico());
            modelo.addAttribute("model", bibliografia);
            path = "ProgramasAnaliticos/Bibliografias/_AgregarRevista";
        }
        if (model.getTipo_referncia() == 3) {
            ReferenciaElectronica bibliografia = new ReferenciaElectronica();
            bibliografia.setTipos(tipos);
            bibliografia.setTipo_referncia(model.getTipo_referncia());
            bibliografia.setId_dct_programa_analitico(model.getId_dct_programa_analitico());
            modelo.addAttribute("model", bibliografia);
            path = "ProgramasAnaliticos/Bibliografias/_AgregarElectronico";
        }
        return path;
    }

    @PostMapping(value = "/programaanaanalitico/bibliografias/agregardocumento")
    public String agregardocumento(@ModelAttribute("model") @Validated ReferenciaDocumentos model, BindingResult result, Model modelo) {
        if (result.hasErrors()) {
            List<ListViewItem> tipos = new ArrayList<>();
            tipos.add(new ListViewItem("Basica", "Basica"));
            tipos.add(new ListViewItem("Complementaria", "Complementaria"));
            model.setTipos(tipos);
            modelo.addAttribute("model", model);
            return "ProgramasAnaliticos/Bibliografias/_AgregarDocumento";
        }
        BiBliografia bibliografia = modelMapper.map(model, BiBliografia.class);
        bibliografia.setFecha_consulta(new Date());
        bibliografia.setFecha_publicacion(new Date());
        mi.registrarBibliografia(bibliografia);
        return "redirect:/programaanaanalitico/listarreferenciasbibliograficas?id_dct_programa_analitico=" + model.getId_dct_programa_analitico();
    }

    @PostMapping(value = "/programaanaanalitico/bibliografias/agregarrevista")
    public String agregarrevista(@ModelAttribute("model") @Validated ReferenciaRevista model, BindingResult result, Model modelo) {
        if (result.hasErrors()) {
            List<ListViewItem> tipos = new ArrayList<>();
            tipos.add(new ListViewItem("Basica", "Basica"));
            tipos.add(new ListViewItem("Complementaria", "Complementaria"));
            model.setTipos(tipos);
            modelo.addAttribute("model", model);
            return "ProgramasAnaliticos/Bibliografias/_AgregarRevista";
        }
        model.setFecha_publicacion(Convert.ToDate(model.getText_fecha_publicacion(),"yyyy-MM-dd"));
        BiBliografia bibliografia = modelMapper.map(model, BiBliografia.class);
        bibliografia.setFecha_consulta(new Date());
        mi.registrarBibliografia(bibliografia);
        return "redirect:/programaanaanalitico/listarreferenciasbibliograficas?id_dct_programa_analitico=" + model.getId_dct_programa_analitico();
    }

    @PostMapping(value = "/programaanaanalitico/bibliografias/agregarelectronico")
    public String agregarelectronico(@ModelAttribute("model") @Validated ReferenciaElectronica model, BindingResult result, Model modelo) {
        if (result.hasErrors()) {
            List<ListViewItem> tipos = new ArrayList<>();
            tipos.add(new ListViewItem("Basica", "Basica"));
            tipos.add(new ListViewItem("Complementaria", "Complementaria"));
            model.setTipos(tipos);
            modelo.addAttribute("model", model);
            return "ProgramasAnaliticos/Bibliografias/_AgregarElectronico";
        }
        model.setFecha_consulta(Convert.ToDate(model.getText_fecha_consulta(),"yyyy-MM-dd"));
        model.setFecha_publicacion(Convert.ToDate(model.getText_fecha_publicacion(),"yyyy-MM-dd"));
        BiBliografia bibliografia = modelMapper.map(model, BiBliografia.class);
        mi.registrarBibliografia(bibliografia);
        return "redirect:/programaanaanalitico/listarreferenciasbibliograficas?id_dct_programa_analitico=" + model.getId_dct_programa_analitico();
    }

    @GetMapping(value = "/programaanaanalitico/bibliografias/editar")
    public String editar(@ModelAttribute("model") ParametroBusquedaBibliografia model, Model modelo) {
        BiBliografia bibliografia = mi.getBibliografia(model.getId_prg_a_bibliografia());
        List<ListViewItem> items = new ArrayList<>();
        items.add(new ListViewItem("1", "REFERENCIA BIBLIOGRAFICA DOCUMENTOS"));
        items.add(new ListViewItem("2", "REFERENCIA BIBLIOGRAFICA REVISTA"));
        items.add(new ListViewItem("3", "REFERENCIA BIBLIOGRAFICA ELECTRONICO"));
        ReferenciaBibliograficaGrupo group = new ReferenciaBibliograficaGrupo();
        group.setTipo_referncia(bibliografia.getTipo_referncia());
        group.setItems(items);
        if (bibliografia.getTipo_referncia() == 1) {
            ReferenciaDocumentos documentos = modelMapper.map(bibliografia, ReferenciaDocumentos.class);
            List<ListViewItem> tipos = new ArrayList<>();
            tipos.add(new ListViewItem("Basica", "Basica"));
            tipos.add(new ListViewItem("Complementaria", "Complementaria"));
            documentos.setTipos(tipos);
            modelo.addAttribute("model", documentos);
        }
        if (bibliografia.getTipo_referncia() == 2) {
            ReferenciaRevista revista = modelMapper.map(bibliografia, ReferenciaRevista.class);
            List<ListViewItem> tipos = new ArrayList<>();
            tipos.add(new ListViewItem("Basica", "Basica"));
            tipos.add(new ListViewItem("Complementaria", "Complementaria"));
            revista.setTipos(tipos);
            modelo.addAttribute("model", revista);
        }
        if (bibliografia.getTipo_referncia() == 3) {
            ReferenciaElectronica electronica = modelMapper.map(bibliografia, ReferenciaElectronica.class);
            List<ListViewItem> tipos = new ArrayList<>();
            tipos.add(new ListViewItem("Basica", "Basica"));
            tipos.add(new ListViewItem("Complementaria", "Complementaria"));
            electronica.setTipos(tipos);
            modelo.addAttribute("model", electronica);
        }
        modelo.addAttribute("group", group);
        return "ProgramasAnaliticos/Bibliografias/Editar";
    }

    @GetMapping(value = "/programaanaanalitico/bibliografias/agregartipoeditar")
    public String agregartipoeditar(@ModelAttribute("model") BibliografiaTipoReferenciaRequest model, Model modelo) {
        BiBliografia bibliografia = mi.getBibliografia(model.getId_prg_a_bibliografia());
        String path = "";
        List<ListViewItem> tipos = new ArrayList<>();
        tipos.add(new ListViewItem("Basica", "Basica"));
        tipos.add(new ListViewItem("Complementaria", "Complementaria"));
        if (model.getTipo_referncia() == 1) {
            ReferenciaDocumentos documentos = modelMapper.map(bibliografia, ReferenciaDocumentos.class);
            documentos.setTipo_referncia(model.getTipo_referncia());
            documentos.setTipos(tipos);
            modelo.addAttribute("model", documentos);
            path = "ProgramasAnaliticos/Bibliografias/_EditarDocumento";
        }
        if (model.getTipo_referncia() == 2) {
            ReferenciaRevista revista = modelMapper.map(bibliografia, ReferenciaRevista.class);
            revista.setTipo_referncia(model.getTipo_referncia());
            revista.setTipos(tipos);
            modelo.addAttribute("model", revista);
            path = "ProgramasAnaliticos/Bibliografias/_EditarRevista";
        }
        if (model.getTipo_referncia() == 3) {
            ReferenciaElectronica electronica = modelMapper.map(bibliografia, ReferenciaElectronica.class);
            electronica.setTipo_referncia(model.getTipo_referncia());
            electronica.setTipos(tipos);
            modelo.addAttribute("model", electronica);
            path = "ProgramasAnaliticos/Bibliografias/_EditarElectronico";
        }
        return path;
    }

    @PostMapping(value = "/programaanaanalitico/bibliografias/editardocumento")
    public String editardocumento(@ModelAttribute("model") @Validated ReferenciaDocumentos model, BindingResult result, Model modelo) {
        if (result.hasErrors()) {
            List<ListViewItem> tipos = new ArrayList<>();
            tipos.add(new ListViewItem("Basica", "Basica"));
            tipos.add(new ListViewItem("Complementaria", "Complementaria"));
            model.setTipos(tipos);
            modelo.addAttribute("model", model);
            return "ProgramasAnaliticos/Bibliografias/_EditarDocumento";
        }
        BiBliografia bibliografia = modelMapper.map(model, BiBliografia.class);
        bibliografia.setFecha_consulta(new Date());
        bibliografia.setFecha_publicacion(new Date());
        mi.actualizarBibliografia(bibliografia);
        return "redirect:/programaanaanalitico/listarreferenciasbibliograficas?id_dct_programa_analitico=" + model.getId_dct_programa_analitico();
    }

    @PostMapping(value = "/programaanaanalitico/bibliografias/editarrevista")
    public String editarrevista(@ModelAttribute("model") @Validated ReferenciaRevista model, BindingResult result, Model modelo) {
        if (result.hasErrors()) {
            List<ListViewItem> tipos = new ArrayList<>();
            tipos.add(new ListViewItem("Basica", "Basica"));
            tipos.add(new ListViewItem("Complementaria", "Complementaria"));
            model.setTipos(tipos);
            model.setFecha_publicacion(Convert.ToDate(model.getText_fecha_publicacion(),"yyyy-MM-dd"));
            modelo.addAttribute("model", model);
            return "ProgramasAnaliticos/Bibliografias/_EditarRevista";
        }
        model.setFecha_publicacion(Convert.ToDate(model.getText_fecha_publicacion(),"yyyy-MM-dd"));
        BiBliografia bibliografia = modelMapper.map(model, BiBliografia.class);
        bibliografia.setFecha_consulta(new Date());
        mi.actualizarBibliografia(bibliografia);
        return "redirect:/programaanaanalitico/listarreferenciasbibliograficas?id_dct_programa_analitico=" + model.getId_dct_programa_analitico();
    }

    @PostMapping(value = "/programaanaanalitico/bibliografias/editarelectronico")
    public String editarelectronico(@ModelAttribute("model") @Validated ReferenciaElectronica model, BindingResult result, Model modelo) {
        if (result.hasErrors()) {
            List<ListViewItem> tipos = new ArrayList<>();
            tipos.add(new ListViewItem("Basica", "Basica"));
            tipos.add(new ListViewItem("Complementaria", "Complementaria"));
            model.setTipos(tipos);
            model.setFecha_consulta(Convert.ToDate(model.getText_fecha_consulta(),"yyyy-MM-dd"));
            model.setFecha_publicacion(Convert.ToDate(model.getText_fecha_publicacion(),"yyyy-MM-dd"));
            modelo.addAttribute("model", model);
            return "ProgramasAnaliticos/Bibliografias/_EditarElectronico";
        }
        model.setFecha_publicacion(Convert.ToDate(model.getText_fecha_publicacion(),"yyyy-MM-dd"));
        model.setFecha_consulta(Convert.ToDate(model.getText_fecha_consulta(),"yyyy-MM-dd"));
        BiBliografia bibliografia = modelMapper.map(model, BiBliografia.class);
        mi.actualizarBibliografia(bibliografia);
        return "redirect:/programaanaanalitico/listarreferenciasbibliograficas?id_dct_programa_analitico=" + model.getId_dct_programa_analitico();
    }

    @RequestMapping(value = "/programaanaanalitico/bibliografias/eliminar", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public MessageResult eliminar(@RequestBody EliminarBibliografiaRequest model) {
        MessageResult Respuesta = new MessageResult();
        try {
            mi.eliminarBibliografia(model.getId_prg_a_bibliografia());
            Respuesta.setMessage("Se elimino los estudiantes con exito..");
            Respuesta.setStatus("OK");
        } catch (Exception ex) {
            Respuesta.setMessage("Problemas al eliminar:" + ex.getMessage());
            Respuesta.setStatus("Error");
        }
        return Respuesta;
    }
}
