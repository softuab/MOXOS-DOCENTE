/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.web.Docentes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.KardexPersonal;
import org.fautapo.domain.ParametrosBusqueda;
import org.fautapo.domain.PersonaKardex;
import org.fautapo.domain.Personas;
import org.fautapo.domain.Programas;
import org.fautapo.domain.logic.MiFacade;
import org.fautapo.model.ImprimirKardexModel;
import org.fautapo.model.Kardex.DatosEducacionPosgradoModel;
import org.fautapo.model.Kardex.DatosEducacionPregradoModel;
import org.fautapo.model.Kardex.DatosIdentificacionPersonalModel;
import org.fautapo.model.Kardex.DatosInformacionContactoModel;
import org.fautapo.model.Kardex.DatosPersonalesModel;
import org.fautapo.model.Kardex.DatosServicioMilitarModel;
import org.fautapo.model.Kardex.DatosformacionLaboralPersonalModel;
import org.fautapo.model.Kardex.FileDownloadModel;
import org.fautapo.model.Kardex.FileKardexModel;
import org.fautapo.model.MediaFileModel;
import org.fautapo.model.MessageMediaResult;
import org.fautapo.model.PersonaKardexModel;
import org.fautapo.model.ResultFileBase64;
import org.fautapo.util.Convert;
import static org.fautapo.util.Directorio.DIRECTORIO_ACTIVIDADES_ACADEMICAS;
import static org.fautapo.util.Directorio.DIRECTORIO_CARNET;
import static org.fautapo.util.Directorio.DIRECTORIO_CURSOSREALIZADOS;
import static org.fautapo.util.Directorio.DIRECTORIO_EVALUACIONDOCENTE;
import static org.fautapo.util.Directorio.DIRECTORIO_EXPERIENCIALABORAL;
import static org.fautapo.util.Directorio.DIRECTORIO_FORMACIONACADEMICA;
import static org.fautapo.util.Directorio.DIRECTORIO_IDIOMAS;
import static org.fautapo.util.Directorio.DIRECTORIO_LIBRETAMILITAR;
import static org.fautapo.util.Directorio.DIRECTORIO_MODALIDADES;
import static org.fautapo.util.Directorio.DIRECTORIO_PRODUCCIONCIENTIFICA;
import static org.fautapo.util.Directorio.DIRECTORIO_PROYECTODOCENTE;
import static org.fautapo.util.Directorio.DIRECTORIO_TITULOPOSGRADOEDUCACIONSUPERIOR;
import static org.fautapo.util.Directorio.DIRECTORIO_TITULOPROVISIONNACIONAL;
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
public class KardexDocenteController {

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

