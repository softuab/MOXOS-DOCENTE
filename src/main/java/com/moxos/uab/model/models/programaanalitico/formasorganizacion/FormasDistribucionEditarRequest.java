package com.moxos.uab.model.models.programaanalitico.formasorganizacion;

import lombok.Data;

@Data
public class FormasDistribucionEditarRequest {
    private Integer id_prg_a_distribucion;
    private Integer horas;
    private Integer totalHoras;
    private Integer horasAnterior;
}
