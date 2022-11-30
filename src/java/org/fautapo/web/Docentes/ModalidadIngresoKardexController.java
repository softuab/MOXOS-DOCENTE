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
import org.fautapo.domain.PersonaKardex;
import org.fautapo.domain.PersonaModalidadIngreso;
import org.fautapo.domain.logic.MiFacade;
import org.fautapo.model.Kardex.DetallePersonaModalidadIngresoModel;
import org.fautapo.model.Kardex.ItemAprobarModel;
import org.fautapo.model.Kardex.ItemEliminarModel;
import org.fautapo.model.Kardex.MessageAprobadoResult;
import org.fautapo.model.Kardex.MessageDeleteResult;
import org.fautapo.model.Kardex.ParametroCreacionModel;
import org.fautapo.model.Kardex.PersonaModalidadIngresoModel;
import org.fautapo.model.MessageResult;
import org.fautapo.util.Convert;
import static org.fautapo.util.Directorio.DIRECTORIO_MODALIDADES;
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
public class ModalidadIngresoKardexController {

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

    @RequestMapping(value = "/ListaParcialModalidad.fautapo", method = RequestMethod.GET)
    public String ListaParcialModalidad(Model modelo) {
        Clientes cliente = this.GetUsuario();
        int number = cliente.getInt(request, "number");
        int id_persona_kardex = cliente.getInt(request, "id_persona_kardex");
        PersonaKardex kardex = new PersonaKardex();
        kardex.setNumber(number);
        kardex.setId_persona_kardex(id_persona_kardex);
        List<DetallePersonaModalidadIngresoModel> modalidades = mi.GetPersonaSubTotalModalidadKardex(kardex);
        int sId_rol = cliente.getId_rol();
        modelo.addAttribute("id_rol", sId_rol);
        modelo.addAttribute("nombres", cliente.getNombres());
        modelo.addAttribute("modalidades", modalidades);
        return "AdministrarKardexPersonal/DetalleModalidades";
    }

    @RequestMapping(value = "/ListaModalidadIngreso.fautapo", method = RequestMethod.GET)
    public String ListaModalidadIngreso(Model modelo) {
        Clientes cliente = this.GetUsuario();
        int id_persona_kardex = cliente.getInt(request, "id_persona_kardex");
        int id_persona = cliente.getInt(request, "id_persona");
        String status = cliente.getString(request, "status");
        String message = cliente.getString(request, "message");
        List<DetallePersonaModalidadIngresoModel> modalidades = mi.GetPersonaTotalModalidadKardex(id_persona_kardex);
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
        return "AdministrarKardexPersonal/Modalidad/DetalleModalidadKardex";
    }

