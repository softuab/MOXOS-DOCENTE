package com.moxos.uab.mybatis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import com.moxos.uab.mybatis.entity.Proveidos;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProveidosDao {

    int setRegistrarProveido(Proveidos proveido) throws DataAccessException;

    Proveidos getBuscarUltimoProveido(Proveidos proveido) throws DataAccessException;

    String getBuscarUltimoProveido2(Proveidos proveido) throws DataAccessException;

    List<Proveidos> getListarProveidosHistoricos(Proveidos proveido) throws DataAccessException;

    Proveidos getBuscarProveido(Proveidos proveido) throws DataAccessException;

    Proveidos getBuscarProveidoCorresp(Proveidos proveido) throws DataAccessException;

}