package com.moxos.uab.mybatis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.moxos.uab.mybatis.entity.Herramientas;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HerramientasDao {

    List<Herramientas> getListarCombosPagina(Herramientas herramienta) throws DataAccessException;

}