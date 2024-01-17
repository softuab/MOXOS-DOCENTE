package com.moxos.uab.model.models.login;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class TwoFactorRecoveryRequest {
    private String nombre_completo;
    private Integer id_docente;
    @NotNull(message = "El token es obligatorio")
    @NotEmpty(message = "El token es obligatorio")
    private String token;
}
