package com.moxos.uab.mybatis.dao;

import com.moxos.uab.mybatis.entity.Instituciones;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

@Mapper
public interface InstitucionesDao {

    Instituciones getBuscarInstitucion(Instituciones institucion) throws DataAccessException;

    Instituciones getBuscarInstitucionSede(Instituciones institucion) throws DataAccessException;
}
