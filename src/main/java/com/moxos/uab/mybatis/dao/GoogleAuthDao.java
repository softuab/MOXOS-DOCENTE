package com.moxos.uab.mybatis.dao;

import com.moxos.uab.mybatis.entity.GoogleAuth;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoogleAuthDao {
    List<GoogleAuth> getListaTokensGoogle();
}
