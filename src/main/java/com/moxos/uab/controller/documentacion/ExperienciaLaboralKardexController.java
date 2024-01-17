package com.moxos.uab.controller.documentacion;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

import com.moxos.uab.model.jsonmodels.MessageResult;
import com.moxos.uab.model.models.Kardex.*;
import com.moxos.uab.mybatis.entity.*;
import com.moxos.uab.mybatis.entity.DetallePersonaExperienciaLaboral;
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

import static com.moxos.uab.util.Directorio.DIRECTORIO_EXPERIENCIALABORAL;

@Controller
public class ExperienciaLaboralKardexController {

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

    @RequestMapping(value = "/ListaParcialExperienciaLaboral", method = RequestMethod.GET)
    public String ListaParcialExperienciaLaboral(Model modelo) {
        Clientes cliente = this.getUsuario();
        int number = Convert.ToInteger(request, "number");
        int id_persona_kardex = Convert.ToInteger(request, "id_persona_kardex");
        PersonaKardex kardex = new PersonaKardex();
        kardex.setNumber(number);
        kardex.setId_persona_kardex(id_persona_kardex);
        List<DetallePersonaExperienciaLaboral> experiencia = mi.getPersonaSubTotalExperienciaLaboralKardex(kardex);
        int sId_rol = cliente.getId_rol();
        modelo.addAttribute("id_rol", sId_rol);
        modelo.addAttribute("nombres", cliente.getNombres());
        modelo.addAttribute("detalles", experiencia);
        return "Kardex/DetalleParcialExperienciaLaboral";
    }

    @RequestMapping(value = "/ListarExperienciaLaboral", method = RequestMethod.GET)
    public String ListarExperienciaLaboral(Model modelo) {
        Clientes cliente = this.getUsuario();
        int id_persona_kardex = Convert.ToInteger(request, "id_persona_kardex");
        int id_persona = Convert.ToInteger(request, "id_persona");
        String status = Convert.ToString(request, "status");
        String message = Convert.ToString(request, "message");
        List<DetallePersonaExperienciaLaboral> detalleexperiencia = mi.getPersonaTotalExperienciaLaboralKardex(id_persona_kardex);
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
        return "Kardex/ExperienciaLaboral/DetalleExperienciaLaboralKardex";
    }

    @RequestMapping(value = "/FormularioRegistroExperienciaLaboralKardex", method = RequestMethod.GET)
    public String formularioRegistroExperienciaLaboralKardex(@ModelAttribute("parametro") ParametroCreacionModel parametro, Model modelo) {
        List<ListViewItem> tipoexperiencia = new ArrayList<>();
        tipoexperiencia.add(new ListViewItem("", "NINGUNO"));
        tipoexperiencia.add(new ListViewItem("EXPERIENCIA PROFESIONAL/LABORAL", "EXPERIENCIA PROFESIONAL/LABORAL"));
        tipoexperiencia.add(new ListViewItem("EXPERIENCIA ACADEMICA-DOCENCIA", "EXPERIENCIA ACADEMICA-DOCENCIA"));
        tipoexperiencia.add(new ListViewItem("EXPERIENCIA TRIBUNAL EN EVENTOS ACADEMICOS", "EXPERIENCIA TRIBUNAL EN EVENTOS ACADEMICOS"));
        tipoexperiencia.add(new ListViewItem("EXPERIENCIA CARGOS JERARQUICOS UNIVERSITARIOS", "EXPERIENCIA CARGOS JERARQUICOS UNIVERSITARIOS"));
        tipoexperiencia.add(new ListViewItem("EXPERIENCIA ADMINISTRATIVAS UNIVERSITARIA", "EXPERIENCIA ADMINISTRATIVAS UNIVERSITARIA"));
        PersonaExperienciaLaboralModel model = null;
        if (parametro.getId_experiencia_laboral() != null) {
            PersonaExperienciaLaboral experiencialaboral = mi.getPersonaExperienciaLaboralKardex(parametro.getId_experiencia_laboral());
            parametro.setTipo_experiencia_laboral(experiencialaboral.getTipo_experiencia_laboral());
            model = modelMapper.map(experiencialaboral, PersonaExperienciaLaboralModel.class);
            model.setText_inicio(Convert.ToString(model.getInicio(), "yyyy-MM-dd"));
            model.setText_fin(Convert.ToString(model.getFin(), "yyyy-MM-dd"));
            model.setId_persona(parametro.getId_persona());
            parametro.setId_persona_kardex(model.getId_persona_kardex());
            modelo.addAttribute("accion", "RegistrarModificarExperiencia");
        } else {
            parametro.setTipo_experiencia_laboral("");
            modelo.addAttribute("accion", "RegistrarPersonaExperienciaKardex");
        }
        modelo.addAttribute("detalleexperiencia", tipoexperiencia);
        modelo.addAttribute("id_rol", this.getUsuario().getId_rol());
        modelo.addAttribute("nombres", this.getUsuario().getNombres());
        modelo.addAttribute("id_docente", this.getUsuario().getId_usuario());
        modelo.addAttribute("mensaje", "Debe seleccionar el tipo de experiencia laboral");
        modelo.addAttribute("parametro", parametro);
        modelo.addAttribute("model", model);
        return "Kardex/ExperienciaLaboral/NuevoExperienciaLaboralKardex";
    }

