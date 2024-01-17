package com.moxos.uab.mybatis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import com.moxos.uab.mybatis.entity.Curriculum;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CurriculumDao {

    List<Curriculum> cvGetListarRubrosPersona(Curriculum curriculum) throws DataAccessException;

    List<Curriculum> cvGetListarRubros(Curriculum curriculum) throws DataAccessException;

    List<Curriculum> cvGetListarSubRubros(Curriculum curriculum) throws DataAccessException;

    int cvSetRegistrarCurriculum(Curriculum curriculum) throws DataAccessException;

    // Adjunto Docente
    int setRegistrarDctAdjuntos(Curriculum curriculum) throws DataAccessException;

    List<Curriculum> getListarAdjuntosDocente(Curriculum curriculum) throws DataAccessException;

    int setEliminarDctAdjunto(Curriculum curriculum) throws DataAccessException;
}
