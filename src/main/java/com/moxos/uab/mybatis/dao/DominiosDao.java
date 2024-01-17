package com.moxos.uab.mybatis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import com.moxos.uab.mybatis.entity.Dominios;
import com.moxos.uab.mybatis.entity.Clientes;
import com.moxos.uab.mybatis.entity.Tramites;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DominiosDao {

    // Administracion de dominios
    List<Dominios> getListarDominios() throws DataAccessException;

    List<Dominios> getListarDominiosAcceso(Clientes cliente) throws DataAccessException;

    List<Dominios> getListarTiposDominios() throws DataAccessException;

    Dominios getBuscarDominio(Dominios dominio) throws DataAccessException;

    Dominios getBuscarTipoDominio(Dominios dominio) throws DataAccessException;

    int setRegistrarDominio(Dominios dominio) throws DataAccessException;

    int setEliminarDominio(Dominios dominio) throws DataAccessException;

    int getBuscarDominioOtrasTb(Dominios dominio) throws DataAccessException;
    // Fin Administracion de dominios

    // Administracion de tuplas
    List<Dominios> getListarTuplas(Dominios dominio) throws DataAccessException;

    List<Dominios> getListarTuplasPadre(Dominios dominio) throws DataAccessException;

    Dominios getBuscarTupla(Dominios dominio) throws DataAccessException;

    int setRegistrarTupla(Dominios dominio) throws DataAccessException;

    int setEliminarTupla(Dominios dominio) throws DataAccessException;
    // Fin Administracion de tuplas

    // Administracion de tramites
    int getBuscarTieneHijos(Tramites tramite) throws DataAccessException;

    List<Dominios> getListarCombos2(Tramites tramite) throws DataAccessException;

    int getBuscarTuplaPadre(Tramites tramite) throws DataAccessException;

    Dominios getBuscarTupla2(Dominios dominio) throws DataAccessException;

    int setRegistrarTempTupla(Tramites tramite) throws DataAccessException;

    int setLimpiarTempTuplas() throws DataAccessException;
    // Fin Administracion de tramites
}