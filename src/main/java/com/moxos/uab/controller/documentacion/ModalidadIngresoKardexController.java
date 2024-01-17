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

import static com.moxos.uab.util.Directorio.DIRECTORIO_MODALIDADES;

@Controller
public class ModalidadIngresoKardexController {

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

    @RequestMapping(value = "/ListaParcialModalidad", method = RequestMethod.GET)
    public String ListaParcialModalidad(Model modelo) {
        Clientes cliente = this.getUsuario();
        int number = Convert.ToInteger(request, "number");
        int id_persona_kardex = Convert.ToInteger(request, "id_persona_kardex");
        PersonaKardex kardex = new PersonaKardex();
        kardex.setNumber(number);
        kardex.setId_persona_kardex(id_persona_kardex);
        List<DetallePersonaModalidadIngreso> modalidades = mi.getPersonaSubTotalModalidadKardex(kardex);
        int sId_rol = cliente.getId_rol();
        modelo.addAttribute("id_rol", sId_rol);
        modelo.addAttribute("nombres", cliente.getNombres());
        modelo.addAttribute("modalidades", modalidades);
        return "Kardex/DetalleModalidades";
    }

    @RequestMapping(value = "/ListaModalidadIngreso", method = RequestMethod.GET)
    public String ListaModalidadIngreso(Model modelo) {
        Clientes cliente = this.getUsuario();
        int id_persona_kardex = Convert.ToInteger(request, "id_persona_kardex");
        int id_persona = Convert.ToInteger(request, "id_persona");
        String status = Convert.ToString(request, "status");
        String message = Convert.ToString(request, "message");
        List<DetallePersonaModalidadIngreso> modalidades = mi.getPersonaTotalModalidadKardex(id_persona_kardex);
        int sId_rol = cliente.getId_rol();
        MessageResult result = new MessageResult();
        result.setMessage(message);
        result.setStatus(status);
        modelo.addAttribute("id_rol", sId_rol);
        modelo.addAttribute("nombres", cliente.getNombres());
        modelo.addAttribute("detallemodalidad", modalidades);
        modelo.addAttribute("id_persona", id_persona);
        modelo.addAttribute("result", result);
        modelo.addAttribute("id_persona_kardex", id_persona_kardex);
        return "Kardex/Modalidad/DetalleModalidadKardex";
    }

