package com.moxos.uab.model.models.programaanalitico.formasorganizacion;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class FormasOrganizacionHorasRequest {

    private Integer id_prg_a_distribucion;
    @NotNull(message = "Debe ingresar cantidad de hora")
    private Integer horas;
    private Integer totalHoras;
    private Integer id_dct_programa_analitico;
    private Integer horasAnterior;
}
