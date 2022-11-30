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
import org.fautapo.domain.PersonaExperienciaLaboral;
import org.fautapo.domain.PersonaKardex;
import org.fautapo.domain.logic.MiFacade;
import org.fautapo.model.Kardex.DetallePersonaExperienciaLaboral;
import org.fautapo.model.Kardex.ItemAprobarModel;
import org.fautapo.model.Kardex.ItemEliminarModel;
import org.fautapo.model.Kardex.MessageAprobadoResult;
import org.fautapo.model.Kardex.MessageDeleteResult;
import org.fautapo.model.Kardex.ParametroCreacionModel;
import org.fautapo.model.Kardex.PersonaExperienciaLaboralModel;
import org.fautapo.model.MessageResult;
import org.fautapo.util.Convert;
import static org.fautapo.util.Directorio.DIRECTORIO_EXPERIENCIALABORAL;
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
public class ExperienciaLaboralKardexController {

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

    @RequestMapping(value = "/ListaParcialExperienciaLaboral.fautapo", method = RequestMethod.GET)
    public String ListaParcialExperienciaLaboral(Model modelo) {
        Clientes cliente = this.GetUsuario();
        int number = cliente.getInt(request, "number");
        int id_persona_kardex = cliente.getInt(request, "id_persona_kardex");
        PersonaKardex kardex = new PersonaKardex();
        kardex.setNumber(number);
        kardex.setId_persona_kardex(id_persona_kardex);
        List<DetallePersonaExperienciaLaboral> experiencia = mi.GetPersonaSubTotalExperienciaLaboralKardex(kardex);
        int sId_rol = cliente.getId_rol();
        modelo.addAttribute("id_rol", sId_rol);
        modelo.addAttribute("nombres", cliente.getNombres());
        modelo.addAttribute("detalles", experiencia);
        return "AdministrarKardexPersonal/DetalleParcialExperienciaLaboral";
    }

    @RequestMapping(value = "/ListarExperienciaLaboral.fautapo", method = RequestMethod.GET)
    public String ListarExperienciaLaboral(Model modelo) {
        Clientes cliente = this.GetUsuario();
        int id_persona_kardex = cliente.getInt(request, "id_persona_kardex");
        int id_persona = cliente.getInt(request, "id_persona");
        String status = cliente.getString(request, "status");
        String message = cliente.getString(request, "message");
        List<DetallePersonaExperienciaLaboral> detalleexperiencia = mi.GetPersonaTotalExperienciaLaboralKardex(id_persona_kardex);
        int sId_rol = cliente.getId_rol();
        MessageResult result = new MessageResult();
        result.setMessage(message);
        result.setStatus(status);
        modelo.addAttribute("id_rol", sId_rol);
        modelo.addAttribute("nombres", cliente.getNombres());
        modelo.addAttribute("detalles", detalleexperiencia);
        modelo.addAttribute("id_persona", id_persona);
        modelo.addAttribute("result", result);
        modelo.addAttribute("id_persona_kardex", id_persona_kardex);
        return "AdministrarKardexPersonal/ExperienciaLaboral/DetalleExperienciaLaboralKardex";
    }

