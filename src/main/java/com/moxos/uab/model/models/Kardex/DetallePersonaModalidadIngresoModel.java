package com.moxos.uab.model.models.Kardex;

import lombok.Data;

import java.util.Date;

@Data
public class DetallePersonaModalidadIngresoModel {

    private Integer id_modalidadingreso;
    private Integer id_persona_kardex;
    private Integer id_programa;
    private String programa;
    private String modalidadingreso;
    private Date fechaingreso;
    private String url_modalidadingreso;
    private Boolean aprobado;
    private String id_estado;
    private Integer id_facultad;
    private Boolean mostrar;
}
