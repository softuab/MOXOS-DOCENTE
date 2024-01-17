package com.moxos.uab.mybatis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import com.moxos.uab.mybatis.entity.Enlaces;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EnlacesDao {

    List<Enlaces> getListarEnlaces(Enlaces enlace) throws DataAccessException;

    Enlaces getBuscarEnlace(Enlaces enlace) throws DataAccessException;

    // INICIO Combustible \\
    Enlaces getEnlBuscarEnlace(Enlaces enlace) throws DataAccessException;
    // FIN Combustible \\

    // INICIO - MI
    // Listar Estados por tabla
    List<Enlaces> getStdListarEstadosTabla(Enlaces enlace) throws DataAccessException;
    // FIN - MI

}