    @RequestMapping(value = "/FormularioRegistroExperienciaLaboralKardex.fautapo", method = RequestMethod.GET)
    public String FormularioRegistroExperienciaLaboralKardex(@ModelAttribute("parametro") ParametroCreacionModel parametro, Model modelo) {
        List<ListViewItem> tipoexperiencia = new ArrayList<>();
        tipoexperiencia.add(new ListViewItem("", "NINGUNO"));
        tipoexperiencia.add(new ListViewItem("EXPERIENCIA PROFESIONAL/LABORAL", "EXPERIENCIA PROFESIONAL/LABORAL"));
        tipoexperiencia.add(new ListViewItem("EXPERIENCIA ACADEMICA-DOCENCIA", "EXPERIENCIA ACADEMICA-DOCENCIA"));
        tipoexperiencia.add(new ListViewItem("EXPERIENCIA TRIBUNAL EN EVENTOS ACADEMICOS", "EXPERIENCIA TRIBUNAL EN EVENTOS ACADEMICOS"));
        tipoexperiencia.add(new ListViewItem("EXPERIENCIA CARGOS JERARQUICOS UNIVERSITARIOS", "EXPERIENCIA CARGOS JERARQUICOS UNIVERSITARIOS"));
        tipoexperiencia.add(new ListViewItem("EXPERIENCIA ADMINISTRATIVAS UNIVERSITARIA", "EXPERIENCIA ADMINISTRATIVAS UNIVERSITARIA"));
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString().replace("-", "").toUpperCase();
        PersonaExperienciaLaboralModel model = null;
        if (parametro.getId_experiencia_laboral() == null) {
            model = new PersonaExperienciaLaboralModel();
            model.setId_persona(parametro.getId_persona());
            model.setId_persona_kardex(parametro.getId_persona_kardex());
            model.setUUID(randomUUIDString);
            model.setRoot(parametro.getRoot());
            modelo.addAttribute("detalleexperiencia", tipoexperiencia);
            modelo.addAttribute("id_rol", this.GetUsuario().getId_rol());
            modelo.addAttribute("nombres", this.GetUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "AdministrarKardexPersonal/ExperienciaLaboral/NuevoExperienciaLaboralKardex";
        } else {
            PersonaExperienciaLaboral experiencialaboral = mi.GetPersonaExperienciaLaboralKardex(parametro.getId_experiencia_laboral());
            model = modelMapper.map(experiencialaboral, PersonaExperienciaLaboralModel.class);
            model.setId_persona(parametro.getId_persona());
            model.setUUID(randomUUIDString);
            model.setRoot(parametro.getRoot());
            model.setText_inicio(Convert.ToString(model.getInicio(), "dd/MM/yyyy"));
            model.setText_fin(Convert.ToString(model.getFin(), "dd/MM/yyyy"));
            modelo.addAttribute("detalleexperiencia", tipoexperiencia);
            modelo.addAttribute("id_rol", this.GetUsuario().getId_rol());
            modelo.addAttribute("nombres", this.GetUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "AdministrarKardexPersonal/ExperienciaLaboral/EditarExperienciaLaboralKardex";
        }
    }

    @RequestMapping(value = "/RegistrarPersonaExperienciaKardex.fautapo", method = RequestMethod.POST)
    public String RegistrarPersonaExperienciaKardex(@ModelAttribute("model") @Validated PersonaExperienciaLaboralModel model, BindingResult result, Model modelo) {
        if (model.getUrl_experiencia().equals("image.png")) {
            if (model.getImage() == null || model.getImage().isEmpty()) {
                result.addError(new FieldError("url_experiencia", "url_experiencia", "Debe cargar la image de su fotocopia del certificado de trabajo"));
            }
        }
        model.setMostrar(model.getText_mostrar() != null ? model.getText_mostrar().equals("on") ? true : false : false);
        model.setFin(Convert.ToDate(model.getText_fin(), "dd/MM/yyyy"));
        model.setInicio(Convert.ToDate(model.getText_inicio(), "dd/MM/yyyy"));
        if (result.hasErrors()) {
            List<ListViewItem> tipoexperiencia = new ArrayList<>();
            tipoexperiencia.add(new ListViewItem("", "NINGUNO"));
            tipoexperiencia.add(new ListViewItem("EXPERIENCIA PROFESIONAL/LABORAL", "EXPERIENCIA PROFESIONAL/LABORAL"));
            tipoexperiencia.add(new ListViewItem("EXPERIENCIA ACADEMICA-DOCENCIA", "EXPERIENCIA ACADEMICA-DOCENCIA"));
            tipoexperiencia.add(new ListViewItem("EXPERIENCIA TRIBUNAL EN EVENTOS ACADEMICOS", "EXPERIENCIA TRIBUNAL EN EVENTOS ACADEMICOS"));
            tipoexperiencia.add(new ListViewItem("EXPERIENCIA CARGOS JERARQUICOS UNIVERSITARIOS", "EXPERIENCIA CARGOS JERARQUICOS UNIVERSITARIOS"));
            tipoexperiencia.add(new ListViewItem("EXPERIENCIA ADMINISTRATIVAS UNIVERSITARIA", "EXPERIENCIA ADMINISTRATIVAS UNIVERSITARIA"));
            modelo.addAttribute("detalleexperiencia", tipoexperiencia);
            modelo.addAttribute("id_rol", this.GetUsuario().getId_rol());
            modelo.addAttribute("nombres", this.GetUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "AdministrarKardexPersonal/ExperienciaLaboral/NuevoExperienciaLaboralKardex";
        }
        PersonaExperienciaLaboral experiencialaboral = modelMapper.map(model, PersonaExperienciaLaboral.class);
        int id_experiencia_laboral = mi.RegistrarExperienciaLaboralKardex(experiencialaboral);
        String filename = String.format("%010d", model.getId_persona()) + String.format("%05d", id_experiencia_laboral);
        FileUploadServlet.MultipartRequest(request, model.getImage(), DIRECTORIO_EXPERIENCIALABORAL.key(), "url_experiencia", filename);
        String nombrearchivo = Util.EsImagenModificado(experiencialaboral.getUrl_experiencia(), request.getAttribute("url_experiencia").toString());
        if (!nombrearchivo.equals("image.png")) {
            experiencialaboral.setId_experiencia_laboral(id_experiencia_laboral);
            experiencialaboral.setUrl_experiencia(nombrearchivo);
            mi.ActualizarImagenExperienciaLaboralKardexDocente(experiencialaboral);
        }
        String request = "?id_persona_kardex=" + model.getId_persona_kardex() + "&" + "id_persona=" + model.getId_persona() + "&" + "status=" + Util.encodeValue("alert-success") + "&" + "message=" + Util.encodeValue("Se grabo correctamente el registro del documento");
        return "redirect:/ListarExperienciaLaboral.fautapo" + request;
    }

    @RequestMapping(value = "/RegistrarModificarExperiencia.fautapo", method = RequestMethod.POST)
    public String RegistrarModificarExperiencia(@ModelAttribute("model") @Validated PersonaExperienciaLaboralModel model, BindingResult result, Model modelo) {
        if (model.getUrl_experiencia().equals("image.png")) {
            if (model.getImage() == null || model.getImage().isEmpty()) {
                result.addError(new FieldError("url_experiencia", "url_experiencia", "Debe cargar la image de su fotocopia del certificado de trabajo"));
            }
        }
        model.setMostrar(model.getText_mostrar() != null ? model.getText_mostrar().equals("on") ? true : false : false);
        model.setFin(Convert.ToDate(model.getText_fin(), "dd/MM/yyyy"));
        model.setInicio(Convert.ToDate(model.getText_inicio(), "dd/MM/yyyy"));
        if (result.hasErrors()) {
            List<ListViewItem> tipoexperiencia = new ArrayList<>();
            tipoexperiencia.add(new ListViewItem("", "NINGUNO"));
            tipoexperiencia.add(new ListViewItem("EXPERIENCIA PROFESIONAL/LABORAL", "EXPERIENCIA PROFESIONAL/LABORAL"));
            tipoexperiencia.add(new ListViewItem("EXPERIENCIA ACADEMICA-DOCENCIA", "EXPERIENCIA ACADEMICA-DOCENCIA"));
            tipoexperiencia.add(new ListViewItem("EXPERIENCIA TRIBUNAL EN EVENTOS ACADEMICOS", "EXPERIENCIA TRIBUNAL EN EVENTOS ACADEMICOS"));
            tipoexperiencia.add(new ListViewItem("EXPERIENCIA CARGOS JERARQUICOS UNIVERSITARIOS", "EXPERIENCIA CARGOS JERARQUICOS UNIVERSITARIOS"));
            tipoexperiencia.add(new ListViewItem("EXPERIENCIA ADMINISTRATIVAS UNIVERSITARIA", "EXPERIENCIA ADMINISTRATIVAS UNIVERSITARIA"));
            modelo.addAttribute("detalleexperiencia", tipoexperiencia);
            modelo.addAttribute("id_rol", this.GetUsuario().getId_rol());
            modelo.addAttribute("nombres", this.GetUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "AdministrarKardexPersonal/ExperienciaLaboral/EditarExperienciaLaboralKardex";
        }
        PersonaExperienciaLaboral experiencialaboral = modelMapper.map(model, PersonaExperienciaLaboral.class);
        String filename = String.format("%010d", model.getId_persona()) + String.format("%05d", experiencialaboral.getId_experiencia_laboral());
        FileUploadServlet.MultipartRequest(request, model.getImage(), DIRECTORIO_EXPERIENCIALABORAL.key(), "url_experiencia", filename);
        String nombrearchivo = Util.EsImagenModificado(experiencialaboral.getUrl_experiencia(), request.getAttribute("url_experiencia").toString());
        if (!nombrearchivo.equals("image.png")) {
            experiencialaboral.setUrl_experiencia(nombrearchivo);
        }
        mi.ActualizarDatosExperienciaLaboralKardexDocente(experiencialaboral);
        String request = "?id_persona_kardex=" + model.getId_persona_kardex() + "&" + "id_persona=" + model.getId_persona() + "&" + "status=" + Util.encodeValue("alert-success") + "&" + "message=" + Util.encodeValue("Se Actualizo correctamente el registro del documento");
        return "redirect:/ListarExperienciaLaboral.fautapo" + request;
    }

    @RequestMapping(value = "/EliminarExperienciaLaboral.fautapo", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageDeleteResult> EliminarExperienciaLaboral(@ModelAttribute("model") ItemEliminarModel model) {
        Clientes cliente = this.GetUsuario();
        MessageDeleteResult Respuesta = new MessageDeleteResult();
        if (cliente == null) {
            Respuesta.setMessage("Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            Respuesta.setStatus("Error");
            return new ResponseEntity<>(Respuesta, HttpStatus.OK);
        } else {
            try {
                mi.EliminarExperienciaLaboralKardexDocente(model.getId_experiencia_laboral());
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

    @RequestMapping(value = "/ModificarEstadoAprobacionDocumentoExperiencia.fautapo", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageAprobadoResult> ModificarEstadoAprobacionDocumentoExperiencia(@ModelAttribute("model") ItemAprobarModel model) {
        Clientes cliente = this.GetUsuario();
        MessageAprobadoResult Respuesta = new MessageAprobadoResult();
        if (cliente == null) {
            Respuesta.setMessage("Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            Respuesta.setStatus("Error");
            return new ResponseEntity<>(Respuesta, HttpStatus.OK);
        } else {
            try {
                Boolean estado = model.getName() != null ? model.getName().equals("on") ? true : false : false;
                PersonaExperienciaLaboral experiencia = new PersonaExperienciaLaboral();
                experiencia.setId_experiencia_laboral(model.getId_experiencia_laboral());
                experiencia.setAprobado(estado);
                mi.AprobarExperienciaLaboralKardexDocente(experiencia);
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
