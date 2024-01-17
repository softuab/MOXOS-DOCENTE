package com.moxos.uab.controller.documentacion;

import com.moxos.uab.model.models.Kardex.*;
import com.moxos.uab.model.models.reportes.pdf.ImprimirKardexModel;
import com.moxos.uab.model.models.utility.MediaFileModel;
import com.moxos.uab.model.models.utility.MessageMediaResult;
import com.moxos.uab.model.models.utility.ResultFileBase64;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.moxos.uab.util.Directorio.*;

@Controller
public class KardexDocenteController {

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

    @RequestMapping("/verificarYObtenerKardex")
    public String VerificarYObtenerKardex(Model modelo) {
        int id_docente = this.getUsuario().getId_usuario();
        Personas persona = mi.getPrsBuscarPersonaDocente(id_docente);
        PersonaKardexRequest KardexModel = null;
        PersonaKardex kardex = mi.getKardexPersonalNuevo(persona.getId_persona());
        if (kardex == null) {
            KardexModel = new PersonaKardexRequest();
            KardexModel.setUlt_usuario(this.getUsuario().getId_usuario());
            KardexModel.setId_persona(persona.getId_persona());
            kardex = modelMapper.map(KardexModel, PersonaKardex.class);
            mi.registrarNuevoKardexDocente(kardex);
            kardex = mi.getKardexPersonalNuevo(persona.getId_persona());
        }
        KardexModel = modelMapper.map(kardex, PersonaKardexRequest.class);
        modelo.addAttribute("snombre", this.getUsuario().getNombres().substring(0, 10) + "...");
        modelo.addAttribute("simagen", this.getUsuario().getImagen());
        modelo.addAttribute("id_persona", persona.getId_persona());
        modelo.addAttribute("id_docente", this.getUsuario().getId_usuario());
        modelo.addAttribute("kardex", KardexModel);
        return "Kardex/DetalleKardex";
    }

    @RequestMapping(value = "/kardex/EditarInformacionPersonal", method = RequestMethod.GET)
    public String EditarInformacionPersonal(@ModelAttribute("id_persona") Integer id_persona, Model modelo) {
        DatosPersonalesModel model = new DatosPersonalesModel();
        PersonaKardex kardex = mi.getKardexPersonalNuevo(id_persona);
        model = modelMapper.map(kardex, DatosPersonalesModel.class);
        model.setText_fechanacimiento(Convert.ToString(model.getFechanacimiento(), "yyyy-MM-dd"));
        List<ListViewItem> localidades = mi.getLocalidadPersona();
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
        return "Kardex/Informacion/InformacionPersonal";
    }

    @RequestMapping(value = "/kardex/RegistrarInformacionPersonal", method = RequestMethod.POST)
    public String RegistrarInformacionPersonal(@ModelAttribute("model") @Validated DatosPersonalesModel model, BindingResult result, Model modelo) {
        model.setSexo(model.getText_sexo() != null ? model.getText_sexo().equals("on") ? true : false : false);
        if (result.hasErrors()) {
            List<ListViewItem> localidades = mi.getLocalidadPersona();
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
            return "Kardex/Informacion/InformacionPersonal";
        }
        model.setFechanacimiento(Convert.ToDate(model.getText_fechanacimiento(), "yyyy-MM-dd"));
        PersonaKardex kardex = modelMapper.map(model, PersonaKardex.class);
        mi.actualizarInformacionPersonalKardexDocente(kardex);
        return "redirect:/verificarYObtenerKardex";
    }

    @RequestMapping(value = "/kardex/EditarIdentificacionPersonalPersonal", method = RequestMethod.GET)
    public String EditarIdentificacionPersonalPersonal(@ModelAttribute("id_persona") Integer id_persona, Model modelo) {
        DatosIdentificacionPersonalModel model = new DatosIdentificacionPersonalModel();
        PersonaKardex kardex = mi.getKardexPersonalNuevo(id_persona);
        model = modelMapper.map(kardex, DatosIdentificacionPersonalModel.class);
        model.setText_fechaexpiracioncarnet(Convert.ToString(model.getFechaexpiracioncarnet(), "yyyy-MM-dd"));
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
        return "Kardex/Informacion/IdentificacionPersonal";
    }

