package com.moxos.uab.mybatis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import com.moxos.uab.mybatis.entity.Categorias;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoriasDao {

    List<Categorias> getListarCategorias(Categorias categoria) throws DataAccessException;

}