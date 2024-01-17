package com.moxos.uab.model.models.Libretas;

import lombok.Data;

@Data
public class ParametrosLibretasRequest {
    private Integer id_asignacion;
    private Integer gestion;
    private Integer periodo;
    private Integer id_modelo_ahorro;
    private Integer id_materia;
    private Integer id_fase;
    private Integer id_programa;
    private Integer id_tipo_evaluacion;
    private Integer id_grupo;
    private Integer id_departamento;
    private String programa;
    private String sigla;
    private String materia;
}
