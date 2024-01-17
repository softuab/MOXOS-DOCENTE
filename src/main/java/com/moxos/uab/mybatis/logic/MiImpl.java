package com.moxos.uab.mybatis.logic;

import java.util.ArrayList;
import java.util.List;

import com.moxos.uab.model.models.Kardex.DetalleFormacionAcademicaPersonaModel;
import com.moxos.uab.model.models.Kardex.DetalleIdiomaPersonaModel;
import com.moxos.uab.model.models.Kardex.DetallePersonaModalidadIngresoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moxos.uab.mybatis.entity.*;
import com.moxos.uab.mybatis.dao.*;

@Service
@Transactional
public class MiImpl implements MiFacade {

    @Autowired
    private ClientesDao clientesDao;
    @Autowired
    private AuditoriaDao auditoriaDao;
    @Autowired
    private DocentesDao docentesDao;
    @Autowired
    private EnlacesDao enlacesDao;
    @Autowired
    private AsignacionesDao asignacionesDao;
    @Autowired
    private CursosMoodleDao cursosmoodleDao;
    @Autowired
    private PersonasDao personasDao;
    @Autowired
    private MateriasDao materiasDao;
    @Autowired
    private LibretasDao libretasDao;
    @Autowired
    private ProgramasDao programasDao;
    @Autowired
    private CalendariosDao calendariosDao;
    @Autowired
    private EstudiantesDao estudiantesDao;
    @Autowired
    private InstitucionesDao instituciones;
    @Autowired
    private KardexDao kardexDao;
    @Autowired
    private TokenDao tokenDao;
    @Autowired
    private ProgramaAnaliticoDao programaAnaliticoDao;
    @Autowired
    private NotasDao notasDao;
    @Autowired
    private UsuariosDao usuariosDao;

    @Override
    public Clientes getBuscarConexion(Usuarios usuario) {
        return this.clientesDao.getBuscarConexion(usuario);
    }

    @Override
    public List<Docentes> getDetallefotoadjunto(Docentes docente) {
        return this.docentesDao.getDetallefotoadjunto(docente);
    }

    @Override
    public Integer setRegistrarDocenteAdjuntos(Docentes docente) {
        return this.docentesDao.setRegistrarDocenteAdjuntos(docente);
    }

    @Override
    public Integer setCambioPinDocente(Docentes docente) {
        return this.docentesDao.setCambioPinDocente(docente);
    }

    @Override
    public Integer setActualizarDocenteAdjuntos(Docentes docente) {
        return this.docentesDao.setActualizarDocenteAdjuntos(docente);
    }

    @Override
    public List<Docentes> GetListaNotificacionDocente(ParametrosBusqueda buscar) {
        return this.docentesDao.GetListaNotificacionDocente(buscar);
    }

    @Override
    public CursosMoodle getUsuarioMoodle(int id) {
        return this.cursosmoodleDao.GetUsuarioMoodle(id);
    }

    @Override
    public List<Enlaces> getListarEnlaces(Enlaces enlace) {
        return this.enlacesDao.getListarEnlaces(enlace);
    }

    @Override
    public Personas getPrsBuscarPersonaDocente(int id_docente) {
        return this.personasDao.getPrsBuscarPersonaDocente(id_docente);
    }

    public List<Asignaciones> getDctListarAsignacionDocente(Asignaciones asignacion) {
        return this.asignacionesDao.getDctListarAsignacionDocente(asignacion);
    }

    @Override
    public List<Asignaciones> getMtrListarMateriaAhorro(Asignaciones asignacion) {
        return this.asignacionesDao.getMtrListarMateriaAhorro(asignacion);

    }

    @Override
    public MoodleConfiguracion getConfiguracionCursosMoodle() {
        return this.cursosmoodleDao.GetConfiguracionCursosMoodle();
    }

    @Override
    public void registrarUsuariosMoodle(CursosMoodle cursos) {
        this.cursosmoodleDao.RegistrarUsuariosMoodle(cursos);
    }

    @Override
    public void updateUserMoodle(CursosMoodle cursos) {
        this.cursosmoodleDao.UpdateUserMoodle(cursos);
    }

    @Override
    public void actualizarUsuariosMoodle(CursosMoodle cursos) {
        this.cursosmoodleDao.ActualizarUsuariosMoodle(cursos);
    }

    @Override
    public void matricularMoodle(CursosMoodle cursos) {
        this.cursosmoodleDao.MatricularMoodle(cursos);
    }

    @Override
    public List<CursosMoodle> getListarCursosMoodleDocente(ParametrosBusqueda parametros) {
        return this.cursosmoodleDao.GetListarCursosMoodleDocente(parametros);
    }

    @Override
    public void registrarCursosMoodleDocente(CursosMoodle cursos) {
        this.cursosmoodleDao.RegistrarCursosMoodleDocente(cursos);
    }

    @Override
    public List<CursosMoodle> getListarCursosMoodleEstudiantePorCurso(ParametrosBusqueda parametros) {
        return this.cursosmoodleDao.GetListarCursosMoodleEstudiantePorCurso(parametros);
    }

    @Override
    public Asignaciones getDctBuscarAsignacionDocente(Asignaciones asignacion) {
        return this.asignacionesDao.getDctBuscarAsignacionDocente(asignacion);
    }

