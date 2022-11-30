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
import org.fautapo.domain.PersonaActividadesAcademicas;
import org.fautapo.domain.PersonaKardex;
import org.fautapo.domain.logic.MiFacade;
import org.fautapo.model.Kardex.ItemAprobarModel;
import org.fautapo.model.Kardex.ItemEliminarModel;
import org.fautapo.model.Kardex.MessageAprobadoResult;
import org.fautapo.model.Kardex.MessageDeleteResult;
import org.fautapo.model.Kardex.ParametroCreacionModel;
import org.fautapo.model.Kardex.PersonaActividadesAcademicasModel;
import org.fautapo.model.MessageResult;
import static org.fautapo.util.Directorio.DIRECTORIO_ACTIVIDADES_ACADEMICAS;
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
public class ActividadesAcademicasKardexController {

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

    @RequestMapping(value = "/ListaParcialActividadesAcademicas.fautapo", method = RequestMethod.GET)
    public String ListaParcialActividadesAcademicas(Model modelo) {
        Clientes cliente = this.GetUsuario();
        int number = cliente.getInt(request, "number");
        int id_persona_kardex = cliente.getInt(request, "id_persona_kardex");
        PersonaKardex kardex = new PersonaKardex();
        kardex.setNumber(number);
        kardex.setId_persona_kardex(id_persona_kardex);
        List<PersonaActividadesAcademicas> actividadesacademicas = mi.GetPersonaSubTotalActividadesAcademicasKardex(kardex);
        int sId_rol = cliente.getId_rol();
        modelo.addAttribute("id_rol", sId_rol);
        modelo.addAttribute("nombres", cliente.getNombres());
        modelo.addAttribute("detalles", actividadesacademicas);
        return "AdministrarKardexPersonal/DetalleParcialActividadesAcademicas";
    }

    @RequestMapping(value = "/ListaActividadesAcademicas.fautapo", method = RequestMethod.GET)
    public String ListaActividadesAcademicas(Model modelo) {
        Clientes cliente = this.GetUsuario();
        int id_persona_kardex = cliente.getInt(request, "id_persona_kardex");
        int id_persona = cliente.getInt(request, "id_persona");
        String status = cliente.getString(request, "status");
        String message = cliente.getString(request, "message");
        List<PersonaActividadesAcademicas> actividadesacademicas = mi.GetPersonaTotalActividadesAcademicasKardex(id_persona_kardex);
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
        return "AdministrarKardexPersonal/ActividadesAcademicas/DetalleActividadesAcademicas";
    }

    @RequestMapping(value = "/FormularioRegistroActividadesAcademicasKardex.fautapo", method = RequestMethod.GET)
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
            return "AdministrarKardexPersonal/ActividadesAcademicas/NuevoActividadesAcademicas";
        } else {
            PersonaActividadesAcademicas actividadesacademicas = mi.GetPersonaActividadesAcademicasKardex(parametro.getId_activades_academicas());
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
            return "AdministrarKardexPersonal/ActividadesAcademicas/EditarActividadesAcademicas";
        }
    }

    @RequestMapping(value = "/RegistrarPersonaActividadesAcademicasKardex.fautapo", method = RequestMethod.POST)
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
            return "AdministrarKardexPersonal/ActividadesAcademicas/NuevoActividadesAcademicas";
        }
        PersonaActividadesAcademicas actividadesacademicas = modelMapper.map(model, PersonaActividadesAcademicas.class);
        int id_activades_academicas = mi.RegistrarNuevoActividadesAcademicasKardex(actividadesacademicas);
        String filename = String.format("%010d", model.getId_persona()) + String.format("%05d", id_activades_academicas);
        FileUploadServlet.MultipartRequest(request, model.getImage(), DIRECTORIO_ACTIVIDADES_ACADEMICAS.key(), "url_actividades_academicas", filename);
        String nombrearchivo = Util.EsImagenModificado(actividadesacademicas.getUrl_actividades_academicas(), request.getAttribute("url_actividades_academicas").toString());
        if (!nombrearchivo.equals("image.png")) {
            actividadesacademicas.setId_activades_academicas(id_activades_academicas);
            actividadesacademicas.setUrl_actividades_academicas(nombrearchivo);
            mi.ActualizarImagenActividadesAcademicasKardexDocente(actividadesacademicas);
        }
        String request = "?id_persona_kardex=" + model.getId_persona_kardex() + "&" + "id_persona=" + model.getId_persona() + "&" + "status=" + Util.encodeValue("alert-success") + "&" + "message=" + Util.encodeValue("Se grabo correctamente el registro del documento");
        return "redirect:/ListaActividadesAcademicas.fautapo" + request;
    }

    @RequestMapping(value = "/RegistrarModificarActividadesAcademicasKardex.fautapo", method = RequestMethod.POST)
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
            return "AdministrarKardexPersonal/ActividadesAcademicas/EditarActividadesAcademicas";
        }
        PersonaActividadesAcademicas actividadesacademicas = modelMapper.map(model, PersonaActividadesAcademicas.class);
        String filename = String.format("%010d", model.getId_persona()) + String.format("%05d", actividadesacademicas.getId_activades_academicas());
        FileUploadServlet.MultipartRequest(request, model.getImage(), DIRECTORIO_ACTIVIDADES_ACADEMICAS.key(), "url_actividades_academicas", filename);
        String nombrearchivo = Util.EsImagenModificado(actividadesacademicas.getUrl_actividades_academicas(), request.getAttribute("url_actividades_academicas").toString());
        if (!nombrearchivo.equals("image.png")) {
            actividadesacademicas.setUrl_actividades_academicas(nombrearchivo);
        }
        mi.ActualizarDatosiActividadesAcademicasKardexDocente(actividadesacademicas);
        String request = "?id_persona_kardex=" + model.getId_persona_kardex() + "&" + "id_persona=" + model.getId_persona() + "&" + "status=" + Util.encodeValue("alert-success") + "&" + "message=" + Util.encodeValue("Se Actualizo correctamente el registro del documento");
        return "redirect:/ListaActividadesAcademicas.fautapo" + request;
    }

    @RequestMapping(value = "/EliminarActividadesAcademicas.fautapo", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageDeleteResult> EliminarActividadesAcademicas(@ModelAttribute("model") ItemEliminarModel model) {
        Clientes cliente = this.GetUsuario();
        MessageDeleteResult Respuesta = new MessageDeleteResult();
        if (cliente == null) {
            Respuesta.setMessage("Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            Respuesta.setStatus("Error");
            return new ResponseEntity<>(Respuesta, HttpStatus.OK);
        } else {
            try {
                mi.EliminarActividadesAcademicasKardexDocente(model.getId_activades_academicas());
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

    @RequestMapping(value = "/ModificarEstadoAprobacionDocumentoActividadesAcademicas.fautapo", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
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
                mi.AprobarActividadesAcademicasKardexDocente(actividadesacademicas);
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
