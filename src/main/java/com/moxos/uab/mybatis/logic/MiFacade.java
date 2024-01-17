package com.moxos.uab.mybatis.logic;

import java.util.List;

import com.moxos.uab.model.models.Kardex.DetalleFormacionAcademicaPersonaModel;
import com.moxos.uab.model.models.Kardex.DetalleIdiomaPersonaModel;
import com.moxos.uab.model.models.Kardex.DetallePersonaModalidadIngresoModel;
import com.moxos.uab.mybatis.entity.*;

public interface MiFacade {
    // Clientes
    Clientes getBuscarConexion(Usuarios usuario);

    Clientes getBuscarConexion2FA(Usuarios usuario);

    List<Docentes> getDetallefotoadjunto(Docentes docente);

    Integer setRegistrarDocenteAdjuntos(Docentes docente);

    Integer setCambioPinDocente(Docentes docente);

    Integer setActualizarDocenteAdjuntos(Docentes docente);

    void bloquearUsuario(Clientes cliente);

    void guardarEvento(Auditoria auditoria);

    List<Docentes> GetListaNotificacionDocente(ParametrosBusqueda buscar);

    CursosMoodle getUsuarioMoodle(int id);

    List<Enlaces> getListarEnlaces(Enlaces enlace);

    Personas getPrsBuscarPersonaDocente(int id_docente);

    List<Asignaciones> getDctListarAsignacionDocente(Asignaciones asignacion);

    List<Asignaciones> getMtrListarMateriaAhorro(Asignaciones asignacion);

    MoodleConfiguracion getConfiguracionCursosMoodle();

    void registrarUsuariosMoodle(CursosMoodle cursos);

    void updateUserMoodle(CursosMoodle cursos);

    void actualizarUsuariosMoodle(CursosMoodle cursos);

    void matricularMoodle(CursosMoodle cursos);

    List<CursosMoodle> getListarCursosMoodleDocente(ParametrosBusqueda parametros);

    void registrarCursosMoodleDocente(CursosMoodle cursos);

    List<CursosMoodle> getListarCursosMoodleEstudiantePorCurso(ParametrosBusqueda parametros);

    Asignaciones getDctBuscarAsignacionDocente(Asignaciones asignacion);

    Materias getMtrBuscarMateria(Materias materia);

    Libretas getLbrBuscarFase(Libretas libreta);

    List<Libretas> getEstBuscarEstudiantesProgramadospersona(Libretas libreta);

    void registrarCursosMoodleEstudiante(CursosMoodle cursos);

    CursosMoodle getCursoMoodleEstudiante(ParametrosBusqueda parametros);

    Programas getPrgBuscarPrograma(Programas programa);

    List<Libretas> getLbrListarTiposNotas(Libretas libreta);

    boolean existeUsuario(String correo);

    List<Docentes> getBuscarListaDocentesCorreo(String correo);

    Integer setGrpEliminarEvaluacion(Libretas libreta);

    Integer setGrpRegistrarEvaluacion(Libretas libreta);

    List<Libretas> getGrpListarEvaluacionDefinida(Libretas libreta);

    Libretas getLbrBuscarTipoNota(Libretas libreta);

    List<Libretas> getCalendarioAcademicoExcepciones(Libretas libreta);

    List<Libretas> getDetalleNotaLibretaMateria(Libretas libreta);

    List<Libretas> getEstBuscarEstudiantesProgramados(Libretas libreta);

    List<Libretas> getEstListarNotasEstudianteLibretaSegunda(Libretas libreta);

    List<Programas> getEstListarNotaMinimaporPrograma(Programas programa);

    Integer setEstInsertarNotaEstudianteFase(Libretas libreta);

    void registrarBitacoraCambiosDocente(Libretas libreta);

    Libretas getNotasEstudiante(Libretas libreta);

    List<Libretas> getPlanillaProgramadosMateria(Libretas libreta);

    List<Libretas> getNotasdeMatriculados(Libretas libreta);

    Integer getLbrBuscarFaseMaxima(Libretas libreta);

    Integer setDctAvanzarFase(Libretas libreta);

    List<Calendarios> getlistarCalendarioDocente(Calendarios calendario);

    Asignaciones getDctBuscarAsignacionDetalleDocente(Asignaciones asignacion);

    List<Estudiantes> getEstListarEstudiantesPorMateria(Estudiantes estudiante);

    List<Libretas> getListaParametrosdeEvaluacionporMateria(Libretas libreta);

    Instituciones getBuscarInstitucion(Instituciones institucion);

