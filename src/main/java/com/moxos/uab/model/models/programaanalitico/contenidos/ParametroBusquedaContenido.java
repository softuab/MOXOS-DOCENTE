package com.moxos.uab.model.models.programaanalitico.contenidos;

import lombok.Data;

@Data
public class ParametroBusquedaContenido {
    private String materia;
    private String grupo;
    private Integer gestion;
    private Integer periodo;
    private Integer id_prg_a_contenido;
}
