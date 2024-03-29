package com.moxos.uab.model.models.programaanalitico;

import lombok.Data;

import java.util.Date;

@Data
public class ProgramaAnaliticoRequest {
    private Integer id_persona;
    private String dip;
    private String nombres;
    private String paterno;
    private String materno;
    private String celular;
    private String correo;
    private Integer id_docente;
    private String categoria;
    private String facultad;
    private String departamento;
    private Integer gestion;
    private Integer periodo;
    private String area;
    private String tipo_materia;
    private Integer id_materia;
    private String sigla;
    private String clasificacion_asignatura;
    private String materia;
    private String grupo;
    private Integer hrs_teoricas;
    private Integer hrs_practicas;
    private Integer hrs_periodo;
    private double creditos;
    private Integer id_asignacion;
    private String id_plan;
    private Integer id_grupo;
    private Integer nivel_academico;
    private String id_estado;
    private String nro_resolucion;
    private String observacion;
    private Integer id_dct_programa_analitico;
    private String marco_referencial;
    private String justificacion;
    private String propositos;
    private String objetivo_desarrollador;
    private String objetivo_educativo;
    private String metodos_estrategias;
    private String recursos;
    private String sistema_evaluacion;
    private Date fec_registro;
    private Date fec_modificacion;
    private Integer ult_usuario;
    private Integer id_mencion;
    private String titulo;
    private Integer id_tipo_materia;
    private Integer id_programa;
    private String ciclo_materia;
    private String tipo;
    private String grado_academico;
    private String tipo_asignacion;
    private String tipo_categoria;
    private String tipo_docente;
    private String telefonocelular;
    private String formacion;
    private String Turno;
    private String modalidad_deingreso;
    private String numerodocumento;
    private Integer id_departamento;
    private String sistema_evaluacion_criterios;
}
