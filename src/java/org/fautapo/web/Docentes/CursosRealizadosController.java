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
import org.fautapo.domain.PersonaCursosRealizados;
import org.fautapo.domain.PersonaKardex;
import org.fautapo.domain.logic.MiFacade;
import org.fautapo.model.Kardex.ItemAprobarModel;
import org.fautapo.model.Kardex.ItemEliminarModel;
import org.fautapo.model.Kardex.MessageAprobadoResult;
import org.fautapo.model.Kardex.MessageDeleteResult;
import org.fautapo.model.Kardex.ParametroCreacionModel;
import org.fautapo.model.Kardex.PersonaCursosRealizadosModel;
import org.fautapo.model.MessageResult;
import org.fautapo.util.Convert;
import static org.fautapo.util.Directorio.DIRECTORIO_CURSOSREALIZADOS;
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
public class CursosRealizadosController {

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

    @RequestMapping(value = "/ListaParcialCursosRealizados.fautapo", method = RequestMethod.GET)
    public String ListaParcialCursosRealizados(Model modelo) {
        Clientes cliente = this.GetUsuario();
        int number = cliente.getInt(request, "number");
        int id_persona_kardex = cliente.getInt(request, "id_persona_kardex");
        PersonaKardex kardex = new PersonaKardex();
        kardex.setNumber(number);
        kardex.setId_persona_kardex(id_persona_kardex);
        List<PersonaCursosRealizados> detallecursos = mi.GetPersonaSubTotalCursosRealizadosKardex(kardex);
        int sId_rol = cliente.getId_rol();
        modelo.addAttribute("id_rol", sId_rol);
        modelo.addAttribute("nombres", cliente.getNombres());
        modelo.addAttribute("detalles", detallecursos);
        return "AdministrarKardexPersonal/DetalleParciaCursosRealizados";
    }

    @RequestMapping(value = "/ListarCursosRealizados.fautapo", method = RequestMethod.GET)
    public String ListarCursosRealizados(Model modelo) {
        Clientes cliente = this.GetUsuario();
        int id_persona_kardex = cliente.getInt(request, "id_persona_kardex");
        int id_persona = cliente.getInt(request, "id_persona");
        String status = cliente.getString(request, "status");
        String message = cliente.getString(request, "message");
        List<PersonaCursosRealizados> detallecursos = mi.GetPersonaTotalCursosRealizadosKardex(id_persona_kardex);
        int sId_rol = cliente.getId_rol();
        MessageResult result = new MessageResult();
        result.setMessage(message);
        result.setStatus(status);
        modelo.addAttribute("id_rol", sId_rol);
        modelo.addAttribute("nombres", cliente.getNombres());
        modelo.addAttribute("detalles", detallecursos);
        modelo.addAttribute("id_persona", id_persona);
        modelo.addAttribute("result", result);
        modelo.addAttribute("id_persona_kardex", id_persona_kardex);
        return "AdministrarKardexPersonal/CursosRealizados/DetalleCursosRealizadosKardex";
    }