    Instituciones getBuscarInstitucionSede(Instituciones institucion);

    List<Libretas> getEstListarNotasLibreta(Libretas libreta);

    PersonaKardex getKardexPersonalNuevo(int id_persona);

    void registrarNuevoKardexDocente(PersonaKardex kardex);

    List<ListViewItem> getLocalidadPersona();

    void actualizarInformacionPersonalKardexDocente(PersonaKardex kardex);

    List<Tokens> getListartokendocente(Tokens token);

    void actualizarIdentificacionPersonalKardexDocente(PersonaKardex kardex);

    PersonaKardex getImagenesPersonaKardex(int id_persona);

    void actualizarServicioMilitarPersonalKardexDocente(PersonaKardex kardex);

    List<ListViewItem> getBancos();

    void actualizarInformacionLaboralPersonalKardexDocente(PersonaKardex kardex);

    List<ListViewItem> getNivelEstudioPorNivel(String grado);

    List<ListViewItem> getColegiosProfesionales();

    List<ListViewItem> getProfesiones();

    void actualizarEducacionPregradoPersonalKardexDocente(PersonaKardex kardex);

    void actualizarEducacionPosgradoKardexDocente(PersonaKardex kardex);

    void actualizarContactoPersonalKardexDocente(PersonaKardex kardex);

    String getImageidiomas(int id_idioma);

    String getImagemodalidad(int id_modalidadingreso);

    String getImageformacionacademica(int id_formacion);

    String getImageexperiencia(int id_experiencia_laboral);

    String getImagecursosrealizados(int id_cursos_realizados);

    String getImageproduccioncientifica(int id_produccion_cientifica);

    String getImageactividadesacademicas(int id_activades_academicas);

    String getImageproyecto(int id_personas_proyecto);

    KardexPersonal getKardexPersonalDocente(int id_persona);

    List<CategoriaDocente> getPersonaCategoriaKardex(ParametrosBusqueda busqueda);

    List<PersonaCursosRealizados> getPersonaTotalCursosRealizadosKardex(int id_persona_kardex);

    List<DetallePersonaExperienciaLaboral> getPersonaTotalExperienciaLaboralKardex(int id_persona_kardex);

    List<DetalleFormacionAcademicaPersonal> getPersonaTotalFormacionAcademicaKardex(int id_persona_kardex);

    List<DetallePersonaModalidadIngreso> getPersonaTotalModalidadKardex(int id_persona_kardex);

    List<PersonaProduccionCientifica> getPersonaTotalProduccionCientificaKardex(int id_persona_kardex);

    List<DetalleIdiomaPersonal> getPersonaTotalIdiomaKardex(int id_persona);

    List<PersonaEvaluacionDocente> getPersonaTotalEvaluacionDocenteKardex(int id_persona_kardex);

    List<PersonaActividadesAcademicas> getPersonaSubTotalActividadesAcademicasKardex(PersonaKardex kardex);

    List<PersonaActividadesAcademicas> getPersonaTotalActividadesAcademicasKardex(int id_persona_kardex);

    PersonaActividadesAcademicas getPersonaActividadesAcademicasKardex(int id_activades_academicas);

    int registrarNuevoActividadesAcademicasKardex(PersonaActividadesAcademicas actividades);

    void actualizarImagenActividadesAcademicasKardexDocente(PersonaActividadesAcademicas actividades);

    void actualizarDatosiActividadesAcademicasKardexDocente(PersonaActividadesAcademicas actividades);

    void eliminarActividadesAcademicasKardexDocente(int id_activades_academicas);

    void aprobarActividadesAcademicasKardexDocente(PersonaActividadesAcademicas actividades);

    List<PersonaCursosRealizados> getPersonaSubTotalCursosRealizadosKardex(PersonaKardex kardex);

    PersonaCursosRealizados getPersonaCursosRealizadosKardex(int id_cursos_realizados);

    int registrarCursosRealizadosKardex(PersonaCursosRealizados cursos);

    void actualizarImagenCursosRealizadosKardexDocente(PersonaCursosRealizados cursos);

    void actualizarDatosCursosRealizadosKardexDocente(PersonaCursosRealizados cursos);

    void eliminarCursosRealizadosKardexDocente(int id_cursos_realizados);

    void aprobarCursosRealizadosKardexDocente(PersonaCursosRealizados cursos);

    List<PersonaEvaluacionDocente> getPersonaSubTotalEvaluacionDocenteKardex(PersonaKardex kardex);

