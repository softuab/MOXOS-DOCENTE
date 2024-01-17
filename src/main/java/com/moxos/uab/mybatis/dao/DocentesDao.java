package com.moxos.uab.mybatis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import com.moxos.uab.mybatis.entity.Docentes;
import com.moxos.uab.mybatis.entity.ParametrosBusqueda;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DocentesDao {

    Docentes getComprobarDocente(Docentes docente) throws DataAccessException;

    Docentes getBuscarDocente(Docentes docente) throws DataAccessException;

    Docentes getBuscarDocentexdepartamento(Docentes docente) throws DataAccessException;

    int setCambioPinDocente(Docentes docente) throws DataAccessException;

    List<Docentes> getBuscarListaDocentesNombres(Docentes docente) throws DataAccessException;

    List<Docentes> getBuscarListaDocentesDip(Docentes docente) throws DataAccessException;

    List<Docentes> getListarTiposDocentes() throws DataAccessException;

    List<Docentes> getListarTiposAsignaciones() throws DataAccessException;

    List<Docentes> getListarTiposFunciones() throws DataAccessException;
    // Docentes Todos

    List<Docentes> getListarDocentesTodos() throws DataAccessException;

    // INICIO - Administrar Docente
    int setRegistrarDocente(Docentes docente) throws DataAccessException;

    int setEliminarDocente(Docentes docente) throws DataAccessException;
    // FIN - Administrar Docente

    // Inicio Cambio PIN Docente General
    int setModificarApodoClaveDocente(Docentes docente) throws DataAccessException;
    // Fin Cambio PIN Docente General

    // INICIO - METODOS ADICIONADOS POR LA UAP
    List<Docentes> getListarDocentesPorDpto(Docentes docente) throws DataAccessException;

    // Listar a todos los Auxiliares de docencia
    List<Docentes> getListarAuxiliaresTodos() throws DataAccessException;

    Docentes getBuscarAuxiliar(Docentes docente) throws DataAccessException;
    // FIN - METODOS ADICIONADOS POR LA UAP

    List<Docentes> getDetallefotoadjunto(Docentes docente) throws DataAccessException;

    int setRegistrarDocenteAdjuntos(Docentes docente) throws DataAccessException;

    int setActualizarDocenteAdjuntos(Docentes docente) throws DataAccessException;

    List<Docentes> getBuscarListaDocentesCorreo(String Correo) throws DataAccessException;

    List<Docentes> GetListaNotificacionDocente(ParametrosBusqueda buscar) throws DataAccessException;

    Docentes getUsuarioDocente(String apodo) throws DataAccessException;
}