    @RequestMapping(value = "/FormularioRegistroCursosRealizadosKardex.fautapo", method = RequestMethod.GET)
    public String FormularioRegistroCursosRealizadosKardex(@ModelAttribute("parametro") ParametroCreacionModel parametro, Model modelo) {
        List<ListViewItem> tipoevento = new ArrayList<>();
        tipoevento.add(new ListViewItem("", "NINGUNO"));
        tipoevento.add(new ListViewItem("DE RECONOCIMIENTOS", "DE RECONOCIMIENTOS "));
        tipoevento.add(new ListViewItem("COMO EXPOSITORES/DISERTANTE", "COMO EXPOSITORES/DISERTANTE"));
        tipoevento.add(new ListViewItem("COMO ORGANIZADOR / COLABORADOR", "COMO ORGANIZADOR / COLABORADOR "));
        tipoevento.add(new ListViewItem("COMO ASISTENTE", "COMO ASISTENTE"));
        List<ListViewItem> tipoorganizacion = new ArrayList<>();
        tipoorganizacion.add(new ListViewItem("", "NINGUNO"));
        tipoorganizacion.add(new ListViewItem("SEMINARIO", "SEMINARIO"));
        tipoorganizacion.add(new ListViewItem("CURSO", "CURSO"));
        tipoorganizacion.add(new ListViewItem("FORO", "FORO"));
        tipoorganizacion.add(new ListViewItem("TALLER", "TALLER"));
        tipoorganizacion.add(new ListViewItem("SIMPOSIO", "SIMPOSIO"));
        tipoorganizacion.add(new ListViewItem("DEBATE", "DEBATE"));
        tipoorganizacion.add(new ListViewItem("CONFERENCIA", "CONFERENCIA"));
        tipoorganizacion.add(new ListViewItem("CONGRESO", "CONGRESO"));
        tipoorganizacion.add(new ListViewItem("JORNADA", "JORNADA"));
        tipoorganizacion.add(new ListViewItem("MESA REDONDA", "MESA REDONDA"));
        tipoorganizacion.add(new ListViewItem("PANELES", "PANELES"));
        tipoorganizacion.add(new ListViewItem("REUNION", "REUNION"));
        tipoorganizacion.add(new ListViewItem("FERIA", "FERIA"));
        List<ListViewItem> objetivo_curso = new ArrayList<>();
        objetivo_curso.add(new ListViewItem("", "NINGUNO"));
        objetivo_curso.add(new ListViewItem("ACTUALIZACION", "ACTUALIZACION"));
        objetivo_curso.add(new ListViewItem("ESPECIALIZACION", "ESPECIALIZACION"));
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString().replace("-", "").toUpperCase();
        PersonaCursosRealizadosModel model = null;
        if (parametro.getId_cursos_realizados() == null) {
            model = new PersonaCursosRealizadosModel();
            model.setId_persona(parametro.getId_persona());
            model.setId_persona_kardex(parametro.getId_persona_kardex());
            model.setUUID(randomUUIDString);
            model.setRoot(parametro.getRoot());
            modelo.addAttribute("detalleorganizacion", tipoorganizacion);
            modelo.addAttribute("detalleevento", tipoevento);
            modelo.addAttribute("objetivo_curso", objetivo_curso);
            modelo.addAttribute("id_rol", this.GetUsuario().getId_rol());
            modelo.addAttribute("nombres", this.GetUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "AdministrarKardexPersonal/CursosRealizados/NuevoCursosRealizadosKardex";
        } else {
            PersonaCursosRealizados cursosrealizados = mi.GetPersonaCursosRealizadosKardex(parametro.getId_cursos_realizados());
            model = modelMapper.map(cursosrealizados, PersonaCursosRealizadosModel.class);
            model.setId_persona(parametro.getId_persona());
            model.setUUID(randomUUIDString);
            model.setRoot(parametro.getRoot());
            model.setText_fechapresentacion(Convert.ToString(model.getFechapresentacion(), "dd/MM/yyyy"));
            model.setMostrar(model.getText_mostrar() != null ? model.getText_mostrar().equals("on") ? true : false : false);

            modelo.addAttribute("detalleorganizacion", tipoorganizacion);
            modelo.addAttribute("detalleevento", tipoevento);
            modelo.addAttribute("objetivo_curso", objetivo_curso);
            modelo.addAttribute("id_rol", this.GetUsuario().getId_rol());
            modelo.addAttribute("nombres", this.GetUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "AdministrarKardexPersonal/CursosRealizados/EditarCursosRealizadosKardex";
        }

    }

    @RequestMapping(value = "/RegistrarPersonaCursosKardex.fautapo", method = RequestMethod.POST)
    public String RegistrarPersonaCursosKardex(@ModelAttribute("model") @Validated PersonaCursosRealizadosModel model, BindingResult result, Model modelo) {
        if (model.getUrl_cursos().equals("image.png")) {
            if (model.getImage() == null || model.getImage().isEmpty()) {
                result.addError(new FieldError("url_cursos", "url_cursos", "Debe cargar la image de su fotocopia del certificado del evento"));
            }
        }
        model.setMostrar(model.getText_mostrar() != null ? model.getText_mostrar().equals("on") ? true : false : false);
        model.setFechapresentacion(Convert.ToDate(model.getText_fechapresentacion(), "dd/MM/yyyy"));
        if (result.hasErrors()) {
            List<ListViewItem> tipoevento = new ArrayList<>();
            tipoevento.add(new ListViewItem("", "NINGUNO"));
            tipoevento.add(new ListViewItem("DE RECONOCIMIENTOS", "DE RECONOCIMIENTOS "));
            tipoevento.add(new ListViewItem("COMO EXPOSITORES/DISERTANTE", "COMO EXPOSITORES/DISERTANTE"));
            tipoevento.add(new ListViewItem("COMO ORGANIZADOR / COLABORADOR", "COMO ORGANIZADOR / COLABORADOR "));
            tipoevento.add(new ListViewItem("COMO ASISTENTE", "COMO ASISTENTE"));
            List<ListViewItem> tipoorganizacion = new ArrayList<>();
            tipoorganizacion.add(new ListViewItem("", "NINGUNO"));
            tipoorganizacion.add(new ListViewItem("SEMINARIO", "SEMINARIO"));
            tipoorganizacion.add(new ListViewItem("CURSO", "CURSO"));
            tipoorganizacion.add(new ListViewItem("FORO", "FORO"));
            tipoorganizacion.add(new ListViewItem("TALLER", "TALLER"));
            tipoorganizacion.add(new ListViewItem("SIMPOSIO", "SIMPOSIO"));
            tipoorganizacion.add(new ListViewItem("DEBATE", "DEBATE"));
            tipoorganizacion.add(new ListViewItem("CONFERENCIA", "CONFERENCIA"));
            tipoorganizacion.add(new ListViewItem("CONGRESO", "CONGRESO"));
            tipoorganizacion.add(new ListViewItem("JORNADA", "JORNADA"));
            tipoorganizacion.add(new ListViewItem("MESA REDONDA", "MESA REDONDA"));
            tipoorganizacion.add(new ListViewItem("PANELES", "PANELES"));
            tipoorganizacion.add(new ListViewItem("REUNION", "REUNION"));
            tipoorganizacion.add(new ListViewItem("FERIA", "FERIA"));
            List<ListViewItem> objetivo_curso = new ArrayList<>();
            objetivo_curso.add(new ListViewItem("", "NINGUNO"));
            objetivo_curso.add(new ListViewItem("ACTUALIZACION", "ACTUALIZACION"));
            objetivo_curso.add(new ListViewItem("ESPECIALIZACION", "ESPECIALIZACION"));
            modelo.addAttribute("detalleorganizacion", tipoorganizacion);
            modelo.addAttribute("detalleevento", tipoevento);
            modelo.addAttribute("objetivo_curso", objetivo_curso);
            modelo.addAttribute("id_rol", this.GetUsuario().getId_rol());
            modelo.addAttribute("nombres", this.GetUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "AdministrarKardexPersonal/CursosRealizados/NuevoCursosRealizadosKardex";
        }
        PersonaCursosRealizados cursosrealizados = modelMapper.map(model, PersonaCursosRealizados.class);
        int id_cursos_realizados = mi.RegistrarCursosRealizadosKardex(cursosrealizados);
        String filename = String.format("%010d", model.getId_persona()) + String.format("%05d", id_cursos_realizados);
        FileUploadServlet.MultipartRequest(request, model.getImage(), DIRECTORIO_CURSOSREALIZADOS.key(), "url_cursos", filename);
        String nombrearchivo = Util.EsImagenModificado(cursosrealizados.getUrl_cursos(), request.getAttribute("url_cursos").toString());
        if (!nombrearchivo.equals("image.png")) {
            cursosrealizados.setId_cursos_realizados(id_cursos_realizados);
            cursosrealizados.setUrl_cursos(nombrearchivo);
            mi.ActualizarImagenCursosRealizadosKardexDocente(cursosrealizados);
        }
        String request = "?id_persona_kardex=" + model.getId_persona_kardex() + "&" + "id_persona=" + model.getId_persona() + "&" + "status=" + Util.encodeValue("alert-success") + "&" + "message=" + Util.encodeValue("Se grabo correctamente el registro del documento");
        return "redirect:/ListarCursosRealizados.fautapo" + request;
    }

    @RequestMapping(value = "/RegistrarModificarCursos.fautapo", method = RequestMethod.POST)
    public String RegistrarModificarCursos(@ModelAttribute("model") @Validated PersonaCursosRealizadosModel model, BindingResult result, Model modelo) {
        if (model.getUrl_cursos().equals("image.png")) {
            if (model.getImage() == null || model.getImage().isEmpty()) {
                result.addError(new FieldError("url_cursos", "url_cursos", "Debe cargar la image de su fotocopia del certificado del evento"));
            }
        }
        model.setMostrar(model.getText_mostrar() != null ? model.getText_mostrar().equals("on") ? true : false : false);
        model.setFechapresentacion(Convert.ToDate(model.getText_fechapresentacion(), "dd/MM/yyyy"));
        if (result.hasErrors()) {
            List<ListViewItem> tipoevento = new ArrayList<>();
            tipoevento.add(new ListViewItem("", "NINGUNO"));
            tipoevento.add(new ListViewItem("DE RECONOCIMIENTOS", "DE RECONOCIMIENTOS "));
            tipoevento.add(new ListViewItem("COMO EXPOSITORES/DISERTANTE", "COMO EXPOSITORES/DISERTANTE"));
            tipoevento.add(new ListViewItem("COMO ORGANIZADOR / COLABORADOR", "COMO ORGANIZADOR / COLABORADOR "));
            tipoevento.add(new ListViewItem("COMO ASISTENTE", "COMO ASISTENTE"));
            List<ListViewItem> tipoorganizacion = new ArrayList<>();
            tipoorganizacion.add(new ListViewItem("", "NINGUNO"));
            tipoorganizacion.add(new ListViewItem("SEMINARIO", "SEMINARIO"));
            tipoorganizacion.add(new ListViewItem("CURSO", "CURSO"));
            tipoorganizacion.add(new ListViewItem("FORO", "FORO"));
            tipoorganizacion.add(new ListViewItem("TALLER", "TALLER"));
            tipoorganizacion.add(new ListViewItem("SIMPOSIO", "SIMPOSIO"));
            tipoorganizacion.add(new ListViewItem("DEBATE", "DEBATE"));
            tipoorganizacion.add(new ListViewItem("CONFERENCIA", "CONFERENCIA"));
            tipoorganizacion.add(new ListViewItem("CONGRESO", "CONGRESO"));
            tipoorganizacion.add(new ListViewItem("JORNADA", "JORNADA"));
            tipoorganizacion.add(new ListViewItem("MESA REDONDA", "MESA REDONDA"));
            tipoorganizacion.add(new ListViewItem("PANELES", "PANELES"));
            tipoorganizacion.add(new ListViewItem("REUNION", "REUNION"));
            tipoorganizacion.add(new ListViewItem("FERIA", "FERIA"));
            List<ListViewItem> objetivo_curso = new ArrayList<>();
            objetivo_curso.add(new ListViewItem("", "NINGUNO"));
            objetivo_curso.add(new ListViewItem("ACTUALIZACION", "ACTUALIZACION"));
            objetivo_curso.add(new ListViewItem("ESPECIALIZACION", "ESPECIALIZACION"));
            modelo.addAttribute("detalleorganizacion", tipoorganizacion);
            modelo.addAttribute("detalleevento", tipoevento);
            modelo.addAttribute("objetivo_curso", objetivo_curso);
            modelo.addAttribute("id_rol", this.GetUsuario().getId_rol());
            modelo.addAttribute("nombres", this.GetUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "AdministrarKardexPersonal/CursosRealizados/EditarCursosRealizadosKardex";
        }
        PersonaCursosRealizados cursosrealizados = modelMapper.map(model, PersonaCursosRealizados.class);
        String filename = String.format("%010d", model.getId_persona()) + String.format("%05d", cursosrealizados.getId_cursos_realizados());
        FileUploadServlet.MultipartRequest(request, model.getImage(), DIRECTORIO_CURSOSREALIZADOS.key(), "url_cursos", filename);
        String nombrearchivo = Util.EsImagenModificado(cursosrealizados.getUrl_cursos(), request.getAttribute("url_cursos").toString());
        if (!nombrearchivo.equals("image.png")) {
            cursosrealizados.setUrl_cursos(nombrearchivo);
        }
        mi.ActualizarDatosCursosRealizadosKardexDocente(cursosrealizados);
        String request = "?id_persona_kardex=" + model.getId_persona_kardex() + "&" + "id_persona=" + model.getId_persona() + "&" + "status=" + Util.encodeValue("alert-success") + "&" + "message=" + Util.encodeValue("Se Actualizo correctamente el registro del documento");
        return "redirect:/ListarCursosRealizados.fautapo" + request;
    }

    @RequestMapping(value = "/EliminarCursosRealizados.fautapo", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageDeleteResult> EliminarCursosRealizados(@ModelAttribute("model") ItemEliminarModel model) {
        Clientes cliente = this.GetUsuario();
        MessageDeleteResult Respuesta = new MessageDeleteResult();
        if (cliente == null) {
            Respuesta.setMessage("Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            Respuesta.setStatus("Error");
            return new ResponseEntity<>(Respuesta, HttpStatus.OK);
        } else {
            try {
                mi.EliminarCursosRealizadosKardexDocente(model.getId_cursos_realizados());
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

    @RequestMapping(value = "/ModificarEstadoAprobacionDocumentoCursos.fautapo", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageAprobadoResult> ModificarEstadoAprobacionDocumentoCursos(@ModelAttribute("model") ItemAprobarModel model) {
        Clientes cliente = this.GetUsuario();
        MessageAprobadoResult Respuesta = new MessageAprobadoResult();
        if (cliente == null) {
            Respuesta.setMessage("Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            Respuesta.setStatus("Error");
            return new ResponseEntity<>(Respuesta, HttpStatus.OK);
        } else {
            try {
                Boolean estado = model.getName() != null ? model.getName().equals("on") ? true : false : false;
                PersonaCursosRealizados cursos = new PersonaCursosRealizados();
                cursos.setId_cursos_realizados(model.getId_cursos_realizados());
                cursos.setAprobado(estado);
                mi.AprobarCursosRealizadosKardexDocente(cursos);
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
