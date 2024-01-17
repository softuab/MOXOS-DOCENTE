package com.moxos.uab.mybatis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.moxos.uab.mybatis.entity.Adjuntos;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdjuntosDao {

    int setRegistrarAdjunto(Adjuntos adjunto) throws DataAccessException;

    List<Adjuntos> getListarAdjuntos(Adjuntos adjunto) throws DataAccessException;

}