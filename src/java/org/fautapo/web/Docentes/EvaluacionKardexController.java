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
import org.fautapo.domain.PersonaEvaluacionDocente;
import org.fautapo.domain.PersonaKardex;
import org.fautapo.domain.logic.MiFacade;
import org.fautapo.model.Kardex.ItemAprobarModel;
import org.fautapo.model.Kardex.ItemEliminarModel;
import org.fautapo.model.Kardex.MessageAprobadoResult;
import org.fautapo.model.Kardex.MessageDeleteResult;
import org.fautapo.model.Kardex.ParametroCreacionModel;
import org.fautapo.model.Kardex.PersonaEvaluacionDocenteModel;
import org.fautapo.model.MessageResult;
import static org.fautapo.util.Directorio.DIRECTORIO_EVALUACIONDOCENTE;
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
public class EvaluacionKardexController {

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

    @RequestMapping(value = "/ListaParcialEvaluacionDocente.fautapo", method = RequestMethod.GET)
    public String ListaParcialEvaluacionDocente(Model modelo) {
        Clientes cliente = this.GetUsuario();
        int number = cliente.getInt(request, "number");
        int id_persona_kardex = cliente.getInt(request, "id_persona_kardex");
        PersonaKardex kardex = new PersonaKardex();
        kardex.setNumber(number);
        kardex.setId_persona_kardex(id_persona_kardex);
        List<PersonaEvaluacionDocente> detalleevaluacion = mi.GetPersonaSubTotalEvaluacionDocenteKardex(kardex);
        int sId_rol = cliente.getId_rol();
        modelo.addAttribute("id_rol", sId_rol);
        modelo.addAttribute("nombres", cliente.getNombres());
        modelo.addAttribute("detalles", detalleevaluacion);
        return "AdministrarKardexPersonal/DetalleParcialEvaluacionDocente";
    }

    @RequestMapping(value = "/ListarEvaluacionDocente.fautapo", method = RequestMethod.GET)
    public String ListarEvaluacionDocente(Model modelo) {
        Clientes cliente = this.GetUsuario();
        int id_persona_kardex = cliente.getInt(request, "id_persona_kardex");
        int id_persona = cliente.getInt(request, "id_persona");
        String status = cliente.getString(request, "status");
        String message = cliente.getString(request, "message");
        List<PersonaEvaluacionDocente> detalleevaluacion = mi.GetPersonaTotalEvaluacionDocenteKardex(id_persona_kardex);
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
        return "AdministrarKardexPersonal/EvaluacionDocente/DetalleEvaluacionDocenteKardex";
    }

    @RequestMapping(value = "/FormularioRegistroEvaluacionKardex.fautapo", method = RequestMethod.GET)
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
            modelo.addAttribute("id_rol", this.GetUsuario().getId_rol());
            modelo.addAttribute("nombres", this.GetUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "AdministrarKardexPersonal/EvaluacionDocente/NuevoEvaluacionDocenteKardex";
        } else {
            PersonaEvaluacionDocente evaluaciondocente = mi.GetPersonaEvaluacionDocenteKardex(parametro.getId_evaluacion_docente());
            model = modelMapper.map(evaluaciondocente, PersonaEvaluacionDocenteModel.class);
            model.setId_persona(parametro.getId_persona());
            model.setUUID(randomUUIDString);
            model.setRoot(parametro.getRoot());
            modelo.addAttribute("detalleperiodo", periodo);
            modelo.addAttribute("id_rol", this.GetUsuario().getId_rol());
            modelo.addAttribute("nombres", this.GetUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "AdministrarKardexPersonal/EvaluacionDocente/EditarEvaluacionDocenteKardex";
        }
    }

