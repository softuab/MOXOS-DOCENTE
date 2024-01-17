package com.moxos.uab.controller.documentacion;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

import com.moxos.uab.model.jsonmodels.MessageResult;
import com.moxos.uab.model.models.Kardex.*;
import com.moxos.uab.mybatis.entity.Clientes;
import com.moxos.uab.mybatis.entity.ListViewItem;
import com.moxos.uab.mybatis.entity.PersonaEvaluacionDocente;
import com.moxos.uab.mybatis.entity.PersonaKardex;
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

import static com.moxos.uab.util.Directorio.DIRECTORIO_EVALUACIONDOCENTE;

@Controller
public class EvaluacionKardexController {

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

    @RequestMapping(value = "/ListaParcialEvaluacionDocente", method = RequestMethod.GET)
    public String ListaParcialEvaluacionDocente(Model modelo) {
        Clientes cliente = this.getUsuario();
        int number = Convert.ToInteger(request, "number");
        int id_persona_kardex = Convert.ToInteger(request, "id_persona_kardex");
        PersonaKardex kardex = new PersonaKardex();
        kardex.setNumber(number);
        kardex.setId_persona_kardex(id_persona_kardex);
        List<PersonaEvaluacionDocente> detalleevaluacion = mi.getPersonaSubTotalEvaluacionDocenteKardex(kardex);
        int sId_rol = cliente.getId_rol();
        modelo.addAttribute("id_rol", sId_rol);
        modelo.addAttribute("nombres", cliente.getNombres());
        modelo.addAttribute("detalles", detalleevaluacion);
        return "Kardex/DetalleParcialEvaluacionDocente";
    }

    @RequestMapping(value = "/ListarEvaluacionDocente", method = RequestMethod.GET)
    public String ListarEvaluacionDocente(Model modelo) {
        Clientes cliente = this.getUsuario();
        int id_persona_kardex = Convert.ToInteger(request, "id_persona_kardex");
        int id_persona = Convert.ToInteger(request, "id_persona");
        String status = Convert.ToString(request, "status");
        String message = Convert.ToString(request, "message");
        List<PersonaEvaluacionDocente> detalleevaluacion = mi.getPersonaTotalEvaluacionDocenteKardex(id_persona_kardex);
        int sId_rol = cliente.getId_rol();
        MessageResult result = new MessageResult();
        result.setMessage(message);
        result.setStatus(status);
        modelo.addAttribute("id_rol", sId_rol);
        modelo.addAttribute("nombres", cliente.getNombres());
        modelo.addAttribute("detalles", detalleevaluacion);
        modelo.addAttribute("id_persona", id_persona);
        modelo.addAttribute("result", result);
        modelo.addAttribute("id_persona_kardex", id_persona_kardex);
        return "Kardex/EvaluacionDocente/DetalleEvaluacionDocenteKardex";
    }