    @Override
    public Materias getMtrBuscarMateria(Materias materia) {
        return this.materiasDao.getMtrBuscarMateria(materia);
    }

    @Override
    public Libretas getLbrBuscarFase(Libretas libreta) {
        return this.libretasDao.getLbrBuscarFase(libreta);
    }

    @Override
    public List<Libretas> getEstBuscarEstudiantesProgramadospersona(Libretas libreta) {
        return this.libretasDao.getEstBuscarEstudiantesProgramadospersona(libreta);
    }

    @Override
    public void registrarCursosMoodleEstudiante(CursosMoodle cursos) {
        this.cursosmoodleDao.RegistrarCursosMoodleEstudiante(cursos);
    }

    @Override
    public CursosMoodle getCursoMoodleEstudiante(ParametrosBusqueda parametros) {
        return this.cursosmoodleDao.GetCursoMoodleEstudiante(parametros);
    }

    @Override
    public Programas getPrgBuscarPrograma(Programas programa) {
        return this.programasDao.getPrgBuscarPrograma(programa);
    }

    @Override
    public List<Libretas> getLbrListarTiposNotas(Libretas libreta) {
        return this.libretasDao.getLbrListarTiposNotas(libreta);
    }

    @Override
    public Integer setGrpEliminarEvaluacion(Libretas libreta) {
        return this.libretasDao.setGrpEliminarEvaluacion(libreta);
    }

    @Override
    public Integer setGrpRegistrarEvaluacion(Libretas libreta) {
        return this.libretasDao.setGrpRegistrarEvaluacion(libreta);
    }

    @Override
    public List<Libretas> getGrpListarEvaluacionDefinida(Libretas libreta) {
        return this.libretasDao.getGrpListarEvaluacionDefinida(libreta);
    }

    @Override
    public Libretas getLbrBuscarTipoNota(Libretas libreta) {
        return this.libretasDao.getLbrBuscarTipoNota(libreta);
    }

    @Override
    public List<Libretas> getCalendarioAcademicoExcepciones(Libretas libreta) {
        return this.libretasDao.getCalendarioAcademicoExcepciones(libreta);
    }

    @Override
    public List<Libretas> getDetalleNotaLibretaMateria(Libretas libreta) {
        return this.libretasDao.getDetalleNotaLibretaMateria(libreta);
    }

    @Override
    public List<Libretas> getEstBuscarEstudiantesProgramados(Libretas libreta) {
        return this.libretasDao.getEstBuscarEstudiantesProgramados(libreta);
    }

    @Override
    public List<Libretas> getEstListarNotasEstudianteLibretaSegunda(Libretas libreta) {
        return this.libretasDao.getEstListarNotasEstudianteLibretaSegunda(libreta);
    }

    @Override
    public List<Programas> getEstListarNotaMinimaporPrograma(Programas programa) {
        return this.programasDao.getEstListarNotaMinimaporPrograma(programa);
    }

    @Override
    public Integer setEstInsertarNotaEstudianteFase(Libretas libreta) {
        return this.libretasDao.setEstInsertarNotaEstudianteFase(libreta);
    }

    @Override
    public void registrarBitacoraCambiosDocente(Libretas libreta) {
        this.libretasDao.RegistrarBitacoraCambiosDocente(libreta);
    }

    @Override
    public Libretas getNotasEstudiante(Libretas libreta) {
        return this.libretasDao.getNotasEstudiante(libreta);
    }

    @Override
    public List<Libretas> getPlanillaProgramadosMateria(Libretas libreta) {
        return this.libretasDao.getPlanillaProgramadosMateria(libreta);
    }

    @Override
    public List<Libretas> getNotasdeMatriculados(Libretas libreta) {
        return this.libretasDao.getNotasdeMatriculados(libreta);
    }

    @Override
    public Integer getLbrBuscarFaseMaxima(Libretas libreta) {
        return this.libretasDao.getLbrBuscarFaseMaxima(libreta);
    }

    @Override
    public Integer setDctAvanzarFase(Libretas libreta) {
        return this.libretasDao.setDctAvanzarFase(libreta);
    }

    @Override
    public List<Calendarios> getlistarCalendarioDocente(Calendarios calendario) {
        return this.calendariosDao.getlistarCalendarioDocente(calendario);
    }

    @Override
    public Asignaciones getDctBuscarAsignacionDetalleDocente(Asignaciones asignacion) {
        return this.asignacionesDao.getDctBuscarAsignacionDetalleDocente(asignacion);
    }

    @Override
    public List<Estudiantes> getEstListarEstudiantesPorMateria(Estudiantes estudiante) {
        return this.estudiantesDao.getEstListarEstudiantesPorMateria(estudiante);
    }

    @Override
    public List<Libretas> getListaParametrosdeEvaluacionporMateria(Libretas libreta) {
        return this.libretasDao.getListaParametrosdeEvaluacionporMateria(libreta);
    }

    @Override
    public Clientes getBuscarConexion2FA(Usuarios usuario) {
        return this.clientesDao.getBuscarConexion2FA(usuario);
    }

    @Override
    public void bloquearUsuario(Clientes cliente) {
        this.clientesDao.bloquearUsuario(cliente);
    }

    @Override
    public void guardarEvento(Auditoria auditoria) {
        this.auditoriaDao.guardarEvento(auditoria);
    }

