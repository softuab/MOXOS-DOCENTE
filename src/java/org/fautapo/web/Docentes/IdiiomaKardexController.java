/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.web.Docentes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.PersonaIdioma;
import org.fautapo.domain.PersonaKardex;
import org.fautapo.domain.logic.MiFacade;
import org.fautapo.model.Kardex.DetalleIdiomaPersonaModel;
import org.fautapo.model.Kardex.ItemAprobarModel;
import org.fautapo.model.Kardex.ItemEliminarModel;
import org.fautapo.model.Kardex.MessageAprobadoResult;
import org.fautapo.model.Kardex.MessageDeleteResult;
import org.fautapo.model.Kardex.ParametroCreacionModel;
import org.fautapo.model.Kardex.PersonaIdiomaModel;
import org.fautapo.model.MessageResult;
import static org.fautapo.util.Directorio.DIRECTORIO_IDIOMAS;
import org.fautapo.util.FileUploadServlet;
import org.fautapo.util.ListViewItem;
import org.fautapo.util.Util;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
public class IdiiomaKardexController {

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

    @RequestMapping(value = "/ListaParcialIdiomaLenguas.fautapo", method = RequestMethod.GET)
    public String ListaParcialIdiomaLenguas(Model modelo) throws Exception {
        Clientes cliente = this.GetUsuario();
        int number = cliente.getInt(request, "number");
        int id_persona_kardex = cliente.getInt(request, "id_persona_kardex");
        PersonaKardex kardex = new PersonaKardex();
        kardex.setNumber(number);
        kardex.setId_persona(id_persona_kardex);
        List<DetalleIdiomaPersonaModel> detalleidioma = mi.GetPersonaSubTotalIdiomaKardex(kardex);
        int sId_rol = cliente.getId_rol();
        modelo.addAttribute("id_rol", sId_rol);
        modelo.addAttribute("nombres", cliente.getNombres());
        modelo.addAttribute("idiomas", detalleidioma);
        return "AdministrarKardexPersonal/DetalleIdiomaLenguas";
    }

    @RequestMapping(value = "/ListaIdiomaLenguas.fautapo", method = RequestMethod.GET)
    public String ListaIdiomaLenguas(Model modelo) {
        Clientes cliente = this.GetUsuario();
        int id_persona_kardex = cliente.getInt(request, "id_persona_kardex");
        int id_persona = cliente.getInt(request, "id_persona");
        String status = cliente.getString(request, "status");
        String message = cliente.getString(request, "message");
        List<DetalleIdiomaPersonaModel> detalleidioma = mi.GetPersonaTotalIdiomaKardex(id_persona_kardex);
        int sId_rol = cliente.getId_rol();
        MessageResult result = new MessageResult();
        result.setMessage(message);
        result.setStatus(status);
        modelo.addAttribute("id_rol", sId_rol);
        modelo.addAttribute("nombres", cliente.getNombres());
        modelo.addAttribute("idiomas", detalleidioma);
        modelo.addAttribute("id_persona", id_persona);
        modelo.addAttribute("result", result);
        modelo.addAttribute("id_persona_kardex", id_persona_kardex);
        return "AdministrarKardexPersonal/IdiomaLenguas/DetalleIdiomaKardex";
    }

