package com.moxos.uab.model.models.programaanalitico.imprimir;

import lombok.Data;

@Data
public class ParametroBusquedaImpresionRequest {
    private Integer id_dct_programa_analitico;
    private Integer id_asignacion;
    private Integer id_materia;
    private Integer id_grupo;
    private Integer id_programa;
    private Integer id_tipo_grado;
    private Integer gestion;
    private Integer periodo;
    private String grupo;
    private String materia;
    private String tipo_evaluacion;
    private String id_plan;
}
