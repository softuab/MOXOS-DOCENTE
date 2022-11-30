/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.model;

import lombok.Getter;
import lombok.Setter;
import org.fautapo.validation.FieldMatch;

@Getter
@Setter
@FieldMatch(first = "confirmar_password", second = "password", message = "Los campos de la contraseña deben coincidir")
public class UserMoodleModel {

    private String idnumber;
    private String password;
    private String confirmar_password;
    private String firstname;
    private String lastname;
    private String email;
    private String usuario;
    private Integer indice;
    private Integer id;
}