    @RequestMapping("/VerificarYObtenerKardex.fautapo")
    public String VerificarYObtenerKardex(Model modelo) {
        Clientes cliente = this.GetUsuario();
        if (cliente == null) {
            modelo.addAttribute("mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            return "Aviso";
        }
        int id_docente = cliente.getId_usuario();
        Personas persona = mi.getPrsBuscarPersonaDocente(id_docente);
        PersonaKardexModel KardexModel = null;
        PersonaKardex kardex = mi.GetKardexPersonalNuevo(persona.getId_persona());
        if (kardex == null) {
            KardexModel = new PersonaKardexModel();
            KardexModel.setUlt_usuario(cliente.getId_usuario());
            KardexModel.setId_persona(persona.getId_persona());
            kardex = modelMapper.map(KardexModel, PersonaKardex.class);
            mi.RegistrarNuevoKardexDocente(kardex);
            kardex = mi.GetKardexPersonalNuevo(persona.getId_persona());
        }
        KardexModel = modelMapper.map(kardex, PersonaKardexModel.class);
        modelo.addAttribute("snombre", cliente.getNombres().substring(0, 10) + "...");
        modelo.addAttribute("simagen", cliente.getImagen());
        modelo.addAttribute("kardex", KardexModel);
        return "AdministrarKardexPersonal/DetalleKardex";
    }

    @RequestMapping(value = "/kardex/EditarInformacionPersonal.fautapo", method = RequestMethod.GET)
    public String EditarInformacionPersonal(@ModelAttribute("id_persona") Integer id_persona, Model modelo) {
        DatosPersonalesModel model = new DatosPersonalesModel();
        PersonaKardex kardex = mi.GetKardexPersonalNuevo(id_persona);
        model = modelMapper.map(kardex, DatosPersonalesModel.class);
        model.setText_fechanacimiento(Convert.ToString(model.getFechanacimiento(), "dd/MM/yyyy"));
        List<ListViewItem> localidades = mi.GetLocalidadPersona();
        List<ListViewItem> estadocivil = new ArrayList<>();
        estadocivil.add(new ListViewItem("", "NINGUNO"));
        estadocivil.add(new ListViewItem("SOLTERO(A)", "SOLTERO(A)"));
        estadocivil.add(new ListViewItem("CASADO(A)", "CASADO(A)"));
        estadocivil.add(new ListViewItem("UNIÓN LIBRE O UNIÓN DE HECHO", "UNIÓN LIBRE O UNIÓN DE HECHO"));
        estadocivil.add(new ListViewItem("DIVORCIADO(A)", "DIVORCIADO(A)"));
        estadocivil.add(new ListViewItem("VIUDO(A)", "VIUDO(A)"));
        modelo.addAttribute("localidades", localidades);
        modelo.addAttribute("estadocivil", estadocivil);
        modelo.addAttribute("model", model);
        return "AdministrarKardexPersonal/kardex/InformacionPersonal";
    }

    @RequestMapping(value = "/kardex/RegistrarInformacionPersonal.fautapo", method = RequestMethod.POST)
    public String RegistrarInformacionPersonal(@ModelAttribute("model") @Validated DatosPersonalesModel model, BindingResult result, Model modelo) {
        model.setSexo(model.getText_sexo() != null ? model.getText_sexo().equals("on") ? true : false : false);
        if (result.hasErrors()) {
            List<ListViewItem> localidades = mi.GetLocalidadPersona();
            List<ListViewItem> estadocivil = new ArrayList<>();
            estadocivil.add(new ListViewItem("", "NINGUNO"));
            estadocivil.add(new ListViewItem("SOLTERO(A)", "SOLTERO(A)"));
            estadocivil.add(new ListViewItem("CASADO(A)", "CASADO(A)"));
            estadocivil.add(new ListViewItem("UNIÓN LIBRE O UNIÓN DE HECHO", "UNIÓN LIBRE O UNIÓN DE HECHO"));
            estadocivil.add(new ListViewItem("DIVORCIADO(A)", "DIVORCIADO(A)"));
            estadocivil.add(new ListViewItem("VIUDO(A)", "VIUDO(A)"));
            modelo.addAttribute("localidades", localidades);
            modelo.addAttribute("estadocivil", estadocivil);
            modelo.addAttribute("model", model);
            return "AdministrarKardexPersonal/kardex/InformacionPersonal";
        }
        model.setFechanacimiento(Convert.ToDate(model.getText_fechanacimiento(), "dd/MM/yyyy"));
        PersonaKardex kardex = modelMapper.map(model, PersonaKardex.class);
        mi.ActualizarInformacionPersonalKardexDocente(kardex);
        return "redirect:/VerificarYObtenerKardex.fautapo";
    }

    @RequestMapping(value = "/kardex/EditarIdentificacionPersonalPersonal.fautapo", method = RequestMethod.GET)
    public String EditarIdentificacionPersonalPersonal(@ModelAttribute("id_persona") Integer id_persona, Model modelo) {
        DatosIdentificacionPersonalModel model = new DatosIdentificacionPersonalModel();
        PersonaKardex kardex = mi.GetKardexPersonalNuevo(id_persona);
        model = modelMapper.map(kardex, DatosIdentificacionPersonalModel.class);
        model.setText_fechaexpiracioncarnet(Convert.ToString(model.getFechaexpiracioncarnet(), "dd/MM/yyyy"));
        List<ListViewItem> tipodocumento = new ArrayList<>();
        tipodocumento.add(new ListViewItem("", "NINGUNO"));
        tipodocumento.add(new ListViewItem("CARNET IDENTIDAD", "CARNET IDENTIDAD"));
        tipodocumento.add(new ListViewItem("PASAPORTE", "PASAPORTE"));
        List<ListViewItem> ciudad = new ArrayList<>();
        ciudad.add(new ListViewItem("", "NINGUNO"));
        ciudad.add(new ListViewItem("BENI", "BENI"));
        ciudad.add(new ListViewItem("SANTA CRUZ", "SANTA CRUZ"));
        ciudad.add(new ListViewItem("PANDO", "PANDO"));
        ciudad.add(new ListViewItem("LA PAZ", "LA PAZ"));
        ciudad.add(new ListViewItem("ORURO", "ORURO"));
        ciudad.add(new ListViewItem("POTOSI", "POTOSI"));
        ciudad.add(new ListViewItem("COCHABAMBA", "COCHABAMBA"));
        ciudad.add(new ListViewItem("TARIJA", "TARIJA"));
        ciudad.add(new ListViewItem("CHUQUISACA", "CHUQUISACA"));
        modelo.addAttribute("tipodocumento", tipodocumento);
        modelo.addAttribute("ciudad", ciudad);
        modelo.addAttribute("model", model);
        return "AdministrarKardexPersonal/kardex/IdentificacionPersonal";
    }

    @RequestMapping(value = "/kardex/RegistrarIdentificacionPersonal.fautapo", method = RequestMethod.POST)
    public String RegistrarIdentificacionPersonal(@ModelAttribute("model") @Validated DatosIdentificacionPersonalModel model, BindingResult result, Model modelo) {
        if (model.getImagecarnetidentidad().equals("image.png")) {
            if (model.getImage() == null || model.getImage().isEmpty()) {
                result.addError(new FieldError("imagecarnetidentidad", "imagecarnetidentidad", "Debe cargar la image de su fotocopia del documento de identificacion personal"));
            }
        }
        if (result.hasErrors()) {
            List<ListViewItem> tipodocumento = new ArrayList<>();
            tipodocumento.add(new ListViewItem("", "NINGUNO"));
            tipodocumento.add(new ListViewItem("CARNET IDENTIDAD", "CARNET IDENTIDAD"));
            tipodocumento.add(new ListViewItem("PASAPORTE", "PASAPORTE"));
            List<ListViewItem> ciudad = new ArrayList<>();
            ciudad.add(new ListViewItem("", "NINGUNO"));
            ciudad.add(new ListViewItem("BENI", "BENI"));
            ciudad.add(new ListViewItem("SANTA CRUZ", "SANTA CRUZ"));
            ciudad.add(new ListViewItem("PANDO", "PANDO"));
            ciudad.add(new ListViewItem("LA PAZ", "LA PAZ"));
            ciudad.add(new ListViewItem("ORURO", "ORURO"));
            ciudad.add(new ListViewItem("POTOSI", "POTOSI"));
            ciudad.add(new ListViewItem("COCHABAMBA", "COCHABAMBA"));
            ciudad.add(new ListViewItem("TARIJA", "TARIJA"));
            ciudad.add(new ListViewItem("CHUQUISACA", "CHUQUISACA"));
            modelo.addAttribute("tipodocumento", tipodocumento);
            modelo.addAttribute("ciudad", ciudad);
            modelo.addAttribute("model", model);
            return "AdministrarKardexPersonal/kardex/IdentificacionPersonal";
        }
        model.setFechaexpiracioncarnet(Convert.ToDate(model.getText_fechaexpiracioncarnet(), "dd/MM/yyyy"));
        PersonaKardex kardex = modelMapper.map(model, PersonaKardex.class);
        FileUploadServlet.MultipartRequest(request, model.getImage(), DIRECTORIO_CARNET.key(), "imagecarnetidentidad", String.format("%010d", kardex.getId_persona()));
        String nombrearchivocarnet = Util.EsImagenModificado(kardex.getImagecarnetidentidad(), request.getAttribute("imagecarnetidentidad").toString());
        if (!nombrearchivocarnet.equals("image.png")) {
            kardex.setImagecarnetidentidad(nombrearchivocarnet);
        }
        mi.ActualizarIdentificacionPersonalKardexDocente(kardex);
        return "redirect:/VerificarYObtenerKardex.fautapo";
    }

    @RequestMapping(value = "/Vistapreviaimagenkardex.fautapo", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageMediaResult> Vistapreviaimagenkardex(@ModelAttribute("model") MediaFileModel model) {
        Clientes cliente = this.GetUsuario();
        MessageMediaResult Respuesta = new MessageMediaResult();
        ResultFileBase64 respuestabase64 = new ResultFileBase64();
        if (cliente == null) {
            Respuesta.setMessage("Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            Respuesta.setStatus("Error");
            return new ResponseEntity<>(Respuesta, HttpStatus.OK);
        } else {
            try {
                if (model.getTabla().equals("persona")) {
                    PersonaKardex kardex = mi.GetImagenesPersonaKardex(model.getId_persona());
                    if (model.getColumna().equals("carnet")) {
                        respuestabase64 = Util.Imagen64(kardex.getImagecarnetidentidad(), DIRECTORIO_CARNET.key());
                    }
                    if (model.getColumna().equals("libreta")) {
                        respuestabase64 = Util.Imagen64(kardex.getImagelibretamilitar(), DIRECTORIO_LIBRETAMILITAR.key());
                    }
                    if (model.getColumna().equals("titulo")) {
                        respuestabase64 = Util.Imagen64(kardex.getImagen(), DIRECTORIO_TITULOPROVISIONNACIONAL.key());
                    }
                    if (model.getColumna().equals("posgrado")) {
                        respuestabase64 = Util.Imagen64(kardex.getImagetituloposgrado(), DIRECTORIO_TITULOPOSGRADOEDUCACIONSUPERIOR.key());
                    }
                }
                Respuesta.setMessage("");
                Respuesta.setStatus("OK");
                Respuesta.setBase64(respuestabase64.getBase64());
                Respuesta.setType(respuestabase64.getContentType().equals("application/pdf") ? "pdf" : "image");
            } catch (Exception exception) {
                Respuesta.setMessage("Ocurrio un error al leer los datos" + exception.getMessage());
                Respuesta.setStatus("Error");
            }
            return new ResponseEntity<>(Respuesta, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/kardex/EditarServicioMilitarPersonal.fautapo", method = RequestMethod.GET)
    public String EditarServicioMilitarPersonal(@ModelAttribute("id_persona") Integer id_persona, Model modelo) {
        DatosServicioMilitarModel model = new DatosServicioMilitarModel();
        PersonaKardex kardex = mi.GetKardexPersonalNuevo(id_persona);
        model = modelMapper.map(kardex, DatosServicioMilitarModel.class);
        modelo.addAttribute("model", model);
        return "AdministrarKardexPersonal/kardex/ServicioMilitarPersonal";
    }

    @RequestMapping(value = "/kardex/RegistrarServicioMilitarPersonal.fautapo", method = RequestMethod.POST)
    public String RegistrarServicioMilitarPersonal(@ModelAttribute("model") @Validated DatosServicioMilitarModel model, BindingResult result, Model modelo) {
        if (model.getImagelibretamilitar().equals("image.png")) {
            if (model.getImage() == null || model.getImage().isEmpty()) {
                result.addError(new FieldError("imagelibretamilitar", "imagelibretamilitar", "Debe cargar la image de su fotocopia de la libreta de servicio militar"));
            }
        }
        if (result.hasErrors()) {
            modelo.addAttribute("model", model);
            return "AdministrarKardexPersonal/kardex/ServicioMilitarPersonal";
        }
        PersonaKardex kardex = modelMapper.map(model, PersonaKardex.class);
        FileUploadServlet.MultipartRequest(request, model.getImage(), DIRECTORIO_LIBRETAMILITAR.key(), "imagelibretamilitar", String.format("%010d", kardex.getId_persona()));
        String nombrearchivocarnet = Util.EsImagenModificado(kardex.getImagelibretamilitar(), request.getAttribute("imagelibretamilitar").toString());
        if (!nombrearchivocarnet.equals("image.png")) {
            kardex.setImagelibretamilitar(nombrearchivocarnet);
        }
        mi.ActualizarServicioMilitarPersonalKardexDocente(kardex);
        
        return "redirect:/VerificarYObtenerKardex.fautapo";
    }

    @RequestMapping(value = "/kardex/EditarInformacionLaboralPersonalPersonal.fautapo", method = RequestMethod.GET)
    public String EditarInformacionLaboralPersonalPersonal(@ModelAttribute("id_persona") Integer id_persona, Model modelo) {
        DatosformacionLaboralPersonalModel model = new DatosformacionLaboralPersonalModel();
        PersonaKardex kardex = mi.GetKardexPersonalNuevo(id_persona);
        model = modelMapper.map(kardex, DatosformacionLaboralPersonalModel.class);
        model.setText_fechacurso1178(Convert.ToString(model.getFechacurso1178(), "dd/MM/yyyy"));
        model.setText_fechaemision(Convert.ToString(model.getFechaemision(), "dd/MM/yyyy"));
        model.setText_fechaemisionsippase(Convert.ToString(model.getFechaemisionsippase(), "dd/MM/yyyy"));
        List<ListViewItem> tiponua = new ArrayList<>();
        tiponua.add(new ListViewItem("", "NINGUNO"));
        tiponua.add(new ListViewItem("PREVISION", "PREVISION"));
        tiponua.add(new ListViewItem("FUTURO", "FUTURO"));
        List<ListViewItem> bancos = mi.GetBancos();
        modelo.addAttribute("tiponua", tiponua);
        modelo.addAttribute("bancos", bancos);
        modelo.addAttribute("model", model);
        return "AdministrarKardexPersonal/kardex/InformacionLaboralPersonal";
    }

    @RequestMapping(value = "/kardex/RegistrarInformacionLaboralPersonal.fautapo", method = RequestMethod.POST)
    public String RegistrarInformacionLaboralPersonal(@ModelAttribute("model") @Validated DatosformacionLaboralPersonalModel model, BindingResult result, Model modelo) {
        model.setDiscapacidad(model.getText_discapacidad() != null ? model.getText_discapacidad().equals("on") ? true : false : false);
        model.setJubilado(model.getText_jubilado() != null ? model.getText_jubilado().equals("on") ? true : false : false);
        model.setSindicato(model.getText_sindicato() != null ? model.getText_sindicato().equals("on") ? true : false : false);
        model.setLey1178(model.getText_ley1178() != null ? model.getText_ley1178().equals("on") ? true : false : false);
        model.setSippase(model.getText_sippase() != null ? model.getText_sippase().equals("on") ? true : false : false);
        if (result.hasErrors()) {
            List<ListViewItem> tiponua = new ArrayList<>();
            tiponua.add(new ListViewItem("", "NINGUNO"));
            tiponua.add(new ListViewItem("PREVISION", "PREVISION"));
            tiponua.add(new ListViewItem("FUTURO", "FUTURO"));
            List<ListViewItem> bancos = mi.GetBancos();
            modelo.addAttribute("tiponua", tiponua);
            modelo.addAttribute("bancos", bancos);
            modelo.addAttribute("model", model);
            return "AdministrarKardexPersonal/kardex/InformacionLaboralPersonal";
        }
        model.setFechacurso1178(Convert.ToDate(model.getText_fechacurso1178(), "dd/MM/yyyy"));
        model.setFechaemision(Convert.ToDate(model.getText_fechaemision(), "dd/MM/yyyy"));
        model.setFechaemisionsippase(Convert.ToDate(model.getText_fechaemisionsippase(), "dd/MM/yyyy"));
        PersonaKardex kardex = modelMapper.map(model, PersonaKardex.class);
        mi.ActualizarInformacionLaboralPersonalKardexDocente(kardex);
        return "redirect:/VerificarYObtenerKardex.fautapo";
    }

    @RequestMapping(value = "/kardex/EditarEducacionPregradoPersonal.fautapo", method = RequestMethod.GET)
    public String EditarEducacionPregradoPersonal(@ModelAttribute("id_persona") Integer id_persona, Model modelo) {
        DatosEducacionPregradoModel model = new DatosEducacionPregradoModel();
        PersonaKardex kardex = mi.GetKardexPersonalNuevo(id_persona);
        model = modelMapper.map(kardex, DatosEducacionPregradoModel.class);
        model.setText_fechatituloprofesion(Convert.ToString(model.getFechatituloprofesion(), "dd/MM/yyyy"));
        List<ListViewItem> nivelestudio = mi.GetNivelEstudioPorNivel("PREGRADO");
        List<ListViewItem> colegioprofesionales = mi.GetColegiosProfesionales();
        List<ListViewItem> profesiones = mi.GetProfesiones();
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
        modelo.addAttribute("nivelestudio", nivelestudio);
        modelo.addAttribute("universidades", universidades);
        modelo.addAttribute("colegioprofesionales", colegioprofesionales);
        modelo.addAttribute("profesiones", profesiones);
        modelo.addAttribute("model", model);
        return "AdministrarKardexPersonal/kardex/EducacionPregradoPersonal";
    }

    @RequestMapping(value = "/kardex/RegistrarEducacionPregradoPersonal.fautapo", method = RequestMethod.POST)
    public String RegistrarEducacionPregradoPersonal(@ModelAttribute("model") @Validated DatosEducacionPregradoModel model, BindingResult result, Model modelo) {
        if (model.getImagen().equals("image.png")) {
            if (model.getFile() == null || model.getFile().isEmpty()) {
                result.addError(new FieldError("imagen", "imagen", "Debe cargar la image de su fotocopia del titulo de provision nacional"));
            }
        }
        if (result.hasErrors()) {
            List<ListViewItem> nivelestudio = mi.GetNivelEstudioPorNivel("PREGRADO");
            List<ListViewItem> colegioprofesionales = mi.GetColegiosProfesionales();
            List<ListViewItem> profesiones = mi.GetProfesiones();
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
            modelo.addAttribute("nivelestudio", nivelestudio);
            modelo.addAttribute("universidades", universidades);
            modelo.addAttribute("colegioprofesionales", colegioprofesionales);
            modelo.addAttribute("profesiones", profesiones);
            modelo.addAttribute("model", model);
        }
        model.setFechatituloprofesion(Convert.ToDate(model.getText_fechatituloprofesion(), "dd/MM/yyyy"));
        PersonaKardex kardex = modelMapper.map(model, PersonaKardex.class);
        FileUploadServlet.MultipartRequest(request, model.getFile(), DIRECTORIO_TITULOPROVISIONNACIONAL.key(), "imagen", String.format("%010d", kardex.getId_persona()));
        String nombrearchivo = Util.EsImagenModificado(kardex.getImagen(), request.getAttribute("imagen").toString());
        if (!nombrearchivo.equals("image.png")) {
            kardex.setImagen(nombrearchivo);
        }
        mi.ActualizarEducacionPregradoPersonalKardexDocente(kardex);
        return "redirect:/VerificarYObtenerKardex.fautapo";
    }

    @RequestMapping(value = "/kardex/EditarEducacionPosgradoPersonal.fautapo", method = RequestMethod.GET)
    public String EditarEducacionPosgradoPersonal(@ModelAttribute("id_persona") Integer id_persona, Model modelo) {
        DatosEducacionPosgradoModel model = new DatosEducacionPosgradoModel();
        PersonaKardex kardex = mi.GetKardexPersonalNuevo(id_persona);
        model = modelMapper.map(kardex, DatosEducacionPosgradoModel.class);
        model.setText_fechaemisionposgrado(Convert.ToString(model.getFechaemisionposgrado(), "dd/MM/yyyy"));
        List<ListViewItem> nivelestudioposgrado = mi.GetNivelEstudioPorNivel("POSGRADO");
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
        modelo.addAttribute("nivelestudio", nivelestudioposgrado);
        modelo.addAttribute("universidades", universidades);
        modelo.addAttribute("model", model);
        return "AdministrarKardexPersonal/kardex/EducacionPosgradoPersonal";
    }

    @RequestMapping(value = "/kardex/RegistrarEducacionPosgradoPersonal.fautapo", method = RequestMethod.POST)
    public String RegistrarEducacionPosgradoPersonal(@ModelAttribute("model") @Validated DatosEducacionPosgradoModel model, BindingResult result, Model modelo) {
        if (model.getImagetituloposgrado().equals("image.png")) {
            if (model.getFile() == null || model.getFile().isEmpty()) {
                result.addError(new FieldError("imagetituloposgrado", "imagetituloposgrado", "Debe cargar la image de su fotocopia del titulo posgradual en educacion superior"));
            }
        }
        if (result.hasErrors()) {
            List<ListViewItem> nivelestudioposgrado = mi.GetNivelEstudioPorNivel("POSGRADO");
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
            modelo.addAttribute("nivelestudio", nivelestudioposgrado);
            modelo.addAttribute("universidades", universidades);
            modelo.addAttribute("model", model);
            return "AdministrarKardexPersonal/kardex/EducacionPosgradoPersonal";
        }
        model.setFechaemisionposgrado(Convert.ToDate(model.getText_fechaemisionposgrado(), "dd/MM/yyyy"));
        PersonaKardex kardex = modelMapper.map(model, PersonaKardex.class);
        FileUploadServlet.MultipartRequest(request, model.getFile(), DIRECTORIO_TITULOPOSGRADOEDUCACIONSUPERIOR.key(), "imagetituloposgrado", String.format("%010d", kardex.getId_persona()));
        String nombrearchivo = Util.EsImagenModificado(kardex.getImagetituloposgrado(), request.getAttribute("imagetituloposgrado").toString());
        if (!nombrearchivo.equals("image.png")) {
            kardex.setImagetituloposgrado(nombrearchivo);
        }
        mi.ActualizarEducacionPosgradoKardexDocente(kardex);
        return "redirect:/VerificarYObtenerKardex.fautapo";
    }

    @RequestMapping(value = "/kardex/EditarContactoPersonal.fautapo", method = RequestMethod.GET)
    public String EditarContactoPersonal(@ModelAttribute("id_persona") Integer id_persona, Model modelo) {
        DatosInformacionContactoModel model = new DatosInformacionContactoModel();
        PersonaKardex kardex = mi.GetKardexPersonalNuevo(id_persona);
        model = modelMapper.map(kardex, DatosInformacionContactoModel.class);
        modelo.addAttribute("model", model);
        return "AdministrarKardexPersonal/kardex/EducacionContactoPersonal";
    }

    @RequestMapping(value = "/kardex/RegistrarContactoPersonal.fautapo", method = RequestMethod.POST)
    public String RegistrarContactoPersonal(@ModelAttribute("model") @Validated DatosInformacionContactoModel model, BindingResult result, Model modelo) {
        if (result.hasErrors()) {
            modelo.addAttribute("model", model);
            return "AdministrarKardexPersonal/kardex/EducacionContactoPersonal";
        }
        PersonaKardex kardex = modelMapper.map(model, PersonaKardex.class);
        mi.ActualizarContactoPersonalKardexDocente(kardex);
        return "redirect:/VerificarYObtenerKardex.fautapo";
    }

    @RequestMapping("/IndexKardexPersonal.fautapo")
    public String IndexKardexPersonal(Model modelo) {
        Clientes cliente = (Clientes) this.GetUsuario();
        int id_persona = cliente.getInt(request, "id_persona");
        PersonaKardex kardex = mi.GetKardexPersonalNuevo(id_persona);
        PersonaKardexModel KardexModel = modelMapper.map(kardex, PersonaKardexModel.class);
        modelo.addAttribute("snombre", cliente.getNombres().substring(0, 10) + "...");
        modelo.addAttribute("simagen", cliente.getImagen());
        modelo.addAttribute("kardex", KardexModel);
        return "AdministrarKardexPersonal/DetalleKardex";
    }

    @RequestMapping(value = "/VistapreviaimagenDocumentos.fautapo", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageMediaResult> VistapreviaimagenDocumentos(@ModelAttribute("model") MediaFileModel model) {
        Clientes cliente = this.GetUsuario();
        MessageMediaResult Respuesta = new MessageMediaResult();
        ResultFileBase64 respuestabase64 = new ResultFileBase64();
        if (cliente == null) {
            Respuesta.setMessage("Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            Respuesta.setStatus("Error");
            return new ResponseEntity<>(Respuesta, HttpStatus.OK);
        } else {
            try {
                switch (model.getTabla()) {
                    case "idioma":
                        String imageidioma = mi.GetImageidiomas(model.getId());
                        respuestabase64 = Util.Imagen64(imageidioma, DIRECTORIO_IDIOMAS.key());
                        break;
                    case "modalidadingreso":
                        String imagemodalidadingreso = mi.GetImagemodalidad(model.getId());
                        respuestabase64 = Util.Imagen64(imagemodalidadingreso, DIRECTORIO_MODALIDADES.key());
                        break;
                    case "formacionacademica":
                        String imageformacionacademica = mi.GetImageformacionacademica(model.getId());
                        respuestabase64 = Util.Imagen64(imageformacionacademica, DIRECTORIO_FORMACIONACADEMICA.key());
                        break;
                    case "experiencialaboral":
                        String imageexperiencialaboral = mi.GetImageexperiencia(model.getId());
                        respuestabase64 = Util.Imagen64(imageexperiencialaboral, DIRECTORIO_EXPERIENCIALABORAL.key());
                        break;
                    case "cursosrealizados":
                        String imagecursosrealizados = mi.GetImagecursosrealizados(model.getId());
                        respuestabase64 = Util.Imagen64(imagecursosrealizados, DIRECTORIO_CURSOSREALIZADOS.key());
                        break;
                    case "produccioncientifica":
                        String imageproduccioncientifica = mi.GetImageproduccioncientifica(model.getId());
                        respuestabase64 = Util.Imagen64(imageproduccioncientifica, DIRECTORIO_PRODUCCIONCIENTIFICA.key());
                        break;
                    case "actividadesacademicas":
                        String imageactividadesacademicas = mi.GetImageactividadesacademicas(model.getId());
                        respuestabase64 = Util.Imagen64(imageactividadesacademicas, DIRECTORIO_ACTIVIDADES_ACADEMICAS.key());
                        break;
                    case "proyectodocente":
                        String imageproyectodocente = mi.GetImageproyecto(model.getId());
                        respuestabase64 = Util.Imagen64(imageproyectodocente, DIRECTORIO_PROYECTODOCENTE.key());
                        break;
                    default:
                }
                Respuesta.setMessage("");
                Respuesta.setStatus("OK");
                Respuesta.setBase64(respuestabase64.getBase64());
                Respuesta.setType(respuestabase64.getContentType().equals("application/pdf") ? "pdf" : "image");
            } catch (Exception exception) {
                Respuesta.setMessage("Ocurrio un error al leer los datos" + exception.getMessage());
                Respuesta.setStatus("Error");
            }
            return new ResponseEntity<>(Respuesta, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/FileDownload.fautapo", method = RequestMethod.GET)
    public void FileDownload(@ModelAttribute("model") FileDownloadModel model, HttpServletResponse response) {
        String dataDirectory = GetDirectorio(model.getDirectorio());
        String contentType = GetContentType(model.getName());
        Path file = Paths.get(dataDirectory, GetName(model.getName()));
        if (Files.exists(file)) {
            response.setContentType(contentType);
            response.addHeader("Content-Disposition", "attachment; filename=" + GetName(model.getName()));
            try {
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private String GetDirectorio(String opcion) {
        String directorio = "";
        switch (opcion) {
            case "idiomas":
                directorio = DIRECTORIO_IDIOMAS.key();
                break;
            case "formacion":
                directorio = DIRECTORIO_FORMACIONACADEMICA.key();
                break;
            case "experiencia":
                directorio = DIRECTORIO_EXPERIENCIALABORAL.key();
                break;
            case "produccion":
                directorio = DIRECTORIO_PRODUCCIONCIENTIFICA.key();
                break;
            case "evaluacion":
                directorio = DIRECTORIO_EVALUACIONDOCENTE.key();
                break;
            case "cursos":
                directorio = DIRECTORIO_CURSOSREALIZADOS.key();
                break;
            case "modalidades":
                directorio = DIRECTORIO_MODALIDADES.key();
                break;
            case "actividadesacademicas":
                directorio = DIRECTORIO_ACTIVIDADES_ACADEMICAS.key();
                break;
            case "proyectodocente":
                directorio = DIRECTORIO_PROYECTODOCENTE.key();
                break;
            default:
                break;
        }
        return directorio;
    }

    private String GetContentType(String value) {
        String scontentype = "";
        if (value.contains(";")) {
            String[] str = value.split(";");
            scontentype = str[0];
        } else {
            scontentype = "image/png";
        }
        return scontentype;
    }

    private String GetName(String value) {
        String name = "";
        if (value.contains(";")) {
            String[] str = value.split(";");
            name = str[1];
        } else {
            name = "image/png";
        }
        return name;
    }

    @RequestMapping(value = "/FileDownloadKardex.fautapo", method = RequestMethod.GET)
    public void FileDownloadKardex(@ModelAttribute("model") FileKardexModel model, HttpServletResponse response) throws Exception {

        ParametrosBusqueda busqueda = modelMapper.map(model, ParametrosBusqueda.class);

        Programas programa = new Programas();
        programa.setId_programa(model.getId_programa());
        programa = this.mi.getPrgBuscarPrograma(programa);

        List<ListViewItem> nivelestudioposgrado = mi.GetNivelEstudioPorNivel("POSGRADO");
        List<ListViewItem> tipoexperiencia = new ArrayList<>();
        tipoexperiencia.add(new ListViewItem("Cargo/funcion", "EXPERIENCIA PROFESIONAL/LABORAL"));
        tipoexperiencia.add(new ListViewItem("Asignatura/funcion", "EXPERIENCIA ACADEMICA-DOCENCIA"));
        tipoexperiencia.add(new ListViewItem("Cartera o funcion", "EXPERIENCIA TRIBUNAL EN EVENTOS ACADEMICOS"));
        tipoexperiencia.add(new ListViewItem("Descripcion del cargo", "EXPERIENCIA CARGOS JERARQUICOS UNIVERSITARIOS"));
        tipoexperiencia.add(new ListViewItem("Descripcion del cargo", "EXPERIENCIA ADMINISTRATIVAS UNIVERSITARIA"));

        List<ListViewItem> tipoevento = new ArrayList<>();
        tipoevento.add(new ListViewItem("Tipo de Reconocimiento", "DE RECONOCIMIENTOS "));
        tipoevento.add(new ListViewItem("Nombre del evento y Tema", "COMO EXPOSITORES/DISERTANTE"));
        tipoevento.add(new ListViewItem("Nombre del evento", "COMO ORGANIZADOR / COLABORADOR "));
        tipoevento.add(new ListViewItem("Nombre del evento", "COMO ASISTENTE"));

        KardexPersonal kardex = mi.getKardexPersonalDocente(model.getId_persona());
        kardex.setImage(Util.GetImagenPerfil(model.getId_docente(), mi));
        kardex.setCarrera(programa.getPrograma().toUpperCase());
        kardex.setFacultad("FACULTAD DE INGENIERIA Y TECNOLOGIA");
        kardex.setNombreinstitucion("UNIVERSIDAD AUTONOMA DEL BENI \n \"JOSE BALLIVIAN\"");
        kardex.setCategoria(mi.GetPersonaCategoriaKardex(busqueda));
        kardex.setLugarimpresion("31/12/2020");
        kardex.setMostrarcarrera(Boolean.TRUE);
        kardex.setPosgrado(nivelestudioposgrado);
        kardex.setTipoexperiencia(tipoexperiencia);
        kardex.setTipoevento(tipoevento);
        int id_persona_kardex = kardex.getId_persona_kardex();
        kardex.setCursosrealizados(mi.GetPersonaTotalCursosRealizadosKardex(id_persona_kardex));
        kardex.setEvaluaciondocente(mi.GetPersonaTotalEvaluacionDocenteKardex(id_persona_kardex));
        kardex.setExperiencialaboral(mi.GetPersonaTotalExperienciaLaboralKardex(id_persona_kardex));
        kardex.setFormacionacademica(mi.GetPersonaTotalFormacionAcademicaKardex(id_persona_kardex));
        kardex.setModalidad(mi.GetPersonaTotalModalidadKardex(id_persona_kardex));
        kardex.setProduccioncientifica(mi.GetPersonaTotalProduccionCientificaKardex(id_persona_kardex));
        kardex.setIdioma(mi.GetPersonaTotalIdiomaKardex(id_persona_kardex));
        ImprimirKardexModel libre = new ImprimirKardexModel(kardex, response);
        libre.Createkardex();
    }
}
