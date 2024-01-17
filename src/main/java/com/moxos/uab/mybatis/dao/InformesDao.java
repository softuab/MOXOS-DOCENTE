package com.moxos.uab.mybatis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import com.moxos.uab.mybatis.entity.Informes;
import com.moxos.uab.mybatis.entity.Tramites;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InformesDao {

    // Administracion de informes
    List<Informes> getListarInformes(Informes informe) throws DataAccessException;

    Informes getBuscarInforme(Informes informe) throws DataAccessException;

    int setRegistrarInforme(Informes informe) throws DataAccessException;

    int setEliminarInforme(Informes informe) throws DataAccessException;
    // Fin Administracion de informes

    // Administracion de tramites
    List<Informes> getListarInformesActividad(Tramites tramite) throws DataAccessException;

    Informes getBuscarInforme2(Informes informe) throws DataAccessException;
    // Fin Administracion de tramites

}