    @RequestMapping(value = "/kardex/RegistrarIdentificacionPersonal", method = RequestMethod.POST)
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
            return "Kardex/Informacion/IdentificacionPersonal";
        }
        model.setFechaexpiracioncarnet(Convert.ToDate(model.getText_fechaexpiracioncarnet(), "yyyy-MM-dd"));
        PersonaKardex kardex = modelMapper.map(model, PersonaKardex.class);
        FileUploadServlet.MultipartRequest(request, model.getImage(), path + DIRECTORIO_CARNET.key(), "imagecarnetidentidad", String.format("%010d", kardex.getId_persona()));
        String nombrearchivocarnet = Utils.EsImagenModificado(kardex.getImagecarnetidentidad(), request.getAttribute("imagecarnetidentidad").toString());
        if (!nombrearchivocarnet.equals("image.png")) {
            kardex.setImagecarnetidentidad(nombrearchivocarnet);
        }
        mi.actualizarIdentificacionPersonalKardexDocente(kardex);
        return "redirect:/verificarYObtenerKardex";
    }

    @RequestMapping(value = "/Vistapreviaimagenkardex", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageMediaResult> Vistapreviaimagenkardex(@ModelAttribute("model") MediaFileModel model) {
        Clientes cliente = this.getUsuario();
        MessageMediaResult Respuesta = new MessageMediaResult();
        ResultFileBase64 respuestabase64 = new ResultFileBase64();
        if (cliente == null) {
            Respuesta.setMessage("Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            Respuesta.setStatus("Error");
            return new ResponseEntity<>(Respuesta, HttpStatus.OK);
        } else {
            try {
                if (model.getTabla().equals("persona")) {
                    PersonaKardex kardex = mi.getImagenesPersonaKardex(model.getId_persona());
                    if (model.getColumna().equals("carnet")) {
                        respuestabase64 = Utils.Imagen64(kardex.getImagecarnetidentidad(), path + DIRECTORIO_CARNET.key());
                    }
                    if (model.getColumna().equals("libreta")) {
                        respuestabase64 = Utils.Imagen64(kardex.getImagelibretamilitar(), path + DIRECTORIO_LIBRETAMILITAR.key());
                    }
                    if (model.getColumna().equals("titulo")) {
                        respuestabase64 = Utils.Imagen64(kardex.getImagen(), path + DIRECTORIO_TITULOPROVISIONNACIONAL.key());
                    }
                    if (model.getColumna().equals("posgrado")) {
                        respuestabase64 = Utils.Imagen64(kardex.getImagetituloposgrado(), path + DIRECTORIO_TITULOPOSGRADOEDUCACIONSUPERIOR.key());
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

    @RequestMapping(value = "/kardex/EditarServicioMilitarPersonal", method = RequestMethod.GET)
    public String EditarServicioMilitarPersonal(@ModelAttribute("id_persona") Integer id_persona, Model modelo) {
        DatosServicioMilitarModel model = new DatosServicioMilitarModel();
        PersonaKardex kardex = mi.getKardexPersonalNuevo(id_persona);
        model = modelMapper.map(kardex, DatosServicioMilitarModel.class);
        modelo.addAttribute("model", model);
        return "Kardex/Informacion/ServicioMilitarPersonal";
    }

    @RequestMapping(value = "/kardex/RegistrarServicioMilitarPersonal", method = RequestMethod.POST)
    public String RegistrarServicioMilitarPersonal(@ModelAttribute("model") @Validated DatosServicioMilitarModel model, BindingResult result, Model modelo) {
        if (model.getImagelibretamilitar().equals("image.png")) {
            if (model.getImage() == null || model.getImage().isEmpty()) {
                result.addError(new FieldError("imagelibretamilitar", "imagelibretamilitar", "Debe cargar la image de su fotocopia de la libreta de servicio militar"));
            }
        }
        if (result.hasErrors()) {
            modelo.addAttribute("model", model);
            return "Kardex/Informacion/ServicioMilitarPersonal";
        }
        PersonaKardex kardex = modelMapper.map(model, PersonaKardex.class);
        FileUploadServlet.MultipartRequest(request, model.getImage(), path + DIRECTORIO_LIBRETAMILITAR.key(), "imagelibretamilitar", String.format("%010d", kardex.getId_persona()));
        String nombrearchivocarnet = Utils.EsImagenModificado(kardex.getImagelibretamilitar(), request.getAttribute("imagelibretamilitar").toString());
        if (!nombrearchivocarnet.equals("image.png")) {
            kardex.setImagelibretamilitar(nombrearchivocarnet);
        }
        mi.actualizarServicioMilitarPersonalKardexDocente(kardex);

        return "redirect:/verificarYObtenerKardex";
    }

    @RequestMapping(value = "/kardex/EditarInformacionLaboralPersonalPersonal", method = RequestMethod.GET)
    public String EditarInformacionLaboralPersonalPersonal(@ModelAttribute("id_persona") Integer id_persona, Model modelo) {
        DatosformacionLaboralPersonalModel model = new DatosformacionLaboralPersonalModel();
        PersonaKardex kardex = mi.getKardexPersonalNuevo(id_persona);
        model = modelMapper.map(kardex, DatosformacionLaboralPersonalModel.class);
        model.setText_fechacurso1178(Convert.ToString(model.getFechacurso1178(), "yyyy-MM-dd"));
        model.setText_fechaemision(Convert.ToString(model.getFechaemision(), "yyyy-MM-dd"));
        model.setText_fechaemisionsippase(Convert.ToString(model.getFechaemisionsippase(), "yyyy-MM-dd"));
        List<ListViewItem> tiponua = new ArrayList<>();
        tiponua.add(new ListViewItem("", "NINGUNO"));
        tiponua.add(new ListViewItem("PREVISION", "PREVISION"));
        tiponua.add(new ListViewItem("FUTURO", "FUTURO"));
        List<ListViewItem> bancos = mi.getBancos();
        modelo.addAttribute("tiponua", tiponua);
        modelo.addAttribute("bancos", bancos);
        modelo.addAttribute("model", model);
        return "Kardex/Informacion/InformacionLaboralPersonal";
    }

    @RequestMapping(value = "/kardex/RegistrarInformacionLaboralPersonal", method = RequestMethod.POST)
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
            List<ListViewItem> bancos = mi.getBancos();
            modelo.addAttribute("tiponua", tiponua);
            modelo.addAttribute("bancos", bancos);
            modelo.addAttribute("model", model);
            return "Kardex/Informacion/InformacionLaboralPersonal";
        }
        model.setFechacurso1178(Convert.ToDate(model.getText_fechacurso1178(), "yyyy-MM-dd"));
        model.setFechaemision(Convert.ToDate(model.getText_fechaemision(), "yyyy-MM-dd"));
        model.setFechaemisionsippase(Convert.ToDate(model.getText_fechaemisionsippase(), "yyyy-MM-dd"));
        PersonaKardex kardex = modelMapper.map(model, PersonaKardex.class);
        mi.actualizarInformacionLaboralPersonalKardexDocente(kardex);
        return "redirect:/verificarYObtenerKardex";
    }

    @RequestMapping(value = "/kardex/EditarEducacionPregradoPersonal", method = RequestMethod.GET)
    public String EditarEducacionPregradoPersonal(@ModelAttribute("id_persona") Integer id_persona, Model modelo) {
        DatosEducacionPregradoModel model = new DatosEducacionPregradoModel();
        PersonaKardex kardex = mi.getKardexPersonalNuevo(id_persona);
        model = modelMapper.map(kardex, DatosEducacionPregradoModel.class);
        model.setText_fechatituloprofesion(Convert.ToString(model.getFechatituloprofesion(), "yyyy-MM-dd"));
        List<ListViewItem> nivelestudio = mi.getNivelEstudioPorNivel("PREGRADO");
        List<ListViewItem> colegioprofesionales = mi.getColegiosProfesionales();
        List<ListViewItem> profesiones = mi.getProfesiones();
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
        return "Kardex/Informacion/EducacionPregradoPersonal";
    }

    @RequestMapping(value = "/kardex/RegistrarEducacionPregradoPersonal", method = RequestMethod.POST)
    public String RegistrarEducacionPregradoPersonal(@ModelAttribute("model") @Validated DatosEducacionPregradoModel model, BindingResult result, Model modelo) {
        if (model.getImagen().equals("image.png")) {
            if (model.getFile() == null || model.getFile().isEmpty()) {
                result.addError(new FieldError("imagen", "imagen", "Debe cargar la image de su fotocopia del titulo de provision nacional"));
            }
        }
        if (result.hasErrors()) {
            List<ListViewItem> nivelestudio = mi.getNivelEstudioPorNivel("PREGRADO");
            List<ListViewItem> colegioprofesionales = mi.getColegiosProfesionales();
            List<ListViewItem> profesiones = mi.getProfesiones();
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
        model.setFechatituloprofesion(Convert.ToDate(model.getText_fechatituloprofesion(), "yyyy-MM-dd"));
        PersonaKardex kardex = modelMapper.map(model, PersonaKardex.class);
        FileUploadServlet.MultipartRequest(request, model.getFile(), path + DIRECTORIO_TITULOPROVISIONNACIONAL.key(), "imagen", String.format("%010d", kardex.getId_persona()));
        String nombrearchivo = Utils.EsImagenModificado(kardex.getImagen(), request.getAttribute("imagen").toString());
        if (!nombrearchivo.equals("image.png")) {
            kardex.setImagen(nombrearchivo);
        }
        mi.actualizarEducacionPregradoPersonalKardexDocente(kardex);
        return "redirect:/verificarYObtenerKardex";
    }

    @RequestMapping(value = "/kardex/EditarEducacionPosgradoPersonal", method = RequestMethod.GET)
    public String EditarEducacionPosgradoPersonal(@ModelAttribute("id_persona") Integer id_persona, Model modelo) {
        DatosEducacionPosgradoModel model = new DatosEducacionPosgradoModel();
        PersonaKardex kardex = mi.getKardexPersonalNuevo(id_persona);
        model = modelMapper.map(kardex, DatosEducacionPosgradoModel.class);
        model.setText_fechaemisionposgrado(Convert.ToString(model.getFechaemisionposgrado(), "yyyy-MM-dd"));
        List<ListViewItem> nivelestudioposgrado = mi.getNivelEstudioPorNivel("POSGRADO");
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
        return "Kardex/Informacion/EducacionPosgradoPersonal";
    }

    @RequestMapping(value = "/kardex/RegistrarEducacionPosgradoPersonal", method = RequestMethod.POST)
    public String RegistrarEducacionPosgradoPersonal(@ModelAttribute("model") @Validated DatosEducacionPosgradoModel model, BindingResult result, Model modelo) {
        if (model.getImagetituloposgrado().equals("image.png")) {
            if (model.getFile() == null || model.getFile().isEmpty()) {
                result.addError(new FieldError("imagetituloposgrado", "imagetituloposgrado", "Debe cargar la image de su fotocopia del titulo posgradual en educacion superior"));
            }
        }
        if (result.hasErrors()) {
            List<ListViewItem> nivelestudioposgrado = mi.getNivelEstudioPorNivel("POSGRADO");
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
            return "Kardex/Informacion/EducacionPosgradoPersonal";
        }
        model.setFechaemisionposgrado(Convert.ToDate(model.getText_fechaemisionposgrado(), "yyyy-MM-dd"));
        PersonaKardex kardex = modelMapper.map(model, PersonaKardex.class);
        FileUploadServlet.MultipartRequest(request, model.getFile(), path + DIRECTORIO_TITULOPOSGRADOEDUCACIONSUPERIOR.key(), "imagetituloposgrado", String.format("%010d", kardex.getId_persona()));
        String nombrearchivo = Utils.EsImagenModificado(kardex.getImagetituloposgrado(), request.getAttribute("imagetituloposgrado").toString());
        if (!nombrearchivo.equals("image.png")) {
            kardex.setImagetituloposgrado(nombrearchivo);
        }
        mi.actualizarEducacionPosgradoKardexDocente(kardex);
        return "redirect:/verificarYObtenerKardex";
    }

    @RequestMapping(value = "/kardex/EditarContactoPersonal", method = RequestMethod.GET)
    public String EditarContactoPersonal(@ModelAttribute("id_persona") Integer id_persona, Model modelo) {
        DatosInformacionContactoModel model = new DatosInformacionContactoModel();
        PersonaKardex kardex = mi.getKardexPersonalNuevo(id_persona);
        model = modelMapper.map(kardex, DatosInformacionContactoModel.class);
        modelo.addAttribute("model", model);
        return "Kardex/Informacion/EducacionContactoPersonal";
    }

    @RequestMapping(value = "/kardex/RegistrarContactoPersonal", method = RequestMethod.POST)
    public String RegistrarContactoPersonal(@ModelAttribute("model") @Validated DatosInformacionContactoModel model, BindingResult result, Model modelo) {
        if (result.hasErrors()) {
            modelo.addAttribute("model", model);
            return "Kardex/Informacion/EducacionContactoPersonal";
        }
        PersonaKardex kardex = modelMapper.map(model, PersonaKardex.class);
        mi.actualizarContactoPersonalKardexDocente(kardex);
        return "redirect:/verificarYObtenerKardex";
    }

    @RequestMapping("/IndexKardexPersonal")
    public String IndexKardexPersonal(Model modelo) {
        Clientes cliente = this.getUsuario();
        int id_persona = Convert.ToInteger(request, "id_persona");
        PersonaKardex kardex = mi.getKardexPersonalNuevo(id_persona);
        PersonaKardexRequest KardexModel = modelMapper.map(kardex, PersonaKardexRequest.class);
        modelo.addAttribute("snombre", cliente.getNombres().substring(0, 10) + "...");
        modelo.addAttribute("simagen", cliente.getImagen());
        modelo.addAttribute("kardex", KardexModel);
        return "Kardex/DetalleKardex";
    }

    @RequestMapping(value = "/VistapreviaimagenDocumentos", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageMediaResult> VistapreviaimagenDocumentos(@ModelAttribute("model") MediaFileModel model) {
        Clientes cliente = this.getUsuario();
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
                        String imageidioma = mi.getImageidiomas(model.getId());
                        respuestabase64 = Utils.Imagen64(imageidioma, path + DIRECTORIO_IDIOMAS.key());
                        break;
                    case "modalidadingreso":
                        String imagemodalidadingreso = mi.getImagemodalidad(model.getId());
                        respuestabase64 = Utils.Imagen64(imagemodalidadingreso, path + DIRECTORIO_MODALIDADES.key());
                        break;
                    case "formacionacademica":
                        String imageformacionacademica = mi.getImageformacionacademica(model.getId());
                        respuestabase64 = Utils.Imagen64(imageformacionacademica, path + DIRECTORIO_FORMACIONACADEMICA.key());
                        break;
                    case "experiencialaboral":
                        String imageexperiencialaboral = mi.getImageexperiencia(model.getId());
                        respuestabase64 = Utils.Imagen64(imageexperiencialaboral, path + DIRECTORIO_EXPERIENCIALABORAL.key());
                        break;
                    case "cursosrealizados":
                        String imagecursosrealizados = mi.getImagecursosrealizados(model.getId());
                        respuestabase64 = Utils.Imagen64(imagecursosrealizados, path + DIRECTORIO_CURSOSREALIZADOS.key());
                        break;
                    case "produccioncientifica":
                        String imageproduccioncientifica = mi.getImageproduccioncientifica(model.getId());
                        respuestabase64 = Utils.Imagen64(imageproduccioncientifica, path + DIRECTORIO_PRODUCCIONCIENTIFICA.key());
                        break;
                    case "actividadesacademicas":
                        String imageactividadesacademicas = mi.getImageactividadesacademicas(model.getId());
                        respuestabase64 = Utils.Imagen64(imageactividadesacademicas, path + DIRECTORIO_ACTIVIDADES_ACADEMICAS.key());
                        break;
                    case "proyectodocente":
                        String imageproyectodocente = mi.getImageproyecto(model.getId());
                        respuestabase64 = Utils.Imagen64(imageproyectodocente, path + DIRECTORIO_PROYECTODOCENTE.key());
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

    @RequestMapping(value = "/FileDownload", method = RequestMethod.GET)
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
                directorio = path + DIRECTORIO_IDIOMAS.key();
                break;
            case "formacion":
                directorio = path + DIRECTORIO_FORMACIONACADEMICA.key();
                break;
            case "experiencia":
                directorio = path + DIRECTORIO_EXPERIENCIALABORAL.key();
                break;
            case "produccion":
                directorio = path + DIRECTORIO_PRODUCCIONCIENTIFICA.key();
                break;
            case "evaluacion":
                directorio = path + DIRECTORIO_EVALUACIONDOCENTE.key();
                break;
            case "cursos":
                directorio = path + DIRECTORIO_CURSOSREALIZADOS.key();
                break;
            case "modalidades":
                directorio = path + DIRECTORIO_MODALIDADES.key();
                break;
            case "actividadesacademicas":
                directorio = path + DIRECTORIO_ACTIVIDADES_ACADEMICAS.key();
                break;
            case "proyectodocente":
                directorio = path + DIRECTORIO_PROYECTODOCENTE.key();
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

    @RequestMapping(value = "/FileDownloadKardex", method = RequestMethod.GET)
    public void FileDownloadKardex(@ModelAttribute("model") FileKardexModel model, HttpServletResponse response) throws Exception {

        ParametrosBusqueda busqueda = modelMapper.map(model, ParametrosBusqueda.class);

        List<ListViewItem> nivelestudioposgrado = mi.getNivelEstudioPorNivel("POSGRADO");
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
        kardex.setImage(Utils.getImagenPerfil(model.getId_docente(), mi));
        kardex.setCarrera("");
        kardex.setFacultad("FACULTAD DE INGENIERIA Y TECNOLOGIA");
        kardex.setNombreinstitucion("UNIVERSIDAD AUTONOMA DEL BENI \n \"JOSE BALLIVIAN\"");
        kardex.setCategoria(mi.getPersonaCategoriaKardex(busqueda));
        kardex.setLugarimpresion("31/12/2020");
        kardex.setMostrarcarrera(Boolean.TRUE);
        kardex.setPosgrado(nivelestudioposgrado);
        kardex.setTipoexperiencia(tipoexperiencia);
        kardex.setTipoevento(tipoevento);
        int id_persona_kardex = kardex.getId_persona_kardex();
        kardex.setCursosrealizados(mi.getPersonaTotalCursosRealizadosKardex(id_persona_kardex));
        kardex.setEvaluaciondocente(mi.getPersonaTotalEvaluacionDocenteKardex(id_persona_kardex));
        kardex.setExperiencialaboral(mi.getPersonaTotalExperienciaLaboralKardex(id_persona_kardex));
        kardex.setFormacionacademica(mi.getPersonaTotalFormacionAcademicaKardex(id_persona_kardex));
        kardex.setModalidad(mi.getPersonaTotalModalidadKardex(id_persona_kardex));
        kardex.setProduccioncientifica(mi.getPersonaTotalProduccionCientificaKardex(id_persona_kardex));
        kardex.setIdioma(mi.getPersonaTotalIdiomaKardex(id_persona_kardex));
        ImprimirKardexModel libre = new ImprimirKardexModel(kardex, response, path);
        libre.Createkardex();
    }
}
