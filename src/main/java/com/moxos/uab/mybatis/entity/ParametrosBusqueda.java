/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moxos.uab.mybatis.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParametrosBusqueda {

    private int id_docente;
    private int gestion;
    private int id_programa;
    private int id_materia;
    private int id_estudiante;
    private int id_grupo;
    private int periodo;
    private int id_persona;
    private int id_departamento;
    private int id_asignacion;
    private int id_tipo_evaluacion;
    private String id_plan;
    private String id_moodle_cursos;
}
