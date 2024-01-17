package com.moxos.uab.controller.programaanaliticos;

import com.itextpdf.text.pdf.qrcode.Mode;
import com.moxos.uab.model.models.programaanalitico.ParametrosImpresionRequest;
import com.moxos.uab.model.models.programaanalitico.formasorganizacion.ContenidosFormasRequest;
import com.moxos.uab.model.models.programaanalitico.formasorganizacion.FormasDistribucionRequest;
import com.moxos.uab.model.models.programaanalitico.imprimir.ParametroBusquedaImpresionRequest;
import com.moxos.uab.mybatis.entity.*;
import com.moxos.uab.mybatis.logic.MiFacade;
import com.moxos.uab.util.Utils;
import com.moxos.uab.util.word.MSWordTool;
import com.moxos.uab.util.word.ParrafoBookMarks;
import com.moxos.uab.util.word.QNames;
import com.moxos.uab.util.word.SDPOIDocxView;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ImprimirProgramaAnaliticoController {
    @Autowired
    private MiFacade mi;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ModelMapper modelMapper;
    @Value("${app.upload.path}")
    private String path;
    @Value("${app-server-path}")
    private String serverPath;

    private Clientes getUsuario() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        Clientes cliente = (Clientes) attr.getRequest().getSession().getAttribute("__sess_cliente");
        return cliente;
    }

    @GetMapping(value = "/programaanaanalitico/impresion/entrada")
    public String entrada(@ModelAttribute("model") ParametroBusquedaImpresionRequest model, Model modelo) {
        modelo.addAttribute("nombres", getUsuario().getNombres());
        modelo.addAttribute("model", model);
        return "ProgramasAnaliticos/Imprimir/Entrada";
    }

    @GetMapping("/programaanaanalitico/impresion/caratula")
    public void downloadWordCaratula(@ModelAttribute("id") Integer id, HttpServletResponse response) throws IOException, InvalidFormatException {

        // Configurar la respuesta HTTP para la descarga
        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        response.setHeader("Content-Disposition", "attachment; filename=caratula.docx");

        // Escribir el contenido del documento en la respuesta
        ProgramaAnalitico programaAnalitico = null;
        programaAnalitico = mi.getDatosCaratula(id);
        crearCaratulaWord(response, programaAnalitico);
    }

    private void crearCaratulaWord(HttpServletResponse response, ProgramaAnalitico p) throws IOException, InvalidFormatException {
        String caractula = path + File.separator + "resources" + File.separator + "CARATULA.docx";
        SDPOIDocxView docx = new SDPOIDocxView(caractula);
        Map modelo = new HashMap();
        modelo.put("facultad", "FACULTAD DE " + p.getFacultad());
        modelo.put("carrera", "CARRERA DE " + p.getDepartamento());
        modelo.put("asignatura", p.getMateria());
        modelo.put("gestion", p.getPeriodo() + "/" + String.valueOf(String.valueOf(p.getGestion())));
        modelo.put("docente", p.getTitulo() + " " + p.getNombres() + " " + p.getPaterno() + " " + p.getMaterno());
        String aux = p.getDepartamento().replace("(", "").replace(")", "");
        String[] lugar = aux.split("-");
        modelo.put("ciudad", lugar[1].replaceAll("\\s", ""));
        docx.replace(modelo);
        docx.writeAndClose(response);
    }

    @GetMapping("/programaanaanalitico/impresion/vistaprevia")
    private String getVistaprevia(@ModelAttribute("model") ParametrosImpresionRequest request, Model modelo) {
        ProgramaAnalitico model = mi.getProgramaanalitico(request.getId());
        ProgramaAnalitico programaAnaliticocaratula = mi.getListarInformeProgramaAnalitico(model.getId_asignacion());
        Planes plan = new Planes();
        plan.setId_plan(model.getId_plan());
        plan.setId_programa(request.getId_programa());
        plan.setId_tipo_grado(request.getId_tipo_grado());
        plan.setId_mencion(model.getId_mencion());
        plan.setId_materia(model.getId_materia());

        List<ProgramaAnalitico> programaAnaliticoPrerequisitoMateria = mi.getListarPrerequisitoMateria(plan);
        String htmlrequisito = "";
        for (ProgramaAnalitico obj : programaAnaliticoPrerequisitoMateria) {
            htmlrequisito += obj.getSigla() + "-" + obj.getMateria() + "\n";
        }
        Contenidos _contenido = new Contenidos();
        _contenido.setId_dct_programa_analitico(model.getId_dct_programa_analitico());
        List<Contenidos> contenidos = mi.getListarContenido(_contenido).stream().sorted(Comparator.comparing(Contenidos::getId_prg_a_contenido)).collect(Collectors.toList());
        int i = 1;
        List<ParrafoBookMarks> detallecontenido = new ArrayList<>();
        for (Contenidos c : contenidos) {
            ParrafoBookMarks parrafo = new ParrafoBookMarks();
            parrafo.setTitle("6.3." + i + " Objetivo Instructivo del Tema " + i + ":");
            parrafo.setContenido(c.getObjetivo_instructivo());
            detallecontenido.add(parrafo);
            i++;
        }
        List<FormasOrganizacionDistribucion> distribuciones = mi.getListarFormasOrganizacionDistribucion(model.getId_dct_programa_analitico());
        List<FormasOrganizacionDistribucion> formasdistribucion = mi.getListarFormasPorDistribucion(model.getId_dct_programa_analitico());
        List<ContenidosFormasRequest> formas = new ArrayList<>();
        for (Contenidos contenido : contenidos) {
            ContenidosFormasRequest forma = new ContenidosFormasRequest();
            forma.setContenido(Utils.capitalizeString(contenido.getContenido()));
            forma.setId_prg_a_contenido(contenido.getId_prg_a_contenido());
            List<FormasOrganizacionDistribucion> distribucion = distribuciones.stream().filter(p -> p.getId_prg_a_contenido().equals(contenido.getId_prg_a_contenido())).collect(Collectors.toList());
            forma.setDistribuciones(distribucion);
            formas.add(forma);
        }
        FormasDistribucionRequest formasorganizacion = new FormasDistribucionRequest();
        formasorganizacion.setTotalHoras(0);
        formasorganizacion.setDistribucion(formas);
        formasorganizacion.setFormasdistribucion(formasdistribucion);
        formasorganizacion.setId_dct_programa_analitico(model.getId_dct_programa_analitico());

        BiBliografia bibliografia = new BiBliografia();
        bibliografia.setId_dct_programa_analitico(model.getId_dct_programa_analitico());
        List<BiBliografia> bibliografias = null;
        bibliografias = mi.getListarBibliografia(bibliografia);
        List<BiBliografia> bibliografiasbasicas = bibliografias.stream().filter(p -> p.getTipobibliografia().equals("Basica")).collect(Collectors.toList());
        List<BiBliografia> bibliografiasComplementaria = bibliografias.stream().filter(p -> p.getTipobibliografia().equals("Complementaria")).collect(Collectors.toList());
        List<Cronograma> cronogramas = mi.getListarCronograma(model.getId_dct_programa_analitico());

        List<FormasOrganizacion> presencial = mi.getListaFormarPorProgramaYTipoPresencial(request.getId());
        List<FormasOrganizacion> virtual = mi.getListaFormarPorProgramaYTipoVirtual(request.getId());
        modelo.addAttribute("QR", "data:image/png;base64," + Utils.QrEncoder("id|" + programaAnaliticocaratula.getId_dct_programa_analitico() + "|Materia:" + programaAnaliticocaratula.getSigla() + " " + programaAnaliticocaratula.getMateria()));
        modelo.addAttribute("facultad", programaAnaliticocaratula.getFacultad());
        modelo.addAttribute("carrera", programaAnaliticocaratula.getDepartamento());
        modelo.addAttribute("gradoacademico", Utils.capitalizeString(programaAnaliticocaratula.getGrado_academico()));
        modelo.addAttribute("sistema", Utils.capitalizeString(programaAnaliticocaratula.getTipo_materia()));
        modelo.addAttribute("ciclo", Utils.capitalizeString(programaAnaliticocaratula.getCiclo_materia()));
        modelo.addAttribute("aniosemestre", Utils.capitalizeString(programaAnaliticocaratula.getId_tipo_materia() == 2 ? programaAnaliticocaratula.getNivel_academico() + "" : programaAnaliticocaratula.getNivel_academico() + ""));
        modelo.addAttribute("observacion", model.getObservacion());
        modelo.addAttribute("nro_resolucion", model.getNro_resolucion());
        modelo.addAttribute("areaambito", Utils.capitalizeString(programaAnaliticocaratula.getArea()));
        modelo.addAttribute("asignatura", programaAnaliticocaratula.getMateria());
        modelo.addAttribute("sigla", programaAnaliticocaratula.getSigla().toUpperCase());
        modelo.addAttribute("preresquisito", htmlrequisito.equals("") ? "Sin requisitos" : htmlrequisito);
        modelo.addAttribute("clasificacion", Utils.capitalizeString(programaAnaliticocaratula.getClasificacion_asignatura()));
        modelo.addAttribute("horasteorica", Utils.capitalizeString(programaAnaliticocaratula.getHrs_teoricas() + " Hrs."));
        modelo.addAttribute("horaspracticas", Utils.capitalizeString(programaAnaliticocaratula.getHrs_practicas() + " Hrs."));
        int semana = programaAnaliticocaratula.getHrs_practicas() + programaAnaliticocaratula.getHrs_teoricas();
        int mes =  semana * programaAnaliticocaratula.getHoras();
        modelo.addAttribute("totalhoraacademica", semana + " Hrs.");
        modelo.addAttribute("totalhoraacademicasemestre", mes + " Hrs.");
        modelo.addAttribute("turno", programaAnaliticocaratula.getTurno());
        modelo.addAttribute("gestionacademica", programaAnaliticocaratula.getId_tipo_materia() == 2 ? programaAnaliticocaratula.getGestion() + "" : programaAnaliticocaratula.getPeriodo() + "/" + programaAnaliticocaratula.getGestion());
        modelo.addAttribute("nombredocente", Utils.capitalizeWords(String.format("%1$s %2$s %3$s", programaAnaliticocaratula.getNombres(), programaAnaliticocaratula.getPaterno(), programaAnaliticocaratula.getMaterno())));
        modelo.addAttribute("categoriadocente", Utils.capitalizeString(programaAnaliticocaratula.getTipo_categoria() + " " + programaAnaliticocaratula.getTipo_docente()));
        modelo.addAttribute("modalidaddeingreso", Utils.capitalizeString(programaAnaliticocaratula.getModalidad_deingreso()));
        modelo.addAttribute("telefono", programaAnaliticocaratula.getTelefonocelular() == null ? "" : programaAnaliticocaratula.getTelefonocelular());
        modelo.addAttribute("correoelectronico", programaAnaliticocaratula.getCorreo());
        modelo.addAttribute("sistemasobeticosinstructivo", ParrafoBookMarks.ConvertHTML(detallecontenido));
        modelo.addAttribute("marcoreferencial", model.getMarco_referencial());
        modelo.addAttribute("justificaciones", model.getJustificacion());
        modelo.addAttribute("propositos", model.getPropositos());
        modelo.addAttribute("sistemasobeticosgeneral", model.getObjetivo_desarrollador());
        modelo.addAttribute("sistemasobeticoseducativo", model.getObjetivo_educativo());
        modelo.addAttribute("contenidosanaliticos", contenidos);
        modelo.addAttribute("metodologia", model.getMetodos_estrategias());
        modelo.addAttribute("recursoseducativos", model.getRecursos());
        modelo.addAttribute("sistemasevaluacion", model.getSistema_evaluacion());
        modelo.addAttribute("sistemasevaluacioncriterio", model.getSistema_evaluacion_criterios());
        modelo.addAttribute("virtual", virtual);
        modelo.addAttribute("precensial", presencial);
        modelo.addAttribute("formasorganizacion", formasorganizacion);
        modelo.addAttribute("basica", bibliografiasbasicas);
        modelo.addAttribute("complementaria", bibliografiasComplementaria);
        modelo.addAttribute("cronograma", cronogramas);
        return "ProgramasAnaliticos/Imprimir/VistaPrevia";
    }

}
