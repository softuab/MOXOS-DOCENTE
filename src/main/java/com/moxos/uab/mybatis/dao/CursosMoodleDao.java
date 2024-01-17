/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moxos.uab.mybatis.dao;

import java.util.List;

import com.moxos.uab.mybatis.entity.CursosMoodle;
import com.moxos.uab.mybatis.entity.MoodleConfiguracion;
import com.moxos.uab.mybatis.entity.ParametrosBusqueda;
import org.springframework.dao.DataAccessException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CursosMoodleDao {

    List<CursosMoodle> GetListarCursosMoodleEstudiante(ParametrosBusqueda parametros) throws DataAccessException;

    List<CursosMoodle> GetListarCursosMoodleDocente(ParametrosBusqueda parametros) throws DataAccessException;

    List<CursosMoodle> GetListarCursosMoodleEstudiantePorCurso(ParametrosBusqueda parametros) throws DataAccessException;

    ;

    void RegistrarCursosMoodleDocente(CursosMoodle cursos) throws DataAccessException;

    void RegistrarCursosMoodleEstudiante(CursosMoodle cursos) throws DataAccessException;

    void MatricularMoodle(CursosMoodle cursos) throws DataAccessException;

    void UpdateUserMoodle(CursosMoodle cursos) throws DataAccessException;

    MoodleConfiguracion GetConfiguracionCursosMoodle() throws DataAccessException;

    CursosMoodle GetCursoMoodleEstudiante(ParametrosBusqueda parametros) throws DataAccessException;

    void RegistrarUsuariosMoodle(CursosMoodle cursos) throws DataAccessException;

    void ActualizarUsuariosMoodle(CursosMoodle cursos) throws DataAccessException;

    CursosMoodle GetUsuarioMoodle(int id) throws DataAccessException;
}
