package com.moxos.uab.mybatis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import com.moxos.uab.mybatis.entity.Tableros;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TablerosDao {

    List<Tableros> getListarNoticias() throws DataAccessException;

    List<Tableros> getListarTiposTableros() throws DataAccessException;

    List<Tableros> getListarTiposAvisos() throws DataAccessException;

    int setRegistrarTablero(Tableros tablero) throws DataAccessException;

    Tableros getBuscarTablero(Tableros tablero) throws DataAccessException;

    int setEliminarTablero(Tableros tablero) throws DataAccessException;

    // INICIO - MI
    List<Tableros> getListarNoticiasRol(Tableros tablero) throws DataAccessException;

    List<Tableros> getListarRolesNoticias() throws DataAccessException;
    // FIN - MI

}