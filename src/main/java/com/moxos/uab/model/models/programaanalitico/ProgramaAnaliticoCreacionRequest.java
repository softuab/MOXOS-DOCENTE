package com.moxos.uab.model.models.programaanalitico;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProgramaAnaliticoCreacionRequest {
    private Integer id_asignacion;
    private Integer id_materia;
    private String materia;
    private Integer id_grupo;
    private String grupo;
    private Integer id_programa;
    private Integer id_fase;
    private Integer id_tipo_evaluacion;
    private String tipo_evaluacion;
    private Integer gestion;
    private Integer periodo;
    private Integer id_docente;
    private String id_plan;
    private Integer id_departamento;
    private Integer id_tipo_grado;
    private Integer id_mencion;
    @NotNull(message = "Debe seleccionar el programa analitico creado anteriormente")
    private Integer id_dct_programa_analitico;
}
