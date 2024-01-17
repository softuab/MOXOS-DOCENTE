package com.moxos.uab.mybatis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.moxos.uab.mybatis.entity.Estudiantes;
import com.moxos.uab.mybatis.entity.Libretas;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LibretasDao {

    //Administrar libretas
    Libretas getLbrBuscarFase(Libretas libreta) throws DataAccessException;

    int getLbrBuscarFaseMinima(Libretas libreta) throws DataAccessException;

    int getLbrBuscarFaseMaxima(Libretas libreta) throws DataAccessException;

    List<Libretas> getGrpListarEvaluacionDefinida(Libretas libreta);

    Libretas getLbrBuscarTipoNota(Libretas libreta) throws DataAccessException;

    List<Libretas> getEstBuscarEstudiantesProgramados(Libretas libreta);

    List<Libretas> getEstBuscarEstudiantesProgramadospersona(Libretas libreta);

    List<Libretas> getPstBuscarPostulantesProgramados(Libretas libreta);

    List<Libretas> getPstBuscarPostulantesProgramadospersona(Libretas libreta);

    List<Libretas> getEstListarNotasEstudiante(Libretas libreta);

    List<Libretas> getEstListarNotasEstudianteLibreta(Libretas libreta);

    List<Libretas> getEstListarNotasEstudianteLibretaSegunda(Libretas libreta);

    List<Libretas> getEstListarNotasLibreta(Libretas libreta);

    List<Libretas> getEstListarNotasEstudiantePermitidoModificar(Libretas libreta);

    List<Libretas> getPstListarNotasPostulante(Libretas libreta);

    int setEstInsertarNotaEstudianteFase(Libretas libreta) throws DataAccessException;

    int setPstInsertarNotaPostulanteFase(Libretas libreta) throws DataAccessException;

    int setDctAvanzarFase(Libretas libreta) throws DataAccessException;

    int setDctAvanzarFaseSemiFinal(Libretas libreta) throws DataAccessException;

    int getEstSumarNotasEstudianteEvalRegular(Libretas libreta) throws DataAccessException;

    int getEstSumarNotasEstudianteEvalContinua(Libretas libreta) throws DataAccessException;
    //Fin Administrar libretas

    //Definir evaluacion
    List<Libretas> getLbrListarTiposNotasDefinidas(Libretas libreta) throws DataAccessException;

    List<Libretas> getLbrListarTiposNotas(Libretas libreta) throws DataAccessException;

    Libretas getLbrBuscarTipoNotaDefinida(Libretas libreta) throws DataAccessException;

    int setGrpInsertarEvaluacion(Libretas libreta) throws DataAccessException;

    int setGrpModificarEvaluacion(Libretas libreta) throws DataAccessException;

    int setGrpRegistrarEvaluacion(Libretas libreta) throws DataAccessException;

    int setGrpEliminarEvaluacion(Libretas libreta) throws DataAccessException;
    //Fin Definir evaluacion

    //Cerrar libreta
    List<Libretas> getListarMateriasCerrarLibreta(Libretas libreta) throws DataAccessException;

    int setCerrarLibreta(Libretas libreta) throws DataAccessException;
    //Fin Cerrar libreta

    //Administrar lbr_tipos_notas
    List<Libretas> getTpsListarTiposEvaluaciones() throws DataAccessException;

    List<Libretas> getListaParametrosdeEvaluacionporMateria(Libretas libreta) throws DataAccessException;

    List<Libretas> getTpsListarTiposEstados() throws DataAccessException;

    Libretas getTpsBuscarTipoEvaluacion(Libretas libreta) throws DataAccessException;

    List<Libretas> getLbrListarFases(Libretas libreta) throws DataAccessException;

    List<Libretas> getLbrListarFases2(Libretas libreta) throws DataAccessException;

    List<Libretas> getLbrListarTiposNotasFase(Libretas libreta) throws DataAccessException;

    int setLbrInsertarTipoNota(Libretas libreta) throws DataAccessException;

    int setLbrModificarTipoNota(Libretas libreta) throws DataAccessException;
    //Fin administrar lbr tipos notas

    //Reporte de libretas
    List<Libretas> getListarNotasFaseEstudiantes(Libretas libreta);
    //Fin reporte de libretas

    //Reporte resumen de Notas
    List<Libretas> getListarResumenNotasEstudiantes(Libretas libreta);
    //Fin reporte de notas

    //Cerrar libreta Por Materia
    List<Libretas> getListarMateriasCerrarLibretaIndiv(Libretas libreta) throws DataAccessException;

    int setCerrarLibretaPorMateria(Libretas libreta) throws DataAccessException;
    //Fin Cerrar libreta Por Materia

    //Edgar
    //Reporte certificado de calificaciones
    List<Libretas> getListarCerticadoCalificaciones(Libretas libreta);
    //Fin reporte certificado de calificaciones

    //listar detalle de materia notas contnua
    List<Libretas> getEstListarNotasEvaluacionContinua(Libretas libreta);
    //fin listar detalle de materia notas contnua

    //listar evaluacion contunua estudiantes
    List<Libretas> getListarEstudiantesEvaluacionContinua(Libretas libreta);
    //fin listar evaluacion continua estudiantes

    //listar fase tipos notas de la definicion evaluacion
    List<Libretas> getLbrTiposnotasListarDefinicion(Libretas libreta);
    //listar fase tipos notas de la definicion evaluacion 

    //RETROCEDER FASE
    // modificar fase del docente
    int setModificarFaseDocente(Libretas libreta) throws DataAccessException;

    int setModificarFaseDocenteCerrarLibreta(Libretas libreta) throws DataAccessException;
    //Fin modificar fase del docente

    // eliminar fase calculado
    int setEliminarFaseEstLibretas(Libretas libreta) throws DataAccessException;
    //Fin eliminar fase calculado
    //FIN RETROCEDER FASE

    //Curso Verano
    List<Libretas> getTrnListarEvaluacionesVerano() throws DataAccessException;
    //Fin curso verano

    //listar fase tipos notas de la definicion evaluacion
    Libretas getBuscarGradoAcademicoPrograma(Libretas libreta);
    //listar fase tipos notas de la definicion evaluacion 
    //Listar tipos notas

    List<Libretas> getListarTiposNotas() throws DataAccessException;

    //Buscar Lbr Fases
    Libretas getBuscarLbrFase(Libretas libreta);

    Libretas getBuscarLbrTipoNota(Libretas libreta);

    int setLbrRegistrarTipoNota(Libretas libreta) throws DataAccessException;

    int setLbrEliminarTipoNota(Libretas libreta) throws DataAccessException;

    //Listar estudiantes cierre libreta
    List<Libretas> getListarEstudiantesParaCierreLibreta(Libretas libreta);

    List<Libretas> getListarEstudiantesEnEstLibretas(Libretas libreta);

    List<Libretas> getListarEvaluacionesFinalesFase(Libretas libreta);

    List<Estudiantes> getTotalAprobadosReprobadosMateria(Libretas libreta);

    //Listar Notas pondradas est_libretas
    List<Libretas> getListarNotasEstudiantesLibretas(Libretas libreta);

    List<Libretas> getListarCalificacionCalendario(Libretas libreta);

    List<Libretas> getListarCalificacionCalendarioDocente(Libretas libreta);
    //Buscar Tipo Nota

    Libretas getMiBuscarTipoNota(Libretas libreta) throws DataAccessException;

    //INICIO - METODOS ADICIONADOS POR LA UAP
    //NOTAS RECTIFICADAS
    List<Libretas> getEstListarNotasRectificadasEstudiante(Libretas libreta);
    //FIN - METODOS ADICIONADOS POR LA UAP

    int setBuscarCalendarioAcademicoPrograma(Libretas libreta) throws DataAccessException;

    int setBuscarCalendarioAcademicoProgramaDocenteMateria(Libretas libreta) throws DataAccessException;

    int setBuscarProgramacionAutorizacion(Libretas libreta) throws DataAccessException;

    int setCambioEstadoProgramacionAutorizacion(Libretas libreta) throws DataAccessException;

    Libretas getNotasEstudiante(Libretas libreta) throws DataAccessException;

    void RegistrarBitacoraCambiosDocente(Libretas libreta) throws DataAccessException;

    List<Libretas> getCalendarioAcademicoExcepciones(Libretas libreta) throws DataAccessException;

    List<Libretas> getCalendarioAcademicoExcepcionesEstudiante(Libretas libreta) throws DataAccessException;

    List<Libretas> getDetalleNotaLibretaMateria(Libretas libreta) throws DataAccessException;

    List<Libretas> getPlanillaProgramadosMateria(Libretas libreta) throws DataAccessException;

    List<Libretas> getNotasdeMatriculados(Libretas libreta) throws DataAccessException;

}
