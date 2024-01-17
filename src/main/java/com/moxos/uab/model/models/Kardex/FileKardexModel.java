package com.moxos.uab.model.models.Kardex;

import lombok.Data;

@Data
public class FileKardexModel {

    private Integer id_persona;
    private Integer id_docente;
    private Integer id_programa;
    private Integer id_asignacion;
    private Integer periodo;
    private Integer gestion;
    private Integer id_tipo_evaluacion;
}
