package com.moxos.uab.model.models.utility;

import lombok.Data;

@Data
public class ParametrosBusquedaRequest {
    private int id_docente;
    private int gestion;
    private int id_programa;
    private int id_materia;
    private int id_estudiante;
    private int id_grupo;
    private int periodo;
    private int id_persona;
    private int id_departamento;
    private int id_asignacion;
    private int id_tipo_evaluacion;
    private String id_plan;

}