    @Override
    public Instituciones getBuscarInstitucion(Instituciones institucion) {
        return this.instituciones.getBuscarInstitucion(institucion);
    }

    @Override
    public Instituciones getBuscarInstitucionSede(Instituciones institucion) {
        return this.instituciones.getBuscarInstitucionSede(institucion);
    }

    @Override
    public List<Libretas> getEstListarNotasLibreta(Libretas libreta) {
        return this.libretasDao.getEstListarNotasLibreta(libreta);
    }

    @Override
    public PersonaKardex getKardexPersonalNuevo(int id_persona) {
        return this.kardexDao.GetKardexPersonalNuevo(id_persona);
    }

    @Override
    public void registrarNuevoKardexDocente(PersonaKardex kardex) {
        this.kardexDao.RegistrarNuevoKardexDocente(kardex);
    }

    @Override
    public List<ListViewItem> getLocalidadPersona() {
        return this.kardexDao.GetLocalidadPersona();
    }

    @Override
    public void actualizarInformacionPersonalKardexDocente(PersonaKardex kardex) {
        this.kardexDao.ActualizarInformacionPersonalKardexDocente(kardex);
    }

    @Override
    public List<Tokens> getListartokendocente(Tokens token) {
        return this.tokenDao.getListartokendocente(token);
    }

    @Override
    public void actualizarIdentificacionPersonalKardexDocente(PersonaKardex kardex) {
        this.kardexDao.ActualizarIdentificacionPersonalKardexDocente(kardex);
    }

