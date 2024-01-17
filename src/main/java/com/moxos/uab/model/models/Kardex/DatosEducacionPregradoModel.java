package com.moxos.uab.model.models.Kardex;

import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class DatosEducacionPregradoModel {

    private Integer id_persona_kardex;
    private Integer id_persona;
    @NotNull(message = "Debe selecionar el nivel de estudio alcanzado")
    private Integer id_nivelestudio;
    @NotNull(message = "Debe selecionar La profesion predeterminada")
    private Integer id_profesiones;
    @NotBlank(message = "Debe seleccionar la universidad dentro del sistema universitario")
    private String universidad;
    @NotBlank(message = "El detalle del numero de titulo de provision nacional es obligatorio")
    private String numerotituloprovision;
    private Date fechatituloprofesion;
    @NotBlank(message = "El detalle de la fecha emision de titulo de provision nacional es obligatorio")
    private String text_fechatituloprofesion;
    @NotNull(message = "Debe selecionar el colegion de profesionales o seleccionar ninguno si no esta registrado")
    private Integer id_colegio_profesionales;
    private String numeroregistroprofesionales;
    private String prefijo_profesional;
    private String imagen;
    private MultipartFile file;

}
