package com.moxos.uab.mybatis.dao;

import com.moxos.uab.mybatis.entity.Docentes;
import org.springframework.dao.DataAccessException;
import com.moxos.uab.mybatis.entity.Clientes;
import com.moxos.uab.mybatis.entity.Usuarios;
import com.moxos.uab.mybatis.entity.Instituciones;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ClientesDao {

    Clientes getBuscarConexion(Usuarios usuario) throws DataAccessException;

    Clientes getBuscarConexion2FA(Usuarios usuario) throws DataAccessException;

    Clientes getComprobarUsuario(Usuarios usuario) throws DataAccessException;

    Clientes getComprobarUsuSede(Usuarios usuario) throws DataAccessException;


    String getFechaCadena(Clientes cliente) throws DataAccessException;

    String getCadenaFecha(Clientes cliente) throws DataAccessException;

    Integer getUsrBuscarIp(Clientes cliente) throws DataAccessException;

    Clientes getBuscarConexionUsuario(String correo) throws DataAccessException;

    void bloquearUsuario(Clientes cliente) throws DataAccessException;

    Integer updateUser2FA(Docentes docentes) throws DataAccessException;

    String getSecret(Integer id_docente) throws DataAccessException;

    Docentes getUsuario(String apodo) throws DataAccessException;
}
