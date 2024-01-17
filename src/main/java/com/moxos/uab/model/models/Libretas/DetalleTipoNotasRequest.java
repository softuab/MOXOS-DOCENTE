package com.moxos.uab.model.models.Libretas;

import lombok.Data;

@Data
public class DetalleTipoNotasRequest {
    private String nro;
    private String detalle;
    private Integer tipoNota;
    private Integer cantidad;
    private Integer ponderacion;
}
