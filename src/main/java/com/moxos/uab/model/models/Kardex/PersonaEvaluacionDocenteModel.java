package com.moxos.uab.model.models.Kardex;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PersonaEvaluacionDocenteModel {

    private Integer id_evaluacion_docente;
    private Integer id_persona_kardex;
    @Pattern(regexp = "[0-9]+", message = "Debe ser un valor numerico")
    @NotBlank(message = "Debe ingresar la gestion de la evaluacion")
    private String gestion;
    @NotBlank(message = "Debe seleccionar el periodo de la evaluacion")
    private String periodo;
    @NotBlank(message = "Debe ingresar la asignatura de la evaluacion")
    private String asignatura;
    @NotBlank(message = "Debe ingresar la puntuacion de la evaluacion")
    @Pattern(regexp = "[0-9]+", message = "Debe ser un valor numerico")
    private String puntaje;
    private String url_certificado_evaluacion;
    private String id_estado;
    private Boolean aprobado;
    private MultipartFile Image;
    private String root;
    private Integer id_persona;
    private String UUID;

    public PersonaEvaluacionDocenteModel() {
        this.setAprobado(Boolean.FALSE);
        this.setAsignatura("");
        this.setGestion("");
        this.setPeriodo("");
        this.setPuntaje("");
        this.setUrl_certificado_evaluacion("image.png");
    }
}
