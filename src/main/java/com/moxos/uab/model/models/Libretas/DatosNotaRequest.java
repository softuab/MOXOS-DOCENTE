package com.moxos.uab.model.models.Libretas;

import lombok.Data;

@Data
public class DatosNotaRequest {

    private Integer id_asignacion;
    private Integer id_programa;
    private Integer id_tipo_nota;
    private Integer id_tipo_grado;
    private Integer nro_nota_s;
    private Integer gestion;
    private Integer periodo;
    private Integer id_modelo_ahorro;
    private Integer id_materia;
    private Integer id_fase;
    private Integer id_tipo_evaluacion;
    private Integer id_grupo;
    private Integer id_departamento;
    private Integer id_estudiante;
    private double nota;
    private String ubicacion;
}
