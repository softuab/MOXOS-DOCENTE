package com.moxos.uab.model.models.Moodle;

import com.moxos.uab.validation.ComparePassword;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ComparePassword(first = "confirmar_password", second = "password", message = "Los campos de la contraseña deben coincidir")
public class UserMoodleRequest {

    private String idnumber;
    @NotBlank(message = "Debe Introducir su contraseña")
    private String password;
    @NotBlank(message = "Debe Introducir su contraseña")
    private String confirmar_password;
    private String firstname;
    private String lastname;
    private String email;
    private String username;
    private Integer indice;
    private Long id;
}
