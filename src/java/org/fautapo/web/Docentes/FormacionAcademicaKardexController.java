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
import org.fautapo.domain.PersonaFormacionAcademica;
import org.fautapo.domain.PersonaKardex;
import org.fautapo.domain.logic.MiFacade;
import org.fautapo.model.Kardex.DetalleFormacionAcademicaPersonaModel;
import org.fautapo.model.Kardex.ItemAprobarModel;
import org.fautapo.model.Kardex.ItemEliminarModel;
import org.fautapo.model.Kardex.MessageAprobadoResult;
import org.fautapo.model.Kardex.MessageDeleteResult;
import org.fautapo.model.Kardex.ParametroCreacionModel;
import org.fautapo.model.Kardex.PersonaFormacionAcademicaModel;
import org.fautapo.model.MessageResult;
import org.fautapo.util.Convert;
import static org.fautapo.util.Directorio.DIRECTORIO_FORMACIONACADEMICA;
import org.fautapo.util.FileUploadServlet;
import org.fautapo.util.ListViewItem;
import org.fautapo.util.ListViewItemSelected;
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
public class FormacionAcademicaKardexController {

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

    @RequestMapping(value = "/ListaParcialFormacionAcademico.fautapo", method = RequestMethod.GET)
    public String ListaParcialFormacionAcademico(Model modelo) {
        Clientes cliente = this.GetUsuario();
        int number = cliente.getInt(request, "number");
        int id_persona_kardex = cliente.getInt(request, "id_persona_kardex");
        PersonaKardex kardex = new PersonaKardex();
        kardex.setNumber(number);
        kardex.setId_persona_kardex(id_persona_kardex);
        List<DetalleFormacionAcademicaPersonaModel> formacion = mi.GetPersonaSubTotalFormacionAcademicaKardex(kardex);
        int sId_rol = cliente.getId_rol();
        modelo.addAttribute("id_rol", sId_rol);
        modelo.addAttribute("nombres", cliente.getNombres());
        modelo.addAttribute("formacion", formacion);
        return "AdministrarKardexPersonal/DetalleParcialFormacionAcademica";
    }

    @RequestMapping(value = "/ListarFormacionAcademica.fautapo", method = RequestMethod.GET)
    public String ListarFormacionAcademica(Model modelo) {
        Clientes cliente = this.GetUsuario();
        int id_persona_kardex = cliente.getInt(request, "id_persona_kardex");
        int id_persona = cliente.getInt(request, "id_persona");
        String status = cliente.getString(request, "status");
        String message = cliente.getString(request, "message");
        List<DetalleFormacionAcademicaPersonaModel> formacion = mi.GetPersonaTotalFormacionAcademicaKardex(id_persona_kardex);
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
        return "AdministrarKardexPersonal/FormacionAcademica/DetalleFormacionAcademica";
    }

