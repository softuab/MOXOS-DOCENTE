package com.moxos.uab.model.models.login;

import com.moxos.uab.validation.ValidToken;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
@Data
@ValidToken(first = "id_docente", second = "token", message = "El token es invalido o expiro el tiempo limite del token")
public class TokenRecoveryRequest {

    private String nombre_completo;
    private Integer id_docente;
    @NotNull(message = "El token es obligatorio")
    @NotEmpty(message = "El token es obligatorio")
    private String token;
}
