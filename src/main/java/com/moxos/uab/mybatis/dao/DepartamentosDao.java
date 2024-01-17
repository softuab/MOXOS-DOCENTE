package com.moxos.uab.mybatis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import com.moxos.uab.mybatis.entity.Departamentos;
import com.moxos.uab.mybatis.entity.Facultades;
import com.moxos.uab.mybatis.entity.Universidades;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DepartamentosDao {

    Departamentos getDptBuscarDepartamento(Departamentos departamento) throws DataAccessException;

    List<Departamentos> getFclListarDepartamentos(Facultades facultad) throws DataAccessException;

    List<Departamentos> getUnvListarDepartamentos(Universidades universidad) throws DataAccessException;

}