    @RequestMapping(value = "/SeleccionarPersonaExperienciaKardex", method = RequestMethod.GET)
    public String seleccionarTipoExperienciaLaboral(@ModelAttribute("parametro") ParametroCreacionModel parametro, Model modelo) {
        PersonaExperienciaLaboralModel model = null;

        if (parametro.getId_experiencia_laboral() != null) {
            PersonaExperienciaLaboral experiencialaboral = mi.getPersonaExperienciaLaboralKardex(parametro.getId_experiencia_laboral());
            model = modelMapper.map(experiencialaboral, PersonaExperienciaLaboralModel.class);
            model.setText_inicio(Convert.ToString(model.getInicio(), "yyyy-MM-dd"));
            model.setText_fin(Convert.ToString(model.getFin(), "yyyy-MM-dd"));
            modelo.addAttribute("accion", "RegistrarModificarExperiencia");
        } else {
            model = new PersonaExperienciaLaboralModel();
            model.setId_persona_kardex(parametro.getId_persona_kardex());
            modelo.addAttribute("accion", "RegistrarPersonaExperienciaKardex");
        }
        model.setTipo_experiencia_laboral(parametro.getTipo_experiencia_laboral());
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString().replace("-", "").toUpperCase();
        String tipo = model.getTipo_experiencia_laboral();
        model.setId_persona(parametro.getId_persona());
        model.setUUID(randomUUIDString);
        model.setRoot(parametro.getRoot());
        String url = "";
        switch (tipo) {
            case "EXPERIENCIA PROFESIONAL/LABORAL":
                model.setGestion("0");
                model.setCalificacion("--");
                url = "Kardex/ExperienciaLaboral/Tipoexperiencianuevo/_experiecialaboral";
                break;
            case "EXPERIENCIA ACADEMICA-DOCENCIA":
                model.setGestion("0");
                model.setCalificacion("--");
                url = "Kardex/ExperienciaLaboral/Tipoexperiencianuevo/_experieciadocencia";
                break;
            case "EXPERIENCIA TRIBUNAL EN EVENTOS ACADEMICOS":
                model.setRefrencia("--");
                model.setCalificacion("--");
                model.setText_inicio(Convert.ToString(new Date(), "yyyy-MM-dd"));
                model.setText_fin(Convert.ToString(new Date(), "yyyy-MM-dd"));
                url = "Kardex/ExperienciaLaboral/Tipoexperiencianuevo/_experieciatribunal";
                break;
            case "EXPERIENCIA CARGOS JERARQUICOS UNIVERSITARIOS":
                model.setRefrencia("--");
                model.setCalificacion("--");
                model.setText_inicio(Convert.ToString(new Date(), "yyyy-MM-dd"));
                model.setText_fin(Convert.ToString(new Date(), "yyyy-MM-dd"));
                url = "Kardex/ExperienciaLaboral/Tipoexperiencianuevo/_experieciacargosjerarquicos";
                break;
            case "EXPERIENCIA ADMINISTRATIVAS UNIVERSITARIA":
                model.setGestion("0");
                model.setCalificacion("--");
                model.setInstitucion("UNIVERSIDAD AUTONOMA DEL BENI \"JOSE BALLIVIAN\"");
                url = "Kardex/ExperienciaLaboral/Tipoexperiencianuevo/_experieciauniversitaria";
                break;
            default:
                modelo.addAttribute("mensaje", "Debe seleccionar el tipo de experiencia laboral");
                url = "Kardex/ExperienciaLaboral/Tipoexperiencianuevo/_aviso";
                break;
        }
        modelo.addAttribute("model", model);
        return url;
    }

