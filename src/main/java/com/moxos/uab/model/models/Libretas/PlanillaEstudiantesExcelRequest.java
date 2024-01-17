package com.moxos.uab.model.models.Libretas;

import lombok.Data;

@Data
public class PlanillaEstudiantesExcelRequest {
    private Integer id_programa;
    private Integer id_tipo_evaluacion;
    private Integer id_grupo;
    private Integer id_materia;
    private Integer gestion;
    private Integer periodo;
    private String id_tipo_nota_s;
}
