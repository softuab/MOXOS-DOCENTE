package com.moxos.uab.mybatis.dao;

import com.moxos.uab.mybatis.entity.Auditoria;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

@Mapper
public interface AuditoriaDao {
    void guardarEvento(Auditoria auditoria) throws DataAccessException;
}