    @Override
    public PersonaKardex getImagenesPersonaKardex(int id_persona) {
        return this.kardexDao.GetKardexPersonalNuevo(id_persona); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizarServicioMilitarPersonalKardexDocente(PersonaKardex kardex) {
        this.kardexDao.ActualizarServicioMilitarPersonalKardexDocente(kardex);
    }

    @Override
    public List<ListViewItem> getBancos() {
        return this.kardexDao.GetBancos();
    }

    @Override
    public void actualizarInformacionLaboralPersonalKardexDocente(PersonaKardex kardex) {
        this.kardexDao.ActualizarInformacionLaboralPersonalKardexDocente(kardex);
    }

    @Override
    public List<ListViewItem> getNivelEstudioPorNivel(String grado) {
        return this.kardexDao.GetNivelEstudioPorNivel(grado);
    }

    @Override
    public List<ListViewItem> getColegiosProfesionales() {
        return this.kardexDao.GetColegiosProfesionales(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ListViewItem> getProfesiones() {
        return this.kardexDao.GetProfesiones(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizarEducacionPregradoPersonalKardexDocente(PersonaKardex kardex) {
        this.kardexDao.ActualizarEducacionPregradoPersonalKardexDocente(kardex);
    }

    @Override
    public void actualizarEducacionPosgradoKardexDocente(PersonaKardex kardex) {
        this.kardexDao.ActualizarEducacionPosgradoKardexDocente(kardex);
    }

    @Override
    public void actualizarContactoPersonalKardexDocente(PersonaKardex kardex) {
        this.kardexDao.ActualizarContactoPersonalKardexDocente(kardex);
    }

    @Override
    public String getImageidiomas(int id_idioma) {
        return this.kardexDao.GetImageidiomas(id_idioma);
    }

    @Override
    public String getImagemodalidad(int id_modalidadingreso) {
        return this.kardexDao.GetImagemodalidad(id_modalidadingreso);
    }

    @Override
    public String getImageformacionacademica(int id_formacion) {
        return this.kardexDao.GetImageformacionacademica(id_formacion);
    }

    @Override
    public String getImageexperiencia(int id_experiencia_laboral) {
        return this.kardexDao.GetImageexperiencia(id_experiencia_laboral);
    }

    @Override
    public String getImagecursosrealizados(int id_cursos_realizados) {
        return this.kardexDao.GetImagecursosrealizados(id_cursos_realizados);
    }

    @Override
    public String getImageproduccioncientifica(int id_produccion_cientifica) {
        return this.kardexDao.GetImageproduccioncientifica(id_produccion_cientifica);
    }

    @Override
    public String getImageactividadesacademicas(int id_activades_academicas) {
        return this.kardexDao.GetImageactividadesacademicas(id_activades_academicas);
    }

    @Override
    public String getImageproyecto(int id_personas_proyecto) {
        return this.kardexDao.GetImageproyecto(id_personas_proyecto);
    }

    @Override
    public KardexPersonal getKardexPersonalDocente(int id_persona) {
        return this.kardexDao.getKardexPersonalDocente(id_persona);
    }

    @Override
    public List<CategoriaDocente> getPersonaCategoriaKardex(ParametrosBusqueda busqueda) {
        return this.kardexDao.GetPersonaCategoriaKardex(busqueda);
    }

    @Override
    public List<PersonaCursosRealizados> getPersonaTotalCursosRealizadosKardex(int id_persona_kardex) {
        return this.kardexDao.GetPersonaTotalCursosRealizadosKardex(id_persona_kardex);
    }

    @Override
    public List<DetallePersonaExperienciaLaboral> getPersonaTotalExperienciaLaboralKardex(int id_persona_kardex) {
        return this.kardexDao.GetPersonaTotalExperienciaLaboralKardex(id_persona_kardex);
    }

    @Override
    public List<DetalleFormacionAcademicaPersonal> getPersonaTotalFormacionAcademicaKardex(int id_persona_kardex) {
        return this.kardexDao.GetPersonaTotalFormacionAcademicaKardex(id_persona_kardex);
    }

    @Override
    public List<DetallePersonaModalidadIngreso> getPersonaTotalModalidadKardex(int id_persona_kardex) {
        return this.kardexDao.GetPersonaTotalModalidadKardex(id_persona_kardex);
    }

    @Override
    public List<PersonaProduccionCientifica> getPersonaTotalProduccionCientificaKardex(int id_persona_kardex) {
        return this.kardexDao.GetPersonaTotalProduccionCientificaKardex(id_persona_kardex);
    }

    @Override
    public List<DetalleIdiomaPersonal> getPersonaTotalIdiomaKardex(int id_persona) {
        return this.kardexDao.GetPersonaTotalIdiomaKardex(id_persona);
    }

    @Override
    public List<PersonaEvaluacionDocente> getPersonaTotalEvaluacionDocenteKardex(int id_persona_kardex) {
        return this.kardexDao.GetPersonaTotalEvaluacionDocenteKardex(id_persona_kardex);
    }

    @Override
    public List<PersonaActividadesAcademicas> getPersonaSubTotalActividadesAcademicasKardex(PersonaKardex kardex) {
        return this.kardexDao.GetPersonaSubTotalActividadesAcademicasKardex(kardex);
    }

    @Override
    public List<PersonaActividadesAcademicas> getPersonaTotalActividadesAcademicasKardex(int id_persona_kardex) {
        return this.kardexDao.GetPersonaTotalActividadesAcademicasKardex(id_persona_kardex);
    }

    @Override
    public PersonaActividadesAcademicas getPersonaActividadesAcademicasKardex(int id_activades_academicas) {
        return this.kardexDao.GetPersonaActividadesAcademicasKardex(id_activades_academicas);
    }

    @Override
    public int registrarNuevoActividadesAcademicasKardex(PersonaActividadesAcademicas actividades) {
        return this.kardexDao.RegistrarNuevoActividadesAcademicasKardex(actividades);
    }

    @Override
    public void actualizarImagenActividadesAcademicasKardexDocente(PersonaActividadesAcademicas actividades) {
        this.kardexDao.ActualizarImagenActividadesAcademicasKardexDocente(actividades);
    }

    @Override
    public void actualizarDatosiActividadesAcademicasKardexDocente(PersonaActividadesAcademicas actividades) {
        this.kardexDao.ActualizarDatosiActividadesAcademicasKardexDocente(actividades);
    }

    @Override
    public void eliminarActividadesAcademicasKardexDocente(int id_activades_academicas) {
        this.kardexDao.EliminarActividadesAcademicasKardexDocente(id_activades_academicas);
    }

    @Override
    public void aprobarActividadesAcademicasKardexDocente(PersonaActividadesAcademicas actividades) {
        this.kardexDao.AprobarActividadesAcademicasKardexDocente(actividades);
    }

    @Override
    public List<PersonaCursosRealizados> getPersonaSubTotalCursosRealizadosKardex(PersonaKardex kardex) {
        return this.kardexDao.GetPersonaSubTotalCursosRealizadosKardex(kardex);
    }

    @Override
    public PersonaCursosRealizados getPersonaCursosRealizadosKardex(int id_cursos_realizados) {
        return this.kardexDao.GetPersonaCursosRealizadosKardex(id_cursos_realizados);
    }

    @Override
    public int registrarCursosRealizadosKardex(PersonaCursosRealizados cursos) {
        return this.kardexDao.RegistrarCursosRealizadosKardex(cursos);
    }

    @Override
    public void actualizarImagenCursosRealizadosKardexDocente(PersonaCursosRealizados cursos) {
        this.kardexDao.ActualizarImagenCursosRealizadosKardexDocente(cursos);
    }

    @Override
    public void actualizarDatosCursosRealizadosKardexDocente(PersonaCursosRealizados cursos) {
        this.kardexDao.ActualizarDatosCursosRealizadosKardexDocente(cursos);
    }

    @Override
    public void eliminarCursosRealizadosKardexDocente(int id_cursos_realizados) {
        this.kardexDao.EliminarCursosRealizadosKardexDocente(id_cursos_realizados);
    }

    @Override
    public void aprobarCursosRealizadosKardexDocente(PersonaCursosRealizados cursos) {
        this.kardexDao.AprobarCursosRealizadosKardexDocente(cursos);
    }

    @Override
    public List<PersonaEvaluacionDocente> getPersonaSubTotalEvaluacionDocenteKardex(PersonaKardex kardex) {
        return this.kardexDao.GetPersonaSubTotalEvaluacionDocenteKardex(kardex);
    }

    @Override
    public PersonaEvaluacionDocente getPersonaEvaluacionDocenteKardex(int id_evaluacion_docente) {
        return this.kardexDao.GetPersonaEvaluacionDocenteKardex(id_evaluacion_docente);
    }

    @Override
    public int registrarEvaluacionDocenteKardex(PersonaEvaluacionDocente evaluacion) {
        return this.kardexDao.RegistrarEvaluacionDocenteKardex(evaluacion); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizarImagenEvaluacionDocenteKardex(PersonaEvaluacionDocente evaluacion) {
        this.kardexDao.ActualizarImagenEvaluacionDocenteKardex(evaluacion);
    }

    @Override
    public void actualizarEvaluacionDocenteKardex(PersonaEvaluacionDocente evaluacion) {
        this.kardexDao.ActualizarEvaluacionDocenteKardex(evaluacion);
    }

    @Override
    public void eliminarEvaluacionDocenteKardex(int id_evaluacion_docente) {
        this.kardexDao.EliminarEvaluacionDocenteKardex(id_evaluacion_docente);
    }

    @Override
    public void aprobarEvaluacionDocenteKardex(PersonaEvaluacionDocente evaluacion) {
        this.kardexDao.AprobarEvaluacionDocenteKardex(evaluacion);
    }

    @Override
    public PersonaExperienciaLaboral getPersonaExperienciaLaboralKardex(int id_experiencia_laboral) {
        return this.kardexDao.GetPersonaExperienciaLaboralKardex(id_experiencia_laboral);
    }

    @Override
    public List<DetallePersonaExperienciaLaboral> getPersonaSubTotalExperienciaLaboralKardex(PersonaKardex kardex) {
        return this.kardexDao.GetPersonaSubTotalExperienciaLaboralKardex(kardex);
    }

    @Override
    public void actualizarDatosExperienciaLaboralKardexDocente(PersonaExperienciaLaboral experiencia) {
        this.kardexDao.ActualizarDatosExperienciaLaboralKardexDocente(experiencia);
    }

    @Override
    public void aprobarExperienciaLaboralKardexDocente(PersonaExperienciaLaboral experiencia) {
        this.kardexDao.AprobarExperienciaLaboralKardexDocente(experiencia);
    }

    @Override
    public void eliminarExperienciaLaboralKardexDocente(int id_experiencia_laboral) {
        this.kardexDao.EliminarExperienciaLaboralKardexDocente(id_experiencia_laboral);
    }

    @Override
    public void actualizarImagenExperienciaLaboralKardexDocente(PersonaExperienciaLaboral experiencia) {
        this.kardexDao.ActualizarImagenExperienciaLaboralKardexDocente(experiencia);
    }

    @Override
    public int registrarExperienciaLaboralKardex(PersonaExperienciaLaboral experiencia) {
        return this.kardexDao.RegistrarExperienciaLaboralKardex(experiencia);//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PersonaFormacionAcademica getPersonaFormacionAcademicaKardex(int id_formacion) {
        return this.kardexDao.GetPersonaFormacionAcademicaKardex(id_formacion); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int registrarNuevoFormacionAcademicaKardex(PersonaFormacionAcademica formacion) {
        return this.kardexDao.RegistrarNuevoFormacionAcademicaKardex(formacion); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizarDatosFormacionAcademicaKardexDocente(PersonaFormacionAcademica formacion) {
        this.kardexDao.ActualizarDatosFormacionAcademicaKardexDocente(formacion);
    }

    @Override
    public void aprobarFormacionAcademicaKardexDocente(PersonaFormacionAcademica formacion) {
        this.kardexDao.AprobarFormacionAcademicaKardexDocente(formacion); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminarFormacionAcademicaKardexDocente(int id_formacion) {
        this.kardexDao.EliminarFormacionAcademicaKardexDocente(id_formacion); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizarImagenFormacionAcademicaKardexDocente(PersonaFormacionAcademica formacion) {
        this.kardexDao.ActualizarImagenFormacionAcademicaKardexDocente(formacion); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DetalleFormacionAcademicaPersonal> getPersonaSubTotalFormacionAcademicaKardex(PersonaKardex kardex) {
        return this.kardexDao.GetPersonaSubTotalFormacionAcademicaKardex(kardex);//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ListViewItem> getProgramasPregrado() {
        return this.kardexDao.GetProgramasPregrado();
    }

    @Override
    public List<DetalleIdiomaPersonal> getPersonaSubTotalIdiomaKardex(PersonaKardex kardex) {
        return this.kardexDao.GetPersonaSubTotalIdiomaKardex(kardex);
    }

    @Override
    public PersonaIdioma getPersonaIdiomaKardex(int id_idioma) {
        return this.kardexDao.GetPersonaIdiomaKardex(id_idioma); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizarImagenIdiomaKardexDocente(PersonaIdioma idioma) {
        this.kardexDao.ActualizarImagenIdiomaKardexDocente(idioma); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int registrarNuevoIdiomaKardex(PersonaIdioma idioma) {
        return this.kardexDao.RegistrarNuevoIdiomaKardex(idioma); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizarDatosiIdiomaKardexDocente(PersonaIdioma idioma) {
        this.kardexDao.ActualizarDatosiIdiomaKardexDocente(idioma); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void aprobarIdiomaKardexDocente(PersonaIdioma idioma) {
        this.kardexDao.AprobarIdiomaKardexDocente(idioma); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminarIdiomaKardexDocente(int id_idioma) {
        this.kardexDao.EliminarIdiomaKardexDocente(id_idioma); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int registrarNuevoModalidadKardex(PersonaModalidadIngreso modalidad) {
        return this.kardexDao.RegistrarNuevoModalidadKardex(modalidad);
    }

    @Override
    public void actualizarDatosiModalidadKardexDocente(PersonaModalidadIngreso modalidad) {
        this.kardexDao.ActualizarDatosiModalidadKardexDocente(modalidad);
    }

    @Override
    public void aprobarModalidadKardexDocente(PersonaModalidadIngreso modalidad) {
        this.kardexDao.AprobarModalidadKardexDocente(modalidad);
    }

    @Override
    public void eliminarModalidadKardexDocente(int id_modalidadingreso) {
        this.kardexDao.EliminarModalidadKardexDocente(id_modalidadingreso);
    }

    @Override
    public void actualizarImagenModalidadKardexDocente(PersonaModalidadIngreso modalidad) {
        this.kardexDao.ActualizarImagenModalidadKardexDocente(modalidad);
    }

    @Override
    public PersonaModalidadIngreso getPersonaModalidadKardex(int id_modalidadingreso) {
        return this.kardexDao.GetPersonaModalidadKardex(id_modalidadingreso);
    }

    @Override
    public List<DetallePersonaModalidadIngreso> getPersonaSubTotalModalidadKardex(PersonaKardex kardex) {
        return this.kardexDao.GetPersonaSubTotalModalidadKardex(kardex);
    }


    @Override
    public int registrarNuevoProyectoKardexPersona(PersonaProyectoDocente proyecto) {
        return this.kardexDao.RegistrarNuevoProyectoKardexPersona(proyecto);
    }

    @Override
    public void actualizarDatosProyectoKardexDocente(PersonaProyectoDocente proyecto) {
        this.kardexDao.ActualizarDatosProyectoKardexDocente(proyecto);
    }

    @Override
    public void aprobarProyectoKardexDocente(PersonaProyectoDocente proyecto) {
        this.kardexDao.AprobarProyectoKardexDocente(proyecto);
    }

    @Override
    public void eliminarProyectoKardexDocente(int id_personas_proyecto) {
        this.kardexDao.EliminarProyectoKardexDocente(id_personas_proyecto);
    }

    @Override
    public List<PersonaProyectoDocente> getPersonaTotalProyectoKardex(int id_persona_kardex) {
        return this.kardexDao.GetPersonaTotalProyectoKardex(id_persona_kardex);
    }

    @Override
    public void actualizarImagenProyectoKardexDocente(PersonaProyectoDocente proyecto) {
        this.kardexDao.ActualizarImagenProyectoKardexDocente(proyecto);
    }

    @Override
    public List<PersonaProyectoDocente> getPersonaSubTotalProyectoKardex(PersonaKardex kardex) {
        return this.kardexDao.GetPersonaSubTotalProyectoKardex(kardex);
    }

    @Override
    public PersonaProyectoDocente getPersonaProyectoKardex(int id_personas_proyecto) {
        return this.kardexDao.GetPersonaProyectoKardex(id_personas_proyecto);
    }

    @Override
    public void actualizarProduccionCientificaKardexDocente(PersonaProduccionCientifica produccion) {
        this.kardexDao.ActualizarProduccionCientificaKardexDocente(produccion);
    }

    @Override
    public void aprobarProduccionCientificaKardexDocente(PersonaProduccionCientifica produccion) {
        this.kardexDao.AprobarProduccionCientificaKardexDocente(produccion);
    }

    @Override
    public void eliminarProduccionCientificaKardexDocente(int id_produccion_cientifica) {
        this.kardexDao.EliminarProduccionCientificaKardexDocente(id_produccion_cientifica);
    }

    @Override
    public void actualizarImagenProduccionCientificaKardexDocente(PersonaProduccionCientifica produccion) {
        this.kardexDao.ActualizarImagenProduccionCientificaKardexDocente(produccion);
    }

    @Override
    public PersonaProduccionCientifica getPersonaProduccionCientificaKardex(int id_produccion_cientifica) {
        return this.kardexDao.GetPersonaProduccionCientificaKardex(id_produccion_cientifica);
    }

    @Override
    public List<PersonaProduccionCientifica> getPersonaSubTotalProduccionCientificaKardex(PersonaKardex kardex) {
        return this.kardexDao.GetPersonaSubTotalProduccionCientificaKardex(kardex);
    }

    @Override
    public int registrarProduccionCientificaKardex(PersonaProduccionCientifica produccion) {
        return this.kardexDao.RegistrarProduccionCientificaKardex(produccion); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Asignaciones> getDctListarAsignacionDocenteporProgramaAnalitico(Asignaciones asignacion) {
        return this.asignacionesDao.getDctListarAsignacionDocenteporProgramaAnalitico(asignacion);

    }

    @Override
    public Integer permitirRegistroPrograma(ProgramaAnalitico programaAnalitico) {
        return this.programaAnaliticoDao.PermitirRegistroPrograma(programaAnalitico);
    }

    @Override
    public List<ProgramaAnalitico> getListaProgramaanalitico(ProgramaAnalitico programaAnalitico) {
        return this.programaAnaliticoDao.GetListaProgramaanalitico(programaAnalitico); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ListViewItem> getListarProgramasAnaliticosPorMateria(ParametrosBusqueda busqueda) {
        return this.programaAnaliticoDao.GetListarProgramasAnaliticosPorMateria(busqueda);
    }

    @Override
    public List<ProgramaAnalitico> getListarDatosCaratula(int id_asignacion) {
        return this.programaAnaliticoDao.GetListarDatosCaratula(id_asignacion);
    }

    @Override
    public List<ProgramaAnalitico> getListarMateriaProgramaAnalitico(int id_asignacion) {
        return this.programaAnaliticoDao.GetListarMateriaProgramaAnalitico(id_asignacion);
    }

    public Docentes getBuscarDocente(Docentes docente) {
        return this.docentesDao.getBuscarDocente(docente);
    }

    @Override
    public List<ProgramaAnalitico> getListarPrerequisitoMateria(Planes plan) {
        return this.programaAnaliticoDao.GetListarPrerequisitoMateria(plan);  //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int registrarProgromaAnalitico(ProgramaAnalitico programaAnalitico) {
        return this.programaAnaliticoDao.RegistrarProgromaAnalitico(programaAnalitico); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ProgramaAnalitico getProgramaanalitico(Integer id_dct_programa_analitico) {
        return this.programaAnaliticoDao.getProgramaanalitico(id_dct_programa_analitico); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int actualizarProgramaAnalitico(ProgramaAnalitico programaAnalitico) {
        return this.programaAnaliticoDao.ActualizarProgramaAnalitico(programaAnalitico); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Contenidos> getListarContenido(Contenidos contenidos) {
        return this.programaAnaliticoDao.GetListarContenido(contenidos); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ProgramaAnalitico getDetalleProgramaAnalitico(int id_dct_programa_analitico) {
        return this.programaAnaliticoDao.getDetalleProgramaAnalitico(id_dct_programa_analitico);
    }

    @Override
    public int actualizarObjetivo_Instructivo(Contenidos contenidos) {
        return this.programaAnaliticoDao.ActualizarObjetivo_Instructivo(contenidos); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int registrarObjetivo_Instructivo(Contenidos contenidos) {
        return this.programaAnaliticoDao.RegistrarObjetivo_Instructivo(contenidos); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int eliminarObjetivo_Instructivo(Contenidos contenidos) {
        return this.programaAnaliticoDao.EliminarObjetivo_Instructivo(contenidos); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Contenidos getContenido(Integer id_prg_a_contenido) {
        return this.programaAnaliticoDao.getContenido(id_prg_a_contenido); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<FormasOrganizacionDistribucion> getListarFormasOrganizacionDistribucion(Integer id_dct_programa_analitico) {
        return this.programaAnaliticoDao.getListarFormasOrganizacionDistribucion(id_dct_programa_analitico);
    }

    @Override
    public List<FormasOrganizacion> getListaFormasPorTipo(String tipo_forma) {
        return this.programaAnaliticoDao.getListaFormasPorTipo(tipo_forma);
    }

    @Override
    public List<FormasOrganizacionDistribucion> getListarFormasPorDistribucion(Integer id_dct_programa_analitico) {
        return this.programaAnaliticoDao.getListarFormasPorDistribucion(id_dct_programa_analitico);
    }

    @Override
    public Integer getidDistribucion(FormasDistribucion distribucion) {
        return this.programaAnaliticoDao.getidDistribucion(distribucion);
    }

    @Override
    public Integer getTotalHorasdistribucion(Integer id_dct_programa_analitico) {
        return this.programaAnaliticoDao.getTotalHorasdistribucion(id_dct_programa_analitico);
    }

    @Override
    public void insertarDistribucion(FormasDistribucion distribucion) {
        this.programaAnaliticoDao.insertarDistribucion(distribucion);
    }

    @Override
    public void actualizarHorasDistribucion(FormasOrganizacionDistribucion distribucion) {
        this.programaAnaliticoDao.actualizarHorasDistribucion(distribucion);
    }

    @Override
    public void eliminarDistribucion(Integer id_prg_a_distribucion) {
        this.programaAnaliticoDao.eliminarDistribucion(id_prg_a_distribucion);
    }

    @Override
    public HorasMaterias getCantidadHorasAsignatura(Integer id_dct_programa_analitico) {
        return programaAnaliticoDao.getCantidadHorasAsignatura(id_dct_programa_analitico);
    }

    @Override
    public FormasDistribucion getFormasDistribucion(Integer id_prg_a_distribucion) {
        return programaAnaliticoDao.getFormasDistribucion(id_prg_a_distribucion);
    }

    @Override
    public List<BiBliografia> getListarBibliografia(BiBliografia bibliografia) {
        return this.programaAnaliticoDao.GetListarBibliografia(bibliografia);
    }

    @Override
    public BiBliografia getBibliografia(Integer id_prg_a_bibliografia) {
        return this.programaAnaliticoDao.getBibliografia(id_prg_a_bibliografia);
    }

    @Override
    public Integer registrarBibliografia(BiBliografia bibliografia) {
        return this.programaAnaliticoDao.registrarBibliografia(bibliografia); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer actualizarBibliografia(BiBliografia bibliografia) {
        return programaAnaliticoDao.actualizarBibliografia(bibliografia);
    }

    @Override
    public Integer eliminarBibliografia(Integer id_prg_a_bibliografia) {
        return programaAnaliticoDao.eliminarBibliografia(id_prg_a_bibliografia);
    }

    @Override
    public Integer actualizarCronograma(Cronograma cronograma) {
        return this.programaAnaliticoDao.actualizarCronograma(cronograma); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer registrarCronograma(Cronograma cronograma) {
        return this.programaAnaliticoDao.registrarCronograma(cronograma); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer eliminarCronograma(Integer id_prg_a_cronograma) {
        return this.programaAnaliticoDao.eliminarCronograma(id_prg_a_cronograma); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Cronograma> getListarCronograma(Integer id_dct_programa_analitico) {
        return this.programaAnaliticoDao.getListarCronograma(id_dct_programa_analitico); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Cronograma getCronograma(Integer id_prg_a_cronograma) {
        return this.programaAnaliticoDao.getCronograma(id_prg_a_cronograma); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ProgramaAnalitico getDatosCaratula(Integer id_dct_programa_analitico) {
        return this.programaAnaliticoDao.GetDatosCaratula(id_dct_programa_analitico);
    }

    @Override
    public List<ListViewItem> getListaFormasClases() {
        return programaAnaliticoDao.getListaFormasClases();
    }

    @Override
    public Integer getIdProgramaAnalitico(Asignaciones asignaciones) {
        return programaAnaliticoDao.getIdProgramaAnalitico(asignaciones);
    }

    @Override
    public ProgramaAnalitico getListarInformeProgramaAnalitico(int id_asignacion) {
        return this.programaAnaliticoDao.getListarInformeProgramaAnalitico(id_asignacion);
    }

    @Override
    public List<FormasOrganizacion> getListaFormarPorProgramaYTipoPresencial(Integer id_dct_programa_analitico) {
        return programaAnaliticoDao.getListaFormarPorProgramaYTipoPresencial(id_dct_programa_analitico);
    }

    @Override
    public List<FormasOrganizacion> getListaFormarPorProgramaYTipoVirtual(Integer id_dct_programa_analitico) {
        return programaAnaliticoDao.getListaFormarPorProgramaYTipoVirtual(id_dct_programa_analitico);
    }

    @Override
    public Integer updateUser2FA(Docentes docentes) {
        return clientesDao.updateUser2FA(docentes);
    }

    @Override
    public String getSecret(Integer id_docente) {
        return clientesDao.getSecret(id_docente);
    }

    @Override
    public Docentes getUsuario(String apodo) {
        return clientesDao.getUsuario(apodo);
    }

    @Override
    public List<GrupoDefinicion> getDefinicionNotasPorPrograma(Materias materias) {
        return this.notasDao.getDefinicionNotasPorPrograma(materias);
    }

    @Override
    public boolean existeUsuario(String correo) {
        Integer id = this.usuariosDao.getIDUsuario(correo);
        return id == null ? false : true;
    }

    @Override
    public List<Docentes> getBuscarListaDocentesCorreo(String correo) {
        return this.docentesDao.getBuscarListaDocentesCorreo(correo);
    }

    @Override
    public int setGenerarToken(Tokens token) {

        return this.tokenDao.setGenerarToken(token);
    }

    @Override
    public Docentes getUsuarioDocente(String apodo) {
        return docentesDao.getUsuarioDocente(apodo);
    }

    @Override
    public List<Libretas> getCalendarioAcademicoExcepcionesEstudiante(Libretas libreta) {
        return libretasDao.getCalendarioAcademicoExcepcionesEstudiante(libreta);
    }

    @Override
    public int copy(ProgramaAnalitico programaAnalitico, Integer idProgramaAnaliticoAnterior) {
        int IDProgrmaAnalitico = this.programaAnaliticoDao.insertProgramaAnalitico(programaAnalitico);
        programaAnalitico.setId_dct_programa_analitico_ant(programaAnalitico.getId_dct_programa_analitico());
        programaAnalitico.setId_dct_programa_analitico(IDProgrmaAnalitico);
        this.programaAnaliticoDao.copyBibliografia(programaAnalitico);
        List<Contenidos> contenidoanalitcos = new ArrayList<>();
        List<Contenidos> contenido = this.programaAnaliticoDao.GetListaCopiarContenidos(programaAnalitico.getId_dct_programa_analitico_ant());
        for (Contenidos b : contenido) {
            b.setId_prg_a_contenido_ant(b.getId_prg_a_contenido());
            b.setId_dct_programa_analitico(IDProgrmaAnalitico);
            int idContenido = programaAnaliticoDao.RegistrarObjetivo_Instructivo(b);
            b.setId_prg_a_contenido(idContenido);
            contenidoanalitcos.add(b);
        }
        List<Contenidos> contenidonuevo = this.programaAnaliticoDao.GetListaCopiarContenidos(IDProgrmaAnalitico);
        for (Contenidos b : contenidoanalitcos) {
            List<FormasDistribucion> listarformas = programaAnaliticoDao.getListarFormasOrganizacionDistribucionCopiar(b.getId_prg_a_contenido_ant());
            for (FormasDistribucion distribucion : listarformas) {
                Integer idContenido = contenidonuevo.stream().filter(p -> p.getContenido().equals(b.getContenido())).findFirst().get().getId_prg_a_contenido();
                distribucion.setId_dct_programa_analitico(IDProgrmaAnalitico);
                distribucion.setId_prg_a_contenido(idContenido);
                programaAnaliticoDao.insertarDistribucion(distribucion);
            }
        }
        return IDProgrmaAnalitico;
    }

    @Override
    public Boolean esMateriaModalidad(Integer id_materia) {
        List<Integer> materias = materiasDao.getMateriasModalidad(id_materia);
        return !materias.isEmpty();
    }

}