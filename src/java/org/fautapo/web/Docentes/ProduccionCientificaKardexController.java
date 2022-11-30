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
import org.fautapo.domain.PersonaProduccionCientifica;
import org.fautapo.domain.logic.MiFacade;
import org.fautapo.model.Kardex.ItemAprobarModel;
import org.fautapo.model.Kardex.ItemEliminarModel;
import org.fautapo.model.Kardex.MessageAprobadoResult;
import org.fautapo.model.Kardex.MessageDeleteResult;
import org.fautapo.model.Kardex.ParametroCreacionModel;
import org.fautapo.model.Kardex.PersonaProduccionCientificaModel;
import org.fautapo.model.MessageResult;
import org.fautapo.util.Convert;
import static org.fautapo.util.Directorio.DIRECTORIO_PRODUCCIONCIENTIFICA;
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
public class ProduccionCientificaKardexController {

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

    @RequestMapping(value = "/ListaParcialProduccionCientifica.fautapo", method = RequestMethod.GET)
    public String ListaParcialProduccionCientifica(Model modelo) {
        Clientes cliente = this.GetUsuario();
        int number = cliente.getInt(request, "number");
        int id_persona_kardex = cliente.getInt(request, "id_persona_kardex");
        PersonaKardex kardex = new PersonaKardex();
        kardex.setNumber(number);
        kardex.setId_persona_kardex(id_persona_kardex);
        List<PersonaProduccionCientifica> produccion = mi.GetPersonaSubTotalProduccionCientificaKardex(kardex);
        int sId_rol = cliente.getId_rol();
        modelo.addAttribute("id_rol", sId_rol);
        modelo.addAttribute("nombres", cliente.getNombres());
        modelo.addAttribute("detalles", produccion);
        return "AdministrarKardexPersonal/DetalleParcialProduccionCientifica";
    }

    @RequestMapping(value = "/ListarProduccionCientifica.fautapo", method = RequestMethod.GET)
    public String ListarProduccionCientifica(Model modelo) {
        Clientes cliente = this.GetUsuario();
        int id_persona_kardex = cliente.getInt(request, "id_persona_kardex");
        int id_persona = cliente.getInt(request, "id_persona");
        String status = cliente.getString(request, "status");
        String message = cliente.getString(request, "message");
        List<PersonaProduccionCientifica> produccion = mi.GetPersonaTotalProduccionCientificaKardex(id_persona_kardex);
        int sId_rol = cliente.getId_rol();
        MessageResult result = new MessageResult();
        result.setMessage(message);
        result.setStatus(status);
        modelo.addAttribute("id_rol", sId_rol);
        modelo.addAttribute("nombres", cliente.getNombres());
        modelo.addAttribute("detalles", produccion);
        modelo.addAttribute("id_persona", id_persona);
        modelo.addAttribute("result", result);
        modelo.addAttribute("id_persona_kardex", id_persona_kardex);
        return "AdministrarKardexPersonal/ProduccionCientifica/DetalleProduccionCientificaKardex";
    }

