/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moxos.uab.mybatis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import com.moxos.uab.mybatis.entity.Tokens;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TokenDao {

    int setGenerarToken(Tokens token) throws DataAccessException;

    List<Tokens> getListartokendocente(Tokens token) throws DataAccessException;
}
