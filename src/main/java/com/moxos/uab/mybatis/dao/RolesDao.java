package com.moxos.uab.mybatis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import com.moxos.uab.mybatis.entity.Roles;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RolesDao {

    Roles getBuscarRol(Roles rol) throws DataAccessException;

    List<Roles> getListarRoles() throws DataAccessException;

    List<Roles> getListarRolesCliente(Roles rol) throws DataAccessException;

    Roles getBuscarRolCliente(Roles rol) throws DataAccessException;

    List<Roles> getListarAlmacenesCliente(Roles rol) throws DataAccessException;

    Roles getBuscarAlmacenCliente(Roles rol) throws DataAccessException;
}