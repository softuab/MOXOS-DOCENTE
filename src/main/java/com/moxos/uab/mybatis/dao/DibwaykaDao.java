package com.moxos.uab.mybatis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import com.moxos.uab.mybatis.entity.Dibwayka;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DibwaykaDao {
    List<Dibwayka> getListarCamposProcesoWK(Dibwayka dibwayka) throws DataAccessException;

    List<Dibwayka> getListarComboWK(Dibwayka dibwayka) throws DataAccessException;

    int setCrearTablasDibWK(Dibwayka dibwayka) throws DataAccessException;

    List<Dibwayka> getListarCamposDibWK(Dibwayka dibwayka) throws DataAccessException;

    Dibwayka getBuscarTablaDibWK(Dibwayka dibwayka) throws DataAccessException;

    Dibwayka getBuscarCampoDibWK(Dibwayka dibwayka) throws DataAccessException;

    Dibwayka getBuscarTuplaDibWK(Dibwayka dibwayka) throws DataAccessException;

    int setInsertarConsultaDibWK(Dibwayka dibwayka) throws DataAccessException;

    List<Dibwayka> getListarCondicionesConsultaDibWK(Dibwayka dibwayka) throws DataAccessException;

    List<Dibwayka> getListarConsultasDibWK() throws DataAccessException;

    int setBorrarConsultaDibWK(Dibwayka dibwayka) throws DataAccessException;

    int setModificarConsultaDibWK(Dibwayka dibwayka) throws DataAccessException;

    Dibwayka getBuscarConsultaDibWK(Dibwayka dibwayka) throws DataAccessException;

    List<Dibwayka> getConsultaCondicionDibWK(Dibwayka dibwayka) throws DataAccessException;

}