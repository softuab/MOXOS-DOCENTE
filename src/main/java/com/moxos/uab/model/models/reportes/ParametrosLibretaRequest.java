package com.moxos.uab.model.models.reportes;

import lombok.Data;

@Data
public class ParametrosLibretaRequest {

    private Integer id_asignacion;
    private Integer id_materia;
    private Integer id_grupo;
    private Integer id_programa;
    private Integer id_tipo_evaluacion;
    private Integer id_modelo_ahorro;
    private Integer gestion;
    private Integer periodo;
}
