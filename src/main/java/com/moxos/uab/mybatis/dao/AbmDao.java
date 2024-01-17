package com.moxos.uab.mybatis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import com.moxos.uab.mybatis.entity.Abm;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AbmDao {

    List<Abm> getListarTablas() throws DataAccessException;

    Abm getBuscarTabla(Abm abm) throws DataAccessException;

    List<Abm> getListarCamposTabla(Abm abm) throws DataAccessException;

    int setEjecutarConsulta(Abm abm) throws DataAccessException;

    List<Abm> getEjecutarListado(Abm abm) throws DataAccessException;

    // INICIO JOJO \\
    List<Abm> getEjecutarListado2(Abm abm) throws DataAccessException;

    String getDibContadorClasico(Abm abm) throws DataAccessException;

    String getDibBuscarParametro(Abm abm) throws DataAccessException;

    List<Abm> getListarRegistros(Abm abm) throws DataAccessException;

    int setInsertarDatos(Abm abm) throws DataAccessException;

    List<Abm> getListarCombos(Abm abm) throws DataAccessException;

    Abm getBuscarForanea(Abm abm) throws DataAccessException;

    Abm getBuscarCampoTabla(Abm abm) throws DataAccessException;

    int getContarDependientes(Abm abm) throws DataAccessException;

    // INICIO huaica \\
    List<Abm> getListarCamposTablaActividad(Abm abm) throws DataAccessException;

    List<Abm> getEjecutarListado3(Abm abm) throws DataAccessException;

    List<Abm> getListarRegistrosActividad(Abm abm) throws DataAccessException;

    // FIN huaica \\
    // INICIO combustible \\
    List<Abm> getEnlListarCamposTabla(Abm abm) throws DataAccessException;

    List<Abm> getEnlEjecutarListado(Abm abm) throws DataAccessException;

    List<Abm> getEnlListarRegistros(Abm abm) throws DataAccessException;

    // FIN combustible \\
    // FIN JOJO \\
    List<Abm> getListarCamposCondicion(Abm abm) throws DataAccessException;

    Abm getBuscarCampo(Abm abm) throws DataAccessException;

    List<Abm> getListarForaneosTabla(Abm abm) throws DataAccessException;

    Abm getBuscarTabla1(Abm abm) throws DataAccessException;

    int setInsertarConsulta(Abm abm) throws DataAccessException;

    Abm getBuscarConsulta(Abm abm) throws DataAccessException;

    int setInsertarConsultaTotales(Abm abm) throws DataAccessException;

    Abm getBuscarConsultaTotales(Abm abm) throws DataAccessException;

    List<Abm> getListarConsultas() throws DataAccessException;

    Abm getBuscarCampoTabla1(Abm abm) throws DataAccessException;

    int setBorrarConsulta(Abm abm) throws DataAccessException;

    int setModificarConsulta(Abm abm) throws DataAccessException;

    // Nuevo
    String setDibInsertarRegistro(Abm abm) throws DataAccessException;

}