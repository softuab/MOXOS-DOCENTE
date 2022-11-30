/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JUserModel {

    @JsonProperty("usuario")
    private String usuario;
    @JsonProperty("id_persona")
    private String id_persona;
    @JsonProperty("ru")
    private String ru;
    @JsonProperty("nombres")
    private String nombres;
    @JsonProperty("registrado")
    private Integer registrado;
    @JsonProperty("registradomoodle")
    private Integer registradomoodle;
}
