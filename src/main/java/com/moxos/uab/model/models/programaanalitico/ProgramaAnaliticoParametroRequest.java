package com.moxos.uab.model.models.programaanalitico;

import lombok.Data;

@Data
public class ProgramaAnaliticoParametroRequest {

    private Integer id_dct_programa_analitico;
    private Integer gestion;
    private Integer periodo;
    private String materia;
    private String grupo;
    private String programa;
    private String tab;
}
