package com.moxos.uab.mybatis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import com.moxos.uab.mybatis.entity.Planes;
import com.moxos.uab.mybatis.entity.Programas;
import com.moxos.uab.mybatis.entity.Facultades;
import com.moxos.uab.mybatis.entity.Materias;
import com.moxos.uab.mybatis.entity.Universidades;
import com.moxos.uab.mybatis.entity.Modelos_ahorros;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlanesDao {

    List<Planes> getPrgListarPlanes(Programas programa) throws DataAccessException;

    List<Planes> getFclListarPlanes(Facultades facultad) throws DataAccessException;

    List<Planes> getUnvListarPlanes(Universidades universidad) throws DataAccessException;

    List<Planes> getPlnListarMateriasNivel(Planes plan) throws DataAccessException;

    int getPlnListarNroNiveles(Planes plan) throws DataAccessException;

    List<Planes> getPlnListarMateriasRequisitos(Planes plan) throws DataAccessException;

    List<Planes> getPlnListarMateriasNroRequisitos(Planes plan) throws DataAccessException;

    List<Planes> getUnvGrdListarPlanes(Planes plan) throws DataAccessException;

    // ADMINISTRAR HORARIOS
    List<Modelos_ahorros> getListarPlanProgramaModeloAhorro(Modelos_ahorros materia) throws DataAccessException;

    Planes getMtrBuscarPlanPrograma(Planes plan) throws DataAccessException;

    // FIN ADMINISTRAR HORARIOS
    // PROGRAMACION COMO ESTUDIANTE
    List<Planes> getMncListarMenciones(Planes plan) throws DataAccessException;

    int setEstRegistrarMencionEstudiante(Planes plan) throws DataAccessException;
    // FIN PROGRAMACION COMO ESTUDIANTE

    // MI SEGUNDA PARTE
    List<Planes> getListarTiposGrados() throws DataAccessException;

    Planes getBuscarTiposGrados(Planes plan) throws DataAccessException;

    List<Planes> getListarPrgPlanesActual(Planes plan) throws DataAccessException;

    // FIN SEGUNDA PARTE
    // INICIO - Convalidacion automatica
    List<Planes> getListarMateriasPlanGrupo(Planes plan) throws DataAccessException;

    List<Planes> getListarMateriasPlanGrupoCantidad(Planes plan) throws DataAccessException;

    List<Planes> getListarMateriasPlan(Planes plan) throws DataAccessException;

    List<Planes> getListarMateriasPlanAnterior(Planes plan) throws DataAccessException;

    List<Planes> getListarMateriasPlanAnterior2(Planes plan) throws DataAccessException;

    List<Planes> getListarMateriasPlanConvalidado(Planes plan) throws DataAccessException;

    int setRegistrarMtrPlan(Planes plan) throws DataAccessException;

    int setEliminarMtrPlan(Planes plan) throws DataAccessException;

    Planes getMncBuscarMencion(Planes plan) throws DataAccessException;

    Planes getBuscarMateriaPlan(Planes plan) throws DataAccessException;
    // FIN - Convalidacion automatica

    // INICIO- Admin Planes de Estudio
    List<Planes> getListarMateriasPlanRequisitos(Planes plan) throws DataAccessException;

    List<Planes> getListarMateriasElectivasPlan(Planes plan) throws DataAccessException;

    List<Planes> getListarMateriasPlanMencion(Planes plan) throws DataAccessException;

    List<Planes> getListarMateriasRequisitos(Planes plan) throws DataAccessException;

    List<Planes> getListarMateriasNoRequisitos(Planes plan) throws DataAccessException;

    List<Planes> getListarMateriasConvalidadas(Planes plan) throws DataAccessException;

    List<Planes> getListarMateriasNoConvalidadas(Planes plan) throws DataAccessException;

    List<Materias> getListarMateriasNoPlan(Planes plan) throws DataAccessException;

    List<Planes> getPlnListarTiposMaterias() throws DataAccessException;

    Planes getPlnBuscarTipoMateria(Planes plan) throws DataAccessException;

    Planes getBuscarPrgPlan(Planes plan) throws DataAccessException;

    Planes getBuscarPrgPlan2(Planes plan) throws DataAccessException;

    Planes getBuscarMtrPlan(Planes plan) throws DataAccessException;

    int setModificarMtrPlan(Planes plan) throws DataAccessException;

    // FIN - Admin Planes de Estudio
    List<Planes> getListarPrgPlanesVestibulares() throws DataAccessException;

    List<Planes> getListarPrgPlanesUniversitarios() throws DataAccessException;

    Planes getBuscarMaxPrgPlanActual(Planes plan) throws DataAccessException;

    // Plan por id_tipo_grado
    List<Planes> getListarMateriasPlanTipoGrado(Planes plan) throws DataAccessException;

    // Convalidacion Manual
    List<Planes> getListarTiposConvalidaciones() throws DataAccessException;

    Planes getBuscarTipoConvalidacion(Planes plan) throws DataAccessException;

    int setRegistrarConvalidacionManual(Planes plan) throws DataAccessException;

    int setRegistrarDetallesConvalidacionManual(Planes plan) throws DataAccessException;

    // Autorizar Convalidacion Manual
    List<Planes> getListarConvalidacionManualPrograma(Planes plan) throws DataAccessException;

    List<Planes> getListarConvalidacionManualPrograma2(Planes plan) throws DataAccessException;

    Planes getBuscarConvalidacionManual(Planes plan) throws DataAccessException;

    List<Planes> getListarCnvDetallesConvalidacion(Planes plan) throws DataAccessException;

    List<Planes> getListarCnvDetallesConvalidacion2(Planes plan) throws DataAccessException;

    List<Planes> getListarNotasCruceCnvDetalles(Planes plan) throws DataAccessException;

    int setRegistrarEstNotasConvalidacionManual(Planes plan) throws DataAccessException;

    int setEliminarCnvDetalle(Planes plan) throws DataAccessException;
    // Fin Convalidacion Manual

}