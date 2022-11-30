/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.web.Docentes;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.fautapo.domain.Asignaciones;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Docentes;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Facultades;
import org.fautapo.domain.ImprimirLibreta;
import org.fautapo.domain.Instituciones;
import org.fautapo.domain.LibretaEstudiante;
import org.fautapo.domain.Libretas;
import org.fautapo.domain.LibretasDocente;
import org.fautapo.domain.NotasEstudiante;
import org.fautapo.domain.Programas;
import org.fautapo.domain.logic.MiFacade;
import org.fautapo.model.ParametrosEntrada;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
public class ReporteController {

    @Autowired
    private MiFacade mi;
    @Autowired
    private HttpServletRequest request;

    private Clientes GetUsuario() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        Clientes cliente = (Clientes) attr.getRequest().getSession().getAttribute("__sess_cliente");
        return cliente;
    }

    @RequestMapping("/docente/verEvaluacionEstudiantes.fautapo")
    public String Entrada(Model modelo) {
        Clientes cliente = this.GetUsuario();
        if (cliente == null) {
            modelo.addAttribute("mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            return "Aviso";
        }
        ParametrosEntrada model = new ParametrosEntrada();
        modelo.addAttribute("simagen", cliente.getImagen());
        modelo.addAttribute("usuario", cliente.getNombres());
        modelo.addAttribute("gestion", Integer.toString(cliente.getGestion()));
        modelo.addAttribute("periodo", Integer.toString(cliente.getPeriodo()));
        modelo.addAttribute("model", model);
        return "reportesAcademicos/verNotasEvaluacionEstudiantesDocente/Entrada";
    }

    @RequestMapping(value = "/listarDctMateriasDocente.fautapo", method = RequestMethod.POST)
    public String listarDctMateriasDocente(@ModelAttribute("model") @Validated ParametrosEntrada model, BindingResult result, Model modelo) {
        Clientes cliente = this.GetUsuario();
        if (cliente == null) {
            modelo.addAttribute("mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            return "Aviso";
        }
        if (result.hasErrors()) {
            modelo.addAttribute("simagen", cliente.getImagen());
            modelo.addAttribute("usuario", cliente.getNombres());
            modelo.addAttribute("gestion", Integer.toString(cliente.getGestion()));
            modelo.addAttribute("periodo", Integer.toString(cliente.getPeriodo()));
            modelo.addAttribute("model", model);
            return "reportesAcademicos/verNotasEvaluacionEstudiantesDocente/Entrada";
        }
        modelo.addAttribute("usuario", cliente.getNombres());
        modelo.addAttribute("gestion", model.getGestion());
        modelo.addAttribute("periodo", model.getPeriodo());
        //Sacamos la asignacion del docente
        Asignaciones asignacion = new Asignaciones();
        asignacion.setId_docente(cliente.getId_usuario());
        asignacion.setGestion(model.getGestion());
        asignacion.setPeriodo(model.getPeriodo());
        List datosAsignacion = this.mi.getDctListarAsignacionDocente(asignacion);
        Asignaciones aux = new Asignaciones();
        for (int i = 0; i < datosAsignacion.size(); i++) {
            aux = (Asignaciones) datosAsignacion.get(i);
            int id_modelo_ahorro = aux.getId_modelo_ahorro();
            int id_programa = aux.getId_programa();
            int id_materia = aux.getId_materia();
            if (id_modelo_ahorro > 0) {
                Asignaciones datos = new Asignaciones();
                datos.setId_modelo_ahorro(id_modelo_ahorro);
                datos.setId_programa(id_programa);
                datos.setGestion(model.getGestion());
                datos.setPeriodo(model.getPeriodo());
                datos.setId_materia(id_materia);
                List materiaAhorro = this.mi.getMtrListarMateriaAhorro(datos);
                aux.setMateria_ahorro(materiaAhorro);
                datosAsignacion.set(i, aux);
            } else {
                datosAsignacion.set(i, aux);

            }
        }

        List lDatosAsignaciones = datosAsignacion;
        modelo.addAttribute("lDatosAsignacion", lDatosAsignaciones);
        modelo.addAttribute("id_rol", cliente.getId_rol());
        return "reportesAcademicos/verNotasEvaluacionEstudiantesDocente/ListarMateriasDocente";
    }

    @RequestMapping(value = "/docente/imprimirEvaluacionEstudiantes.fautapo", method = RequestMethod.GET)
    public void imprimirEvaluacionEstudiantes(HttpServletResponse response) throws IOException {
        Clientes cliente = this.GetUsuario();
        if (cliente == null) {
            getException("Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
        }

        String sIdAsignacion = request.getParameter("id_asignacion");
        String sIdMateria = request.getParameter("id_materia");
        String sIdGrupo = request.getParameter("id_grupo");
        String sIdPrograma = request.getParameter("id_programa");
        String sIdTipoEvaluacion = request.getParameter("id_tipo_evaluacion");
        String sIdModeloAhorro = request.getParameter("id_modelo_ahorro");
        String sGestion = request.getParameter("gestion");
        String sPeriodo = request.getParameter("periodo");

        Asignaciones datosAsignacion = new Asignaciones();
        datosAsignacion.setId_asignacion(Integer.parseInt(sIdAsignacion));
        datosAsignacion = this.mi.getDctBuscarAsignacionDetalleDocente(datosAsignacion);

        Estudiantes listadeestudiantes = new Estudiantes();
        listadeestudiantes.setId_grupo(Integer.parseInt(sIdGrupo));
        listadeestudiantes.setId_materia(Integer.parseInt(sIdMateria));
        listadeestudiantes.setGestion(Integer.parseInt(sGestion));
        listadeestudiantes.setPeriodo(Integer.parseInt(sPeriodo));
        listadeestudiantes.setId_tipo_evaluacion(Integer.parseInt(sIdTipoEvaluacion));
        List<Estudiantes> nominadeestudiantes = this.mi.getEstListarEstudiantesPorMateria(listadeestudiantes);

        Libretas libretaparametrosdeevaluacion = new Libretas();
        libretaparametrosdeevaluacion.setId_materia(Integer.parseInt(sIdMateria));
        libretaparametrosdeevaluacion.setGestion(Integer.parseInt(sGestion));
        libretaparametrosdeevaluacion.setPeriodo(Integer.parseInt(sPeriodo));
        libretaparametrosdeevaluacion.setId_tipo_evaluacion(Integer.parseInt(sIdTipoEvaluacion));
        libretaparametrosdeevaluacion.setId_grupo(Integer.parseInt(sIdGrupo));
        List<Libretas> parametroslibreta = this.mi.getListaParametrosdeEvaluacionporMateria(libretaparametrosdeevaluacion);

        //Buscamos nota minima por programa
        Programas programa = new Programas();
        programa.setId_programa(Integer.parseInt(sIdPrograma));
        programa.setId_tipo_evaluacion(Integer.parseInt(sIdTipoEvaluacion));
        programa.setPeriodo(Integer.parseInt(sPeriodo));
        programa.setGestion(Integer.parseInt(sGestion));
        Programas notaminimaporprograma = null;
        try {
            notaminimaporprograma = this.mi.getEstListarNotaMinimaporPrograma(programa).stream().findFirst().get();
        } catch (Exception ex) {
            getException("No esta definido las notas minimas para imprimir las libretas");
        }
        if (notaminimaporprograma == null && sIdTipoEvaluacion.equals("1")) {
            getException("No esta definido las notas minimas para imprimir las libretas");
        }

        Instituciones datosInstitucion = new Instituciones();
        datosInstitucion.setId_institucion(1); //--------------------------ESTATICO
        datosInstitucion = this.mi.getBuscarInstitucion(datosInstitucion);

        Libretas libretanota = new Libretas();
        libretanota.setId_grupo(Integer.parseInt(sIdGrupo));
        libretanota.setId_modelo_ahorro(Integer.parseInt(sIdModeloAhorro));
        libretanota.setId_tipo_evaluacion(Integer.parseInt(sIdTipoEvaluacion));
        libretanota.setId_materia(Integer.parseInt(sIdMateria));
        libretanota.setGestion(Integer.parseInt(sGestion));
        libretanota.setPeriodo(Integer.parseInt(sPeriodo));
        List<Libretas> detallenotas = mi.getEstListarNotasLibreta(libretanota);

        LibretasDocente libretadocente = new LibretasDocente();
        libretadocente.setAsignatura(datosAsignacion.getSigla() + " - " + datosAsignacion.getMateria());
        libretadocente.setCarrera(datosAsignacion.getPrograma());
        libretadocente.setDocente(datosAsignacion.getNombre_completo());
        libretadocente.setNivelacademico(String.valueOf(datosAsignacion.getNivel_academico()));
        libretadocente.setFacultad(datosAsignacion.getFacultad());
        String periodo = datosAsignacion.getId_periodo() == 1 ? "PERIODO ACADÉMICO: " + sPeriodo + "/" + sGestion : "GESTIÓN ACADÉMICO: " + sGestion;
        libretadocente.setTipoperiodo(periodo);
        libretadocente.setGestion(sGestion);
        libretadocente.setGrupo(datosAsignacion.getGrupo());
        libretadocente.setPeriodo(sPeriodo);
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
        if (sIdTipoEvaluacion.equals("1")) {
            ImprimirLibreta libre = new ImprimirLibreta(libretadocente, parametroslibreta, notaminimaporprograma.getNota_minima(), response);
            libre.CreateLibretaRegular();
        }
        if (sIdTipoEvaluacion.equals("3")) {
            ImprimirLibreta libretaverano = new ImprimirLibreta(libretadocente, parametroslibreta, 51, response);
            libretaverano.CreateLibretaVerano();
        }
        if (sIdTipoEvaluacion.equals("4")) {
            ImprimirLibreta libre = new ImprimirLibreta(libretadocente, parametroslibreta, 51, response);
            libre.CreateLibretaMesa();
        }
    }

    private Exception getException(String message) {
        return new Exception(message);
    }

    @ExceptionHandler(value = Exception.class)
    public String handleException(Model modelo, Exception e) {
        modelo.addAttribute("mensaje", e.getMessage());
        return "Error";
    }
}
