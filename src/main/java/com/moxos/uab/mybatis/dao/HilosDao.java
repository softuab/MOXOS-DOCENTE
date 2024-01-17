package com.moxos.uab.mybatis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import com.moxos.uab.mybatis.entity.Hilos;
import com.moxos.uab.mybatis.entity.Usuarios;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HilosDao {

    List<Usuarios> getListarUsuariosHilos(Usuarios usuario) throws DataAccessException;

    List<Hilos> getListarTiposHilos() throws DataAccessException;

    int setRegistrarHilo(Hilos hilo) throws DataAccessException;

    List<Hilos> getListarTiposSegmentos() throws DataAccessException;

    List<Hilos> getListarSegmentos(Hilos hilo) throws DataAccessException;

    List<Hilos> getListarDestinatarios(Hilos hilo) throws DataAccessException;

    int setRegistrarSegmento(Hilos hilo) throws DataAccessException;

    int setRegistrarSgmAdjunto(Hilos hilo) throws DataAccessException;

    List<Hilos> getListarAdjuntosHilos(Hilos hilo) throws DataAccessException;

    Hilos getBuscarHilo(Hilos hilo) throws DataAccessException;

    List<Hilos> getListarHilosMios(Hilos hilo) throws DataAccessException;

    List<Hilos> getListarHilosAMi(Hilos hilo) throws DataAccessException;

    int getNroMensajes(Hilos hilo) throws DataAccessException;

    int getNroMensajesNuevos(Hilos hilo) throws DataAccessException;

    int setBorrarHilo(Hilos hilo) throws DataAccessException;

}
