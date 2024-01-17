package com.moxos.uab.model.models.programaanalitico;

import lombok.Data;

@Data
public class ProgramaAnaliticoGuardarRequest {
    private Integer id_dct_programa_analitico;
    private String marco_referencial;
    private String justificacion;
    private String propositos;
    private String objetivo_desarrollador;
    private String objetivo_educativo;
    private String metodos_estrategias;
    private String recursos;
    private String sistema_evaluacion;
    private String id_estado;
    private String nro_resolucion;
    private String observacion;
    private String sistema_evaluacion_criterios;
    private Integer gestion;
    private Integer periodo;
}
