package com.moxos.uab.model.models.programaanalitico.cronograma;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class CronogramaRequest {
    private int id_prg_a_cronograma;
    private int id_dct_programa_analitico;
    @NotBlank(message = "Debe ingresar el tipo de clase")
    private String tipo_de_clase;
    private String tipos_de_clases;
    @NotBlank(message = "Debe ingresar el titulo de clase")
    private String titulo_de_clase;
    @NotBlank(message = "Debe ingresar el tiempo a emplear en clase")
    private String tiempo_a_emplear;
    private String observaciones;
    private String id_estado;
    private int ult_usuario;
    private Date fecha;
    @NotBlank(message = "Debe ingresar la fecha de clase")
    private String text_fecha;
}
