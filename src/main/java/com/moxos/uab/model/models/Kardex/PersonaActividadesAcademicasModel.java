package com.moxos.uab.model.models.Kardex;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PersonaActividadesAcademicasModel {

    private Integer id_activades_academicas;
    private Integer id_persona_kardex;
    @NotBlank(message = "Debe seleccionar el tipo documento con lo cual fue designado a dicha actividad academica")
    private String tipo_documento;
    @NotBlank(message = "Debe ingresar el numero de resolucion")
    private String detalle_documento;
    @NotBlank(message = "Debe seleccionar el tipo de actividad academica")
    private String tipo_de_actividad;
    @Pattern(regexp = "[0-9]+", message = "Debe ser un valor numerico")
    @NotBlank(message = "Debe ingresar la gestion de la actividad academica")
    private String gestion;
    @NotBlank(message = "Debe seleccionar el mes en el que realizo la actividad academica")
    private String mes;
    @NotBlank(message = "Debe ingresar el detalle de la actividad academica realizada")
    private String descripcion_actividad;
    private Boolean aprobado;
    private Boolean mostrar;
    private String text_mostrar;
    private String id_estado;
    private String url_actividades_academicas;
    private MultipartFile Image;
    private String root;
    private Integer id_persona;
    private String UUID;

    public PersonaActividadesAcademicasModel() {
        this.setTipo_documento("");
        this.setDetalle_documento("");
        this.setTipo_de_actividad("");
        this.setGestion("");
        this.setMes("");
        this.setDescripcion_actividad("");
        this.setAprobado(Boolean.FALSE);
        this.setMostrar(Boolean.TRUE);
        this.setUrl_actividades_academicas("image.png");
    }
}
