package com.moxos.uab.model.models.Kardex;

import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class DatosEducacionPosgradoModel {

    private Integer id_persona_kardex;
    private Integer id_persona;
    @NotNull(message = "Debe selecionar el nivel de estudio alcanzado")
    private int id_nivelestudio_posgrado;
    @NotBlank(message = "El detalle del numero de titulo de provision nacional es obligatorio")
    private String tituloposgrado;
    @NotBlank(message = "Debe seleccionar la universidad dentro del sistema universitario")
    private String emisiontituloposgrado;
    private Date fechaemisionposgrado;
    @NotBlank(message = "El detalle de la fecha emision de titulo es obligatorio")
    private String text_fechaemisionposgrado;
    private String imagetituloposgrado;
    private MultipartFile file;
}
