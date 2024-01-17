package com.moxos.uab.controller.documentacion;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

import com.moxos.uab.model.jsonmodels.MessageResult;
import com.moxos.uab.model.models.Kardex.*;
import com.moxos.uab.model.models.utility.ListViewItemSelected;
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

import static com.moxos.uab.util.Directorio.DIRECTORIO_FORMACIONACADEMICA;

@Controller
public class FormacionAcademicaKardexController {

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

    @RequestMapping(value = "/ListaParcialFormacionAcademico", method = RequestMethod.GET)
    public String ListaParcialFormacionAcademico(Model modelo) {
        Clientes cliente = this.getUsuario();
        int number = Convert.ToInteger(request, "number");
        int id_persona_kardex = Convert.ToInteger(request, "id_persona_kardex");
        PersonaKardex kardex = new PersonaKardex();
        kardex.setNumber(number);
        kardex.setId_persona_kardex(id_persona_kardex);
        List<DetalleFormacionAcademicaPersonal> formacion = mi.getPersonaSubTotalFormacionAcademicaKardex(kardex);
        int sId_rol = cliente.getId_rol();
        modelo.addAttribute("id_rol", sId_rol);
        modelo.addAttribute("nombres", cliente.getNombres());
        modelo.addAttribute("formacion", formacion);
        return "Kardex/DetalleParcialFormacionAcademica";
    }

    @RequestMapping(value = "/ListarFormacionAcademica", method = RequestMethod.GET)
    public String ListarFormacionAcademica(Model modelo) {
        Clientes cliente = this.getUsuario();
        int id_persona_kardex = Convert.ToInteger(request, "id_persona_kardex");
        int id_persona = Convert.ToInteger(request, "id_persona");
        String status = Convert.ToString(request, "status");
        String message = Convert.ToString(request, "message");
        List<DetalleFormacionAcademicaPersonal> formacion = mi.getPersonaTotalFormacionAcademicaKardex(id_persona_kardex);
        int sId_rol = cliente.getId_rol();
        MessageResult result = new MessageResult();
        result.setMessage(message);
        result.setStatus(status);
        modelo.addAttribute("id_rol", sId_rol);
        modelo.addAttribute("nombres", cliente.getNombres());
        modelo.addAttribute("formacion", formacion);
        modelo.addAttribute("id_persona", id_persona);
        modelo.addAttribute("result", result);
        modelo.addAttribute("id_persona_kardex", id_persona_kardex);
        return "Kardex/FormacionAcademica/DetalleFormacionAcademica";
    }