    @RequestMapping(value = "/FormularioRegistroProduccionKardex.fautapo", method = RequestMethod.GET)
    public String FormularioRegistroProduccionKardex(@ModelAttribute("parametro") ParametroCreacionModel parametro, Model modelo) {
        List<ListViewItem> programas = mi.GetProgramasPregrado();
        List<ListViewItem> categoria = new ArrayList<>();
        categoria.add(new ListViewItem("", "NINGUNO"));
        categoria.add(new ListViewItem("LIBROS", "LIBROS"));
        categoria.add(new ListViewItem("REVISTAS", "REVISTAS"));
        categoria.add(new ListViewItem("MANUALES", "MANUALES"));
        categoria.add(new ListViewItem("GUÍAS", "GUÍAS"));
        categoria.add(new ListViewItem("DIAPOSITIVAS", "DIAPOSITIVAS"));
        categoria.add(new ListViewItem("VIDEOS EDUCATIVOS", "VIDEOS EDUCATIVOS"));
        categoria.add(new ListViewItem("OTROS", "OTROS"));
        List<ListViewItem> tipo_produccion = new ArrayList<>();
        tipo_produccion.add(new ListViewItem("", "NINGUNO"));
        tipo_produccion.add(new ListViewItem("EDITADO", "EDITADO"));
        tipo_produccion.add(new ListViewItem("INEDITO", "INEDITO"));
        List<ListViewItem> tipo_objetivo = new ArrayList<>();
        tipo_objetivo.add(new ListViewItem("", "NINGUNO"));
        tipo_objetivo.add(new ListViewItem("EXTENSION", "EXTENSION"));
        tipo_objetivo.add(new ListViewItem("INVESTIGACION", "INVESTIGACION"));
        tipo_objetivo.add(new ListViewItem("DOCENCIA", "DOCENCIA"));
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString().replace("-", "").toUpperCase();
        PersonaProduccionCientificaModel model = null;
        String[] ids = null;
        List<ListViewItemSelected> programaseleccionados = new ArrayList<>();
        if (parametro.getId_produccion_cientifica() == null) {
            model = new PersonaProduccionCientificaModel();
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
            modelo.addAttribute("detallecategoria", categoria);
            modelo.addAttribute("detalleproduccion", tipo_produccion);
            modelo.addAttribute("detalleobjetivo", tipo_objetivo);
            modelo.addAttribute("id_rol", this.GetUsuario().getId_rol());
            modelo.addAttribute("nombres", this.GetUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "AdministrarKardexPersonal/ProduccionCientifica/NuevoProduccionCientificaKardex";
        } else {
            PersonaProduccionCientifica produccioncientifica = mi.GetPersonaProduccionCientificaKardex(parametro.getId_produccion_cientifica());
            model = modelMapper.map(produccioncientifica, PersonaProduccionCientificaModel.class);
            model.setId_persona(parametro.getId_persona());
            model.setText_fecha_certificacion(Convert.ToString(model.getFecha_certificacion(), "dd/MM/yyyy"));
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
            modelo.addAttribute("detallecategoria", categoria);
            modelo.addAttribute("detalleproduccion", tipo_produccion);
            modelo.addAttribute("detalleobjetivo", tipo_objetivo);
            modelo.addAttribute("id_rol", this.GetUsuario().getId_rol());
            modelo.addAttribute("nombres", this.GetUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "AdministrarKardexPersonal/ProduccionCientifica/EditarProduccionCientificaKardex";
        }
    }

    @RequestMapping(value = "/RegistrarPersonaProduccionKardex.fautapo", method = RequestMethod.POST)
    public String RegistrarPersonaProduccionKardex(@ModelAttribute("model") @Validated PersonaProduccionCientificaModel model, BindingResult result, Model modelo) {
        if (model.getUrl_portada_libro().equals("image.png")) {
            if (model.getImage() == null || model.getImage().isEmpty()) {
                result.addError(new FieldError("url_portada_libro", "url_portada_libro", "Debe cargar la image de su fotocopia de la portada de produccion cientifica"));
            }
        }
        model.setPublicado(model.getText_publicado() != null ? model.getText_publicado().equals("on") ? true : false : false);
        model.setMostrar(model.getText_mostrar() != null ? model.getText_mostrar().equals("on") ? true : false : false);
        model.setFecha_certificacion(Convert.ToDate(model.getText_fecha_certificacion(), "dd/MM/yyyy"));
        if (result.hasErrors()) {
            List<ListViewItem> programas = mi.GetProgramasPregrado();
            List<ListViewItem> categoria = new ArrayList<>();
            categoria.add(new ListViewItem("", "NINGUNO"));
            categoria.add(new ListViewItem("LIBROS", "LIBROS"));
            categoria.add(new ListViewItem("REVISTAS", "REVISTAS"));
            categoria.add(new ListViewItem("MANUALES", "MANUALES"));
            categoria.add(new ListViewItem("GUÍAS", "GUÍAS"));
            categoria.add(new ListViewItem("DIAPOSITIVAS", "DIAPOSITIVAS"));
            categoria.add(new ListViewItem("VIDEOS EDUCATIVOS", "VIDEOS EDUCATIVOS"));
            categoria.add(new ListViewItem("OTROS", "OTROS"));
            List<ListViewItem> tipo_produccion = new ArrayList<>();
            tipo_produccion.add(new ListViewItem("", "NINGUNO"));
            tipo_produccion.add(new ListViewItem("EDITADO", "EDITADO"));
            tipo_produccion.add(new ListViewItem("INEDITO", "INEDITO"));
            List<ListViewItem> tipo_objetivo = new ArrayList<>();
            tipo_objetivo.add(new ListViewItem("", "NINGUNO"));
            tipo_objetivo.add(new ListViewItem("EXTENSION", "EXTENSION"));
            tipo_objetivo.add(new ListViewItem("INVESTIGACION", "INVESTIGACION"));
            tipo_objetivo.add(new ListViewItem("DOCENCIA", "DOCENCIA"));
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
            modelo.addAttribute("detallecategoria", categoria);
            modelo.addAttribute("detalleproduccion", tipo_produccion);
            modelo.addAttribute("detalleobjetivo", tipo_objetivo);
            modelo.addAttribute("id_rol", this.GetUsuario().getId_rol());
            modelo.addAttribute("nombres", this.GetUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "AdministrarKardexPersonal/ProduccionCientifica/NuevoProduccionCientificaKardex";
        }
        PersonaProduccionCientifica produccioncientifica = modelMapper.map(model, PersonaProduccionCientifica.class);
        int id_produccion_cientifica = mi.RegistrarProduccionCientificaKardex(produccioncientifica);
        String filename = String.format("%010d", model.getId_persona()) + String.format("%05d", id_produccion_cientifica);
        FileUploadServlet.MultipartRequest(request, model.getImage(), DIRECTORIO_PRODUCCIONCIENTIFICA.key(), "url_portada_libro", filename);
        String nombrearchivo = Util.EsImagenModificado(produccioncientifica.getUrl_portada_libro(), request.getAttribute("url_portada_libro").toString());
        if (!nombrearchivo.equals("image.png")) {
            produccioncientifica.setId_produccion_cientifica(id_produccion_cientifica);
            produccioncientifica.setUrl_portada_libro(nombrearchivo);
            mi.ActualizarImagenProduccionCientificaKardexDocente(produccioncientifica);
        }
        String request = "?id_persona_kardex=" + model.getId_persona_kardex() + "&" + "id_persona=" + model.getId_persona() + "&" + "status=" + Util.encodeValue("alert-success") + "&" + "message=" + Util.encodeValue("Se grabo correctamente el registro del documento");
        return "redirect:/ListarProduccionCientifica.fautapo" + request;
    }

    @RequestMapping(value = "/RegistrarModificarProduccion.fautapo", method = RequestMethod.POST)
    public String RegistrarModificarProduccion(@ModelAttribute("model") @Validated PersonaProduccionCientificaModel model, BindingResult result, Model modelo) {
        if (model.getUrl_portada_libro().equals("image.png")) {
            if (model.getImage() == null || model.getImage().isEmpty()) {
                result.addError(new FieldError("url_portada_libro", "url_portada_libro", "Debe cargar la image de su fotocopia de la portada de produccion cientifica"));
            }
        }
        model.setPublicado(model.getText_publicado() != null ? model.getText_publicado().equals("on") ? true : false : false);
        model.setMostrar(model.getText_mostrar() != null ? model.getText_mostrar().equals("on") ? true : false : false);
        model.setFecha_certificacion(Convert.ToDate(model.getText_fecha_certificacion(), "dd/MM/yyyy"));
        if (result.hasErrors()) {
            List<ListViewItem> programas = mi.GetProgramasPregrado();
            List<ListViewItem> categoria = new ArrayList<>();
            categoria.add(new ListViewItem("", "NINGUNO"));
            categoria.add(new ListViewItem("LIBROS", "LIBROS"));
            categoria.add(new ListViewItem("REVISTAS", "REVISTAS"));
            categoria.add(new ListViewItem("MANUALES", "MANUALES"));
            categoria.add(new ListViewItem("GUÍAS", "GUÍAS"));
            categoria.add(new ListViewItem("DIAPOSITIVAS", "DIAPOSITIVAS"));
            categoria.add(new ListViewItem("VIDEOS EDUCATIVOS", "VIDEOS EDUCATIVOS"));
            categoria.add(new ListViewItem("OTROS", "OTROS"));
            List<ListViewItem> tipo_produccion = new ArrayList<>();
            tipo_produccion.add(new ListViewItem("", "NINGUNO"));
            tipo_produccion.add(new ListViewItem("EDITADO", "EDITADO"));
            tipo_produccion.add(new ListViewItem("INEDITO", "INEDITO"));
            List<ListViewItem> tipo_objetivo = new ArrayList<>();
            tipo_objetivo.add(new ListViewItem("", "NINGUNO"));
            tipo_objetivo.add(new ListViewItem("EXTENSION", "EXTENSION"));
            tipo_objetivo.add(new ListViewItem("INVESTIGACION", "INVESTIGACION"));
            tipo_objetivo.add(new ListViewItem("DOCENCIA", "DOCENCIA"));
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
            modelo.addAttribute("detallecategoria", categoria);
            modelo.addAttribute("detalleproduccion", tipo_produccion);
            modelo.addAttribute("detalleobjetivo", tipo_objetivo);
            modelo.addAttribute("id_rol", this.GetUsuario().getId_rol());
            modelo.addAttribute("nombres", this.GetUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "AdministrarKardexPersonal/ProduccionCientifica/EditarProduccionCientificaKardex";
        }
        PersonaProduccionCientifica produccioncientifica = modelMapper.map(model, PersonaProduccionCientifica.class);
        String filename = String.format("%010d", model.getId_persona()) + String.format("%05d", produccioncientifica.getId_produccion_cientifica());
        FileUploadServlet.MultipartRequest(request, model.getImage(), DIRECTORIO_PRODUCCIONCIENTIFICA.key(), "url_portada_libro", filename);
        String nombrearchivo = Util.EsImagenModificado(produccioncientifica.getUrl_portada_libro(), request.getAttribute("url_portada_libro").toString());
        if (!nombrearchivo.equals("image.png")) {
            produccioncientifica.setUrl_portada_libro(nombrearchivo);
        }
        mi.ActualizarProduccionCientificaKardexDocente(produccioncientifica);
        String request = "?id_persona_kardex=" + model.getId_persona_kardex() + "&" + "id_persona=" + model.getId_persona() + "&" + "status=" + Util.encodeValue("alert-success") + "&" + "message=" + Util.encodeValue("Se Actualizo correctamente el registro del documento");
        return "redirect:/ListarProduccionCientifica.fautapo" + request;
    }

    @RequestMapping(value = "/EliminarProduccionCientifica.fautapo", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageDeleteResult> EliminarProduccionCientifica(@ModelAttribute("model") ItemEliminarModel model) {
        Clientes cliente = this.GetUsuario();
        MessageDeleteResult Respuesta = new MessageDeleteResult();
        if (cliente == null) {
            Respuesta.setMessage("Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            Respuesta.setStatus("Error");
            return new ResponseEntity<>(Respuesta, HttpStatus.OK);
        } else {
            try {
                mi.EliminarProduccionCientificaKardexDocente(model.getId_produccion_cientifica());
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

    @RequestMapping(value = "/ModificarEstadoAprobacionDocumentoProduccion.fautapo", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageAprobadoResult> ModificarEstadoAprobacionDocumentoProduccion(@ModelAttribute("model") ItemAprobarModel model) {
        Clientes cliente = this.GetUsuario();
        MessageAprobadoResult Respuesta = new MessageAprobadoResult();
        if (cliente == null) {
            Respuesta.setMessage("Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            Respuesta.setStatus("Error");
            return new ResponseEntity<>(Respuesta, HttpStatus.OK);
        } else {
            try {
                Boolean estado = model.getName() != null ? model.getName().equals("on") ? true : false : false;
                PersonaProduccionCientifica produccion = new PersonaProduccionCientifica();
                produccion.setId_produccion_cientifica(model.getId_produccion_cientifica());
                produccion.setAprobado(estado);
                mi.AprobarProduccionCientificaKardexDocente(produccion);
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
