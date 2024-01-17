package com.moxos.uab.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import com.moxos.uab.mybatis.entity.Actividades;
import com.moxos.uab.mybatis.entity.Clientes;
import com.moxos.uab.mybatis.entity.Personas;

@Mapper
public interface ActividadesDao {

    // Administrar actividades
    List<Actividades> getListarActividades(Actividades actividad) throws DataAccessException;

    List<Actividades> getListarActividadesNoLimbo(Actividades actividad) throws DataAccessException;

    List<Actividades> getListarActividadesLimbo(Actividades actividad) throws DataAccessException;

    List<Actividades> getListarTiposAlertasAct(Actividades actividad) throws DataAccessException;

    Actividades getBuscarProceso(Actividades actividad) throws DataAccessException;

    List<Actividades> getListarProcesosAcceso(Clientes cliente) throws DataAccessException;

    List<Actividades> getListarProcesosAccesoTramites(Clientes cliente) throws DataAccessException;

    List<Actividades> getListarProcesosAccesoTramites2(Clientes cliente) throws DataAccessException;

    List<Actividades> getListarProcesosAccesoCorresp(Clientes cliente) throws DataAccessException;

    Actividades getBuscarTipoAlerta(Actividades actividad) throws DataAccessException;

    List<Actividades> getListarTiposAlertas(Actividades actividad) throws DataAccessException;

    Actividades getBuscarActividad(Actividades actividad) throws DataAccessException;

    Actividades getBuscarActividadOrden(Actividades actividad) throws DataAccessException;

    List<Actividades> getListarUbicacionesOrganicas() throws DataAccessException;

    Actividades getBuscarUbicacionOrganica(Actividades actividad) throws DataAccessException;

    List<Actividades> getListarTiposActuaciones() throws DataAccessException;

    Actividades getBuscarTipoActuacion(Actividades actividad) throws DataAccessException;

    int setRegistrarActividad(Actividades actividad) throws DataAccessException;

    int setReiniciarTiposAlertas(Actividades actividad) throws DataAccessException;

    int setRegistrarTipoAlerta(Actividades actividad) throws DataAccessException;

    int setEliminarActividad(Actividades actividad) throws DataAccessException;
    // Fin Administrar actividades

    List<Actividades> getListarTiposProcesos() throws DataAccessException;

    // Redireccionar tramites
    List<Actividades> getListarActividades2(Actividades actividad) throws DataAccessException;

    List<Personas> getListarUsuariosRolActividad(Actividades actividad) throws DataAccessException;
    // Fin Redireccionar tramites

    // Reporte de actividades por roles
    String getListarActividadesRoles(Actividades actividad) throws DataAccessException;
    // Fin Reporte de actividades por roles

    // Tipos duraciones
    List<Actividades> getListarTiposDuraciones() throws DataAccessException;

    Actividades getBuscarTipoDuracion(Actividades actividad) throws DataAccessException;
    // Fin Tipos duraciones

    // Administrar Kardex
    List<Actividades> getListarProcesosAccesoKardex() throws DataAccessException;

    List<Actividades> getListarProcesosKardexs() throws DataAccessException;

    int setRegistrarProcesoKardex(Actividades actividad) throws DataAccessException;

    int setModificarProcesoKardex(Actividades actividad) throws DataAccessException;

    int setEliminarProcesoKardex(Actividades actividad) throws DataAccessException;
    // Fin Administrar Kardex

}