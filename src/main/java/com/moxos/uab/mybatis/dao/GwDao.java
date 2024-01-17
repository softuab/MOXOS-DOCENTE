package com.moxos.uab.mybatis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import com.moxos.uab.mybatis.entity.Abm;
import com.moxos.uab.mybatis.entity.Tramites;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GwDao {

    String getListarDatosTabla(Abm abm) throws DataAccessException;

    String getListarDatosPrimarios(Abm abm) throws DataAccessException;

    Abm getListarCamposTabla2(Abm abm) throws DataAccessException;

    Tramites getBuscarCampoGw(Tramites tramite) throws DataAccessException;

    // Tramites Limbo
    List<Tramites> getListarTramitesMiosLimbo(Tramites tramite) throws DataAccessException;

    int setRegistrarValorLimbo(Tramites tramite) throws DataAccessException;

    int setInsertarTramiteLimbo(Tramites tramite) throws DataAccessException;

    int setRetrocederTramiteLimbo(Tramites tramite) throws DataAccessException;
    // Fin Tramites Limbo

    String getBuscarTablaLimbo(Tramites tramite) throws DataAccessException;

    int setAvanzarTramiteLimbo(Tramites tramite) throws DataAccessException;

    int getBuscarIdCampoLimbo(Tramites tramite) throws DataAccessException;

    int setRegistrarValorLimbo2(Tramites tramite) throws DataAccessException;

    String getContarPaginasLimbo(Tramites tramite) throws DataAccessException;

    int setRegistrarTrPrFrLogLimbo(Tramites tramite) throws DataAccessException;

}