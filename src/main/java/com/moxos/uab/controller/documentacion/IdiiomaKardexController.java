package com.moxos.uab.controller.documentacion;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

import com.moxos.uab.model.jsonmodels.MessageResult;
import com.moxos.uab.model.models.Kardex.*;
import com.moxos.uab.mybatis.entity.*;
import com.moxos.uab.mybatis.logic.MiFacade;
import com.moxos.uab.util.Convert;
import com.moxos.uab.util.FileUploadServlet;
import com.moxos.uab.util.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import static com.moxos.uab.util.Directorio.DIRECTORIO_IDIOMAS;

@Controller
public class IdiiomaKardexController {

    @Autowired
    private MiFacade mi;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ModelMapper modelMapper;
    @Value("${app.upload.path}")
    private String path;

    private Clientes getUsuario() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        Clientes cliente = (Clientes) attr.getRequest().getSession().getAttribute("__sess_cliente");
        return cliente;
    }

    @RequestMapping(value = "/ListaParcialIdiomaLenguas", method = RequestMethod.GET)
    public String ListaParcialIdiomaLenguas(Model modelo) throws Exception {
        Clientes cliente = this.getUsuario();
        int number = Convert.ToInteger(request, "number");
        int id_persona_kardex = Convert.ToInteger(request, "id_persona_kardex");
        PersonaKardex kardex = new PersonaKardex();
        kardex.setNumber(number);
        kardex.setId_persona(id_persona_kardex);
        List<DetalleIdiomaPersonal> detalleidioma = mi.getPersonaSubTotalIdiomaKardex(kardex);
        int sId_rol = cliente.getId_rol();
        modelo.addAttribute("id_rol", sId_rol);
        modelo.addAttribute("nombres", cliente.getNombres());
        modelo.addAttribute("idiomas", detalleidioma);
        return "Kardex/DetalleIdiomaLenguas";
    }

    @RequestMapping(value = "/ListaIdiomaLenguas", method = RequestMethod.GET)
    public String ListaIdiomaLenguas(Model modelo) {
        Clientes cliente = this.getUsuario();
        int id_persona_kardex = Convert.ToInteger(request, "id_persona_kardex");
        int id_persona = Convert.ToInteger(request, "id_persona");
        String status = Convert.ToString(request, "status");
        String message = Convert.ToString(request, "message");
        List<DetalleIdiomaPersonal> detalleidioma = mi.getPersonaTotalIdiomaKardex(id_persona_kardex);
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
        return "Kardex/IdiomaLenguas/DetalleIdiomaKardex";
    }

    @RequestMapping(value = "/FormularioRegistroIdiomaKardex", method = RequestMethod.GET)
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
            modelo.addAttribute("id_rol", this.getUsuario().getId_rol());
            modelo.addAttribute("nombres", this.getUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "Kardex/IdiomaLenguas/NuevoIdiomaKardex";
        } else {
            PersonaIdioma idioma = mi.getPersonaIdiomaKardex(parametro.getId_idioma());
            model = modelMapper.map(idioma, PersonaIdiomaModel.class);
            model.setId_persona(parametro.getId_persona());
            model.setUUID(randomUUIDString);
            modelo.addAttribute("id_rol", this.getUsuario().getId_rol());
            modelo.addAttribute("nombres", this.getUsuario().getNombres());
            modelo.addAttribute("tipo_idiomas", tipo_idiomas);
            modelo.addAttribute("model", model);
            return "Kardex/IdiomaLenguas/EditarIdiomaKardex";
        }
    }

    @RequestMapping(value = "/RegistrarPersonaIdiomaKardex", method = RequestMethod.POST)
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
            modelo.addAttribute("id_rol", this.getUsuario().getId_rol());
            modelo.addAttribute("nombres", this.getUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "Kardex/IdiomaLenguas/NuevoIdiomaKardex";
        }
        PersonaIdioma idioma = modelMapper.map(model, PersonaIdioma.class);
        int id_idioma = mi.registrarNuevoIdiomaKardex(idioma);
        String filename = String.format("%010d", model.getId_persona()) + String.format("%05d", id_idioma);
        FileUploadServlet.MultipartRequest(request, model.getImage(), path + DIRECTORIO_IDIOMAS.key(), "url_idioma", filename);
        String nombrearchivo = Utils.EsImagenModificado(idioma.getUrl_idioma(), request.getAttribute("url_idioma").toString());
        if (!nombrearchivo.equals("image.png")) {
            idioma.setId_idioma(id_idioma);
            idioma.setUrl_idioma(nombrearchivo);
            mi.actualizarImagenIdiomaKardexDocente(idioma);
        }
        String request = "?id_persona_kardex=" + model.getId_persona_kardex() + "&" + "id_persona=" + model.getId_persona() + "&" + "status=" + Utils.encodeValue("alert-success") + "&" + "message=" + Utils.encodeValue("Se Actualizo correctamente el registro del documento");
        return "redirect:/ListaIdiomaLenguas" + request;
    }

    @RequestMapping(value = "/RegistrarModificarIdiomaLenguas", method = RequestMethod.POST)
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
            modelo.addAttribute("id_rol", this.getUsuario().getId_rol());
            modelo.addAttribute("nombres", this.getUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "Kardex/IdiomaLenguas/EditarIdiomaKardex";
        }
        PersonaIdioma idioma = modelMapper.map(model, PersonaIdioma.class);
        String filename = String.format("%010d", model.getId_persona()) + String.format("%05d", idioma.getId_idioma());
        FileUploadServlet.MultipartRequest(request, model.getImage(), path + DIRECTORIO_IDIOMAS.key(), "url_idioma", filename);
        String nombrearchivo = Utils.EsImagenModificado(idioma.getUrl_idioma(), request.getAttribute("url_idioma").toString());
        if (!nombrearchivo.equals("image.png")) {
            idioma.setUrl_idioma(nombrearchivo);
        }
        mi.actualizarDatosiIdiomaKardexDocente(idioma);
        String request = "?id_persona_kardex=" + model.getId_persona_kardex() + "&" + "id_persona=" + model.getId_persona() + "&" + "status=" + Utils.encodeValue("alert-success") + "&" + "message=" + Utils.encodeValue("Se Actualizo correctamente el registro del documento");
        return "redirect:/ListaIdiomaLenguas" + request;
    }

    @RequestMapping(value = "/EliminarIdiomaLenguas", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageDeleteResult> EliminarIdiomaLenguas(@ModelAttribute("model") ItemEliminarModel model) {
        Clientes cliente = this.getUsuario();
        MessageDeleteResult Respuesta = new MessageDeleteResult();
        if (cliente == null) {
            Respuesta.setMessage("Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            Respuesta.setStatus("Error");
            return new ResponseEntity<>(Respuesta, HttpStatus.OK);
        } else {
            try {
                mi.eliminarIdiomaKardexDocente(model.getId_idioma());
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

    @RequestMapping(value = "/ModificarEstadoAprobacionDocumentoIdioma", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageAprobadoResult> ModificarEstadoAprobacionDocumentoIdioma(@ModelAttribute("model") ItemAprobarModel model) {
        Clientes cliente = this.getUsuario();
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
                mi.aprobarIdiomaKardexDocente(idioma);
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
