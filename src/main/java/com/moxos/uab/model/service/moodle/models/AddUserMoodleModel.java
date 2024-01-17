package com.moxos.uab.model.service.moodle.models;

import com.moxos.uab.validation.ComparePassword;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ComparePassword(first = "confirmar_password", second = "password", message = "Los campos de la contraseña deben coincidir")
public class AddUserMoodleModel {

    @NotBlank(message = "De ingresar el usuario para el registro o comunicar con DTIC para actualizar su correo")
    private String usuario;
    @NotBlank(message = "Debe ingresar su nombre")
    private String nombre;
    @NotBlank(message = "Debe ingresar su apellido")
    private String apellidos;
    private String password;
    private String confirmar_password;
    @NotBlank(message = "De ingresar el correo para el registro o comunicar con DTIC para actualizar su correo")
    private String correo;
    private Integer idnumber;
    private String aux;
    private Integer gestion;
    private Integer periodo;
    private String avanzado;
    private String nombres;
    private String clave;
}