    @RequestMapping(value = "/RegistrarPersonaExperienciaKardex", method = RequestMethod.POST)
    public String RegistrarPersonaExperienciaKardex(@ModelAttribute("model") @Validated PersonaExperienciaLaboralModel model, BindingResult result, Model modelo) {
        if (model.getUrl_experiencia().equals("image.png")) {
            if (model.getImage() == null || model.getImage().isEmpty()) {
                result.addError(new FieldError("url_experiencia", "url_experiencia", "Debe cargar la image de su fotocopia del certificado de trabajo"));
            }
        }
        model.setMostrar(model.getText_mostrar() != null ? model.getText_mostrar().equals("on") ? true : false : false);
        model.setFin(Convert.ToDate(model.getText_fin(), "yyyy-MM-dd"));
        model.setInicio(Convert.ToDate(model.getText_inicio(), "yyyy-MM-dd"));
        if (result.hasErrors()) {
            List<ListViewItem> tipoexperiencia = new ArrayList<>();
            tipoexperiencia.add(new ListViewItem("", "NINGUNO"));
            tipoexperiencia.add(new ListViewItem("EXPERIENCIA PROFESIONAL/LABORAL", "EXPERIENCIA PROFESIONAL/LABORAL"));
            tipoexperiencia.add(new ListViewItem("EXPERIENCIA ACADEMICA-DOCENCIA", "EXPERIENCIA ACADEMICA-DOCENCIA"));
            tipoexperiencia.add(new ListViewItem("EXPERIENCIA TRIBUNAL EN EVENTOS ACADEMICOS", "EXPERIENCIA TRIBUNAL EN EVENTOS ACADEMICOS"));
            tipoexperiencia.add(new ListViewItem("EXPERIENCIA CARGOS JERARQUICOS UNIVERSITARIOS", "EXPERIENCIA CARGOS JERARQUICOS UNIVERSITARIOS"));
            tipoexperiencia.add(new ListViewItem("EXPERIENCIA ADMINISTRATIVAS UNIVERSITARIA", "EXPERIENCIA ADMINISTRATIVAS UNIVERSITARIA"));
            modelo.addAttribute("detalleexperiencia", tipoexperiencia);
            modelo.addAttribute("id_rol", this.getUsuario().getId_rol());
            modelo.addAttribute("nombres", this.getUsuario().getNombres());
            ParametroCreacionModel parametro = new ParametroCreacionModel();
            parametro.setTipo_experiencia_laboral(model.getTipo_experiencia_laboral());
            parametro.setId_persona_kardex(model.getId_persona_kardex());
            parametro.setId_persona(model.getId_persona());
            parametro.setRoot(model.getRoot());
            modelo.addAttribute("parametro", parametro);
            modelo.addAttribute("model", model);
            return "Kardex/ExperienciaLaboral/NuevoExperienciaLaboralKardex";
        }
        PersonaExperienciaLaboral experiencialaboral = modelMapper.map(model, PersonaExperienciaLaboral.class);
        int id_experiencia_laboral = mi.registrarExperienciaLaboralKardex(experiencialaboral);
        String filename = String.format("%010d", model.getId_persona()) + String.format("%05d", id_experiencia_laboral);
        FileUploadServlet.MultipartRequest(request, model.getImage(), path + DIRECTORIO_EXPERIENCIALABORAL.key(), "url_experiencia", filename);
        String nombrearchivo = Utils.EsImagenModificado(experiencialaboral.getUrl_experiencia(), request.getAttribute("url_experiencia").toString());
        if (!nombrearchivo.equals("image.png")) {
            experiencialaboral.setId_experiencia_laboral(id_experiencia_laboral);
            experiencialaboral.setUrl_experiencia(nombrearchivo);
            mi.actualizarImagenExperienciaLaboralKardexDocente(experiencialaboral);
        }
        String request = "?id_persona_kardex=" + model.getId_persona_kardex() + "&" + "id_persona=" + model.getId_persona() + "&" + "status=" + Utils.encodeValue("alert-success") + "&" + "message=" + Utils.encodeValue("Se grabo correctamente el registro del documento");
        return "redirect:/ListarExperienciaLaboral" + request;
    }