    @RequestMapping(value = "/FormularioRegistroEvaluacionKardex", method = RequestMethod.GET)
    public String FormularioRegistroEvaluacionKardex(@ModelAttribute("parametro") ParametroCreacionModel parametro, Model modelo) {
        List<ListViewItem> periodo = new ArrayList<>();
        periodo.add(new ListViewItem("", "NINGUNO"));
        periodo.add(new ListViewItem("1", "1"));
        periodo.add(new ListViewItem("2", "2"));
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString().replace("-", "").toUpperCase();
        PersonaEvaluacionDocenteModel model = null;
        if (parametro.getId_evaluacion_docente() == null) {
            model = new PersonaEvaluacionDocenteModel();
            model.setId_persona(parametro.getId_persona());
            model.setId_persona_kardex(parametro.getId_persona_kardex());
            model.setUUID(randomUUIDString);
            model.setRoot(parametro.getRoot());
            modelo.addAttribute("detalleperiodo", periodo);
            modelo.addAttribute("id_rol", this.getUsuario().getId_rol());
            modelo.addAttribute("nombres", this.getUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "Kardex/EvaluacionDocente/NuevoEvaluacionDocenteKardex";
        } else {
            PersonaEvaluacionDocente evaluaciondocente = mi.getPersonaEvaluacionDocenteKardex(parametro.getId_evaluacion_docente());
            model = modelMapper.map(evaluaciondocente, PersonaEvaluacionDocenteModel.class);
            model.setId_persona(parametro.getId_persona());
            model.setUUID(randomUUIDString);
            model.setRoot(parametro.getRoot());
            modelo.addAttribute("detalleperiodo", periodo);
            modelo.addAttribute("id_rol", this.getUsuario().getId_rol());
            modelo.addAttribute("nombres", this.getUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "Kardex/EvaluacionDocente/EditarEvaluacionDocenteKardex";
        }
    }

    @RequestMapping(value = "/RegistrarPersonaEvaluacionKardex", method = RequestMethod.POST)
    public String RegistrarPersonaEvaluacionKardex(@ModelAttribute("model") @Validated PersonaEvaluacionDocenteModel model, BindingResult result, Model modelo) {
        if (model.getUrl_certificado_evaluacion().equals("image.png")) {
            if (model.getImage() == null || model.getImage().isEmpty()) {
                result.addError(new FieldError("url_certificado_evaluacion", "url_certificado_evaluacion", "Debe cargar la image de su fotocopia de la evaluacion"));
            }
        }
        if (result.hasErrors()) {
            List<ListViewItem> periodo = new ArrayList<>();
            periodo.add(new ListViewItem("", "NINGUNO"));
            periodo.add(new ListViewItem("1", "1"));
            periodo.add(new ListViewItem("2", "2"));
            modelo.addAttribute("detalleperiodo", periodo);
            modelo.addAttribute("id_rol", this.getUsuario().getId_rol());
            modelo.addAttribute("nombres", this.getUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "Kardex/EvaluacionDocente/EditarEvaluacionDocenteKardex";
        }
        PersonaEvaluacionDocente evaluaciondocente = modelMapper.map(model, PersonaEvaluacionDocente.class);
        int id_evaluacion_docente = mi.registrarEvaluacionDocenteKardex(evaluaciondocente);
        String filename = String.format("%010d", model.getId_persona()) + String.format("%05d", id_evaluacion_docente);
        FileUploadServlet.MultipartRequest(request, model.getImage(), path + DIRECTORIO_EVALUACIONDOCENTE.key(), "url_certificado_evaluacion", filename);
        String nombrearchivo = Utils.EsImagenModificado(evaluaciondocente.getUrl_certificado_evaluacion(), request.getAttribute("url_certificado_evaluacion").toString());
        if (!nombrearchivo.equals("image.png")) {
            evaluaciondocente.setId_evaluacion_docente(id_evaluacion_docente);
            evaluaciondocente.setUrl_certificado_evaluacion(nombrearchivo);
            mi.actualizarImagenEvaluacionDocenteKardex(evaluaciondocente);
        }
        String request = "?id_persona_kardex=" + model.getId_persona_kardex() + "&" + "id_persona=" + model.getId_persona() + "&" + "status=" + Utils.encodeValue("alert-success") + "&" + "message=" + Utils.encodeValue("Se grabo correctamente el registro del documento");
        return "redirect:/ListarEvaluacionDocente" + request;
    }

    @RequestMapping(value = "/RegistrarModificarEvaluacion", method = RequestMethod.POST)
    public String RegistrarModificarEvaluacion(@ModelAttribute("model") @Validated PersonaEvaluacionDocenteModel model, BindingResult result, Model modelo) {
        if (model.getUrl_certificado_evaluacion().equals("image.png")) {
            if (model.getImage() == null || model.getImage().isEmpty()) {
                result.addError(new FieldError("url_certificado_evaluacion", "url_certificado_evaluacion", "Debe cargar la image de su fotocopia de la evaluacion"));
            }
        }
        if (result.hasErrors()) {
            List<ListViewItem> periodo = new ArrayList<>();
            periodo.add(new ListViewItem("", "NINGUNO"));
            periodo.add(new ListViewItem("1", "1"));
            periodo.add(new ListViewItem("2", "2"));
            modelo.addAttribute("detalleperiodo", periodo);
            modelo.addAttribute("id_rol", this.getUsuario().getId_rol());
            modelo.addAttribute("nombres", this.getUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "Kardex/EvaluacionDocente/NuevoEvaluacionDocenteKardex";
        }
        PersonaEvaluacionDocente evaluaciondocente = modelMapper.map(model, PersonaEvaluacionDocente.class);
        String filename = String.format("%010d", model.getId_persona()) + String.format("%05d", evaluaciondocente.getId_evaluacion_docente());
        FileUploadServlet.MultipartRequest(request, model.getImage(), path + DIRECTORIO_EVALUACIONDOCENTE.key(), "url_certificado_evaluacion", filename);
        String nombrearchivo = Utils.EsImagenModificado(evaluaciondocente.getUrl_certificado_evaluacion(), request.getAttribute("url_certificado_evaluacion").toString());
        if (!nombrearchivo.equals("image.png")) {
            evaluaciondocente.setUrl_certificado_evaluacion(nombrearchivo);
        }
        mi.actualizarEvaluacionDocenteKardex(evaluaciondocente);
        String request = "?id_persona_kardex=" + model.getId_persona_kardex() + "&" + "id_persona=" + model.getId_persona() + "&" + "status=" + Utils.encodeValue("alert-success") + "&" + "message=" + Utils.encodeValue("Se Actualizo correctamente el registro del documento");
        return "redirect:/ListarEvaluacionDocente" + request;
    }

    @RequestMapping(value = "/EliminarEvaluacionDocente", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageDeleteResult> EliminarEvaluacionDocente(@ModelAttribute("model") ItemEliminarModel model) {
        Clientes cliente = this.getUsuario();
        MessageDeleteResult Respuesta = new MessageDeleteResult();
        if (cliente == null) {
            Respuesta.setMessage("Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            Respuesta.setStatus("Error");
            return new ResponseEntity<>(Respuesta, HttpStatus.OK);
        } else {
            try {
                mi.eliminarEvaluacionDocenteKardex(model.getId_evaluacion_docente());
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

    @RequestMapping(value = "/ModificarEstadoAprobacionDocumentoEvaluacion", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageAprobadoResult> ModificarEstadoAprobacionDocumentoEvaluacion(@ModelAttribute("model") ItemAprobarModel model) {
        Clientes cliente = this.getUsuario();
        MessageAprobadoResult Respuesta = new MessageAprobadoResult();
        if (cliente == null) {
            Respuesta.setMessage("Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            Respuesta.setStatus("Error");
            return new ResponseEntity<>(Respuesta, HttpStatus.OK);
        } else {
            try {
                Boolean estado = model.getName() != null ? model.getName().equals("on") ? true : false : false;
                PersonaEvaluacionDocente evaluacion = new PersonaEvaluacionDocente();
                evaluacion.setId_evaluacion_docente(model.getId_evaluacion_docente());
                evaluacion.setAprobado(estado);
                mi.aprobarEvaluacionDocenteKardex(evaluacion);
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
