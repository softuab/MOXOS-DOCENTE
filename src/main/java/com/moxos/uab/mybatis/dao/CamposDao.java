package com.moxos.uab.mybatis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import com.moxos.uab.mybatis.entity.Campos;
import com.moxos.uab.mybatis.entity.Clientes;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CamposDao {

    // Administracion de campos
    List<Campos> getListarFormularios() throws DataAccessException;

    List<Campos> getListarFormulariosAcceso(Clientes cliente) throws DataAccessException;

    List<Campos> getListarCampos(Campos campo) throws DataAccessException;

    Campos getBuscarFormulario(Campos campo) throws DataAccessException;

    Campos getBuscarCampoForm(Campos campo) throws DataAccessException;

    List<Campos> getListarTiposValidaciones() throws DataAccessException;

    Campos getBuscarTipoValidacion(Campos campo) throws DataAccessException;

    int setRegistrarCampo(Campos campo) throws DataAccessException;

    int setEliminarCampo(Campos campo) throws DataAccessException;
    // Fin Administracion de campos

    // Administracion de acl
    Campos getBuscarFormulario1(Campos campo) throws DataAccessException;

    List<Campos> getListarTiposPermisos() throws DataAccessException;

    List<Campos> getListarCamposAcl(Campos campo) throws DataAccessException;

    int setRegistrarAcl(Campos campo) throws DataAccessException;

    int setEliminarAcl(Campos campo) throws DataAccessException;
    // Fin Administracion de acl

    // Administracion de Reportes
    List<Campos> getListarCamposProceso(Campos campo) throws DataAccessException;

    List<Campos> getListarCamposReporte(Campos campo) throws DataAccessException;

    List<Campos> getListarCamposReporte2(Campos campo) throws DataAccessException;

    String getListarTotalesDatos(Campos campo) throws DataAccessException;
    // Fin Administracion de Reportes

    // Reporte de campos por actividades
    String getListarCamposActividad(Campos campo) throws DataAccessException;
    // Fin Reporte de campos por actividades

    // Administracion de acl dibRap
    List<Campos> getListarCamposAcl2(Campos campo) throws DataAccessException;

    int setRegistrarAcl2(Campos campo) throws DataAccessException;

    int setEliminarAcl2(Campos campo) throws DataAccessException;
    // Fin Administracion de acl dibRap

    // Administracion de formularios
    int setRegistrarFormulario(Campos campo) throws DataAccessException;

    int setEliminarFormulario(Campos campo) throws DataAccessException;
    // Fin Administracion de formularios

    // Administrar Campos y Acl Proceso Kardex
    Campos getBuscarTipoPermiso(Campos campo) throws DataAccessException;

    int setRegistrarCampoAclProcesoKardex(Campos campo) throws DataAccessException;
    // Fin Administrar Campos y Acl Proceso KArdex

    List<Campos> getListarCamposReferenciaProceso(Campos campo) throws DataAccessException;

    List<Campos> getListarCamposReporteProceso(Campos campo) throws DataAccessException;

    // INICIO - Administrar Reportes
    List<Campos> getListarTuplasCampo(Campos campo) throws DataAccessException;
    // NUEVO - Administrar Reportes

}