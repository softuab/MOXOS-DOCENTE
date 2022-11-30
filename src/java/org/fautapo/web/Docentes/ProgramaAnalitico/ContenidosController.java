/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.web.Docentes.ProgramaAnalitico;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Contenidos;
import org.fautapo.domain.logic.MiFacade;
import org.fautapo.model.MessageResult;
import org.fautapo.model.programaanalitico.ContenidosModel;
import org.fautapo.model.programaanalitico.MessageContenidoResult;
import org.fautapo.util.ListViewItem;
import org.jsoup.Jsoup;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
public class ContenidosController {

    @Autowired
    private MiFacade mi;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ModelMapper modelMapper;

    private Clientes GetUsuario() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        Clientes cliente = (Clientes) attr.getRequest().getSession().getAttribute("__sess_cliente");
        return cliente;
    }

    @RequestMapping(value = "/ListarContenidos.fautapo", method = RequestMethod.POST)
    public String ListarContenidos(@ModelAttribute("id_dct_programa_analitico") Integer id_dct_programa_analitico, Model modelo) {
        Contenidos _contenido = new Contenidos();
        _contenido.setId_dct_programa_analitico(id_dct_programa_analitico);
        List<Contenidos> contenidos = null;
        contenidos = mi.GetListarContenido(_contenido);
        String estado = "0";
        if (!contenidos.isEmpty()) {
            estado = "1";
        }
        modelo.addAttribute("contenidos", contenidos);
        modelo.addAttribute("estado", estado);
        modelo.addAttribute("id_dct_programa_analitico", id_dct_programa_analitico);
        return "AdministrarProgramasAnaliticos/Detallecontenidos";
    }

    @RequestMapping(value = "/RegistrarContenidos.fautapo", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageResult> RegistrarContenidos(@ModelAttribute("model") ContenidosModel model) {
        MessageResult result = new MessageResult();
        Contenidos contenidos = modelMapper.map(model, Contenidos.class);
        contenidos.setUlt_usuario(GetUsuario().getId_usuario());
        String Respuesta = "";
        Integer id_prg_a_contenido = null;
        try {
            id_prg_a_contenido = mi.RegistrarObjetivo_Instructivo(contenidos);
        } catch (Exception ex) {
            result.setMessage("Ocurrio un error :" + ex.getMessage());
            result.setStatus("Error");
        }
        if (id_prg_a_contenido == null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            if (id_prg_a_contenido != 0) {
                contenidos.setId_prg_a_contenido(id_prg_a_contenido);
                contenidos = mi.GetContenido(contenidos).stream().findFirst().get();
                String boton = "";
                if (contenidos.getMapa().equals("images.png")) {
                    boton = "<div class=\"btn-group\">\n"
                            + "<button type=\"button\" class=\"btn btn-outline-light dropdown-toggle\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">\n"
                            + "<img id=\"imagecontenido" + contenidos.getId_prg_a_contenido() + "\" src=\"./imagenes/none.png\" width=\"48\" height=\"48\" class=\"align-self-start mr-3 rounded-circle\">\n"
                            + "</button>\n"
                            + "<div class=\"dropdown-menu\">\n"
                            + "<a class=\"dropdown-item\" onclick=\"modificarcontenido(this)\" data-id=\"" + contenidos.getId_prg_a_contenido() + "\"  data-url=\"./UploadContenido.fautapo\" href=\"#\">Modificar mapa</a>\n"
                            + "<div class=\"dropdown-divider\"></div>\n"
                            + "<a class=\"dropdown-item\" onclick=\"vercontenido(this)\" data-id=\"" + contenidos.getId_prg_a_contenido() + "\" href=\"#\">Ver mapa de contenido</a>\n"
                            + "</div>\n"
                            + "</div>";
                } else {
                    boton = "                            <div class=\"btn-group\">\n"
                            + "                                <button type=\"button\" class=\"btn btn-outline-light dropdown-toggle\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">\n"
                            + "                                    <img id=\"imagecontenido" + contenidos.getId_prg_a_contenido() + "\" src=\"./imagenes/none.png\" width=\"48\" height=\"48\" class=\"align-self-start mr-3 rounded-circle\">\n"
                            + "                                </button>\n"
                            + "                                <div class=\"dropdown-menu\">\n"
                            + "                                    <a class=\"dropdown-item\" onclick=\"modificarcontenido(this)\" data-id=\"" + contenidos.getId_prg_a_contenido() + "\"  data-url=\"./UploadContenido.fautapo\" href=\"#\">Modificar mapa</a>\n"
                            + "                                    <div class=\"dropdown-divider\"></div>\n"
                            + "                                    <a class=\"dropdown-item\" onclick=\"vercontenido(this)\" data-id=\"" + contenidos.getId_prg_a_contenido() + "\" href=\"#\">Ver mapa de contenido</a>\n"
                            + "                                </div>\n"
                            + "                            </div>\n";
                }
                Respuesta = "<li id=\"li" + contenidos.getId_prg_a_contenido() + "\" class=\"media\">\n"
                        + boton
                        + "                        <div class=\"media-body\">\n"
                        + "                            <h5 class=\"mt-0 mb-1\" id=\"title" + contenidos.getId_prg_a_contenido() + "\">" + contenidos.getContenido() + "</h5>\n"
                        + "                            <p id=\"content" + contenidos.getId_prg_a_contenido() + "\">\n"
                        + "                                <b>Objetivo instuctivo:</b>" + contenidos.getObjetivo_instructivo() + "<br>\n"
                        + "                                <b>Conocimientos:</b>" + Jsoup.parse(contenidos.getConocimientos()) + "<br>\n"
                        + "                                <b>Habilidades:</b>" + Jsoup.parse(contenidos.getHabilidades()) + "<br>\n"
                        + "                                <b>Valores:</b>" + Jsoup.parse(contenidos.getValores()) + "<br>\n"
                        + "                            </p>\n"
                        + "                            <div class=\"text-right\">\n"
                        + "                                <button type=\"button\" class=\"btn btn-primary\" data-contenido=\"detail\" data-edit=\"" + contenidos.getId_prg_a_contenido() + "\">\n"
                        + "                                    <i class=\"fas fa-edit\"></i>\n"
                        + "                                </button>\n"
                        + "                                <button type=\"button\" class=\"btn btn-primary\" data-contenido=\"delete\" data-delete=\"" + contenidos.getId_prg_a_contenido() + "\">\n"
                        + "                                    <i class=\"fas fa-trash-alt\"></i>\n"
                        + "                                </button>\n"
                        + "                            </div>\n"
                        + "                        </div>\n"
                        + "                    </li>";
                result.setMessage(Respuesta);
                result.setStatus("OK");
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                result.setMessage("No se registro sin exito");
                result.setStatus("Error");
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        }
    }

    @RequestMapping(value = "/DetalleContenidos.fautapo", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Contenidos> DetalleContenidos(@ModelAttribute("id") Integer id) {
        Contenidos contenidos = new Contenidos();
        contenidos.setId_prg_a_contenido(id);
        contenidos = mi.GetContenido(contenidos).stream().findFirst().get();
        return new ResponseEntity<>(contenidos, HttpStatus.OK);
    }

    @RequestMapping(value = "/EditarContenidos.fautapo", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageContenidoResult> EditarContenidos(@ModelAttribute("model") ContenidosModel model) {
        MessageContenidoResult result = new MessageContenidoResult();
        Contenidos contenido = modelMapper.map(model, Contenidos.class);
        contenido.setUlt_usuario(GetUsuario().getId_usuario());
        String Respuesta = "";
        Integer id_prg_a_contenido = null;
        try {
            id_prg_a_contenido = mi.ActualizarObjetivo_Instructivo(contenido);
        } catch (Exception ex) {
            result.setMessage("Ocurrio un error :" + ex.getMessage());
            result.setStatus("Error");
        }
        if (id_prg_a_contenido == null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            if (id_prg_a_contenido != 0) {
                Respuesta = "<b>Objetivo instuctivo:</b>" + Jsoup.parse(contenido.getObjetivo_instructivo()) + "<br> <b>Conocimientos:</b>" + Jsoup.parse(contenido.getConocimientos()) + "<br> <b>Habilidades:</b>" + Jsoup.parse(contenido.getHabilidades()) + "<br><b>Valores:</b>" + Jsoup.parse(contenido.getValores()) + "<br>";
                result.setMessage(Respuesta);
                result.setId_prg_a_contenido(contenido.getId_prg_a_contenido());
                result.setStatus("OK");
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                result.setMessage("No se registro sin exito");
                result.setStatus("Error");
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        }
    }

    @RequestMapping(value = "/EliminarContenidos.fautapo", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageResult> EliminarContenidos(@ModelAttribute("id") Integer id) throws Exception {

        Contenidos contenido = new Contenidos();
        contenido.setId_prg_a_contenido(id);
        contenido.setUlt_usuario(GetUsuario().getId_usuario());
        MessageResult result = new MessageResult();
        Integer id_prg_a_contenido = null;
        try {
            id_prg_a_contenido = mi.EliminarObjetivo_Instructivo(contenido);
        } catch (Exception ex) {
            result.setMessage("Ocurrio un error :" + ex.getMessage());
            result.setStatus("Error");
        }
        if (id_prg_a_contenido == null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            if (id_prg_a_contenido != 0) {
                result.setMessage("Se elimino correctamente el registro");
                result.setStatus("OK");
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                result.setMessage("No se elimino sin exito");
                result.setStatus("Error");
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        }
    }
}
