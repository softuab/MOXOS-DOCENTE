package com.moxos.uab.model.models.programaanalitico.contenidos;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ContenidosRequest {
    private Integer id_prg_a_contenido;
    private Integer id_dct_programa_analitico;
    @NotBlank(message = "Debe ingresar el detalle del contenido")
    private String contenido;
    @NotBlank(message = "Debe ingresar el objetivo instructivo")
    private String objetivo_instructivo;
    @NotBlank(message = "Debe ingresar los conocimientos")
    private String conocimientos;
    @NotBlank(message = "Debe ingresar las habilidades")
    private String habilidades;
    @NotBlank(message = "Debe ingresar los valores")
    private String valores;
    private String id_estado;
    private String mapa;
    private MultipartFile Image;
    private Integer gestion;
    private Integer periodo;
    private String materia;
    private String grupo;
}
