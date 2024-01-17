package com.moxos.uab.model.models.Kardex;

import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.moxos.uab.util.Convert;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PersonaFormacionAcademicaModel {

    private Integer id_formacion;
    private Integer id_persona_kardex;
    @NotBlank(message = "Debe ingresar el detalle donde realizo el estudio")
    private String expedido;
    @NotBlank(message = "Debe ingresar el detalle de la universidad donde realizo el estudio")
    private String universidad;
    private Date fechaemision;
    @NotBlank(message = "Debe ingresar la fecha de emision del titulo")
    private String text_fechaemision;
    @NotNull(message = "Debe seleccionar el nivel de estudio alcanzado")
    private Integer id_nivelestudio;
    private Integer id_profesiones;
    @NotBlank(message = "Debe ingresar el detalle de la descripcion del titulo emitido")
    @NotNull(message = "Debe ingresar el detalle de la descripcion del titulo emitido")
    private String descripcion;
    private String url_formacion;
    @NotBlank(message = "Debe seleccionar el tipo de titulo alcanzado")
    @NotNull(message = "Debe seleccionar el tipo de titulo alcanzado")
    private String tipotitulo;
    @NotBlank(message = "Debe ingresar el detalle el numero del titulo emitido")
    @NotNull(message = "Debe ingresar el detalle el numero titulo emitido")
    private String numerotitulo;
    private Boolean eseducacionsuperor;
    private String text_eseducacionsuperor;
    private String id_estado;
    private Boolean aprobado;
    private String text_aprobado;
    private Boolean mostrar;
    private String text_mostrar;
    @NotBlank(message = "Debe seleccionar las carreras en la que sera presentado este documento")
    @NotNull(message = "Debe seleccionar las carreras en la que sera presentado este documento")
    private String id_programas;
    private Integer id_persona;
    private String UUID;
    private String root;
    private MultipartFile Image;

    public PersonaFormacionAcademicaModel() {
        this.setAprobado(Boolean.FALSE);
        this.setDescripcion("");
        this.setId_persona_kardex(id_persona_kardex);
        this.setEseducacionsuperor(Boolean.FALSE);
        this.setExpedido("");
        this.setUniversidad("");
        this.setFechaemision(new Date());
        this.setText_fechaemision(Convert.ToString(fechaemision, "yyyy-MM-dd"));
        this.setId_nivelestudio(1);
        this.setId_profesiones(1);
        this.setNumerotitulo("");
        this.setTipotitulo("");
        this.setUrl_formacion("image.png");
        this.setId_programas("");
        this.setMostrar(Boolean.TRUE);
    }
}
