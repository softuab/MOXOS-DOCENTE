package com.moxos.uab.model.models.login;

import com.moxos.uab.validation.ExisteUsuario;
import com.moxos.uab.validation.VerificarCapchat;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class RecoveryPasswordRequest {

    @NotNull(message = "El correo es obligatorio")
    @NotEmpty(message = "El correo es obligatorio")
    @Email(message = "Debe ser un correo electronico valido")
    @ExisteUsuario
    private String correo;
    @NotNull(message = "El codigo capchat es obligatorio")
    @NotEmpty(message = "El codigo capchat es obligatorio")
    @VerificarCapchat
    private String capchat;
}
