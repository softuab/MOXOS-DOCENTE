package com.moxos.uab.controller.docentes;

import com.moxos.uab.model.models.reportes.LibretaEstudiante;
import com.moxos.uab.model.models.reportes.LibretasDocente;
import com.moxos.uab.model.models.reportes.NotasEstudiante;
import com.moxos.uab.model.models.reportes.ParametrosLibretaRequest;
import com.moxos.uab.model.models.reportes.pdf.ImprimirLibretaForma2;
import com.moxos.uab.model.models.utility.ParametroEntradaRequest;
import com.moxos.uab.mybatis.entity.*;
import com.moxos.uab.mybatis.logic.MiFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ReporteController {
    @Autowired
    private MiFacade mi;
    @Autowired
    private HttpServletRequest request;
    @Value("${app.upload.path}")
    private String path;

    private Clientes getUsuario() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        Clientes cliente = (Clientes) attr.getRequest().getSession().getAttribute("__sess_cliente");
        return cliente;
    }

    @RequestMapping("/docente/verEvaluacionEstudiantes")
    public String Entrada(Model modelo) {
        ParametroEntradaRequest model = new ParametroEntradaRequest();
        modelo.addAttribute("simagen", this.getUsuario().getImagen());
        modelo.addAttribute("usuario", this.getUsuario().getNombres());
        modelo.addAttribute("gestion", Integer.toString(this.getUsuario().getGestion()));
        modelo.addAttribute("periodo", Integer.toString(this.getUsuario().getPeriodo()));
        modelo.addAttribute("model", model);
        return "ReportesAcademicos/Entrada";
    }

    @RequestMapping(value = "/reportes/listarDctMateriasDocente", method = RequestMethod.POST)
    public String listarDctMateriasDocente(@ModelAttribute("model") @Validated ParametroEntradaRequest model, BindingResult result, Model modelo) {
        if (result.hasErrors()) {
            modelo.addAttribute("simagen", this.getUsuario().getImagen());
            modelo.addAttribute("usuario", this.getUsuario().getNombres());
            modelo.addAttribute("gestion", Integer.toString(this.getUsuario().getGestion()));
            modelo.addAttribute("periodo", Integer.toString(this.getUsuario().getPeriodo()));
            modelo.addAttribute("model", model);
            return "ReportesAcademicos/Entrada";
        }
        modelo.addAttribute("usuario", this.getUsuario().getNombres());
        modelo.addAttribute("gestion", model.getGestion());
        modelo.addAttribute("periodo", model.getPeriodo());
        //Sacamos la asignacion del docente
        Asignaciones asignacion = new Asignaciones();
        asignacion.setId_docente(this.getUsuario().getId_usuario());
        asignacion.setGestion(model.getGestion());
        asignacion.setPeriodo(model.getPeriodo());
        List<Asignaciones> datosAsignacion = this.mi.getDctListarAsignacionDocente(asignacion);
        modelo.addAttribute("lDatosAsignacion", datosAsignacion);
        modelo.addAttribute("id_rol", this.getUsuario().getId_rol());
        return "ReportesAcademicos/ListarMateriasDocente";
    }

    @RequestMapping(value = "/reportes/imprimirEvaluacion", method = RequestMethod.GET)
    public void imprimirEvaluacion(@ModelAttribute("model") ParametrosLibretaRequest model, HttpServletResponse response) throws Exception {
        Asignaciones datosAsignacion = new Asignaciones();
        datosAsignacion.setId_asignacion(model.getId_asignacion());
        datosAsignacion = this.mi.getDctBuscarAsignacionDetalleDocente(datosAsignacion);

        Estudiantes listadeestudiantes = new Estudiantes();
        listadeestudiantes.setId_grupo(model.getId_grupo());
        listadeestudiantes.setId_materia(model.getId_materia());
        listadeestudiantes.setGestion(model.getGestion());
        listadeestudiantes.setPeriodo(model.getPeriodo());
        listadeestudiantes.setId_tipo_evaluacion(model.getId_tipo_evaluacion());
        List<Estudiantes> nominadeestudiantes = this.mi.getEstListarEstudiantesPorMateria(listadeestudiantes);

        Libretas libretaparametrosdeevaluacion = new Libretas();
        libretaparametrosdeevaluacion.setId_materia(model.getId_materia());
        libretaparametrosdeevaluacion.setGestion(model.getGestion());
        libretaparametrosdeevaluacion.setPeriodo(model.getPeriodo());
        libretaparametrosdeevaluacion.setId_tipo_evaluacion(model.getId_tipo_evaluacion());
        libretaparametrosdeevaluacion.setId_grupo(model.getId_grupo());
        List<Libretas> parametroslibreta = this.mi.getListaParametrosdeEvaluacionporMateria(libretaparametrosdeevaluacion);

        //Buscamos nota minima por programa
        Programas programa = new Programas();
        programa.setId_programa(model.getId_programa());
        programa.setId_tipo_evaluacion(model.getId_tipo_evaluacion());
        programa.setPeriodo(model.getPeriodo());
        programa.setGestion(model.getGestion());
        Programas notaminimaporprograma = null;
        try {
            notaminimaporprograma = this.mi.getEstListarNotaMinimaporPrograma(programa).stream().findFirst().get();
        } catch (Exception ex) {
            if (model.getId_tipo_evaluacion() == 1)
                throw new Exception("No esta definido las notas minimas para imprimir las libretas");
        }
        if (notaminimaporprograma == null && model.getId_tipo_evaluacion() == 1) {
            throw new Exception("No esta definido las notas minimas para imprimir las libretas");
        }

        Instituciones datosInstitucion = new Instituciones();
        datosInstitucion.setId_institucion(1); //--------------------------ESTATICO
        datosInstitucion = this.mi.getBuscarInstitucion(datosInstitucion);

        Libretas libretanota = new Libretas();
        libretanota.setId_grupo(model.getId_grupo());
        libretanota.setId_modelo_ahorro(model.getId_modelo_ahorro());
        libretanota.setId_tipo_evaluacion(model.getId_tipo_evaluacion());
        libretanota.setId_materia(model.getId_materia());
        libretanota.setGestion(model.getGestion());
        libretanota.setPeriodo(model.getPeriodo());
        List<Libretas> detallenotas = mi.getEstListarNotasLibreta(libretanota);

        LibretasDocente libretadocente = new LibretasDocente();
        libretadocente.setAsignatura(datosAsignacion.getSigla() + " - " + datosAsignacion.getMateria());
        libretadocente.setCarrera(datosAsignacion.getPrograma());
        libretadocente.setDocente(datosAsignacion.getNombre_completo());
        libretadocente.setNivelacademico(String.valueOf(datosAsignacion.getNivel_academico()));
        libretadocente.setFacultad(datosAsignacion.getFacultad());
        String periodo = datosAsignacion.getId_periodo() == 1 ? "PERIODO ACADÉMICO: " + model.getPeriodo() + "/" + model.getGestion() : "GESTIÓN ACADÉMICO: " + model.getGestion();
        libretadocente.setTipoperiodo(periodo);
        libretadocente.setGestion(model.getGestion().toString());
        libretadocente.setGrupo(datosAsignacion.getGrupo());
        libretadocente.setPeriodo(model.getPeriodo().toString());
        libretadocente.setId_fase(datosAsignacion.getId_fase());
        libretadocente.setTipoevaluacion(datosAsignacion.getTipo_evaluacion());
        libretadocente.setNombreinstitucion(datosInstitucion.getInstitucion());
        libretadocente.setLugarimpresion(datosInstitucion.getLocalidad().trim() + "-" + datosInstitucion.getDepartamento().trim() + "-" + datosInstitucion.getPais().trim());
        for (Estudiantes obj : nominadeestudiantes) {
            LibretaEstudiante estudiantelibreta = new LibretaEstudiante();
            estudiantelibreta.setNombrecompleto(obj.getNombre_completo());
            estudiantelibreta.setRu(obj.getId_estudiante());
            estudiantelibreta.setSede_desconcentrada(obj.getSede_desconcentrada());
            for (Libretas o : parametroslibreta) {
                Libretas aux = o;
                NotasEstudiante tiponota = new NotasEstudiante(aux.getTipo_nota(), aux.getPonderacion(), aux.getId_tipo_nota());
                int idEstudiante = estudiantelibreta.getRu();
                int idTipoNota = tiponota.getId();
                for (int i = 1; i <= o.getCantidad(); i++) {
                    Libretas nota = null;
                    int nroNota = i;
                    List<Libretas> notas = detallenotas.stream().filter(p -> p.getId_estudiante() == idEstudiante && p.getId_tipo_nota() == idTipoNota && p.getNro_nota() == nroNota).sorted(Comparator.comparing(Libretas::getNro_nota)).collect(Collectors.toList());
                    nota = notas.isEmpty() ? null : notas.stream().findFirst().get();
                    if (nota == null) {
                        tiponota.Add(0);
                    } else {
                        tiponota.Add(nota.getNota());
                    }
                }
                estudiantelibreta.Add(tiponota);
                //getEstListarNotasEstudiante
            }
            libretadocente.Add(estudiantelibreta);
        }
        if (model.getId_tipo_evaluacion() == 1) {
            ImprimirLibretaForma2 libre = new ImprimirLibretaForma2(libretadocente, parametroslibreta, notaminimaporprograma.getNota_minima(), response, path);
            libre.CreateLibretaRegular();
        }
        if (model.getId_tipo_evaluacion() == 3) {
            ImprimirLibretaForma2 libretaverano = new ImprimirLibretaForma2(libretadocente, parametroslibreta, 51, response, path);
            libretaverano.CreateLibretaVerano();
        }
        if (model.getId_tipo_evaluacion() == 4) {
            ImprimirLibretaForma2 libre = new ImprimirLibretaForma2(libretadocente, parametroslibreta, 51, response, path);
            libre.CreateLibretaMesa();
        }
    }

    @ExceptionHandler(value = Exception.class)
    public String handleException(Model modelo, Exception e) {
        modelo.addAttribute("mensaje", e.getMessage());
        return "Error/Error";
    }
}
