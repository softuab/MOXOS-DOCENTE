package com.moxos.uab.model.models.Kardex;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Data
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
