package com.moxos.uab.mybatis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import com.moxos.uab.mybatis.entity.Usuarios;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsuariosDao {

    Usuarios getBuscarUsuario(Usuarios usuario) throws DataAccessException;

    List<Usuarios> getListarUsuarios(Usuarios usuario) throws DataAccessException;

    List<Usuarios> getListarUsuariosUbicacionOrganica(Usuarios usuario) throws DataAccessException;

    int getVerificarUsuario(Usuarios usuario) throws DataAccessException;

    int setRegistrarNuevaClave(Usuarios usuario) throws DataAccessException;

    int setRegistrarUsuario(Usuarios usuario) throws DataAccessException;

    int setEliminarUsuario(Usuarios usuario) throws DataAccessException;

    Integer getIDUsuario(String correo) throws DataAccessException;
}
