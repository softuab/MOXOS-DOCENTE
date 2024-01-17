package com.moxos.uab.mybatis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import com.moxos.uab.mybatis.entity.Materias;
import com.moxos.uab.mybatis.entity.Planes;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MateriasDao {
    // PLANES
    List<Materias> getPlnListarMaterias(Planes plan) throws DataAccessException;
    // FIN PLANES

    // LIBRETAS
    Materias getMtrBuscarMateria(Materias materia) throws DataAccessException;
    // FIN LIBRETAS

    // EST_ PROGRAMACIONES
    List<Materias> getEstPrgListarProgramacionMateriasAut(Materias materia) throws DataAccessException;

    List<Materias> getDptoListarMateriaGrupo(Materias materia) throws DataAccessException;

    List<Materias> getPstPrgListarProgramacionMateriasAut(Materias materia) throws DataAccessException;
    // FIN EST_ PROGRAMACIONES

    // PROGRAMACIONES COMO ESTUDIANTE
    List<Materias> getEstListarProgramacionMateriasReq(Materias materia) throws DataAccessException;
    // FIN PROGRAMACIONES COMO ESTUDIANTE

    // Programacion automatica
    Materias getDptoListarMateriaGrupoMinimo(Materias materia) throws DataAccessException;
    // Fin Programacion automatica

    List<Materias> getListarMateriasGradoPlanPrograma(Materias materia) throws DataAccessException;

    // INICIO - Admin Materias
    List<Materias> getListarMateriasPorDepartamento(Materias materia) throws DataAccessException;

    List<Materias> getListarMateriasPorSigla(Materias materia) throws DataAccessException;

    List<Materias> getListarMateriasPorMateria(Materias materia) throws DataAccessException;

    Materias getMtrBuscarTipoMateria(Materias materia) throws DataAccessException;

    List<Materias> getMtrListarTiposMaterias() throws DataAccessException;

    int setRegistrarMateria(Materias materia) throws DataAccessException;

    int setEliminarMateria(Materias materia) throws DataAccessException;

    int getBuscar_nro_excepcion_calendario(Materias materia) throws DataAccessException;
    // FIN - Admin Materias

    List<Integer> getMateriasModalidad(Integer id_materia) throws DataAccessException;
}