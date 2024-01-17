package com.moxos.uab.model.models.programaanalitico.formasorganizacion;

import lombok.Data;

@Data
public class ParametroBusquedaDistribucionRequest {
    private Integer id_prg_a_contenido;
    private Integer id_dct_programa_analitico;
    private Integer totalHoras;
}
