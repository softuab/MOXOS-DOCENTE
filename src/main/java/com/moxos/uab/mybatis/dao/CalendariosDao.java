package com.moxos.uab.mybatis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.moxos.uab.mybatis.entity.Calendarios;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CalendariosDao {

    List<Calendarios> getListarCalendarios(Calendarios calendario) throws DataAccessException;

    List<Calendarios> getlistarCalendarioDocente(Calendarios calendario) throws DataAccessException;

}