    @RequestMapping(value = "/FormularioRegistroModalidadIngresoKardex.fautapo", method = RequestMethod.GET)
    public String FormularioRegistroModalidadIngresoKardex(@ModelAttribute("parametro") ParametroCreacionModel parametro, Model modelo) {
        List<ListViewItem> modalidadingreso = new ArrayList<>();
        modalidadingreso.add(new ListViewItem("", "NINGUNO"));
        modalidadingreso.add(new ListViewItem("CONCURSO DE MERITO", "CONCURSO DE MERITO (INTERINOS, TITULARES)"));
        modalidadingreso.add(new ListViewItem("DESIGNACION DIRECTA", "DESIGNACION DIRECTA (INVITADOS)"));
        List<ListViewItem> programas = mi.GetProgramasPregrado();
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
            modelo.addAttribute("id_rol", this.GetUsuario().getId_rol());
            modelo.addAttribute("nombres", this.GetUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "AdministrarKardexPersonal/Modalidad/NuevoModalidadKardex";
        } else {
            PersonaModalidadIngreso modalidad = mi.GetPersonaModalidadKardex(parametro.getId_modalidadingreso());
            model = modelMapper.map(modalidad, PersonaModalidadIngresoModel.class);
            model.setId_persona(parametro.getId_persona());
            model.setUUID(randomUUIDString);
            model.setRoot(parametro.getRoot());
            model.setText_fechaingreso(Convert.ToString(model.getFechaingreso(), "dd/MM/yyyy"));
            modelo.addAttribute("modalidadingreso", modalidadingreso);
            modelo.addAttribute("programas", programas);
            modelo.addAttribute("id_rol", this.GetUsuario().getId_rol());
            modelo.addAttribute("nombres", this.GetUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "AdministrarKardexPersonal/Modalidad/EditarModalidadKardex";
        }
    }

    @RequestMapping(value = "/RegistrarPersonaModalidadIngreso.fautapo", method = RequestMethod.POST)
    public String RegistrarPersonaModalidadIngreso(@ModelAttribute("model") @Validated PersonaModalidadIngresoModel model, BindingResult result, Model modelo) {
        if (model.getUrl_modalidadingreso().equals("image.png")) {
            if (model.getImage() == null || model.getImage().isEmpty()) {
                result.addError(new FieldError("url_modalidadingreso", "url_modalidadingreso", "Debe cargar la image de su memoramdun o resolucion"));
            }
        }
        model.setMostrar(model.getText_mostrar() != null ? model.getText_mostrar().equals("on") ? true : false : false);
        model.setFechaingreso(Convert.ToDate(model.getText_fechaingreso(), "dd/MM/yyyy"));
        if (result.hasErrors()) {
            List<ListViewItem> modalidadingreso = new ArrayList<>();
            modalidadingreso.add(new ListViewItem("", "NINGUNO"));
            modalidadingreso.add(new ListViewItem("CONCURSO DE MERITO", "CONCURSO DE MERITO"));
            modalidadingreso.add(new ListViewItem("DESIGNACION DIRECTA", "DESIGNACION DIRECTA"));
            List<ListViewItem> programas = mi.GetProgramasPregrado();
            modelo.addAttribute("modalidadingreso", modalidadingreso);
            modelo.addAttribute("programas", programas);
            modelo.addAttribute("id_rol", this.GetUsuario().getId_rol());
            modelo.addAttribute("nombres", this.GetUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "AdministrarKardexPersonal/Modalidad/NuevoModalidadKardex";
        }
        PersonaModalidadIngreso modalidadingreso = modelMapper.map(model, PersonaModalidadIngreso.class);
        int id_modalidadingreso = mi.RegistrarNuevoModalidadKardex(modalidadingreso);
        String filename = String.format("%010d", model.getId_persona()) + String.format("%05d", id_modalidadingreso);
        FileUploadServlet.MultipartRequest(request, model.getImage(), DIRECTORIO_MODALIDADES.key(), "url_modalidadingreso", filename);
        String nombrearchivo = Util.EsImagenModificado(modalidadingreso.getUrl_modalidadingreso(), request.getAttribute("url_modalidadingreso").toString());
        if (!nombrearchivo.equals("image.png")) {
            modalidadingreso.setId_modalidadingreso(id_modalidadingreso);
            modalidadingreso.setUrl_modalidadingreso(nombrearchivo);
            mi.ActualizarImagenModalidadKardexDocente(modalidadingreso);
        }
        String request = "?id_persona_kardex=" + model.getId_persona_kardex() + "&" + "id_persona=" + model.getId_persona() + "&" + "status=" + Util.encodeValue("alert-success") + "&" + "message=" + Util.encodeValue("Se grabo correctamente el registro del documento");
        return "redirect:/ListaModalidadIngreso.fautapo" + request;
    }

    @RequestMapping(value = "/RegistrarModificarModalidadIngresos.fautapo", method = RequestMethod.POST)
    public String RegistrarModificarModalidadIngresos(@ModelAttribute("model") @Validated PersonaModalidadIngresoModel model, BindingResult result, Model modelo) {
        if (model.getUrl_modalidadingreso().equals("image.png")) {
            if (model.getImage() == null || model.getImage().isEmpty()) {
                result.addError(new FieldError("url_modalidadingreso", "url_modalidadingreso", "Debe cargar la image de su memoramdun o resolucion"));
            }
        }
        model.setMostrar(model.getText_mostrar() != null ? model.getText_mostrar().equals("on") ? true : false : false);
        model.setFechaingreso(Convert.ToDate(model.getText_fechaingreso(), "dd/MM/yyyy"));
        if (result.hasErrors()) {
            List<ListViewItem> modalidadingreso = new ArrayList<>();
            modalidadingreso.add(new ListViewItem("", "NINGUNO"));
            modalidadingreso.add(new ListViewItem("CONCURSO DE MERITO", "CONCURSO DE MERITO"));
            modalidadingreso.add(new ListViewItem("DESIGNACION DIRECTA", "DESIGNACION DIRECTA"));
            List<ListViewItem> programas = mi.GetProgramasPregrado();
            modelo.addAttribute("modalidadingreso", modalidadingreso);
            modelo.addAttribute("programas", programas);
            modelo.addAttribute("id_rol", this.GetUsuario().getId_rol());
            modelo.addAttribute("nombres", this.GetUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "AdministrarKardexPersonal/Modalidad/EditarModalidadKardex";
        }

        PersonaModalidadIngreso modalidadingreso = modelMapper.map(model, PersonaModalidadIngreso.class);
        String filename = String.format("%010d", model.getId_persona()) + String.format("%05d", modalidadingreso.getId_modalidadingreso());
        FileUploadServlet.MultipartRequest(request, model.getImage(), DIRECTORIO_MODALIDADES.key(), "url_modalidadingreso", filename);
        String nombrearchivo = Util.EsImagenModificado(modalidadingreso.getUrl_modalidadingreso(), request.getAttribute("url_modalidadingreso").toString());
        if (!nombrearchivo.equals("image.png")) {
            modalidadingreso.setUrl_modalidadingreso(nombrearchivo);
        }
        mi.ActualizarDatosiModalidadKardexDocente(modalidadingreso);
        String request = "?id_persona_kardex=" + model.getId_persona_kardex() + "&" + "id_persona=" + model.getId_persona() + "&" + "status=" + Util.encodeValue("alert-success") + "&" + "message=" + Util.encodeValue("Se Actualizo correctamente el registro del documento");
        return "redirect:/ListaModalidadIngreso.fautapo" + request;
    }

    @RequestMapping(value = "/EliminarModalidadIngreso.fautapo", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageDeleteResult> EliminarModalidadIngreso(@ModelAttribute("model") ItemEliminarModel model) {
        Clientes cliente = this.GetUsuario();
        MessageDeleteResult Respuesta = new MessageDeleteResult();
        if (cliente == null) {
            Respuesta.setMessage("Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            Respuesta.setStatus("Error");
            return new ResponseEntity<>(Respuesta, HttpStatus.OK);
        } else {
            try {
                mi.EliminarModalidadKardexDocente(model.getId_modalidadingreso());
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

    @RequestMapping(value = "/ModificarEstadoAprobacionDocumentoModalidadIngreso.fautapo", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageAprobadoResult> ModificarEstadoAprobacionDocumentoModalidadIngreso(@ModelAttribute("model") ItemAprobarModel model) {
        Clientes cliente = this.GetUsuario();
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
                mi.AprobarModalidadKardexDocente(modalidadingreso);
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
