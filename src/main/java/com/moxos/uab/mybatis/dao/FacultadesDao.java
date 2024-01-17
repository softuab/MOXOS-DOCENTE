package com.moxos.uab.mybatis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import com.moxos.uab.mybatis.entity.Facultades;
import com.moxos.uab.mybatis.entity.ListViewItem;
import com.moxos.uab.mybatis.entity.Universidades;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FacultadesDao {

    Facultades getFclBuscarFacultad(Facultades facultad) throws DataAccessException;

    List<Facultades> getUnvListarFacultades(Universidades universidad) throws DataAccessException;

    List<Facultades> getUnvListarFacultadesPost(Universidades universidad) throws DataAccessException;

    List<ListViewItem> getListarFacultades() throws DataAccessException;
}