    PersonaEvaluacionDocente getPersonaEvaluacionDocenteKardex(int id_evaluacion_docente);

    int registrarEvaluacionDocenteKardex(PersonaEvaluacionDocente evaluacion);

    void actualizarImagenEvaluacionDocenteKardex(PersonaEvaluacionDocente evaluacion);

    void actualizarEvaluacionDocenteKardex(PersonaEvaluacionDocente evaluacion);

    void eliminarEvaluacionDocenteKardex(int id_evaluacion_docente);

    void aprobarEvaluacionDocenteKardex(PersonaEvaluacionDocente evaluacion);

    PersonaExperienciaLaboral getPersonaExperienciaLaboralKardex(int id_experiencia_laboral);

    List<DetallePersonaExperienciaLaboral> getPersonaSubTotalExperienciaLaboralKardex(PersonaKardex kardex);

    void actualizarDatosExperienciaLaboralKardexDocente(PersonaExperienciaLaboral experiencia);

    void aprobarExperienciaLaboralKardexDocente(PersonaExperienciaLaboral experiencia);

    void eliminarExperienciaLaboralKardexDocente(int id_experiencia_laboral);

    void actualizarImagenExperienciaLaboralKardexDocente(PersonaExperienciaLaboral experiencia);

    int registrarExperienciaLaboralKardex(PersonaExperienciaLaboral experiencia);

    PersonaFormacionAcademica getPersonaFormacionAcademicaKardex(int id_formacion);

    int registrarNuevoFormacionAcademicaKardex(PersonaFormacionAcademica formacion);

    void actualizarDatosFormacionAcademicaKardexDocente(PersonaFormacionAcademica formacion);

    void aprobarFormacionAcademicaKardexDocente(PersonaFormacionAcademica formacion);

    void eliminarFormacionAcademicaKardexDocente(int id_formacion);

    void actualizarImagenFormacionAcademicaKardexDocente(PersonaFormacionAcademica formacion);

    List<DetalleFormacionAcademicaPersonal> getPersonaSubTotalFormacionAcademicaKardex(PersonaKardex kardex);

    List<ListViewItem> getProgramasPregrado();

    void eliminarIdiomaKardexDocente(int id_idioma);

    void aprobarIdiomaKardexDocente(PersonaIdioma idioma);

    void actualizarDatosiIdiomaKardexDocente(PersonaIdioma idioma);

    int registrarNuevoIdiomaKardex(PersonaIdioma idioma);

    void actualizarImagenIdiomaKardexDocente(PersonaIdioma idioma);

    PersonaIdioma getPersonaIdiomaKardex(int id_idioma);

    List<DetalleIdiomaPersonal> getPersonaSubTotalIdiomaKardex(PersonaKardex kardex);

    int registrarNuevoModalidadKardex(PersonaModalidadIngreso modalidad);

    void actualizarDatosiModalidadKardexDocente(PersonaModalidadIngreso modalidad);

    void aprobarModalidadKardexDocente(PersonaModalidadIngreso modalidad);

    void eliminarModalidadKardexDocente(int id_modalidadingreso);

    void actualizarImagenModalidadKardexDocente(PersonaModalidadIngreso modalidad);

    PersonaModalidadIngreso getPersonaModalidadKardex(int id_modalidadingreso);

    List<DetallePersonaModalidadIngreso> getPersonaSubTotalModalidadKardex(PersonaKardex kardex);

    int registrarNuevoProyectoKardexPersona(PersonaProyectoDocente proyecto);

    void actualizarDatosProyectoKardexDocente(PersonaProyectoDocente proyecto);

    void aprobarProyectoKardexDocente(PersonaProyectoDocente proyecto);

    void eliminarProyectoKardexDocente(int id_personas_proyecto);

    List<PersonaProyectoDocente> getPersonaTotalProyectoKardex(int id_persona_kardex);

    List<PersonaProyectoDocente> getPersonaSubTotalProyectoKardex(PersonaKardex kardex);

    void actualizarImagenProyectoKardexDocente(PersonaProyectoDocente proyecto);

    PersonaProyectoDocente getPersonaProyectoKardex(int id_personas_proyecto);

    int registrarProduccionCientificaKardex(PersonaProduccionCientifica produccion);

    void actualizarProduccionCientificaKardexDocente(PersonaProduccionCientifica produccion);

    void aprobarProduccionCientificaKardexDocente(PersonaProduccionCientifica produccion);

    void eliminarProduccionCientificaKardexDocente(int id_produccion_cientifica);

    void actualizarImagenProduccionCientificaKardexDocente(PersonaProduccionCientifica produccion);

