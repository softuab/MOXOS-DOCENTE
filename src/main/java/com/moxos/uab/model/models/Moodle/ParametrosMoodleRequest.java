package com.moxos.uab.model.models.Moodle;

import lombok.Data;

@Data
public class ParametrosMoodleRequest {
    private Integer id_materia;
    private Integer id_grupo;
    private Integer id_programa;
    private Integer gestion;
    private Integer periodo;
    private Integer id_docente;
    private String sigla;
    private String grupo;
    private String materia;
    private String programa;
    private String fase;
    private Integer id_asignacion;
    private String id_tipo_grado;
}