    @RequestMapping(value = "/FormularioRegistroModalidadIngresoKardex", method = RequestMethod.GET)
    public String FormularioRegistroModalidadIngresoKardex(@ModelAttribute("parametro") ParametroCreacionModel parametro, Model modelo) {
        List<ListViewItem> modalidadingreso = new ArrayList<>();
        modalidadingreso.add(new ListViewItem("", "NINGUNO"));
        modalidadingreso.add(new ListViewItem("CONCURSO DE MERITO", "CONCURSO DE MERITO (INTERINOS, TITULARES)"));
        modalidadingreso.add(new ListViewItem("DESIGNACION DIRECTA", "DESIGNACION DIRECTA (INVITADOS)"));
        List<ListViewItem> programas = mi.getProgramasPregrado();
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString().replace("-", "").toUpperCase();
        PersonaModalidadIngresoModel model = null;
        if (parametro.getId_modalidadingreso() == null) {
            model = new PersonaModalidadIngresoModel();
            model.setId_persona(parametro.getId_persona());
            model.setId_persona_kardex(parametro.getId_persona_kardex());
            model.setUUID(randomUUIDString);
            model.setRoot(parametro.getRoot());
            modelo.addAttribute("modalidadingreso", modalidadingreso);
            modelo.addAttribute("programas", programas);
            modelo.addAttribute("id_rol", this.getUsuario().getId_rol());
            modelo.addAttribute("nombres", this.getUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "Kardex/Modalidad/NuevoModalidadKardex";
        } else {
            PersonaModalidadIngreso modalidad = mi.getPersonaModalidadKardex(parametro.getId_modalidadingreso());
            model = modelMapper.map(modalidad, PersonaModalidadIngresoModel.class);
            model.setId_persona(parametro.getId_persona());
            model.setUUID(randomUUIDString);
            model.setRoot(parametro.getRoot());
            model.setText_fechaingreso(Convert.ToString(model.getFechaingreso(), "yyyy-MM-dd"));
            modelo.addAttribute("modalidadingreso", modalidadingreso);
            modelo.addAttribute("programas", programas);
            modelo.addAttribute("id_rol", this.getUsuario().getId_rol());
            modelo.addAttribute("nombres", this.getUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "Kardex/Modalidad/EditarModalidadKardex";
        }
    }

    @RequestMapping(value = "/RegistrarPersonaModalidadIngreso", method = RequestMethod.POST)
    public String RegistrarPersonaModalidadIngreso(@ModelAttribute("model") @Validated PersonaModalidadIngresoModel model, BindingResult result, Model modelo) {
        if (model.getUrl_modalidadingreso().equals("image.png")) {
            if (model.getImage() == null || model.getImage().isEmpty()) {
                result.addError(new FieldError("url_modalidadingreso", "url_modalidadingreso", "Debe cargar la image de su memoramdun o resolucion"));
            }
        }
        model.setMostrar(model.getText_mostrar() != null ? model.getText_mostrar().equals("on") ? true : false : false);
        model.setFechaingreso(Convert.ToDate(model.getText_fechaingreso(), "yyyy-MM-dd"));
        if (result.hasErrors()) {
            List<ListViewItem> modalidadingreso = new ArrayList<>();
            modalidadingreso.add(new ListViewItem("", "NINGUNO"));
            modalidadingreso.add(new ListViewItem("CONCURSO DE MERITO", "CONCURSO DE MERITO"));
            modalidadingreso.add(new ListViewItem("DESIGNACION DIRECTA", "DESIGNACION DIRECTA"));
            List<ListViewItem> programas = mi.getProgramasPregrado();
            modelo.addAttribute("modalidadingreso", modalidadingreso);
            modelo.addAttribute("programas", programas);
            modelo.addAttribute("id_rol", this.getUsuario().getId_rol());
            modelo.addAttribute("nombres", this.getUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "Kardex/Modalidad/NuevoModalidadKardex";
        }
        PersonaModalidadIngreso modalidadingreso = modelMapper.map(model, PersonaModalidadIngreso.class);
        int id_modalidadingreso = mi.registrarNuevoModalidadKardex(modalidadingreso);
        String filename = String.format("%010d", model.getId_persona()) + String.format("%05d", id_modalidadingreso);
        FileUploadServlet.MultipartRequest(request, model.getImage(), path + DIRECTORIO_MODALIDADES.key(), "url_modalidadingreso", filename);
        String nombrearchivo = Utils.EsImagenModificado(modalidadingreso.getUrl_modalidadingreso(), request.getAttribute("url_modalidadingreso").toString());
        if (!nombrearchivo.equals("image.png")) {
            modalidadingreso.setId_modalidadingreso(id_modalidadingreso);
            modalidadingreso.setUrl_modalidadingreso(nombrearchivo);
            mi.actualizarImagenModalidadKardexDocente(modalidadingreso);
        }
        String request = "?id_persona_kardex=" + model.getId_persona_kardex() + "&" + "id_persona=" + model.getId_persona() + "&" + "status=" + Utils.encodeValue("alert-success") + "&" + "message=" + Utils.encodeValue("Se grabo correctamente el registro del documento");
        return "redirect:/ListaModalidadIngreso" + request;
    }

    @RequestMapping(value = "/RegistrarModificarModalidadIngresos", method = RequestMethod.POST)
    public String RegistrarModificarModalidadIngresos(@ModelAttribute("model") @Validated PersonaModalidadIngresoModel model, BindingResult result, Model modelo) {
        if (model.getUrl_modalidadingreso().equals("image.png")) {
            if (model.getImage() == null || model.getImage().isEmpty()) {
                result.addError(new FieldError("url_modalidadingreso", "url_modalidadingreso", "Debe cargar la image de su memoramdun o resolucion"));
            }
        }
        model.setMostrar(model.getText_mostrar() != null ? model.getText_mostrar().equals("on") ? true : false : false);
        model.setFechaingreso(Convert.ToDate(model.getText_fechaingreso(), "yyyy-MM-dd"));
        if (result.hasErrors()) {
            List<ListViewItem> modalidadingreso = new ArrayList<>();
            modalidadingreso.add(new ListViewItem("", "NINGUNO"));
            modalidadingreso.add(new ListViewItem("CONCURSO DE MERITO", "CONCURSO DE MERITO"));
            modalidadingreso.add(new ListViewItem("DESIGNACION DIRECTA", "DESIGNACION DIRECTA"));
            List<ListViewItem> programas = mi.getProgramasPregrado();
            modelo.addAttribute("modalidadingreso", modalidadingreso);
            modelo.addAttribute("programas", programas);
            modelo.addAttribute("id_rol", this.getUsuario().getId_rol());
            modelo.addAttribute("nombres", this.getUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "Kardex/Modalidad/EditarModalidadKardex";
        }

        PersonaModalidadIngreso modalidadingreso = modelMapper.map(model, PersonaModalidadIngreso.class);
        String filename = String.format("%010d", model.getId_persona()) + String.format("%05d", modalidadingreso.getId_modalidadingreso());
        FileUploadServlet.MultipartRequest(request, model.getImage(), path + DIRECTORIO_MODALIDADES.key(), "url_modalidadingreso", filename);
        String nombrearchivo = Utils.EsImagenModificado(modalidadingreso.getUrl_modalidadingreso(), request.getAttribute("url_modalidadingreso").toString());
        if (!nombrearchivo.equals("image.png")) {
            modalidadingreso.setUrl_modalidadingreso(nombrearchivo);
        }
        mi.actualizarDatosiModalidadKardexDocente(modalidadingreso);
        String request = "?id_persona_kardex=" + model.getId_persona_kardex() + "&" + "id_persona=" + model.getId_persona() + "&" + "status=" + Utils.encodeValue("alert-success") + "&" + "message=" + Utils.encodeValue("Se Actualizo correctamente el registro del documento");
        return "redirect:/ListaModalidadIngreso" + request;
    }

    @RequestMapping(value = "/EliminarModalidadIngreso", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageDeleteResult> EliminarModalidadIngreso(@ModelAttribute("model") ItemEliminarModel model) {
        Clientes cliente = this.getUsuario();
        MessageDeleteResult Respuesta = new MessageDeleteResult();
        if (cliente == null) {
            Respuesta.setMessage("Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            Respuesta.setStatus("Error");
            return new ResponseEntity<>(Respuesta, HttpStatus.OK);
        } else {
            try {
                mi.eliminarModalidadKardexDocente(model.getId_modalidadingreso());
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

    @RequestMapping(value = "/ModificarEstadoAprobacionDocumentoModalidadIngreso", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageAprobadoResult> ModificarEstadoAprobacionDocumentoModalidadIngreso(@ModelAttribute("model") ItemAprobarModel model) {
        Clientes cliente = this.getUsuario();
        MessageAprobadoResult Respuesta = new MessageAprobadoResult();
        if (cliente == null) {
            Respuesta.setMessage("Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            Respuesta.setStatus("Error");
            return new ResponseEntity<>(Respuesta, HttpStatus.OK);
        } else {
            try {
                Boolean estado = model.getName() != null ? model.getName().equals("on") ? true : false : false;
                PersonaModalidadIngreso modalidadingreso = new PersonaModalidadIngreso();
                modalidadingreso.setId_modalidadingreso(model.getId_modalidadingreso());
                modalidadingreso.setAprobado(estado);
                mi.aprobarModalidadKardexDocente(modalidadingreso);
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
