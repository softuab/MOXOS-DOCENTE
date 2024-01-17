package com.moxos.uab.controller.documentacion;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

import com.moxos.uab.model.jsonmodels.MessageResult;
import com.moxos.uab.model.models.Kardex.*;
import com.moxos.uab.mybatis.entity.Clientes;
import com.moxos.uab.mybatis.entity.ListViewItem;
import com.moxos.uab.mybatis.entity.PersonaActividadesAcademicas;
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

import static com.moxos.uab.util.Directorio.DIRECTORIO_ACTIVIDADES_ACADEMICAS;

@Controller
public class ActividadesAcademicasKardexController {

    @Autowired
    private MiFacade mi;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ModelMapper modelMapper;

    @Value("${app.upload.path}")
    private String path;

    private Clientes GetUsuario() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        Clientes cliente = (Clientes) attr.getRequest().getSession().getAttribute("__sess_cliente");
        return cliente;
    }

    @RequestMapping(value = "/ListaParcialActividadesAcademicas", method = RequestMethod.GET)
    public String ListaParcialActividadesAcademicas(Model modelo) {
        Clientes cliente = this.GetUsuario();
        int number = Convert.ToInteger(request, "number");
        int id_persona_kardex = Convert.ToInteger(request, "id_persona_kardex");
        PersonaKardex kardex = new PersonaKardex();
        kardex.setNumber(number);
        kardex.setId_persona_kardex(id_persona_kardex);
        List<PersonaActividadesAcademicas> actividadesacademicas = mi.getPersonaSubTotalActividadesAcademicasKardex(kardex);
        int sId_rol = cliente.getId_rol();
        modelo.addAttribute("id_rol", sId_rol);
        modelo.addAttribute("nombres", cliente.getNombres());
        modelo.addAttribute("detalles", actividadesacademicas);
        return "Kardex/DetalleParcialActividadesAcademicas";
    }

    @RequestMapping(value = "/ListaActividadesAcademicas", method = RequestMethod.GET)
    public String ListaActividadesAcademicas(Model modelo) {
        Clientes cliente = this.GetUsuario();
        int id_persona_kardex = Convert.ToInteger(request, "id_persona_kardex");
        int id_persona = Convert.ToInteger(request, "id_persona");
        String status = Convert.ToString(request, "status");
        String message = Convert.ToString(request, "message");
        List<PersonaActividadesAcademicas> actividadesacademicas = mi.getPersonaTotalActividadesAcademicasKardex(id_persona_kardex);
        int sId_rol = cliente.getId_rol();
        MessageResult result = new MessageResult();
        result.setMessage(message);
        result.setStatus(status);
        modelo.addAttribute("id_rol", sId_rol);
        modelo.addAttribute("nombres", cliente.getNombres());
        modelo.addAttribute("detalles", actividadesacademicas);
        modelo.addAttribute("id_persona", id_persona);
        modelo.addAttribute("result", result);
        modelo.addAttribute("id_persona_kardex", id_persona_kardex);
        return "Kardex/ActividadesAcademicas/DetalleActividadesAcademicas";
    }

    @RequestMapping(value = "/FormularioRegistroActividadesAcademicasKardex", method = RequestMethod.GET)
    public String FormularioRegistroActividadesAcademicasKardex(@ModelAttribute("parametro") ParametroCreacionModel parametro, Model modelo) {
        List<ListViewItem> tipodocumento = new ArrayList<>();
        tipodocumento.add(new ListViewItem("", "NINGUNO"));
        tipodocumento.add(new ListViewItem("RESOLUCION DEL HONORABLE CONSEJO UNIVERSITARIO", "RESOLUCION DEL HONORABLE CONSEJO UNIVERSITARIO"));
        tipodocumento.add(new ListViewItem("RESOLUCION RECTORAL", "RESOLUCION RECTORAL"));
        tipodocumento.add(new ListViewItem("RESOLUCION FACULTATIVO", "RESOLUCION FACULTATIVO"));
        tipodocumento.add(new ListViewItem("RESOLUCION DE CARRERA", "RESOLUCION DE CARRERA"));
        tipodocumento.add(new ListViewItem("MEMORÁNDUM RECTORAL", "MEMORÁNDUM RECTORAL"));
        tipodocumento.add(new ListViewItem("MEMORÁNDUM FACULTATIVO", "MEMORÁNDUM FACULTATIVO"));
        tipodocumento.add(new ListViewItem("MEMORÁNDUM DE CARRERA", "MEMORÁNDUM DE CARRERA"));
        List<ListViewItem> tipo_de_actividad = new ArrayList<>();
        tipo_de_actividad.add(new ListViewItem("", "NINGUNO"));
        tipo_de_actividad.add(new ListViewItem("MIEMBRO DEL HONORABLE CONSEJO UNIVERSITARIO", "MIEMBRO DEL HONORABLE CONSEJO UNIVERSITARIO"));
        tipo_de_actividad.add(new ListViewItem("MIEMBRO DEL HONORABLE CONSEJO FACULTATIVO", "MIEMBRO DEL HONORABLE CONSEJO FACULTATIVO"));
        tipo_de_actividad.add(new ListViewItem("MIEMBRO DEL HONORABLE CONSEJO DE CARRERA", "MIEMBRO DEL HONORABLE CONSEJO DE CARRERA"));
        tipo_de_actividad.add(new ListViewItem("MIEMBRO DE TRIBUNALES DE EXÁMENES DE COMPETENCIA O DE GRADO Y/O POSGRADO", "MIEMBRO DE TRIBUNALES DE EXÁMENES DE COMPETENCIA O DE GRADO Y/O POSGRADO"));
        tipo_de_actividad.add(new ListViewItem("MIEMBRO DE COMISIONES INSTITUCIONALES (UABJB)", "MIEMBRO DE COMISIONES INSTITUCIONALES (UABJB)"));
        tipo_de_actividad.add(new ListViewItem("OTRO TIPO DE PARTICIPACIÓN EN ACTIVIDADES DE COGOBIERNO", "OTRO TIPO DE PARTICIPACIÓN EN ACTIVIDADES DE COGOBIERNO"));
        List<ListViewItem> mes = new ArrayList<>();
        mes.add(new ListViewItem("", "NINGUNO"));
        mes.add(new ListViewItem("ENERO", "ENERO"));
        mes.add(new ListViewItem("FEBRERO", "FEBRERO"));
        mes.add(new ListViewItem("MARZO", "MARZO"));
        mes.add(new ListViewItem("ABRIL", "ABRIL"));
        mes.add(new ListViewItem("MAYO", "MAYO"));
        mes.add(new ListViewItem("JUNIO", "JUNIO"));
        mes.add(new ListViewItem("JULIO", "JULIO"));
        mes.add(new ListViewItem("AGOSTO", "AGOSTO"));
        mes.add(new ListViewItem("SEPTIEMBRE", "SEPTIEMBRE"));
        mes.add(new ListViewItem("OCTUBRE", "OCTUBRE"));
        mes.add(new ListViewItem("NOVIEMBRE", "NOVIEMBRE"));
        mes.add(new ListViewItem("DICIEMBRE", "DICIEMBRE"));
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString().replace("-", "").toUpperCase();
        PersonaActividadesAcademicasModel model = null;
        if (parametro.getId_activades_academicas() == null) {
            model = new PersonaActividadesAcademicasModel();
            model.setId_persona(parametro.getId_persona());
            model.setId_persona_kardex(parametro.getId_persona_kardex());
            model.setUUID(randomUUIDString);
            model.setRoot(parametro.getRoot());
            modelo.addAttribute("detalletipodocumento", tipodocumento);
            modelo.addAttribute("detalletipo_de_actividad", tipo_de_actividad);
            modelo.addAttribute("detallemes", mes);
            modelo.addAttribute("id_rol", this.GetUsuario().getId_rol());
            modelo.addAttribute("nombres", this.GetUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "Kardex/ActividadesAcademicas/NuevoActividadesAcademicas";
        } else {
            PersonaActividadesAcademicas actividadesacademicas = mi.getPersonaActividadesAcademicasKardex(parametro.getId_activades_academicas());
            model = modelMapper.map(actividadesacademicas, PersonaActividadesAcademicasModel.class);
            model.setId_persona(parametro.getId_persona());
            model.setUUID(randomUUIDString);
            model.setRoot(parametro.getRoot());
            modelo.addAttribute("detalletipodocumento", tipodocumento);
            modelo.addAttribute("detalletipo_de_actividad", tipo_de_actividad);
            modelo.addAttribute("detallemes", mes);
            modelo.addAttribute("id_rol", this.GetUsuario().getId_rol());
            modelo.addAttribute("nombres", this.GetUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "Kardex/ActividadesAcademicas/EditarActividadesAcademicas";
        }
    }

    @RequestMapping(value = "/RegistrarPersonaActividadesAcademicasKardex", method = RequestMethod.POST)
    public String RegistrarPersonaActividadesAcademicasKardex(@ModelAttribute("model") @Validated PersonaActividadesAcademicasModel model, BindingResult result, Model modelo) {
        if (model.getUrl_actividades_academicas().equals("image.png")) {
            if (model.getImage() == null || model.getImage().isEmpty()) {
                result.addError(new FieldError("url_actividades_academicas", "url_actividades_academicas", "Debe cargar la image de su fotocopia del documento"));
            }
        }
        model.setMostrar(model.getText_mostrar() != null ? model.getText_mostrar().equals("on") ? true : false : false);
        if (result.hasErrors()) {
            List<ListViewItem> tipodocumento = new ArrayList<>();
            tipodocumento.add(new ListViewItem("", "NINGUNO"));
            tipodocumento.add(new ListViewItem("RESOLUCION DEL HONORABLE CONSEJO UNIVERSITARIO", "RESOLUCION DEL HONORABLE CONSEJO UNIVERSITARIO"));
            tipodocumento.add(new ListViewItem("RESOLUCION RECTORAL", "RESOLUCION RECTORAL"));
            tipodocumento.add(new ListViewItem("RESOLUCION FACULTATIVO", "RESOLUCION FACULTATIVO"));
            tipodocumento.add(new ListViewItem("RESOLUCION DE CARRERA", "RESOLUCION DE CARRERA"));
            tipodocumento.add(new ListViewItem("MEMORÁNDUM RECTORAL", "MEMORÁNDUM RECTORAL"));
            tipodocumento.add(new ListViewItem("MEMORÁNDUM FACULTATIVO", "MEMORÁNDUM FACULTATIVO"));
            tipodocumento.add(new ListViewItem("MEMORÁNDUM DE CARRERA", "MEMORÁNDUM DE CARRERA"));
            List<ListViewItem> tipo_de_actividad = new ArrayList<>();
            tipo_de_actividad.add(new ListViewItem("", "NINGUNO"));
            tipo_de_actividad.add(new ListViewItem("MIEMBRO DEL HONORABLE CONSEJO UNIVERSITARIO", "MIEMBRO DEL HONORABLE CONSEJO UNIVERSITARIO"));
            tipo_de_actividad.add(new ListViewItem("MIEMBRO DEL HONORABLE CONSEJO FACULTATIVO", "MIEMBRO DEL HONORABLE CONSEJO FACULTATIVO"));
            tipo_de_actividad.add(new ListViewItem("MIEMBRO DEL HONORABLE CONSEJO DE CARRERA", "MIEMBRO DEL HONORABLE CONSEJO DE CARRERA"));
            tipo_de_actividad.add(new ListViewItem("MIEMBRO DE TRIBUNALES DE EXÁMENES DE COMPETENCIA O DE GRADO Y/O POSGRADO", "MIEMBRO DE TRIBUNALES DE EXÁMENES DE COMPETENCIA O DE GRADO Y/O POSGRADO"));
            tipo_de_actividad.add(new ListViewItem("MIEMBRO DE COMISIONES INSTITUCIONALES (UABJB)", "MIEMBRO DE COMISIONES INSTITUCIONALES (UABJB)"));
            tipo_de_actividad.add(new ListViewItem("OTRO TIPO DE PARTICIPACIÓN EN ACTIVIDADES DE COGOBIERNO", "OTRO TIPO DE PARTICIPACIÓN EN ACTIVIDADES DE COGOBIERNO"));
            List<ListViewItem> mes = new ArrayList<>();
            mes.add(new ListViewItem("", "NINGUNO"));
            mes.add(new ListViewItem("ENERO", "ENERO"));
            mes.add(new ListViewItem("FEBRERO", "FEBRERO"));
            mes.add(new ListViewItem("MARZO", "MARZO"));
            mes.add(new ListViewItem("ABRIL", "ABRIL"));
            mes.add(new ListViewItem("MAYO", "MAYO"));
            mes.add(new ListViewItem("JUNIO", "JUNIO"));
            mes.add(new ListViewItem("JULIO", "JULIO"));
            mes.add(new ListViewItem("AGOSTO", "AGOSTO"));
            mes.add(new ListViewItem("SEPTIEMBRE", "SEPTIEMBRE"));
            mes.add(new ListViewItem("OCTUBRE", "OCTUBRE"));
            mes.add(new ListViewItem("NOVIEMBRE", "NOVIEMBRE"));
            mes.add(new ListViewItem("DICIEMBRE", "DICIEMBRE"));
            modelo.addAttribute("detalletipodocumento", tipodocumento);
            modelo.addAttribute("detalletipo_de_actividad", tipo_de_actividad);
            modelo.addAttribute("detallemes", mes);
            modelo.addAttribute("id_rol", this.GetUsuario().getId_rol());
            modelo.addAttribute("nombres", this.GetUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "Kardex/ActividadesAcademicas/NuevoActividadesAcademicas";
        }
        PersonaActividadesAcademicas actividadesacademicas = modelMapper.map(model, PersonaActividadesAcademicas.class);
        int id_activades_academicas = mi.registrarNuevoActividadesAcademicasKardex(actividadesacademicas);
        String filename = String.format("%010d", model.getId_persona()) + String.format("%05d", id_activades_academicas);
        FileUploadServlet.MultipartRequest(request, model.getImage(), path + DIRECTORIO_ACTIVIDADES_ACADEMICAS.key(), "url_actividades_academicas", filename);
        String nombrearchivo = Utils.EsImagenModificado(actividadesacademicas.getUrl_actividades_academicas(), request.getAttribute("url_actividades_academicas").toString());
        if (!nombrearchivo.equals("image.png")) {
            actividadesacademicas.setId_activades_academicas(id_activades_academicas);
            actividadesacademicas.setUrl_actividades_academicas(nombrearchivo);
            mi.actualizarImagenActividadesAcademicasKardexDocente(actividadesacademicas);
        }
        String request = "?id_persona_kardex=" + model.getId_persona_kardex() + "&" + "id_persona=" + model.getId_persona() + "&" + "status=" + Utils.encodeValue("alert-success") + "&" + "message=" + Utils.encodeValue("Se grabo correctamente el registro del documento");
        return "redirect:/ListaActividadesAcademicas" + request;
    }

    @RequestMapping(value = "/RegistrarModificarActividadesAcademicasKardex", method = RequestMethod.POST)
    public String RegistrarModificarActividadesAcademicasKardex(@ModelAttribute("model") @Validated PersonaActividadesAcademicasModel model, BindingResult result, Model modelo) {
        if (model.getUrl_actividades_academicas().equals("image.png")) {
            if (model.getImage() == null || model.getImage().isEmpty()) {
                result.addError(new FieldError("url_actividades_academicas", "url_actividades_academicas", "Debe cargar la image de su fotocopia del documento"));
            }
        }
        model.setMostrar(model.getText_mostrar() != null ? model.getText_mostrar().equals("on") ? true : false : false);
        if (result.hasErrors()) {
            List<ListViewItem> tipodocumento = new ArrayList<>();
            tipodocumento.add(new ListViewItem("", "NINGUNO"));
            tipodocumento.add(new ListViewItem("RESOLUCION DEL HONORABLE CONSEJO UNIVERSITARIO", "RESOLUCION DEL HONORABLE CONSEJO UNIVERSITARIO"));
            tipodocumento.add(new ListViewItem("RESOLUCION RECTORAL", "RESOLUCION RECTORAL"));
            tipodocumento.add(new ListViewItem("RESOLUCION FACULTATIVO", "RESOLUCION FACULTATIVO"));
            tipodocumento.add(new ListViewItem("RESOLUCION DE CARRERA", "RESOLUCION DE CARRERA"));
            tipodocumento.add(new ListViewItem("MEMORÁNDUM RECTORAL", "MEMORÁNDUM RECTORAL"));
            tipodocumento.add(new ListViewItem("MEMORÁNDUM FACULTATIVO", "MEMORÁNDUM FACULTATIVO"));
            tipodocumento.add(new ListViewItem("MEMORÁNDUM DE CARRERA", "MEMORÁNDUM DE CARRERA"));
            List<ListViewItem> tipo_de_actividad = new ArrayList<>();
            tipo_de_actividad.add(new ListViewItem("", "NINGUNO"));
            tipo_de_actividad.add(new ListViewItem("MIEMBRO DEL HONORABLE CONSEJO UNIVERSITARIO", "MIEMBRO DEL HONORABLE CONSEJO UNIVERSITARIO"));
            tipo_de_actividad.add(new ListViewItem("MIEMBRO DEL HONORABLE CONSEJO FACULTATIVO", "MIEMBRO DEL HONORABLE CONSEJO FACULTATIVO"));
            tipo_de_actividad.add(new ListViewItem("MIEMBRO DEL HONORABLE CONSEJO DE CARRERA", "MIEMBRO DEL HONORABLE CONSEJO DE CARRERA"));
            tipo_de_actividad.add(new ListViewItem("MIEMBRO DE TRIBUNALES DE EXÁMENES DE COMPETENCIA O DE GRADO Y/O POSGRADO", "MIEMBRO DE TRIBUNALES DE EXÁMENES DE COMPETENCIA O DE GRADO Y/O POSGRADO"));
            tipo_de_actividad.add(new ListViewItem("MIEMBRO DE COMISIONES INSTITUCIONALES (UABJB)", "MIEMBRO DE COMISIONES INSTITUCIONALES (UABJB)"));
            tipo_de_actividad.add(new ListViewItem("OTRO TIPO DE PARTICIPACIÓN EN ACTIVIDADES DE COGOBIERNO", "OTRO TIPO DE PARTICIPACIÓN EN ACTIVIDADES DE COGOBIERNO"));
            List<ListViewItem> mes = new ArrayList<>();
            mes.add(new ListViewItem("", "NINGUNO"));
            mes.add(new ListViewItem("ENERO", "ENERO"));
            mes.add(new ListViewItem("FEBRERO", "FEBRERO"));
            mes.add(new ListViewItem("MARZO", "MARZO"));
            mes.add(new ListViewItem("ABRIL", "ABRIL"));
            mes.add(new ListViewItem("MAYO", "MAYO"));
            mes.add(new ListViewItem("JUNIO", "JUNIO"));
            mes.add(new ListViewItem("JULIO", "JULIO"));
            mes.add(new ListViewItem("AGOSTO", "AGOSTO"));
            mes.add(new ListViewItem("SEPTIEMBRE", "SEPTIEMBRE"));
            mes.add(new ListViewItem("OCTUBRE", "OCTUBRE"));
            mes.add(new ListViewItem("NOVIEMBRE", "NOVIEMBRE"));
            mes.add(new ListViewItem("DICIEMBRE", "DICIEMBRE"));
            modelo.addAttribute("detalletipodocumento", tipodocumento);
            modelo.addAttribute("detalletipo_de_actividad", tipo_de_actividad);
            modelo.addAttribute("detallemes", mes);
            modelo.addAttribute("id_rol", this.GetUsuario().getId_rol());
            modelo.addAttribute("nombres", this.GetUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "Kardex/ActividadesAcademicas/EditarActividadesAcademicas";
        }
        PersonaActividadesAcademicas actividadesacademicas = modelMapper.map(model, PersonaActividadesAcademicas.class);
        String filename = String.format("%010d", model.getId_persona()) + String.format("%05d", actividadesacademicas.getId_activades_academicas());
        FileUploadServlet.MultipartRequest(request, model.getImage(), path + DIRECTORIO_ACTIVIDADES_ACADEMICAS.key(), "url_actividades_academicas", filename);
        String nombrearchivo = Utils.EsImagenModificado(actividadesacademicas.getUrl_actividades_academicas(), request.getAttribute("url_actividades_academicas").toString());
        if (!nombrearchivo.equals("image.png")) {
            actividadesacademicas.setUrl_actividades_academicas(nombrearchivo);
        }
        mi.actualizarDatosiActividadesAcademicasKardexDocente(actividadesacademicas);
        String request = "?id_persona_kardex=" + model.getId_persona_kardex() + "&" + "id_persona=" + model.getId_persona() + "&" + "status=" + Utils.encodeValue("alert-success") + "&" + "message=" + Utils.encodeValue("Se Actualizo correctamente el registro del documento");
        return "redirect:/ListaActividadesAcademicas" + request;
    }

    @RequestMapping(value = "/EliminarActividadesAcademicas", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageDeleteResult> EliminarActividadesAcademicas(@ModelAttribute("model") ItemEliminarModel model) {
        Clientes cliente = this.GetUsuario();
        MessageDeleteResult Respuesta = new MessageDeleteResult();
        if (cliente == null) {
            Respuesta.setMessage("Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            Respuesta.setStatus("Error");
            return new ResponseEntity<>(Respuesta, HttpStatus.OK);
        } else {
            try {
                mi.eliminarActividadesAcademicasKardexDocente(model.getId_activades_academicas());
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

    @RequestMapping(value = "/ModificarEstadoAprobacionDocumentoActividadesAcademicas", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageAprobadoResult> ModificarEstadoAprobacionDocumentoActividadesAcademicas(@ModelAttribute("model") ItemAprobarModel model) {
        Clientes cliente = this.GetUsuario();
        MessageAprobadoResult Respuesta = new MessageAprobadoResult();
        if (cliente == null) {
            Respuesta.setMessage("Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            Respuesta.setStatus("Error");
            return new ResponseEntity<>(Respuesta, HttpStatus.OK);
        } else {
            try {
                Boolean estado = model.getName() != null ? model.getName().equals("on") ? true : false : false;
                PersonaActividadesAcademicas actividadesacademicas = new PersonaActividadesAcademicas();
                actividadesacademicas.setId_activades_academicas(model.getId_activades_academicas());
                actividadesacademicas.setAprobado(estado);
                mi.aprobarActividadesAcademicasKardexDocente(actividadesacademicas);
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
