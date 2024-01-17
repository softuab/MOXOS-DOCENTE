package com.moxos.uab.mybatis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import com.moxos.uab.mybatis.entity.Horarios;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HorariosDao {

    List<Horarios> getListarDias() throws DataAccessException;

    List<Horarios> getListarHorario(Horarios horario) throws DataAccessException;

    List<Horarios> getListarAulasDisponibles(Horarios horario) throws DataAccessException;

    void setHrsLimpiarHorarioMateria(Horarios horario) throws DataAccessException;

    int setHrsRegistrarHorarioAula(Horarios horario) throws DataAccessException;

}