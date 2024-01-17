package com.moxos.uab.model.models.utility;

import com.moxos.uab.validation.VerificarClave;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ParametroEntradaRequest {
    private Integer id_prg_plan;
    private Integer id_tipo_evaluacion;
    private Integer id_programa;
    private Integer id_departamento;
    private Integer periodo;
    private Integer gestion;
    @NotBlank(message = "Debe Introducir su contraseña")
    @VerificarClave(message = "Contraseña incorrecta")
    private String clave;
}
