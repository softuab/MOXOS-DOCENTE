package com.moxos.uab.model.models.login;

import com.moxos.uab.validation.ComparePassword;
import com.moxos.uab.validation.ValidPassword;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ComparePassword(first = "conf_nueva_clave", second = "nueva_clave", message = "Los campos de la contraseña deben coincidir")
public class PasswordRecoveryRequest {
    private Integer id_docente;
    @NotBlank(message = "La clave es obligatorio")
    @Size(min = 8, max = 30)
    @ValidPassword(message = "Debe introducir al menos 1 Mayuscula, 1 Minuscula y 1 Numero para su contraseña")
    private String nueva_clave;
    @NotBlank(message = "La clave de confirmacion es obligatorio")
    @Size(min = 8, max = 30)
    private String conf_nueva_clave;
}