    PersonaProduccionCientifica getPersonaProduccionCientificaKardex(int id_produccion_cientifica);

    List<PersonaProduccionCientifica> getPersonaSubTotalProduccionCientificaKardex(PersonaKardex kardex);

    List<Asignaciones> getDctListarAsignacionDocenteporProgramaAnalitico(Asignaciones asignacion);

    Integer permitirRegistroPrograma(ProgramaAnalitico programaAnalitico);

    List<ProgramaAnalitico> getListaProgramaanalitico(ProgramaAnalitico programaAnalitico);

    List<ListViewItem> getListarProgramasAnaliticosPorMateria(ParametrosBusqueda busqueda);

    List<ProgramaAnalitico> getListarDatosCaratula(int id_asignacion);

    List<ProgramaAnalitico> getListarMateriaProgramaAnalitico(int id_asignacion);

    Docentes getBuscarDocente(Docentes docente);

    List<ProgramaAnalitico> getListarPrerequisitoMateria(Planes plan);

    ProgramaAnalitico getProgramaanalitico(Integer id_dct_programa_analitico);

    int registrarProgromaAnalitico(ProgramaAnalitico programaAnalitico);

    int actualizarProgramaAnalitico(ProgramaAnalitico programaAnalitico);

    List<Contenidos> getListarContenido(Contenidos contenidos);

    ProgramaAnalitico getDetalleProgramaAnalitico(int id_dct_programa_analitico);

    int actualizarObjetivo_Instructivo(Contenidos contenidos);

    int registrarObjetivo_Instructivo(Contenidos contenidos);

    int eliminarObjetivo_Instructivo(Contenidos contenidos);

    Contenidos getContenido(Integer id_prg_a_contenido);

    List<FormasOrganizacionDistribucion> getListarFormasOrganizacionDistribucion(Integer id_dct_programa_analitico);

    List<FormasOrganizacion> getListaFormasPorTipo(String tipo_forma);

    List<FormasOrganizacionDistribucion> getListarFormasPorDistribucion(Integer id_dct_programa_analitico);

    Integer getidDistribucion(FormasDistribucion distribucion);

    Integer getTotalHorasdistribucion(Integer id_dct_programa_analitico);

    void insertarDistribucion(FormasDistribucion distribucion);

    void actualizarHorasDistribucion(FormasOrganizacionDistribucion distribucion);

    void eliminarDistribucion(Integer id_prg_a_distribucion);

    HorasMaterias getCantidadHorasAsignatura(Integer id_dct_programa_analitico);

    FormasDistribucion getFormasDistribucion(Integer id_prg_a_distribucion);

    List<BiBliografia> getListarBibliografia(BiBliografia bibliografia);

    BiBliografia getBibliografia(Integer id_prg_a_bibliografia);

    Integer registrarBibliografia(BiBliografia bibliografia);

    Integer actualizarBibliografia(BiBliografia bibliografia);

    Integer eliminarBibliografia(Integer id_prg_a_bibliografia);

    Integer actualizarCronograma(Cronograma cronograma);

    Integer registrarCronograma(Cronograma cronograma);

    Integer eliminarCronograma(Integer id_prg_a_cronograma);

    List<Cronograma> getListarCronograma(Integer id_dct_programa_analitico);

    Cronograma getCronograma(Integer id_prg_a_cronograma);

    List<ListViewItem> getListaFormasClases();

    Integer getIdProgramaAnalitico(Asignaciones asignaciones);

    ProgramaAnalitico getDatosCaratula(Integer id_dct_programa_analitico);

    ProgramaAnalitico getListarInformeProgramaAnalitico(int id_asignacion);

    List<FormasOrganizacion> getListaFormarPorProgramaYTipoPresencial(Integer id_dct_programa_analitico);

    List<FormasOrganizacion> getListaFormarPorProgramaYTipoVirtual(Integer id_dct_programa_analitico);

    Integer updateUser2FA(Docentes docentes);

    String getSecret(Integer id_docente);

    Docentes getUsuario(String apodo);

    List<GrupoDefinicion> getDefinicionNotasPorPrograma(Materias materias);

    int setGenerarToken(Tokens token);

    Docentes getUsuarioDocente(String apodo);

    List<Libretas> getCalendarioAcademicoExcepcionesEstudiante(Libretas libreta);

    int copy(ProgramaAnalitico programaAnalitico, Integer idProgramaAnaliticoAnterior);

    Boolean esMateriaModalidad(Integer id_materia);

}