    @RequestMapping(value = "/RegistrarPersonaEvaluacionKardex.fautapo", method = RequestMethod.POST)
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
            modelo.addAttribute("id_rol", this.GetUsuario().getId_rol());
            modelo.addAttribute("nombres", this.GetUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "AdministrarKardexPersonal/EvaluacionDocente/EditarEvaluacionDocenteKardex";
        }
        PersonaEvaluacionDocente evaluaciondocente = modelMapper.map(model, PersonaEvaluacionDocente.class);
        int id_evaluacion_docente = mi.RegistrarEvaluacionDocenteKardex(evaluaciondocente);
        String filename = String.format("%010d", model.getId_persona()) + String.format("%05d", id_evaluacion_docente);
        FileUploadServlet.MultipartRequest(request, model.getImage(), DIRECTORIO_EVALUACIONDOCENTE.key(), "url_certificado_evaluacion", filename);
        String nombrearchivo = Util.EsImagenModificado(evaluaciondocente.getUrl_certificado_evaluacion(), request.getAttribute("url_certificado_evaluacion").toString());
        if (!nombrearchivo.equals("image.png")) {
            evaluaciondocente.setId_evaluacion_docente(id_evaluacion_docente);
            evaluaciondocente.setUrl_certificado_evaluacion(nombrearchivo);
            mi.ActualizarImagenEvaluacionDocenteKardex(evaluaciondocente);
        }
        String request = "?id_persona_kardex=" + model.getId_persona_kardex() + "&" + "id_persona=" + model.getId_persona() + "&" + "status=" + Util.encodeValue("alert-success") + "&" + "message=" + Util.encodeValue("Se grabo correctamente el registro del documento");
        return "redirect:/ListarEvaluacionDocente.fautapo" + request;
    }

    @RequestMapping(value = "/RegistrarModificarEvaluacion.fautapo", method = RequestMethod.POST)
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
            modelo.addAttribute("id_rol", this.GetUsuario().getId_rol());
            modelo.addAttribute("nombres", this.GetUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "AdministrarKardexPersonal/EvaluacionDocente/NuevoEvaluacionDocenteKardex";
        }
        PersonaEvaluacionDocente evaluaciondocente = modelMapper.map(model, PersonaEvaluacionDocente.class);
        String filename = String.format("%010d", model.getId_persona()) + String.format("%05d", evaluaciondocente.getId_evaluacion_docente());
        FileUploadServlet.MultipartRequest(request, model.getImage(), DIRECTORIO_EVALUACIONDOCENTE.key(), "url_certificado_evaluacion", filename);
        String nombrearchivo = Util.EsImagenModificado(evaluaciondocente.getUrl_certificado_evaluacion(), request.getAttribute("url_certificado_evaluacion").toString());
        if (!nombrearchivo.equals("image.png")) {
            evaluaciondocente.setUrl_certificado_evaluacion(nombrearchivo);
        }
        mi.ActualizarEvaluacionDocenteKardex(evaluaciondocente);
        String request = "?id_persona_kardex=" + model.getId_persona_kardex() + "&" + "id_persona=" + model.getId_persona() + "&" + "status=" + Util.encodeValue("alert-success") + "&" + "message=" + Util.encodeValue("Se Actualizo correctamente el registro del documento");
        return "redirect:/ListarEvaluacionDocente.fautapo" + request;
    }

    @RequestMapping(value = "/EliminarEvaluacionDocente.fautapo", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageDeleteResult> EliminarEvaluacionDocente(@ModelAttribute("model") ItemEliminarModel model) {
        Clientes cliente = this.GetUsuario();
        MessageDeleteResult Respuesta = new MessageDeleteResult();
        if (cliente == null) {
            Respuesta.setMessage("Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            Respuesta.setStatus("Error");
            return new ResponseEntity<>(Respuesta, HttpStatus.OK);
        } else {
            try {
                mi.EliminarEvaluacionDocenteKardex(model.getId_evaluacion_docente());
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

    @RequestMapping(value = "/ModificarEstadoAprobacionDocumentoEvaluacion.fautapo", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageAprobadoResult> ModificarEstadoAprobacionDocumentoEvaluacion(@ModelAttribute("model") ItemAprobarModel model) {
        Clientes cliente = this.GetUsuario();
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
                mi.AprobarEvaluacionDocenteKardex(evaluacion);
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
