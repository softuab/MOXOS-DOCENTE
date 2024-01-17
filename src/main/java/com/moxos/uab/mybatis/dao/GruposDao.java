package com.moxos.uab.mybatis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import com.moxos.uab.mybatis.entity.Grupos;
import com.moxos.uab.mybatis.entity.Materias;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GruposDao {

    // Administrar Horarios
    List<Grupos> getDptoListarGruposMateria(Materias materia) throws DataAccessException;

    Grupos getDptoBuscarGrupo(Grupos grupo) throws DataAccessException;
    // Fin Administrar Horarios

    // Cambio de grupos
    List<Grupos> getListarEstudiantesGrupos(Grupos grupo) throws DataAccessException;

    Grupos setCambiarGrupoEstudiante(Grupos grupo) throws DataAccessException;
    // Fin Cambio de grupos

    // est_programaciones
    Grupos getGrpBuscarGrupo(Grupos grupo) throws DataAccessException;

    Grupos getDptoBuscarCupoRestanteGrupo(Grupos grupo) throws DataAccessException;

    // inicio - GRUPO jojo
    List<Grupos> getPrgListarGrupos(Grupos grupo) throws DataAccessException;

    List<Grupos> getMtrListarGruposNoAsignados(Grupos grupo) throws DataAccessException;

    Grupos getMiDptoBuscarGrupo(Grupos grupo) throws DataAccessException;

    // fin - GRUPO jojo
    // dct_asignacion
    List<Grupos> getDptoListarGruposMateriaTipoEvaluacion(Materias materia) throws DataAccessException;

    List<Grupos> getDptoListarGruposMateriaTipoEvaluacionDesignado(Materias materia) throws DataAccessException;

    // INICIO - METODOS ADICIONADOS POR LA UAP
    // aux_asignacion
    List<Grupos> getDptoListarGruposMateriaTipoEvaluacionAuxiliares(Materias materia) throws DataAccessException;
    // FIN - METODOS ADICIONADOS POR LA UAP

}
