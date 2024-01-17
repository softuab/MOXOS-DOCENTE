package com.moxos.uab.mybatis.dao;

import java.util.List;

import com.moxos.uab.mybatis.entity.*;
import org.springframework.dao.DataAccessException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NotasDao {

    // Mover notas
    Notas getMtcMoverNoMatriculados(Notas nota) throws DataAccessException;

    Notas getMtcMoverMatriculados(Notas nota) throws DataAccessException;

    // Fin mover notas
    // Reporte acta de calificaciones
    List<Notas> getListarActaCalificaciones(Notas nota) throws DataAccessException;

    // Fin Reporte acta de calificaciones
    // Reporte acta de calificaciones PRECIERRE
    List<Notas> getListarActaCalificacionesPreCierre(Notas nota) throws DataAccessException;
    // Fin Reporte acta de calificaciones PRECIERRE

    // Reporte acta de calificaciones Por Fase
    List<Notas> getListarActaCalificacionesPorFase(Notas nota) throws DataAccessException;
    // Fin Reporte acta de calificaciones Por Fase

    // INICIO - Impresion de Certificado de Notas
    List<Libretas> getListarCertificadoNotasTodas(Estudiantes estudiante) throws DataAccessException;

    List<Libretas> getListarCertificadoNotasNivel(Estudiantes estudiante) throws DataAccessException;

    List<Libretas> getListarCertificadoNotasAprobadas(Estudiantes estudiante) throws DataAccessException;
    // FIN - Impresion de Certificado de Notas

    // INICIO2 - Impresion de Certificado de Notas
    List<Libretas> getListarCertificadoNotasTodas2(Estudiantes estudiante) throws DataAccessException;

    List<Libretas> getListarCertificadoNotasAprobadas2(Estudiantes estudiante) throws DataAccessException;

    List<Libretas> getListarCertificadoNotasTodas3(Estudiantes estudiante) throws DataAccessException;

    List<Libretas> getListarCertificadoNotasAprobadas3(Estudiantes estudiante) throws DataAccessException;

    List<Libretas> getListarHistorialAcademico(Estudiantes estudiante) throws DataAccessException;

    // FIN - Impresion de Certificado de Notas

    // INICIO - Rectificacion de notas
    List<Notas> getListarNotasRectificar(Notas nota) throws DataAccessException;

    Notas getBuscarNota(Notas nota) throws DataAccessException;

    int setRegistrarRectificacion(Notas nota) throws DataAccessException;

    int setRegistrarRectificacionNota(Notas nota) throws DataAccessException;
    // FIN - Rectificacion de notas

    Notas getEstListarFichaAcademicaBuscar(Notas nota) throws DataAccessException;

    Notas getEstListarFichaAcademicaBuscarBuscarAnulada(Notas nota) throws DataAccessException;

    // INICIO - Historial Academico
    List<Notas> getListarPlanMateriasNotas(Estudiantes estudiante) throws DataAccessException;

    List<Notas> getListarPlanMateriasNotas2(Estudiantes estudiante) throws DataAccessException;

    List<Notas> getListarPlanMateriasNotas3(Estudiantes estudiante) throws DataAccessException;

    double getBuscarPromedioDeNotas(Notas nota) throws DataAccessException;

    int getCantidadMateriasAprobadas(Estudiantes estudiante) throws DataAccessException;

    // FIN - Historial Academico
    List<GrupoDefinicion> getDefinicionNotasPorPrograma(Materias materias) throws DataAccessException;
}