    @RequestMapping(value = "/FormularioRegistroIdiomaKardex.fautapo", method = RequestMethod.GET)
    public String FormularioRegistroIdiomaKardex(@ModelAttribute("parametro") ParametroCreacionModel parametro, Model modelo) {
        List<ListViewItem> tipo_idiomas = new ArrayList<>();
        tipo_idiomas.add(new ListViewItem("NINGUNO", ""));
        tipo_idiomas.add(new ListViewItem("EXTRANJERA", "EXTRANJERA"));
        tipo_idiomas.add(new ListViewItem("ORIGINARIA", "ORIGINARIA"));
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString().replace("-", "").toUpperCase();
        PersonaIdiomaModel model = null;
        if (parametro.getId_idioma() == null) {
            model = new PersonaIdiomaModel();
            model.setId_persona(parametro.getId_persona());
            model.setId_persona_kardex(parametro.getId_persona_kardex());
            model.setUUID(randomUUIDString);
            model.setRoot(parametro.getRoot());
            modelo.addAttribute("tipo_idiomas", tipo_idiomas);
            modelo.addAttribute("id_rol", this.GetUsuario().getId_rol());
            modelo.addAttribute("nombres", this.GetUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "AdministrarKardexPersonal/IdiomaLenguas/NuevoIdiomaKardex";
        } else {
            PersonaIdioma idioma = mi.GetPersonaIdiomaKardex(parametro.getId_idioma());
            model = modelMapper.map(idioma, PersonaIdiomaModel.class);
            model.setId_persona(parametro.getId_persona());
            model.setUUID(randomUUIDString);
            modelo.addAttribute("id_rol", this.GetUsuario().getId_rol());
            modelo.addAttribute("nombres", this.GetUsuario().getNombres());
            modelo.addAttribute("tipo_idiomas", tipo_idiomas);
            modelo.addAttribute("model", model);
            return "AdministrarKardexPersonal/IdiomaLenguas/EditarIdiomaKardex";
        }
    }

    @RequestMapping(value = "/RegistrarPersonaIdiomaKardex.fautapo", method = RequestMethod.POST)
    public String RegistrarPersonaIdiomaKardex(@ModelAttribute("model") @Validated PersonaIdiomaModel model, BindingResult result, Model modelo) {
        if (model.getUrl_idioma().equals("image.png")) {
            if (model.getImage() == null || model.getImage().isEmpty()) {
                result.addError(new FieldError("url_idioma", "url_idioma", "Debe cargar la image de su fotocopia del certificado en idiomas"));
            }
        }
        model.setLee(model.getText_lee() != null ? model.getText_lee().equals("on") ? true : false : false);
        model.setEscribe(model.getText_escribe() != null ? model.getText_escribe().equals("on") ? true : false : false);
        model.setMostrar(model.getText_mostrar() != null ? model.getText_mostrar().equals("on") ? true : false : false);
        if (result.hasErrors()) {
            List<ListViewItem> tipo_idiomas = new ArrayList<>();
            tipo_idiomas.add(new ListViewItem("NINGUNO", ""));
            tipo_idiomas.add(new ListViewItem("EXTRANJERA", "EXTRANJERA"));
            tipo_idiomas.add(new ListViewItem("ORIGINARIA", "ORIGINARIA"));
            modelo.addAttribute("tipo_idiomas", tipo_idiomas);
            modelo.addAttribute("id_rol", this.GetUsuario().getId_rol());
            modelo.addAttribute("nombres", this.GetUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "AdministrarKardexPersonal/IdiomaLenguas/NuevoIdiomaKardex";
        }
        PersonaIdioma idioma = modelMapper.map(model, PersonaIdioma.class);
        int id_idioma = mi.RegistrarNuevoIdiomaKardex(idioma);
        String filename = String.format("%010d", model.getId_persona()) + String.format("%05d", id_idioma);
        FileUploadServlet.MultipartRequest(request, model.getImage(), DIRECTORIO_IDIOMAS.key(), "url_idioma", filename);
        String nombrearchivo = Util.EsImagenModificado(idioma.getUrl_idioma(), request.getAttribute("url_idioma").toString());
        if (!nombrearchivo.equals("image.png")) {
            idioma.setId_idioma(id_idioma);
            idioma.setUrl_idioma(nombrearchivo);
            mi.ActualizarImagenIdiomaKardexDocente(idioma);
        }
        if (model.getRoot().equals("total")) {
            String request = "?id_persona_kardex=" + model.getId_persona_kardex() + "&" + "id_persona=" + model.getId_persona() + "&" + "status=" + Util.encodeValue("alert-success") + "&" + "message=" + Util.encodeValue("Se grabo correctamente el registro del documento");
            return "redirect:/ListaIdiomaLenguas.fautapo" + request;
        }
        return "redirect:/VerificarYObtenerKardex.fautapo";
    }

    @RequestMapping(value = "/RegistrarModificarIdiomaLenguas.fautapo", method = RequestMethod.POST)
    public String RegistrarModificarIdiomaLenguas(@ModelAttribute("model") @Validated PersonaIdiomaModel model, BindingResult result, Model modelo) {
        model.setLee(model.getText_lee() != null ? model.getText_lee().equals("on") ? true : false : false);
        model.setEscribe(model.getText_escribe() != null ? model.getText_escribe().equals("on") ? true : false : false);
        model.setMostrar(model.getText_mostrar() != null ? model.getText_mostrar().equals("on") ? true : false : false);
        if (result.hasErrors()) {
            List<ListViewItem> tipo_idiomas = new ArrayList<>();
            tipo_idiomas.add(new ListViewItem("NINGUNO", ""));
            tipo_idiomas.add(new ListViewItem("EXTRANJERA", "EXTRANJERA"));
            tipo_idiomas.add(new ListViewItem("ORIGINARIA", "ORIGINARIA"));
            modelo.addAttribute("tipo_idiomas", tipo_idiomas);
            modelo.addAttribute("id_rol", this.GetUsuario().getId_rol());
            modelo.addAttribute("nombres", this.GetUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "AdministrarKardexPersonal/IdiomaLenguas/EditarIdiomaKardex";
        }
        PersonaIdioma idioma = modelMapper.map(model, PersonaIdioma.class);
        String filename = String.format("%010d", model.getId_persona()) + String.format("%05d", idioma.getId_idioma());
        FileUploadServlet.MultipartRequest(request, model.getImage(), DIRECTORIO_IDIOMAS.key(), "url_idioma", filename);
        String nombrearchivo = Util.EsImagenModificado(idioma.getUrl_idioma(), request.getAttribute("url_idioma").toString());
        if (!nombrearchivo.equals("image.png")) {
            idioma.setUrl_idioma(nombrearchivo);
        }
        mi.ActualizarDatosiIdiomaKardexDocente(idioma);
        String request = "?id_persona_kardex=" + model.getId_persona_kardex() + "&" + "id_persona=" + model.getId_persona() + "&" + "status=" + Util.encodeValue("alert-success") + "&" + "message=" + Util.encodeValue("Se Actualizo correctamente el registro del documento");
        return "redirect:/ListaIdiomaLenguas.fautapo" + request;
    }

    @RequestMapping(value = "/EliminarIdiomaLenguas.fautapo", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageDeleteResult> EliminarIdiomaLenguas(@ModelAttribute("model") ItemEliminarModel model) {
        Clientes cliente = this.GetUsuario();
        MessageDeleteResult Respuesta = new MessageDeleteResult();
        if (cliente == null) {
            Respuesta.setMessage("Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            Respuesta.setStatus("Error");
            return new ResponseEntity<>(Respuesta, HttpStatus.OK);
        } else {
            try {
                mi.EliminarIdiomaKardexDocente(model.getId_idioma());
                Respuesta.setMessage("");
                Respuesta.setStatus("OK");
                Respuesta.setId(model.getName());
            } catch (Exception exception) {
                Respuesta.setMessage("Ocurrio un error al grabar los datos verifique su conexion.. " + exception.getMessage());
                Respuesta.setStatus("Error");
            }
            return new ResponseEntity<>(Respuesta, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/ModificarEstadoAprobacionDocumentoIdioma.fautapo", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageAprobadoResult> ModificarEstadoAprobacionDocumentoIdioma(@ModelAttribute("model") ItemAprobarModel model) {
        Clientes cliente = this.GetUsuario();
        MessageAprobadoResult Respuesta = new MessageAprobadoResult();
        if (cliente == null) {
            Respuesta.setMessage("Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            Respuesta.setStatus("Error");
            return new ResponseEntity<>(Respuesta, HttpStatus.OK);
        } else {
            try {
                Boolean estado = model.getName() != null ? model.getName().equals("on") ? true : false : false;
                PersonaIdioma idioma = new PersonaIdioma();
                idioma.setId_idioma(model.getId_idioma());
                idioma.setAprobado(estado);
                mi.AprobarIdiomaKardexDocente(idioma);
                Respuesta.setMessage("");
                Respuesta.setStatus("OK");
                Respuesta.setId(model.getName());
            } catch (Exception exception) {
                Respuesta.setMessage("Ocurrio un error al grabar los datos verifique su conexion.. " + exception.getMessage());
                Respuesta.setStatus("Error");
            }
            return new ResponseEntity<>(Respuesta, HttpStatus.OK);
        }
    }

}
