package com.moxos.uab.model.models.programaanalitico;

import lombok.Data;

@Data
public class ParametroBusquedaProgramaAnaliticoRequest {
    private String materia;
    private String grupo;
    private Integer gestion;
    private Integer periodo;
    private Integer id_dct_programa_analitico;
}
