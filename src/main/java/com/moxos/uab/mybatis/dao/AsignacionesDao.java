package com.moxos.uab.mybatis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.moxos.uab.mybatis.entity.Asignaciones;
import com.moxos.uab.mybatis.entity.Materias;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AsignacionesDao {

    Asignaciones getDctBuscarAsignacionDocente(Asignaciones asignacion) throws DataAccessException;

    Asignaciones getDctBuscarAsignacionDocentemaslafuncion(Asignaciones asignacion) throws DataAccessException;

    Asignaciones getDctBuscarAsignacionDocenteDesignacion(Asignaciones asignacion) throws DataAccessException;

    Asignaciones getDctBuscarAsignacionDetalleDocente(Asignaciones asignacion) throws DataAccessException;

    List<Asignaciones> getMtrBuscarMateriaAhorro(Asignaciones asignacion) throws DataAccessException;
    // Alex

    List<Asignaciones> getDctListarAsignacionDocente(Asignaciones asignacion) throws DataAccessException;

    List<Asignaciones> getMtrListarMateriaAhorro(Asignaciones asignacion) throws DataAccessException;

    Asignaciones getDctBuscarAsignacionDocenteMateria(Asignaciones asignacion) throws DataAccessException;
    // Fin Alex

    int setRegistrarAsignacionDocente(Asignaciones asignacion) throws DataAccessException;

    int setRegistrarAsignacionDocentefac(Asignaciones asignacion) throws DataAccessException;

    int setRegistrarMemo(Asignaciones asignacion) throws DataAccessException;

    int setRegistrarFaseResolucion(Asignaciones asignacion) throws DataAccessException;

    int setRegistrarFaseResolucionfac(Asignaciones asignacion) throws DataAccessException;

    int setRegistrarFaseResolucionuni(Asignaciones asignacion) throws DataAccessException;

    int setmostrarplan(Asignaciones asignacion) throws DataAccessException;

    int setRegistrarRetrocederFaseResolucion(Asignaciones asignacion) throws DataAccessException;

    Asignaciones getDctVerificarAsignacionDocenteGestion(Asignaciones asignacion) throws DataAccessException;

    List<Asignaciones> getListarDocentesProgramados(Asignaciones asignacion) throws DataAccessException;
    // para sacar el numero del siguiente memo

    int getTrnBuscarSiguienteNroMemo(Asignaciones asignacion) throws DataAccessException;

    int getTrnBuscaridMemo(Asignaciones asignacion) throws DataAccessException;
    // por programa

    List<Asignaciones> getDctListarAsignacionDocenteProgramaPlan(Asignaciones asignacion) throws DataAccessException;

    List<Asignaciones> getDctListarAsignacionDocenteProgramaPlanTipoGrado(Asignaciones asignacion)
            throws DataAccessException;

    // Buscar docentes
    List<Asignaciones> getListarAsignacionDocenteTodas(Asignaciones asignacion) throws DataAccessException;

    // Cerrar libreta Por Dct Asignacion
    List<Asignaciones> getListarMateriasCerrarLibretaDctAsignacion(Materias materia) throws DataAccessException;

    // Eliminar Asignacion
    int setEliminarAsignacionDocenteMateria(Asignaciones asignacion) throws DataAccessException;

    // Devuelve id fase resolucion
    int setBuscar_id_fase_resolucion(Asignaciones asignacion) throws DataAccessException;

    int setBuscar_id_fase_resolucionFinal(Asignaciones asignacion) throws DataAccessException;

    // INICIO - METODOS ADICIONADOS POR LA UAP
    // Asignacion Docente- Materia
    List<Asignaciones> getDctListarAsignacionDocenteMateria(Asignaciones asignacion) throws DataAccessException;

    List<Asignaciones> getDctListarAsignacionDocenteMateriaFuncion(Asignaciones asignacion) throws DataAccessException;

    List<Asignaciones> getDctListarAsignacionDocenteMateriaFuncionFiltrar(Asignaciones asignacion)
            throws DataAccessException;

    List<Asignaciones> getDctListarAsignacionDocenteMateriaFuncionxid(Asignaciones asignacion)
            throws DataAccessException;

    List<Asignaciones> getDctListarAsignacionDocenteMateriaFuncionsintitular(Asignaciones asignacion)
            throws DataAccessException;

    List<Asignaciones> getDctListarAsignacionDocenteMateriaFuncionsintitularfinal(Asignaciones asignacion)
            throws DataAccessException;

    List<Asignaciones> getDctListarAsignacionDocenteMateriaFuncionsoloid(Asignaciones asignacion)
            throws DataAccessException;

    List<Asignaciones> getDctListarAsignacionDocenteMateriaFuncionparamemo(Asignaciones asignacion)
            throws DataAccessException;

    List<Asignaciones> getDctListarAsignacionDocenteMateriacontador(Asignaciones asignacion) throws DataAccessException;

    List<Asignaciones> getDctListarAsignacionDocenteMateriatc(Asignaciones asignacion) throws DataAccessException;

    List<Asignaciones> getDctListarNroAsignacionDocenteMateriaFuncionxid(Asignaciones asignacion)
            throws DataAccessException;
    // Asignacion Auxiliar- Materia

    List<Asignaciones> getDctListarAsignacionAuxiliarMateria(Asignaciones asignacion) throws DataAccessException;

    // Asignacion Auxiliar docencia
    int setRegistrarAsignacionAuxiliar(Asignaciones asignacion) throws DataAccessException;

    Asignaciones getDctBuscarAsignacionAuxiliar(Asignaciones asignacion) throws DataAccessException;

    int setEliminarAsignacionAuxiliarMateria(Asignaciones asignacion) throws DataAccessException;

    List<Asignaciones> getDctListarAsignacionDocenteporProgramaAnalitico(Asignaciones asignacion) throws DataAccessException;

    // FIN - METODOS ADICIONADOS POR LA UAP
}
