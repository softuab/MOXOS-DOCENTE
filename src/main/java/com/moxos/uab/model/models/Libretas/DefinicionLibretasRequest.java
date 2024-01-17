package com.moxos.uab.model.models.Libretas;

import lombok.Data;

@Data
public class DefinicionLibretasRequest {

    private Integer id_asignacion;
    private Integer id_programa;
    private Integer id_modelo_ahorro;
    private Integer id_tipo_evaluacion;
    private DetalleTipoNotasRequest[] detalle;
}
