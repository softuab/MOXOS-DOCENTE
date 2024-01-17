package com.moxos.uab.model.models.programaanalitico;

import lombok.Data;

@Data
public class ImportarProgramaAnaliticoRequest {
    private Integer id_dct_programa_analitico;
    private Integer id_asignacion;
    private Integer id_materia;
    private Integer id_programa;
    private Integer id_grupo;
    private Integer id_mencion;
    private Integer id_fase;
    private Integer id_tipo_evaluacion;
    private Integer gestion;
    private Integer periodo;
    private String id_plan;
    private String materia;
    private String grupo;
}