    @RequestMapping(value = "/GetNivelEstudio", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<ListViewItem>> GetNivelEstudio(@ModelAttribute("tipotitulo") String tipotitulo) {
        List<ListViewItem> nivelestudio = mi.getNivelEstudioPorNivel(tipotitulo);
        if (nivelestudio.isEmpty()) {
            return new ResponseEntity<>(nivelestudio, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(nivelestudio, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/FormularioRegistroFormacionKardex", method = RequestMethod.GET)
    public String FormularioRegistroFormacionKardex(@ModelAttribute("parametro") ParametroCreacionModel parametro, Model modelo) {
        List<ListViewItem> nivelestudio = null;
        List<ListViewItem> programas = mi.getProgramasPregrado();
        List<ListViewItem> tipotitulo = new ArrayList<>();
        tipotitulo.add(new ListViewItem("", "NINGUNO"));
        tipotitulo.add(new ListViewItem("PREGRADO", "PREGRADO"));
        tipotitulo.add(new ListViewItem("POSGRADO", "POSGRADO"));
        List<ListViewItem> universidades = new ArrayList<>();
        universidades.add(new ListViewItem("", "NINGUNO"));
        universidades.add(new ListViewItem("UNIVERSIDAD TÉCNICA DE ORURO", "UNIVERSIDAD TÉCNICA DE ORURO"));
        universidades.add(new ListViewItem("UNIVERSIDAD PÚBLICA DE EL ALTO", "UNIVERSIDAD PÚBLICA DE EL ALTO"));
        universidades.add(new ListViewItem("UNIVERSIDAD NACIONAL SIGLO XX", "UNIVERSIDAD NACIONAL SIGLO XX"));
        universidades.add(new ListViewItem("UNIVERSIDAD MAYOR REAL Y PONTIFICIA SAN FRANCISCO XAVIER DE CHUQUISACA", "UNIVERSIDAD MAYOR REAL Y PONTIFICIA SAN FRANCISCO XAVIER DE CHUQUISACA"));
        universidades.add(new ListViewItem("UNIVERSIDAD MAYOR DE SAN SIMÓN", "UNIVERSIDAD MAYOR DE SAN SIMÓN"));
        universidades.add(new ListViewItem("UNIVERSIDAD MAYOR DE SAN ANDRÉS", "UNIVERSIDAD MAYOR DE SAN ANDRÉS"));
        universidades.add(new ListViewItem("UNIVERSIDAD CATÓLICA BOLIVIANA SAN PABLO", "UNIVERSIDAD CATÓLICA BOLIVIANA SAN PABLO"));
        universidades.add(new ListViewItem("UNIVERSIDAD AUTÓNOMA TOMÁS FRÍAS", "UNIVERSIDAD AUTÓNOMA TOMÁS FRÍAS"));
        universidades.add(new ListViewItem("UNIVERSIDAD AUTÓNOMA JUAN MISAEL SARACHO", "UNIVERSIDAD AUTÓNOMA JUAN MISAEL SARACHO"));
        universidades.add(new ListViewItem("UNIVERSIDAD AUTÓNOMA GABRIEL RENÉ MORENO", "UNIVERSIDAD AUTÓNOMA GABRIEL RENÉ MORENO"));
        universidades.add(new ListViewItem("UNIVERSIDAD AUTÓNOMA DEL BENI JOSÉ BALLIVIÁN", "UNIVERSIDAD AUTÓNOMA DEL BENI JOSÉ BALLIVIÁN"));
        universidades.add(new ListViewItem("UNIVERSIDAD ANDINA SIMÓN BOLÍVAR", "UNIVERSIDAD ANDINA SIMÓN BOLÍVAR"));
        universidades.add(new ListViewItem("UNIVERSIDAD AMAZÓNICA DE PANDO", "UNIVERSIDAD AMAZÓNICA DE PANDO"));
        universidades.add(new ListViewItem("ESCUELA MILITAR DE INGENIERÍA", "ESCUELA MILITAR DE INGENIERÍA"));
        universidades.add(new ListViewItem("UNIVERSIDAD POLICIAL", "UNIVERSIDAD POLICIAL"));
        universidades.add(new ListViewItem("OTRO", "OTRO"));
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString().replace("-", "").toUpperCase();
        PersonaFormacionAcademicaModel model = null;
        String[] ids = null;
        List<ListViewItemSelected> programaseleccionados = new ArrayList<>();
        if (parametro.getId_formacion() == null) {
            nivelestudio = mi.getNivelEstudioPorNivel("");
            model = new PersonaFormacionAcademicaModel();
            model.setId_persona(parametro.getId_persona());
            model.setId_persona_kardex(parametro.getId_persona_kardex());
            model.setUUID(randomUUIDString);
            model.setRoot(parametro.getRoot());
            ids = Utils.isNullOrBlank(model.getId_programas()) ? null : model.getId_programas().split(",");
            for (ListViewItem item : programas) {
                boolean encontrado = false;
                if (ids != null) {
                    for (String value : ids) {
                        if (value.equals(item.getId())) {
                            encontrado = true;
                            break;
                        } else {
                            encontrado = false;
                        }
                    }
                }
                programaseleccionados.add(new ListViewItemSelected(item.getId(), item.getValue(), encontrado));
            }
            ;
            modelo.addAttribute("programas", programaseleccionados);
            modelo.addAttribute("universidades", universidades);
            modelo.addAttribute("nivelestudio", nivelestudio);
            modelo.addAttribute("detalletipotitulo", tipotitulo);
            modelo.addAttribute("id_rol", this.getUsuario().getId_rol());
            modelo.addAttribute("nombres", this.getUsuario().getNombres());
            modelo.addAttribute("readonly", model.getUniversidad().equals("OTRO") ? false : true);
            modelo.addAttribute("model", model);
            return "Kardex/FormacionAcademica/NuevoFormacionAcademicaKardex";
        } else {
            PersonaFormacionAcademica formacion = mi.getPersonaFormacionAcademicaKardex(parametro.getId_formacion());
            model = modelMapper.map(formacion, PersonaFormacionAcademicaModel.class);
            model.setId_persona(parametro.getId_persona());
            model.setUUID(randomUUIDString);
            model.setText_fechaemision(Convert.ToString(model.getFechaemision(), "yyyy-MM-dd"));
            nivelestudio = mi.getNivelEstudioPorNivel(model.getTipotitulo());
            ids = Utils.isNullOrBlank(model.getId_programas()) ? null : model.getId_programas().split(",");
            for (ListViewItem item : programas) {
                boolean encontrado = false;
                if (ids != null) {
                    for (String value : ids) {
                        if (value.equals(item.getId())) {
                            encontrado = true;
                            break;
                        } else {
                            encontrado = false;
                        }
                    }
                }
                programaseleccionados.add(new ListViewItemSelected(item.getId(), item.getValue(), encontrado));
            }
            ;
            modelo.addAttribute("programas", programaseleccionados);
            modelo.addAttribute("universidades", universidades);
            modelo.addAttribute("nivelestudio", nivelestudio);
            modelo.addAttribute("detalletipotitulo", tipotitulo);
            modelo.addAttribute("id_rol", this.getUsuario().getId_rol());
            modelo.addAttribute("nombres", this.getUsuario().getNombres());
            modelo.addAttribute("readonly", model.getUniversidad().equals("OTRO") ? false : true);
            modelo.addAttribute("model", model);
            return "Kardex/FormacionAcademica/EditarFormacionAcademicaKardex";
        }
    }

    @RequestMapping(value = "/RegistrarPersonaFormacionAcademicaKardex", method = RequestMethod.POST)
    public String RegistrarPersonaFormacionAcademicaKardex(@ModelAttribute("model") @Validated PersonaFormacionAcademicaModel model, BindingResult result, Model modelo) {
        if (model.getUrl_formacion().equals("image.png")) {
            if (model.getImage() == null || model.getImage().isEmpty()) {
                result.addError(new FieldError("url_formacion", "url_formacion", "Debe cargar la image de su fotocopia del titulo presentado"));
            }
        }
        model.setEseducacionsuperor(model.getText_eseducacionsuperor() != null ? model.getText_eseducacionsuperor().equals("on") ? true : false : false);
        model.setMostrar(model.getText_mostrar() != null ? model.getText_mostrar().equals("on") ? true : false : false);
        model.setFechaemision(Convert.ToDate(model.getText_fechaemision(), "yyyy-MM-dd"));
        if (result.hasErrors()) {
            List<ListViewItem> programas = mi.getProgramasPregrado();
            List<ListViewItem> tipotitulo = new ArrayList<>();
            tipotitulo.add(new ListViewItem("", "NINGUNO"));
            tipotitulo.add(new ListViewItem("PREGRADO", "PREGRADO"));
            tipotitulo.add(new ListViewItem("POSGRADO", "POSGRADO"));
            List<ListViewItem> universidades = new ArrayList<>();
            universidades.add(new ListViewItem("", "NINGUNO"));
            universidades.add(new ListViewItem("UNIVERSIDAD TÉCNICA DE ORURO", "UNIVERSIDAD TÉCNICA DE ORURO"));
            universidades.add(new ListViewItem("UNIVERSIDAD PÚBLICA DE EL ALTO", "UNIVERSIDAD PÚBLICA DE EL ALTO"));
            universidades.add(new ListViewItem("UNIVERSIDAD NACIONAL SIGLO XX", "UNIVERSIDAD NACIONAL SIGLO XX"));
            universidades.add(new ListViewItem("UNIVERSIDAD MAYOR REAL Y PONTIFICIA SAN FRANCISCO XAVIER DE CHUQUISACA", "UNIVERSIDAD MAYOR REAL Y PONTIFICIA SAN FRANCISCO XAVIER DE CHUQUISACA"));
            universidades.add(new ListViewItem("UNIVERSIDAD MAYOR DE SAN SIMÓN", "UNIVERSIDAD MAYOR DE SAN SIMÓN"));
            universidades.add(new ListViewItem("UNIVERSIDAD MAYOR DE SAN ANDRÉS", "UNIVERSIDAD MAYOR DE SAN ANDRÉS"));
            universidades.add(new ListViewItem("UNIVERSIDAD CATÓLICA BOLIVIANA SAN PABLO", "UNIVERSIDAD CATÓLICA BOLIVIANA SAN PABLO"));
            universidades.add(new ListViewItem("UNIVERSIDAD AUTÓNOMA TOMÁS FRÍAS", "UNIVERSIDAD AUTÓNOMA TOMÁS FRÍAS"));
            universidades.add(new ListViewItem("UNIVERSIDAD AUTÓNOMA JUAN MISAEL SARACHO", "UNIVERSIDAD AUTÓNOMA JUAN MISAEL SARACHO"));
            universidades.add(new ListViewItem("UNIVERSIDAD AUTÓNOMA GABRIEL RENÉ MORENO", "UNIVERSIDAD AUTÓNOMA GABRIEL RENÉ MORENO"));
            universidades.add(new ListViewItem("UNIVERSIDAD AUTÓNOMA DEL BENI JOSÉ BALLIVIÁN", "UNIVERSIDAD AUTÓNOMA DEL BENI JOSÉ BALLIVIÁN"));
            universidades.add(new ListViewItem("UNIVERSIDAD ANDINA SIMÓN BOLÍVAR", "UNIVERSIDAD ANDINA SIMÓN BOLÍVAR"));
            universidades.add(new ListViewItem("UNIVERSIDAD AMAZÓNICA DE PANDO", "UNIVERSIDAD AMAZÓNICA DE PANDO"));
            universidades.add(new ListViewItem("ESCUELA MILITAR DE INGENIERÍA", "ESCUELA MILITAR DE INGENIERÍA"));
            universidades.add(new ListViewItem("UNIVERSIDAD POLICIAL", "UNIVERSIDAD POLICIAL"));
            universidades.add(new ListViewItem("OTRO", "OTRO"));
            List<ListViewItem> nivelestudio = mi.getNivelEstudioPorNivel(model.getTipotitulo());
            String[] ids = Utils.isNullOrBlank(model.getId_programas()) ? null : model.getId_programas().split(",");
            List<ListViewItemSelected> programaseleccionados = new ArrayList<>();
            for (ListViewItem item : programas) {
                boolean encontrado = false;
                if (ids != null) {
                    for (String value : ids) {
                        if (value.equals(item.getId())) {
                            encontrado = true;
                            break;
                        } else {
                            encontrado = false;
                        }
                    }
                }
                programaseleccionados.add(new ListViewItemSelected(item.getId(), item.getValue(), encontrado));
            }
            ;
            modelo.addAttribute("programas", programaseleccionados);
            modelo.addAttribute("universidades", universidades);
            modelo.addAttribute("nivelestudio", nivelestudio);
            modelo.addAttribute("detalletipotitulo", tipotitulo);
            modelo.addAttribute("id_rol", this.getUsuario().getId_rol());
            modelo.addAttribute("nombres", this.getUsuario().getNombres());
            modelo.addAttribute("readonly", model.getUniversidad().equals("OTRO") ? false : true);
            modelo.addAttribute("model", model);
            return "Kardex/FormacionAcademica/NuevoFormacionAcademicaKardex";
        }
        PersonaFormacionAcademica formacion = modelMapper.map(model, PersonaFormacionAcademica.class);
        int id_formacion = mi.registrarNuevoFormacionAcademicaKardex(formacion);
        String filename = String.format("%010d", model.getId_persona()) + String.format("%05d", id_formacion);
        FileUploadServlet.MultipartRequest(request, model.getImage(), path + DIRECTORIO_FORMACIONACADEMICA.key(), "url_formacion", filename);
        String nombrearchivo = Utils.EsImagenModificado(formacion.getUrl_formacion(), request.getAttribute("url_formacion").toString());
        if (!nombrearchivo.equals("image.png")) {
            formacion.setId_formacion(id_formacion);
            formacion.setUrl_formacion(nombrearchivo);
            mi.actualizarImagenFormacionAcademicaKardexDocente(formacion);
        }
        String request = "?id_persona_kardex=" + model.getId_persona_kardex() + "&" + "id_persona=" + model.getId_persona() + "&" + "status=" + Utils.encodeValue("alert-success") + "&" + "message=" + Utils.encodeValue("Se grabo correctamente el registro del documento");
        return "redirect:/ListarFormacionAcademica" + request;
    }

    @RequestMapping(value = "/RegistrarModificarFormacionAcademica", method = RequestMethod.POST)
    public String RegistrarModificarFormacionAcademica(@ModelAttribute("model") @Validated PersonaFormacionAcademicaModel model, BindingResult result, Model modelo) {
        if (model.getUrl_formacion().equals("image.png")) {
            if (model.getImage() == null || model.getImage().isEmpty()) {
                result.addError(new FieldError("url_formacion", "url_formacion", "Debe cargar la image de su fotocopia del titulo presentado"));
            }
        }
        model.setEseducacionsuperor(model.getText_eseducacionsuperor() != null ? model.getText_eseducacionsuperor().equals("on") ? true : false : false);
        model.setMostrar(model.getText_mostrar() != null ? model.getText_mostrar().equals("on") ? true : false : false);
        model.setFechaemision(Convert.ToDate(model.getText_fechaemision(), "yyyy-MM-dd"));
        if (result.hasErrors()) {
            List<ListViewItem> programas = mi.getProgramasPregrado();
            List<ListViewItem> tipotitulo = new ArrayList<>();
            tipotitulo.add(new ListViewItem("", "NINGUNO"));
            tipotitulo.add(new ListViewItem("PREGRADO", "PREGRADO"));
            tipotitulo.add(new ListViewItem("POSGRADO", "POSGRADO"));
            List<ListViewItem> universidades = new ArrayList<>();
            universidades.add(new ListViewItem("", "NINGUNO"));
            universidades.add(new ListViewItem("UNIVERSIDAD TÉCNICA DE ORURO", "UNIVERSIDAD TÉCNICA DE ORURO"));
            universidades.add(new ListViewItem("UNIVERSIDAD PÚBLICA DE EL ALTO", "UNIVERSIDAD PÚBLICA DE EL ALTO"));
            universidades.add(new ListViewItem("UNIVERSIDAD NACIONAL SIGLO XX", "UNIVERSIDAD NACIONAL SIGLO XX"));
            universidades.add(new ListViewItem("UNIVERSIDAD MAYOR REAL Y PONTIFICIA SAN FRANCISCO XAVIER DE CHUQUISACA", "UNIVERSIDAD MAYOR REAL Y PONTIFICIA SAN FRANCISCO XAVIER DE CHUQUISACA"));
            universidades.add(new ListViewItem("UNIVERSIDAD MAYOR DE SAN SIMÓN", "UNIVERSIDAD MAYOR DE SAN SIMÓN"));
            universidades.add(new ListViewItem("UNIVERSIDAD MAYOR DE SAN ANDRÉS", "UNIVERSIDAD MAYOR DE SAN ANDRÉS"));
            universidades.add(new ListViewItem("UNIVERSIDAD CATÓLICA BOLIVIANA SAN PABLO", "UNIVERSIDAD CATÓLICA BOLIVIANA SAN PABLO"));
            universidades.add(new ListViewItem("UNIVERSIDAD AUTÓNOMA TOMÁS FRÍAS", "UNIVERSIDAD AUTÓNOMA TOMÁS FRÍAS"));
            universidades.add(new ListViewItem("UNIVERSIDAD AUTÓNOMA JUAN MISAEL SARACHO", "UNIVERSIDAD AUTÓNOMA JUAN MISAEL SARACHO"));
            universidades.add(new ListViewItem("UNIVERSIDAD AUTÓNOMA GABRIEL RENÉ MORENO", "UNIVERSIDAD AUTÓNOMA GABRIEL RENÉ MORENO"));
            universidades.add(new ListViewItem("UNIVERSIDAD AUTÓNOMA DEL BENI JOSÉ BALLIVIÁN", "UNIVERSIDAD AUTÓNOMA DEL BENI JOSÉ BALLIVIÁN"));
            universidades.add(new ListViewItem("UNIVERSIDAD ANDINA SIMÓN BOLÍVAR", "UNIVERSIDAD ANDINA SIMÓN BOLÍVAR"));
            universidades.add(new ListViewItem("UNIVERSIDAD AMAZÓNICA DE PANDO", "UNIVERSIDAD AMAZÓNICA DE PANDO"));
            universidades.add(new ListViewItem("ESCUELA MILITAR DE INGENIERÍA", "ESCUELA MILITAR DE INGENIERÍA"));
            universidades.add(new ListViewItem("UNIVERSIDAD POLICIAL", "UNIVERSIDAD POLICIAL"));
            universidades.add(new ListViewItem("OTRO", "OTRO"));
            List<ListViewItem> nivelestudio = mi.getNivelEstudioPorNivel(model.getTipotitulo());
            String[] ids = Utils.isNullOrBlank(model.getId_programas()) ? null : model.getId_programas().split(",");
            List<ListViewItemSelected> programaseleccionados = new ArrayList<>();
            for (ListViewItem item : programas) {
                boolean encontrado = false;
                if (ids != null) {
                    for (String value : ids) {
                        if (value.equals(item.getId())) {
                            encontrado = true;
                            break;
                        } else {
                            encontrado = false;
                        }
                    }
                }
                programaseleccionados.add(new ListViewItemSelected(item.getId(), item.getValue(), encontrado));
            }
            ;
            modelo.addAttribute("programas", programaseleccionados);
            modelo.addAttribute("universidades", universidades);
            modelo.addAttribute("nivelestudio", nivelestudio);
            modelo.addAttribute("detalletipotitulo", tipotitulo);
            modelo.addAttribute("id_rol", this.getUsuario().getId_rol());
            modelo.addAttribute("nombres", this.getUsuario().getNombres());
            modelo.addAttribute("readonly", model.getUniversidad().equals("OTRO") ? false : true);
            modelo.addAttribute("model", model);
            return "Kardex/FormacionAcademica/EditarFormacionAcademicaKardex";
        }
        PersonaFormacionAcademica formacion = modelMapper.map(model, PersonaFormacionAcademica.class);
        String filename = String.format("%010d", model.getId_persona()) + String.format("%05d", formacion.getId_formacion());
        FileUploadServlet.MultipartRequest(request, model.getImage(), path + DIRECTORIO_FORMACIONACADEMICA.key(), "url_formacion", filename);
        String nombrearchivo = Utils.EsImagenModificado(formacion.getUrl_formacion(), request.getAttribute("url_formacion").toString());
        if (!nombrearchivo.equals("image.png")) {
            formacion.setUrl_formacion(nombrearchivo);
        }
        mi.actualizarDatosFormacionAcademicaKardexDocente(formacion);
        String request = "?id_persona_kardex=" + model.getId_persona_kardex() + "&" + "id_persona=" + model.getId_persona() + "&" + "status=" + Utils.encodeValue("alert-success") + "&" + "message=" + Utils.encodeValue("Se Actualizo correctamente el registro del documento");
        return "redirect:/ListarFormacionAcademica" + request;
    }

    @RequestMapping(value = "/EliminarFormacionAcademica", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageDeleteResult> EliminarFormacionAcademica(@ModelAttribute("model") ItemEliminarModel model) {
        Clientes cliente = this.getUsuario();
        MessageDeleteResult Respuesta = new MessageDeleteResult();
        if (cliente == null) {
            Respuesta.setMessage("Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            Respuesta.setStatus("Error");
            return new ResponseEntity<>(Respuesta, HttpStatus.OK);
        } else {
            try {
                mi.eliminarFormacionAcademicaKardexDocente(model.getId_formacion());
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

    @RequestMapping(value = "/ModificarEstadoAprobacionDocumentoFormacionAcademica", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageAprobadoResult> ModificarEstadoAprobacionDocumentoFormacionAcademica(@ModelAttribute("model") ItemAprobarModel model) {
        Clientes cliente = this.getUsuario();
        MessageAprobadoResult Respuesta = new MessageAprobadoResult();
        if (cliente == null) {
            Respuesta.setMessage("Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            Respuesta.setStatus("Error");
            return new ResponseEntity<>(Respuesta, HttpStatus.OK);
        } else {
            try {
                Boolean estado = model.getName() != null ? model.getName().equals("on") ? true : false : false;
                PersonaFormacionAcademica formacion = new PersonaFormacionAcademica();
                formacion.setId_formacion(model.getId_formacion());
                formacion.setAprobado(estado);
                mi.aprobarFormacionAcademicaKardexDocente(formacion);
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
