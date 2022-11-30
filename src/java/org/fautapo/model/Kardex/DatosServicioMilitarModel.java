/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.model.Kardex;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class DatosServicioMilitarModel {

    private Integer id_persona_kardex;
    private Integer id_persona;
    @NotBlank(message = "El detalle del numero de libreta es obligatorio")
    private String numerolibreta;
    @NotBlank(message = "El detalle de la matricula es obligatorio")
    private String matriculalibreta;
    @NotBlank(message = "El detalle el escalon es obligatorio")
    private String escalon;
    @NotBlank(message = "El detalle del año de servicio es obligatorio")
    private String aserviciomilitar;
    private String imagelibretamilitar;
    private MultipartFile Image;
}
