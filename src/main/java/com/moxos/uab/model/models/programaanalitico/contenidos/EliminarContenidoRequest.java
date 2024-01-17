package com.moxos.uab.model.models.programaanalitico.contenidos;

import lombok.Data;

@Data
public class EliminarContenidoRequest {
    private Integer id_prg_a_contenido;
    private Integer id_dct_programa_analitico;
    private String unidad;
}