    @RequestMapping(value = "/RegistrarModificarExperiencia", method = RequestMethod.POST)
    public String RegistrarModificarExperiencia(@ModelAttribute("model") @Validated PersonaExperienciaLaboralModel model, BindingResult result, Model modelo) {
        if (model.getUrl_experiencia().equals("image.png")) {
            if (model.getImage() == null || model.getImage().isEmpty()) {
                result.addError(new FieldError("url_experiencia", "url_experiencia", "Debe cargar la image de su fotocopia del certificado de trabajo"));
            }
        }
        model.setMostrar(model.getText_mostrar() != null ? model.getText_mostrar().equals("on") ? true : false : false);
        model.setFin(Convert.ToDate(model.getText_fin(), "yyyy-MM-dd"));
        model.setInicio(Convert.ToDate(model.getText_inicio(), "yyyy-MM-dd"));
        if (result.hasErrors()) {
            List<ListViewItem> tipoexperiencia = new ArrayList<>();
            tipoexperiencia.add(new ListViewItem("", "NINGUNO"));
            tipoexperiencia.add(new ListViewItem("EXPERIENCIA PROFESIONAL/LABORAL", "EXPERIENCIA PROFESIONAL/LABORAL"));
            tipoexperiencia.add(new ListViewItem("EXPERIENCIA ACADEMICA-DOCENCIA", "EXPERIENCIA ACADEMICA-DOCENCIA"));
            tipoexperiencia.add(new ListViewItem("EXPERIENCIA TRIBUNAL EN EVENTOS ACADEMICOS", "EXPERIENCIA TRIBUNAL EN EVENTOS ACADEMICOS"));
            tipoexperiencia.add(new ListViewItem("EXPERIENCIA CARGOS JERARQUICOS UNIVERSITARIOS", "EXPERIENCIA CARGOS JERARQUICOS UNIVERSITARIOS"));
            tipoexperiencia.add(new ListViewItem("EXPERIENCIA ADMINISTRATIVAS UNIVERSITARIA", "EXPERIENCIA ADMINISTRATIVAS UNIVERSITARIA"));
            modelo.addAttribute("detalleexperiencia", tipoexperiencia);
            modelo.addAttribute("id_rol", this.getUsuario().getId_rol());
            modelo.addAttribute("nombres", this.getUsuario().getNombres());
            ParametroCreacionModel parametro = new ParametroCreacionModel();
            parametro.setTipo_experiencia_laboral(model.getTipo_experiencia_laboral());
            parametro.setId_persona_kardex(model.getId_persona_kardex());
            parametro.setId_persona(model.getId_persona());
            parametro.setRoot(model.getRoot());
            modelo.addAttribute("parametro", parametro);
            modelo.addAttribute("model", model);
            return "Kardex/ExperienciaLaboral/NuevoExperienciaLaboralKardex";
        }
        PersonaExperienciaLaboral experiencialaboral = modelMapper.map(model, PersonaExperienciaLaboral.class);
        String filename = String.format("%010d", model.getId_persona()) + String.format("%05d", experiencialaboral.getId_experiencia_laboral());
        FileUploadServlet.MultipartRequest(request, model.getImage(), path + DIRECTORIO_EXPERIENCIALABORAL.key(), "url_experiencia", filename);
        String nombrearchivo = Utils.EsImagenModificado(experiencialaboral.getUrl_experiencia(), request.getAttribute("url_experiencia").toString());
        if (!nombrearchivo.equals("image.png")) {
            experiencialaboral.setUrl_experiencia(nombrearchivo);
        }
        mi.actualizarDatosExperienciaLaboralKardexDocente(experiencialaboral);
        String request = "?id_persona_kardex=" + model.getId_persona_kardex() + "&" + "id_persona=" + model.getId_persona() + "&" + "status=" + Utils.encodeValue("alert-success") + "&" + "message=" + Utils.encodeValue("Se Actualizo correctamente el registro del documento");
        return "redirect:/ListarExperienciaLaboral" + request;
    }

    @RequestMapping(value = "/EliminarExperienciaLaboral", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageDeleteResult> EliminarExperienciaLaboral(@ModelAttribute("model") ItemEliminarModel model) {
        Clientes cliente = this.getUsuario();
        MessageDeleteResult Respuesta = new MessageDeleteResult();
        if (cliente == null) {
            Respuesta.setMessage("Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            Respuesta.setStatus("Error");
            return new ResponseEntity<>(Respuesta, HttpStatus.OK);
        } else {
            try {
                mi.eliminarExperienciaLaboralKardexDocente(model.getId_experiencia_laboral());
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

    @RequestMapping(value = "/ModificarEstadoAprobacionDocumentoExperiencia", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageAprobadoResult> ModificarEstadoAprobacionDocumentoExperiencia(@ModelAttribute("model") ItemAprobarModel model) {
        Clientes cliente = this.getUsuario();
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
                mi.aprobarExperienciaLaboralKardexDocente(experiencia);
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
