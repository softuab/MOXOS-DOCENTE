package com.moxos.uab.mybatis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import com.moxos.uab.mybatis.entity.Universidades;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UniversidadesDao {

    Universidades getUnvBuscarUniversidad(Universidades universidad) throws DataAccessException;

    List<Universidades> getUnvListarUniversidades() throws DataAccessException;

}