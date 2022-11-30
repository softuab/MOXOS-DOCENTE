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
import org.fautapo.domain.PersonaProyectoDocente;
import org.fautapo.domain.logic.MiFacade;
import org.fautapo.model.Kardex.ItemAprobarModel;
import org.fautapo.model.Kardex.ItemEliminarModel;
import org.fautapo.model.Kardex.MessageAprobadoResult;
import org.fautapo.model.Kardex.MessageDeleteResult;
import org.fautapo.model.Kardex.ParametroCreacionModel;
import org.fautapo.model.Kardex.PersonaProyectoDocenteModel;
import org.fautapo.model.MessageResult;
import static org.fautapo.util.Directorio.DIRECTORIO_PROYECTODOCENTE;
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
public class ProyectoKardexController {

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

    @RequestMapping(value = "/ListaParcialProyectoDocente.fautapo", method = RequestMethod.GET)
    public String ListaParcialProyectoDocente(Model modelo) {
        Clientes cliente = this.GetUsuario();
        int number = cliente.getInt(request, "number");
        int id_persona_kardex = cliente.getInt(request, "id_persona_kardex");
        PersonaKardex kardex = new PersonaKardex();
        kardex.setNumber(number);
        kardex.setId_persona_kardex(id_persona_kardex);
        List<PersonaProyectoDocente> detalleproyecto = mi.GetPersonaSubTotalProyectoKardex(kardex);
        int sId_rol = cliente.getId_rol();
        modelo.addAttribute("id_rol", sId_rol);
        modelo.addAttribute("nombres", cliente.getNombres());
        modelo.addAttribute("detalles", detalleproyecto);
        return "AdministrarKardexPersonal/DetalleParcialProyectoDocente";
    }

    @RequestMapping(value = "/ListarProyectoDocente.fautapo", method = RequestMethod.GET)
    public String ListarProyectoDocente(Model modelo) {
        Clientes cliente = this.GetUsuario();
        int id_persona_kardex = cliente.getInt(request, "id_persona_kardex");
        int id_persona = cliente.getInt(request, "id_persona");
        String status = cliente.getString(request, "status");
        String message = cliente.getString(request, "message");
        List<PersonaProyectoDocente> detalleproyecto = mi.GetPersonaTotalProyectoKardex(id_persona_kardex);
        int sId_rol = cliente.getId_rol();
        MessageResult result = new MessageResult();
        result.setMessage(message);
        result.setStatus(status);
        modelo.addAttribute("id_rol", sId_rol);
        modelo.addAttribute("nombres", cliente.getNombres());
        modelo.addAttribute("detalles", detalleproyecto);
        modelo.addAttribute("id_persona", id_persona);
        modelo.addAttribute("result", result);
        modelo.addAttribute("id_persona_kardex", id_persona_kardex);
        return "AdministrarKardexPersonal/ProyectoDocente/DetalleProyectoDocenteKardex";
    }

