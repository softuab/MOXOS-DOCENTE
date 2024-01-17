package com.moxos.uab.mybatis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import com.moxos.uab.mybatis.entity.Personas;
import com.moxos.uab.mybatis.entity.Estudiantes;
import com.moxos.uab.mybatis.entity.Planes;
import com.moxos.uab.mybatis.entity.Menciones;
import com.moxos.uab.mybatis.entity.Notas;
import com.moxos.uab.mybatis.entity.Perfiles;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EstudiantesDao {

    int getMiEstListarCompromisosCant(Estudiantes estudiante) throws DataAccessException;

    // CRCB est_ prgramaciones
    Estudiantes getEstBuscarEstudiante(Estudiantes estudiante) throws DataAccessException;

    Estudiantes getEstBuscarEstudiantePrograma(Estudiantes estudiante) throws DataAccessException;

    Estudiantes getEstBuscarEstudianteAccesos(Estudiantes estudiante) throws DataAccessException;

    List<Estudiantes> getEstListarEstudiantesNombres(Estudiantes estudiante) throws DataAccessException;

    List<Estudiantes> getEstListarEstudiantesDip(Estudiantes estudiante) throws DataAccessException;

    List<Estudiantes> getEstListarEstudiantesNombresAccesos(Estudiantes estudiante) throws DataAccessException;

    List<Estudiantes> getEstListarEstudiantesDipAccesos(Estudiantes estudiante) throws DataAccessException;

    List<Estudiantes> getEstListarEstudiantesNombres2(Estudiantes estudiante) throws DataAccessException;

    List<Estudiantes> getEstListarEstudiantesDip2(Estudiantes estudiante) throws DataAccessException;

    // programaciones como estudiante
    Estudiantes getComprobarEstudiante(Estudiantes estudiante) throws DataAccessException;
    // fin programaciones como estudiante

    // Cambio Pin Estudiante
    Estudiantes getMtrBuscarMatricula(Estudiantes estudiante) throws DataAccessException;

    Estudiantes getMtrBuscarMatriculaNuevo(Estudiantes estudiante) throws DataAccessException;

    Estudiantes getEstBuscarEstudianteTipoGrado(Estudiantes estudiante) throws DataAccessException;

    int setMtrModificarPinEstudiante(Estudiantes estudiante) throws DataAccessException;

    // MOSTRAR año de ingreso a la UAB
    Estudiantes getListarIngresoUAB(Estudiantes estudiante) throws DataAccessException;

    // Registar Estudiante
    int setRegistrarEstudiante(Estudiantes estudiante) throws DataAccessException;

    int setModificarEstudiante(Estudiantes estudiante) throws DataAccessException;

    Estudiantes getEstBuscarEstudiantePrs(Estudiantes estudiante) throws DataAccessException;

    Estudiantes getEstBuscarEstudiantePrsSede(Estudiantes estudiante) throws DataAccessException;

    Estudiantes getEstBuscarEstudiantePrsPos(Estudiantes estudiante) throws DataAccessException;

    Estudiantes getEstBuscarEstudiantePrsPre(Estudiantes estudiante) throws DataAccessException;

    Estudiantes getEstBuscarEstudiantePrsPreSede(Estudiantes estudiante) throws DataAccessException;

    int setRegistrarMatriculaEstudiante(Estudiantes estudiante) throws DataAccessException;

    Estudiantes getBuscarMatriculaEstPrs(Estudiantes estudiante) throws DataAccessException;

    // Fin Reg. Estudiante
    // INICIO - Ver Ficha Academica
    Estudiantes getEstBuscarEstudianteNombres(Estudiantes estudiante) throws DataAccessException;

    Estudiantes getEstBuscarEstudianteNombresSede(Estudiantes estudiante) throws DataAccessException;

    List<Notas> getEstListarFichaAcademica(Estudiantes estudiante) throws DataAccessException;

    List<Notas> getEstListarFichaAcademicaModificar(Estudiantes estudiante) throws DataAccessException;

    List<Notas> getEstListarFichaAcademicaConvalidada(Estudiantes estudiante) throws DataAccessException;

    List<Estudiantes> getEstListarEstudiantesPorMateria(Estudiantes estudiante) throws DataAccessException;

    List<Notas> getEstListarFichaAcademicaConvalidada2(Estudiantes estudiante) throws DataAccessException;

    List<Notas> getEstListarFichaAcademicaAprobadas(Estudiantes estudiante) throws DataAccessException;
    // FIN - Ver Ficha Academica

    // INICIO - Ver Programacion
    List<Notas> getEstListarProgramacion(Estudiantes estudiante) throws DataAccessException;

    List<Notas> getEstListarProgramacioncv(Estudiantes estudiante) throws DataAccessException;
    // FIN - Ver Programacion

    // INICIO - Cambio de plan
    int setEstRegistrarCambioPlan(Planes plan) throws DataAccessException;
    // FIN - Cambio de plan

    // INICIO - Materias no aprobadas
    List<Notas> getEstListarMateriasNoAprobadas(Estudiantes estudiante) throws DataAccessException;
    // FIN - Materias no aprobadas

    // Mi Matricula antiguo
    List<Estudiantes> getMtrListarMatriculasEstudiante(Estudiantes estudiante) throws DataAccessException;

    // Fin Mi Matricula antiguo
    // Cambiar estado estudiante
    int setRegistrarCambiarEstadoEstudiante(Estudiantes estudiante) throws DataAccessException;

    int setRegistrarCambiarEstadoMatricula(Estudiantes estudiante) throws DataAccessException;
    // Fin Cambiar estado estudiante

    // Adjunto Docente
    int setRegistrarEstAdjuntos(Estudiantes estudiante) throws DataAccessException;

    List<Estudiantes> getListarAdjuntosEstudiante(Estudiantes estudiante) throws DataAccessException;

    int setEliminarEstAdjunto(Estudiantes estudiante) throws DataAccessException;

    // Inicio Estadisticas
    List<Estudiantes> getListarNroEstudiantesMatriculados(Estudiantes estudiante) throws DataAccessException;

    List<Estudiantes> getListarNroEstMatriculadosSexosNacionalidades(Estudiantes estudiante) throws DataAccessException;

    List<Estudiantes> getListarNroEstMatriculadosTipoEstudiante(Estudiantes estudiante) throws DataAccessException;

    List<Estudiantes> getListarNroEstMatriculadosTipoAdmision(Estudiantes estudiante) throws DataAccessException;

    List<Estudiantes> getListarNroEstMatriculadosSexos(Estudiantes estudiante) throws DataAccessException;

    List<Estudiantes> getListarNroEstMatriculadosNacionalidad(Estudiantes estudiante) throws DataAccessException;

    List<Planes> getListarNroEstProgramadosMaterias(Estudiantes estudiante) throws DataAccessException;

    List<Estudiantes> getListarNroEstProgramadosSexosNacionalidades(Estudiantes estudiante) throws DataAccessException;

    List<Estudiantes> getListarNroPostProgramadosMaterias(Estudiantes estudiante) throws DataAccessException;

    List<Estudiantes> getListarNroEstProgAprReprAbaMaterias(Estudiantes estudiante) throws DataAccessException;

    List<Estudiantes> getListarNroEstAproPreU(Estudiantes estudiante) throws DataAccessException;

    List<Estudiantes> getListarNroEstAproAdmiEsp(Estudiantes estudiante) throws DataAccessException;

    List<Estudiantes> getListarNroEstAproPreUSexosNacionalidad(Estudiantes estudiante) throws DataAccessException;

    List<Estudiantes> getListarNroEstAproAdEspSexosNacionalidad(Estudiantes estudiante) throws DataAccessException;
    // Fin Estadisticas

    // Reportes Edgar
    // listar Datos detalle de programacion
    List<Notas> getEstListarDetalleProgramacion(Estudiantes estudiante) throws DataAccessException;
    // FIN - listar datos detalle de programacion

    // listar Estudiantes por Grupos
    List<Estudiantes> getEstListarEstudiantesPorGrupos(Estudiantes estudiante) throws DataAccessException;
    // FIN - listar Estudiantes por Grupos

    // Bloquear estudiantes todos
    int setBloquearEstudiantesTodos(Estudiantes estudiante) throws DataAccessException;
    // Fin bloquear estudiantes todos

    // Modificar Tipo Estudiante
    int setModificarTipoEstudiante(Estudiantes estudiante) throws DataAccessException;

    // Fin Modificar Tipo Estudiante
    List<Personas> getMiEstListarCompromisos(Estudiantes estudiante) throws DataAccessException;

    // Registrar est_programacion
    int setRegistrarEstProgramacionTipo(Estudiantes estudiante) throws DataAccessException;
    // Fin est_programacion

    Estudiantes getMiPrsBuscarEstudiante(Estudiantes estudiante) throws DataAccessException;

    // inicio JOJO
    List<Perfiles> getTrnListarMateriasVerano(Estudiantes estudiante) throws DataAccessException;
    // fin JOJO

    List<Estudiantes> getEstListarProgramasEstudiante(Estudiantes estudiante) throws DataAccessException;

    List<Estudiantes> getEstListarMatriculadosPorPrograma(Estudiantes estudiante) throws DataAccessException;

    List<Estudiantes> getEstListarMatriculadosPorProgramaTipoAdmision(Estudiantes estudiante) throws DataAccessException;

    // Buscar est_programacion
    Estudiantes getMiBuscarEstProgramacion(Estudiantes estudiante) throws DataAccessException;

    // Listado de Estudiantes Matriculados
    List<Estudiantes> getEstListarMatriculadosPorProgramaTipoEstudiante(Estudiantes estudiante)
            throws DataAccessException;

    // Buscar tipos Admisiones
    Estudiantes getBuscarTipoAdmision(Estudiantes estudiante) throws DataAccessException;

    // Est_clasificaciones
    Estudiantes getBuscarTipoClasificacionEstudiante(Estudiantes estudiante) throws DataAccessException;

    int setRegistrarEstClasificacion(Estudiantes estudiante) throws DataAccessException;

    // Est_regularizaciones
    Estudiantes getMiBuscarUltimoEstRegularizacion(Estudiantes estudiante) throws DataAccessException;

    Estudiantes getMiBuscarEstRegularizacion(Estudiantes estudiante) throws DataAccessException;

    List<Estudiantes> getMiListarRegularizacionesEstudiante(Estudiantes estudiante) throws DataAccessException;

    int setRegistrarEstRegularizacionBloqueoEst(Estudiantes estudiante) throws DataAccessException;

    int setModificarRegularizar(Estudiantes estudiante) throws DataAccessException;

    List<Estudiantes> getMiListarTiposRegularizaciones() throws DataAccessException;
    // Fin Est_regularizaciones

    Menciones getEstBuscarMencion(Estudiantes estudiante) throws DataAccessException;

    // Inicio est_deudas
    List<Estudiantes> getListarDeudasEstudiante(Estudiantes estudiante) throws DataAccessException;

    Estudiantes getMiBuscarEstDeuda(Estudiantes estudiante) throws DataAccessException;

    Estudiantes getBuscarUltimaEstDeuda(Estudiantes estudiante) throws DataAccessException;

    int setRegistrarEstDeuda(Estudiantes estudiante) throws DataAccessException;

    int setModificarEstDeuda(Estudiantes estudiante) throws DataAccessException;

    List<Estudiantes> getMiListarTiposDeudas() throws DataAccessException;

    // Fin est_deudas
    // Inicio cambio PIN Estudiante General
    int setMtrModificarApodoClaveEstudiante(Estudiantes estudiante) throws DataAccessException;

    int setRegistrarApodoClaveMatricula(Estudiantes estudiante) throws DataAccessException;
    // Fin cambio PIN Estudiante General

    // INICIO - METODOS ADICIONADOS POR LA UAP
    // Listado de Estudiantes Con Descuentos
    List<Estudiantes> getEstListarPorProgramaTipoDescuento(Estudiantes estudiante) throws DataAccessException;

    // Listado de Estudiante Con Prorroga
    List<Estudiantes> getListarEstConProrroga(Estudiantes estudiante) throws DataAccessException;

    // Listado de Estudiante Con Rendimiento Academico
    List<Estudiantes> getRendimientoAcademico(Estudiantes promedio) throws DataAccessException;

    // Becas Trabajo
    Estudiantes getDesignacionBecaTrabajo(Estudiantes becario) throws DataAccessException;

    // Listado de Becarios
    List<Estudiantes> getListarEstBecasTrabajo(Estudiantes estudiante) throws DataAccessException;

    // Listado de Becarios por Unidad Funcional
    List<Estudiantes> getListarEstBecasTrabajoFuncional(Estudiantes estudiante) throws DataAccessException;

    // Listado de Estudiantes por Nivel Academico
    List<Estudiantes> getListarNiveles(Estudiantes estudiante) throws DataAccessException;

    // Listado de Claves de Estudiantes por Programas
    List<Estudiantes> getListarClavesEstPorPrograma(Estudiantes estudiante) throws DataAccessException;

    // Listado de Curso de Preparatoria de Ingles
    List<Estudiantes> getListarCursoPreIngles(Estudiantes estudiante) throws DataAccessException;

    // Admision auxiliares de docencia
    Estudiantes getEstBuscarEstudianteNombresMatriculados(Estudiantes estudiante) throws DataAccessException;

    Estudiantes getEstBuscarEstudianteAdmitidoAuxiliar(Estudiantes estudiante) throws DataAccessException;

    int setRegistrarAdmisionEstudianteAuxiliar(Estudiantes estudiante) throws DataAccessException;

    int setEliminarAdmisionEstudianteAuxiliar(Estudiantes estudiante) throws DataAccessException;

    List<Estudiantes> getBuscarEstudianteAuxiliar(Estudiantes estudiante) throws DataAccessException;

    List<Estudiantes> getBuscarEstudianteAuxiliarTodas(Estudiantes estudiante) throws DataAccessException;

    // REPORTES DE ESTUDIANTES DOCENCIA POR PROGRAMA
    List<Estudiantes> getListarEstudiantesAuxiliaresPorPrograma(Estudiantes estudiante) throws DataAccessException;

    List<Estudiantes> getBuscarEstudiantesAuxiliaresPorPrograma(Estudiantes estudiante) throws DataAccessException;

    // TARJETAS MAGNETICAS
    List<Estudiantes> getBuscarEstudiantePersona(Estudiantes estudiante) throws DataAccessException;

    // Listado de Curso Varios Extracurricular en la UAP
    List<Estudiantes> getListarCursoPsicoEst(Estudiantes estudiante) throws DataAccessException;

    List<Estudiantes> getListarCursoSemioEst(Estudiantes estudiante) throws DataAccessException;

    List<Estudiantes> getlistarMiembrosT(Estudiantes estudiante) throws DataAccessException;

    // Cambiar CODE
    int setRegistrarCerGen(Estudiantes estudiante) throws DataAccessException;

    int getBuscarMaxCertSede(Estudiantes estudiante) throws DataAccessException;

    int setRegistrarCerGenNotas(Estudiantes estudiante) throws DataAccessException;

    List<Estudiantes> getListarCertGen(Estudiantes estudiante) throws DataAccessException;

    List<Estudiantes> getListarCertGenAnulados(Estudiantes estudiante) throws DataAccessException;

    List<Estudiantes> getListarCertGenEmitidos(Estudiantes estudiante) throws DataAccessException;

    List<Estudiantes> getListarNotasCertificados(Estudiantes estudiante) throws DataAccessException;

    void setEliminarCertificadoNotas(Estudiantes estudiante) throws DataAccessException;

    int getbuscarnrotransacciones(Estudiantes estudiante) throws DataAccessException;

    int getbuscarnrocertificado(Estudiantes estudiante) throws DataAccessException;

    int getcert_buscar_nro_certificado_gestioncode(Estudiantes estudiante) throws DataAccessException;
    // FIN - METODOS ADICIONADOS POR LA UAP

}