    @RequestMapping(value = "/GetNivelEstudio.fautapo", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<ListViewItem>> GetNivelEstudio(@ModelAttribute("tipotitulo") String tipotitulo) {
        List<ListViewItem> nivelestudio = mi.GetNivelEstudioPorNivel(tipotitulo);
        if (nivelestudio.isEmpty()) {
            return new ResponseEntity<>(nivelestudio, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(nivelestudio, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/FormularioRegistroFormacionKardex.fautapo", method = RequestMethod.GET)
    public String FormularioRegistroFormacionKardex(@ModelAttribute("parametro") ParametroCreacionModel parametro, Model modelo) {
        List<ListViewItem> nivelestudio = null;
        List<ListViewItem> programas = mi.GetProgramasPregrado();
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
            nivelestudio = mi.GetNivelEstudioPorNivel("");
            model = new PersonaFormacionAcademicaModel();
            model.setId_persona(parametro.getId_persona());
            model.setId_persona_kardex(parametro.getId_persona_kardex());
            model.setUUID(randomUUIDString);
            model.setRoot(parametro.getRoot());
            ids = Util.isNullOrBlank(model.getId_programas()) ? null : model.getId_programas().split(",");
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
            };
            modelo.addAttribute("programas", programaseleccionados);
            modelo.addAttribute("universidades", universidades);
            modelo.addAttribute("nivelestudio", nivelestudio);
            modelo.addAttribute("detalletipotitulo", tipotitulo);
            modelo.addAttribute("id_rol", this.GetUsuario().getId_rol());
            modelo.addAttribute("nombres", this.GetUsuario().getNombres());
            modelo.addAttribute("readonly", model.getUniversidad().equals("OTRO") ? false : true);
            modelo.addAttribute("model", model);
            return "AdministrarKardexPersonal/FormacionAcademica/NuevoFormacionAcademicaKardex";
        } else {
            PersonaFormacionAcademica formacion = mi.GetPersonaFormacionAcademicaKardex(parametro.getId_formacion());
            model = modelMapper.map(formacion, PersonaFormacionAcademicaModel.class);
            model.setId_persona(parametro.getId_persona());
            model.setUUID(randomUUIDString);
            model.setText_fechaemision(Convert.ToString(model.getFechaemision(), "dd/MM/yyyy"));
            nivelestudio = mi.GetNivelEstudioPorNivel(model.getTipotitulo());
            ids = Util.isNullOrBlank(model.getId_programas()) ? null : model.getId_programas().split(",");
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
            };
            modelo.addAttribute("programas", programaseleccionados);
            modelo.addAttribute("universidades", universidades);
            modelo.addAttribute("nivelestudio", nivelestudio);
            modelo.addAttribute("detalletipotitulo", tipotitulo);
            modelo.addAttribute("id_rol", this.GetUsuario().getId_rol());
            modelo.addAttribute("nombres", this.GetUsuario().getNombres());
            modelo.addAttribute("readonly", model.getUniversidad().equals("OTRO") ? false : true);
            modelo.addAttribute("model", model);
            return "AdministrarKardexPersonal/FormacionAcademica/EditarFormacionAcademicaKardex";
        }
    }

    @RequestMapping(value = "/RegistrarPersonaFormacionAcademicaKardex.fautapo", method = RequestMethod.POST)
    public String RegistrarPersonaFormacionAcademicaKardex(@ModelAttribute("model") @Validated PersonaFormacionAcademicaModel model, BindingResult result, Model modelo) {
        if (model.getUrl_formacion().equals("image.png")) {
            if (model.getImage() == null || model.getImage().isEmpty()) {
                result.addError(new FieldError("url_formacion", "url_formacion", "Debe cargar la image de su fotocopia del titulo presentado"));
            }
        }
        model.setEseducacionsuperor(model.getText_eseducacionsuperor() != null ? model.getText_eseducacionsuperor().equals("on") ? true : false : false);
        model.setMostrar(model.getText_mostrar() != null ? model.getText_mostrar().equals("on") ? true : false : false);
        model.setFechaemision(Convert.ToDate(model.getText_fechaemision(), "dd/MM/yyyy"));
        if (result.hasErrors()) {
            List<ListViewItem> programas = mi.GetProgramasPregrado();
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
            List<ListViewItem> nivelestudio = mi.GetNivelEstudioPorNivel(model.getTipotitulo());
            String[] ids = Util.isNullOrBlank(model.getId_programas()) ? null : model.getId_programas().split(",");
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
            };
            modelo.addAttribute("programas", programaseleccionados);
            modelo.addAttribute("universidades", universidades);
            modelo.addAttribute("nivelestudio", nivelestudio);
            modelo.addAttribute("detalletipotitulo", tipotitulo);
            modelo.addAttribute("id_rol", this.GetUsuario().getId_rol());
            modelo.addAttribute("nombres", this.GetUsuario().getNombres());
            modelo.addAttribute("readonly", model.getUniversidad().equals("OTRO") ? false : true);
            modelo.addAttribute("model", model);
            return "AdministrarKardexPersonal/FormacionAcademica/NuevoFormacionAcademicaKardex";
        }
        PersonaFormacionAcademica formacion = modelMapper.map(model, PersonaFormacionAcademica.class);
        int id_formacion = mi.RegistrarNuevoFormacionAcademicaKardex(formacion);
        String filename = String.format("%010d", model.getId_persona()) + String.format("%05d", id_formacion);
        FileUploadServlet.MultipartRequest(request, model.getImage(), DIRECTORIO_FORMACIONACADEMICA.key(), "url_formacion", filename);
        String nombrearchivo = Util.EsImagenModificado(formacion.getUrl_formacion(), request.getAttribute("url_formacion").toString());
        if (!nombrearchivo.equals("image.png")) {
            formacion.setId_formacion(id_formacion);
            formacion.setUrl_formacion(nombrearchivo);
            mi.ActualizarImagenFormacionAcademicaKardexDocente(formacion);
        }
        String request = "?id_persona_kardex=" + model.getId_persona_kardex() + "&" + "id_persona=" + model.getId_persona() + "&" + "status=" + Util.encodeValue("alert-success") + "&" + "message=" + Util.encodeValue("Se grabo correctamente el registro del documento");
        return "redirect:/ListarFormacionAcademica.fautapo" + request;
    }

    @RequestMapping(value = "/RegistrarModificarFormacionAcademica.fautapo", method = RequestMethod.POST)
    public String RegistrarModificarFormacionAcademica(@ModelAttribute("model") @Validated PersonaFormacionAcademicaModel model, BindingResult result, Model modelo) {
        if (model.getUrl_formacion().equals("image.png")) {
            if (model.getImage() == null || model.getImage().isEmpty()) {
                result.addError(new FieldError("url_formacion", "url_formacion", "Debe cargar la image de su fotocopia del titulo presentado"));
            }
        }
        model.setEseducacionsuperor(model.getText_eseducacionsuperor() != null ? model.getText_eseducacionsuperor().equals("on") ? true : false : false);
        model.setMostrar(model.getText_mostrar() != null ? model.getText_mostrar().equals("on") ? true : false : false);
        model.setFechaemision(Convert.ToDate(model.getText_fechaemision(), "dd/MM/yyyy"));
        if (result.hasErrors()) {
            List<ListViewItem> programas = mi.GetProgramasPregrado();
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
            List<ListViewItem> nivelestudio = mi.GetNivelEstudioPorNivel(model.getTipotitulo());
            String[] ids = Util.isNullOrBlank(model.getId_programas()) ? null : model.getId_programas().split(",");
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
            };
            modelo.addAttribute("programas", programaseleccionados);
            modelo.addAttribute("universidades", universidades);
            modelo.addAttribute("nivelestudio", nivelestudio);
            modelo.addAttribute("detalletipotitulo", tipotitulo);
            modelo.addAttribute("id_rol", this.GetUsuario().getId_rol());
            modelo.addAttribute("nombres", this.GetUsuario().getNombres());
            modelo.addAttribute("readonly", model.getUniversidad().equals("OTRO") ? false : true);
            modelo.addAttribute("model", model);
            return "AdministrarKardexPersonal/FormacionAcademica/EditarFormacionAcademicaKardex";
        }
        PersonaFormacionAcademica formacion = modelMapper.map(model, PersonaFormacionAcademica.class);
        String filename = String.format("%010d", model.getId_persona()) + String.format("%05d", formacion.getId_formacion());
        FileUploadServlet.MultipartRequest(request, model.getImage(), DIRECTORIO_FORMACIONACADEMICA.key(), "url_formacion", filename);
        String nombrearchivo = Util.EsImagenModificado(formacion.getUrl_formacion(), request.getAttribute("url_formacion").toString());
        if (!nombrearchivo.equals("image.png")) {
            formacion.setUrl_formacion(nombrearchivo);
        }
        mi.ActualizarDatosFormacionAcademicaKardexDocente(formacion);
        String request = "?id_persona_kardex=" + model.getId_persona_kardex() + "&" + "id_persona=" + model.getId_persona() + "&" + "status=" + Util.encodeValue("alert-success") + "&" + "message=" + Util.encodeValue("Se Actualizo correctamente el registro del documento");
        return "redirect:/ListarFormacionAcademica.fautapo" + request;
    }

    @RequestMapping(value = "/EliminarFormacionAcademica.fautapo", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageDeleteResult> EliminarFormacionAcademica(@ModelAttribute("model") ItemEliminarModel model) {
        Clientes cliente = this.GetUsuario();
        MessageDeleteResult Respuesta = new MessageDeleteResult();
        if (cliente == null) {
            Respuesta.setMessage("Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            Respuesta.setStatus("Error");
            return new ResponseEntity<>(Respuesta, HttpStatus.OK);
        } else {
            try {
                mi.EliminarFormacionAcademicaKardexDocente(model.getId_formacion());
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

    @RequestMapping(value = "/ModificarEstadoAprobacionDocumentoFormacionAcademica.fautapo", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageAprobadoResult> ModificarEstadoAprobacionDocumentoFormacionAcademica(@ModelAttribute("model") ItemAprobarModel model) {
        Clientes cliente = this.GetUsuario();
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
                mi.AprobarFormacionAcademicaKardexDocente(formacion);
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