    @RequestMapping(value = "/FormularioRegistroProyectoKardex.fautapo", method = RequestMethod.GET)
    public String FormularioRegistroProyectoKardex(@ModelAttribute("parametro") ParametroCreacionModel parametro, Model modelo) {
        List<ListViewItem> origenproyecto = new ArrayList<>();
        origenproyecto.add(new ListViewItem("", "NINGUNO"));
        origenproyecto.add(new ListViewItem("INTER INSTITUCIONAL", "INTER INSTITUCIONAL"));
        origenproyecto.add(new ListViewItem("FACULTATIVO", "FACULTATIVO"));
        origenproyecto.add(new ListViewItem("CARRERA", "CARRERA"));

        List<ListViewItem> estado = new ArrayList<>();
        estado.add(new ListViewItem("", "NINGUNO"));
        estado.add(new ListViewItem("PRESENTADO", "PRESENTADO"));
        estado.add(new ListViewItem("EVALUADO", "EVALUADO"));
        estado.add(new ListViewItem("APROBADO", "APROBADO"));
        estado.add(new ListViewItem("ELABORADO", "ELABORADO"));
        estado.add(new ListViewItem("EJECUTADO", "EJECUTADO"));

        List<ListViewItem> modalidadgraduacion = new ArrayList<>();
        modalidadgraduacion.add(new ListViewItem("", "NINGUNO"));
        modalidadgraduacion.add(new ListViewItem("TESIS", "TESIS"));
        modalidadgraduacion.add(new ListViewItem("PROYECTO DE GRADO", "PROYECTO DE GRADO"));
        modalidadgraduacion.add(new ListViewItem("INTERNADO ROTATORIO", "INTERNADO ROTATORIO"));
        modalidadgraduacion.add(new ListViewItem("TRABAJO DIRIGIDO", "TRABAJO DIRIGIDO"));
        modalidadgraduacion.add(new ListViewItem("GRADUACIÓN POR EXCELENCIA", "GRADUACIÓN POR EXCELENCIA"));
        modalidadgraduacion.add(new ListViewItem("OTRO", "OTRO"));

        List<ListViewItem> funcion = new ArrayList<>();
        funcion.add(new ListViewItem("", "NINGUNO"));
        funcion.add(new ListViewItem("AUTOR", "AUTOR"));
        funcion.add(new ListViewItem("ASESOR", "ASESOR"));
        funcion.add(new ListViewItem("EJECUTADOR", "EJECUTADOR"));

        List<ListViewItem> tipoproyecto = new ArrayList<>();
        tipoproyecto.add(new ListViewItem("", "NINGUNO"));
        tipoproyecto.add(new ListViewItem("INTERACCIÓN SOCIAL O UNIVERSITARIA", "INTERACCIÓN SOCIAL O UNIVERSITARIA"));
        tipoproyecto.add(new ListViewItem("EXTRA CURRICULAR", "EXTRA CURRICULAR"));
        tipoproyecto.add(new ListViewItem("OTRO", "OTRO"));

        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString().replace("-", "").toUpperCase();
        PersonaProyectoDocenteModel model = null;
        String[] ids = null;
        List<ListViewItemSelected> estadoseleccionados = new ArrayList<>();
        if (parametro.getId_personas_proyecto() == null) {
            model = new PersonaProyectoDocenteModel();
            model.setId_persona(parametro.getId_persona());
            model.setId_persona_kardex(parametro.getId_persona_kardex());
            model.setUUID(randomUUIDString);
            model.setRoot(parametro.getRoot());
            ids = Util.isNullOrBlank(model.getEstado()) ? null : model.getEstado().split(",");
            for (ListViewItem item : estado) {
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
                estadoseleccionados.add(new ListViewItemSelected(item.getId(), item.getValue(), encontrado));
            };
            modelo.addAttribute("detalleorigenproyecto", origenproyecto);
            modelo.addAttribute("detalleestado", estadoseleccionados);
            modelo.addAttribute("detallemodalidadgraduacion", modalidadgraduacion);
            modelo.addAttribute("detallefuncion", funcion);
            modelo.addAttribute("detalletipoproyecto", tipoproyecto);
            modelo.addAttribute("id_rol", this.GetUsuario().getId_rol());
            modelo.addAttribute("nombres", this.GetUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "AdministrarKardexPersonal/ProyectoDocente/NuevoProyectoDocenteKardex";
        } else {
            PersonaProyectoDocente proyectodocente = mi.GetPersonaProyectoKardex(parametro.getId_personas_proyecto());
            model = modelMapper.map(proyectodocente, PersonaProyectoDocenteModel.class);
            model.setId_persona(parametro.getId_persona());
            model.setUUID(randomUUIDString);
            model.setRoot(parametro.getRoot());
            ids = Util.isNullOrBlank(model.getEstado()) ? null : model.getEstado().split(",");
            for (ListViewItem item : estado) {
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
                estadoseleccionados.add(new ListViewItemSelected(item.getId(), item.getValue(), encontrado));
            };
            modelo.addAttribute("detalleorigenproyecto", origenproyecto);
            modelo.addAttribute("detalleestado", estadoseleccionados);
            modelo.addAttribute("detallemodalidadgraduacion", modalidadgraduacion);
            modelo.addAttribute("detallefuncion", funcion);
            modelo.addAttribute("detalletipoproyecto", tipoproyecto);
            modelo.addAttribute("id_rol", this.GetUsuario().getId_rol());
            modelo.addAttribute("nombres", this.GetUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "AdministrarKardexPersonal/ProyectoDocente/EditarProyectoDocenteKardex";
        }
    }

    @RequestMapping(value = "/RegistrarPersonaProyectoKardex.fautapo", method = RequestMethod.POST)
    public String RegistrarPersonaProyectoKardex(@ModelAttribute("model") @Validated PersonaProyectoDocenteModel model, BindingResult result, Model modelo) {
        if (model.getDocumento().equals("image.png")) {
            if (model.getImage() == null || model.getImage().isEmpty()) {
                result.addError(new FieldError("documento", "documento", "Debe cargar la portada de su proyecto"));
            }
        }
        model.setMostrar(model.getText_mostrar() != null ? model.getText_mostrar().equals("on") ? true : false : false);
        model.setRepresentaciondirigencial(model.getText_representaciondirigencial() != null ? model.getText_representaciondirigencial().equals("on") ? true : false : false);
        if (result.hasErrors()) {
            List<ListViewItem> origenproyecto = new ArrayList<>();
            origenproyecto.add(new ListViewItem("", "NINGUNO"));
            origenproyecto.add(new ListViewItem("INTER INSTITUCIONAL", "INTER INSTITUCIONAL"));
            origenproyecto.add(new ListViewItem("FACULTATIVO", "FACULTATIVO"));
            origenproyecto.add(new ListViewItem("CARRERA", "CARRERA"));

            List<ListViewItem> estado = new ArrayList<>();
            estado.add(new ListViewItem("", "NINGUNO"));
            estado.add(new ListViewItem("PRESENTADO", "PRESENTADO"));
            estado.add(new ListViewItem("EVALUADO", "EVALUADO"));
            estado.add(new ListViewItem("APROBADO", "APROBADO"));
            estado.add(new ListViewItem("ELABORADO", "ELABORADO"));
            estado.add(new ListViewItem("EJECUTADO", "EJECUTADO"));

            List<ListViewItem> modalidadgraduacion = new ArrayList<>();
            modalidadgraduacion.add(new ListViewItem("", "NINGUNO"));
            modalidadgraduacion.add(new ListViewItem("TESIS", "TESIS"));
            modalidadgraduacion.add(new ListViewItem("PROYECTO DE GRADO", "PROYECTO DE GRADO"));
            modalidadgraduacion.add(new ListViewItem("INTERNADO ROTATORIO", "INTERNADO ROTATORIO"));
            modalidadgraduacion.add(new ListViewItem("TRABAJO DIRIGIDO", "TRABAJO DIRIGIDO"));
            modalidadgraduacion.add(new ListViewItem("GRADUACIÓN POR EXCELENCIA", "GRADUACIÓN POR EXCELENCIA"));
            modalidadgraduacion.add(new ListViewItem("OTRO", "OTRO"));

            List<ListViewItem> funcion = new ArrayList<>();
            funcion.add(new ListViewItem("", "NINGUNO"));
            funcion.add(new ListViewItem("AUTOR", "AUTOR"));
            funcion.add(new ListViewItem("ASESOR", "ASESOR"));
            funcion.add(new ListViewItem("EJECUTADOR", "EJECUTADOR"));

            List<ListViewItem> tipoproyecto = new ArrayList<>();
            tipoproyecto.add(new ListViewItem("", "NINGUNO"));
            tipoproyecto.add(new ListViewItem("INTERACCIÓN SOCIAL O UNIVERSITARIA", "INTERACCIÓN SOCIAL O UNIVERSITARIA"));
            tipoproyecto.add(new ListViewItem("EXTRA CURRICULAR", "EXTRA CURRICULAR"));
            tipoproyecto.add(new ListViewItem("OTRO", "OTRO"));
            List<ListViewItemSelected> estadoseleccionados = new ArrayList<>();
            String[] ids = Util.isNullOrBlank(model.getEstado()) ? null : model.getEstado().split(",");
            for (ListViewItem item : estado) {
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
                estadoseleccionados.add(new ListViewItemSelected(item.getId(), item.getValue(), encontrado));
            };
            modelo.addAttribute("detalleorigenproyecto", origenproyecto);
            modelo.addAttribute("detalleestado", estadoseleccionados);
            modelo.addAttribute("detallemodalidadgraduacion", modalidadgraduacion);
            modelo.addAttribute("detallefuncion", funcion);
            modelo.addAttribute("detalletipoproyecto", tipoproyecto);
            modelo.addAttribute("id_rol", this.GetUsuario().getId_rol());
            modelo.addAttribute("nombres", this.GetUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "AdministrarKardexPersonal/ProyectoDocente/NuevoProyectoDocenteKardex";
        }
        PersonaProyectoDocente proyectodocente = modelMapper.map(model, PersonaProyectoDocente.class);
        int id_personas_proyecto = mi.RegistrarNuevoProyectoKardexPersona(proyectodocente);
        String filename = String.format("%010d", model.getId_persona()) + String.format("%05d", id_personas_proyecto);
        FileUploadServlet.MultipartRequest(request, model.getImage(), DIRECTORIO_PROYECTODOCENTE.key(), "documento", filename);
        String nombrearchivo = Util.EsImagenModificado(proyectodocente.getDocumento(), request.getAttribute("documento").toString());
        if (!nombrearchivo.equals("image.png")) {
            proyectodocente.setId_personas_proyecto(id_personas_proyecto);
            proyectodocente.setDocumento(nombrearchivo);
            mi.ActualizarImagenProyectoKardexDocente(proyectodocente);
        }
        String request = "?id_persona_kardex=" + model.getId_persona_kardex() + "&" + "id_persona=" + model.getId_persona() + "&" + "status=" + Util.encodeValue("alert-success") + "&" + "message=" + Util.encodeValue("Se grabo correctamente el registro del documento");
        return "redirect:/ListarProyectoDocente.fautapo" + request;
    }

    @RequestMapping(value = "/RegistrarModificarProyecto.fautapo", method = RequestMethod.POST)
    public String RegistrarModificarProyecto(@ModelAttribute("model") @Validated PersonaProyectoDocenteModel model, BindingResult result, Model modelo) {
        if (model.getDocumento().equals("image.png")) {
            if (model.getImage() == null || model.getImage().isEmpty()) {
                result.addError(new FieldError("documento", "documento", "Debe cargar la portada de su proyecto"));
            }
        }
        model.setMostrar(model.getText_mostrar() != null ? model.getText_mostrar().equals("on") ? true : false : false);
        model.setRepresentaciondirigencial(model.getText_representaciondirigencial() != null ? model.getText_representaciondirigencial().equals("on") ? true : false : false);
        if (result.hasErrors()) {
            List<ListViewItem> origenproyecto = new ArrayList<>();
            origenproyecto.add(new ListViewItem("", "NINGUNO"));
            origenproyecto.add(new ListViewItem("INTER INSTITUCIONAL", "INTER INSTITUCIONAL"));
            origenproyecto.add(new ListViewItem("FACULTATIVO", "FACULTATIVO"));
            origenproyecto.add(new ListViewItem("CARRERA", "CARRERA"));

            List<ListViewItem> estado = new ArrayList<>();
            estado.add(new ListViewItem("", "NINGUNO"));
            estado.add(new ListViewItem("PRESENTADO", "PRESENTADO"));
            estado.add(new ListViewItem("EVALUADO", "EVALUADO"));
            estado.add(new ListViewItem("APROBADO", "APROBADO"));
            estado.add(new ListViewItem("ELABORADO", "ELABORADO"));
            estado.add(new ListViewItem("EJECUTADO", "EJECUTADO"));

            List<ListViewItem> modalidadgraduacion = new ArrayList<>();
            modalidadgraduacion.add(new ListViewItem("", "NINGUNO"));
            modalidadgraduacion.add(new ListViewItem("TESIS", "TESIS"));
            modalidadgraduacion.add(new ListViewItem("PROYECTO DE GRADO", "PROYECTO DE GRADO"));
            modalidadgraduacion.add(new ListViewItem("INTERNADO ROTATORIO", "INTERNADO ROTATORIO"));
            modalidadgraduacion.add(new ListViewItem("TRABAJO DIRIGIDO", "TRABAJO DIRIGIDO"));
            modalidadgraduacion.add(new ListViewItem("GRADUACIÓN POR EXCELENCIA", "GRADUACIÓN POR EXCELENCIA"));
            modalidadgraduacion.add(new ListViewItem("OTRO", "OTRO"));

            List<ListViewItem> funcion = new ArrayList<>();
            funcion.add(new ListViewItem("", "NINGUNO"));
            funcion.add(new ListViewItem("AUTOR", "AUTOR"));
            funcion.add(new ListViewItem("ASESOR", "ASESOR"));
            funcion.add(new ListViewItem("EJECUTADOR", "EJECUTADOR"));

            List<ListViewItem> tipoproyecto = new ArrayList<>();
            tipoproyecto.add(new ListViewItem("", "NINGUNO"));
            tipoproyecto.add(new ListViewItem("INTERACCIÓN SOCIAL O UNIVERSITARIA", "INTERACCIÓN SOCIAL O UNIVERSITARIA"));
            tipoproyecto.add(new ListViewItem("EXTRA CURRICULAR", "EXTRA CURRICULAR"));
            tipoproyecto.add(new ListViewItem("OTRO", "OTRO"));
            List<ListViewItemSelected> estadoseleccionados = new ArrayList<>();
            String[] ids = Util.isNullOrBlank(model.getEstado()) ? null : model.getEstado().split(",");
            for (ListViewItem item : estado) {
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
                estadoseleccionados.add(new ListViewItemSelected(item.getId(), item.getValue(), encontrado));
            };
            modelo.addAttribute("detalleorigenproyecto", origenproyecto);
            modelo.addAttribute("detalleestado", estadoseleccionados);
            modelo.addAttribute("detallemodalidadgraduacion", modalidadgraduacion);
            modelo.addAttribute("detallefuncion", funcion);
            modelo.addAttribute("detalletipoproyecto", tipoproyecto);
            modelo.addAttribute("id_rol", this.GetUsuario().getId_rol());
            modelo.addAttribute("nombres", this.GetUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "AdministrarKardexPersonal/ProyectoDocente/EditarProyectoDocenteKardex";
        }
        PersonaProyectoDocente proyectodocente = modelMapper.map(model, PersonaProyectoDocente.class);
        String filename = String.format("%010d", model.getId_persona()) + String.format("%05d", proyectodocente.getId_personas_proyecto());
        FileUploadServlet.MultipartRequest(request, model.getImage(), DIRECTORIO_PROYECTODOCENTE.key(), "documento", filename);
        String nombrearchivo = Util.EsImagenModificado(proyectodocente.getDocumento(), request.getAttribute("documento").toString());
        if (!nombrearchivo.equals("image.png")) {
            proyectodocente.setDocumento(nombrearchivo);
        }
        mi.ActualizarDatosProyectoKardexDocente(proyectodocente);
        String request = "?id_persona_kardex=" + model.getId_persona_kardex() + "&" + "id_persona=" + model.getId_persona() + "&" + "status=" + Util.encodeValue("alert-success") + "&" + "message=" + Util.encodeValue("Se Actualizo correctamente el registro del documento");
        return "redirect:/ListarProyectoDocente.fautapo" + request;
    }

    @RequestMapping(value = "/EliminarProyectoDocente.fautapo", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageDeleteResult> EliminarProyectoDocente(@ModelAttribute("model") ItemEliminarModel model) {
        Clientes cliente = this.GetUsuario();
        MessageDeleteResult Respuesta = new MessageDeleteResult();
        if (cliente == null) {
            Respuesta.setMessage("Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            Respuesta.setStatus("Error");
            return new ResponseEntity<>(Respuesta, HttpStatus.OK);
        } else {
            try {
                mi.EliminarProyectoKardexDocente(model.getId_personas_proyecto());
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

    @RequestMapping(value = "/ModificarEstadoAprobacionProyectoEvaluacion.fautapo", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageAprobadoResult> ModificarEstadoAprobacionProyectoEvaluacion(@ModelAttribute("model") ItemAprobarModel model) {
        Clientes cliente = this.GetUsuario();
        MessageAprobadoResult Respuesta = new MessageAprobadoResult();
        if (cliente == null) {
            Respuesta.setMessage("Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            Respuesta.setStatus("Error");
            return new ResponseEntity<>(Respuesta, HttpStatus.OK);
        } else {
            try {
                Boolean estado = model.getName() != null ? model.getName().equals("on") ? true : false : false;
                PersonaProyectoDocente evaluacion = new PersonaProyectoDocente();
                evaluacion.setId_personas_proyecto(model.getId_personas_proyecto());
                evaluacion.setAprobado(estado);
                mi.AprobarProyectoKardexDocente(evaluacion);
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
