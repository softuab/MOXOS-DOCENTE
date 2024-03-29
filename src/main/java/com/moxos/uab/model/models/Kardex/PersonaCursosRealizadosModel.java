package com.moxos.uab.model.models.Kardex;

import java.util.Date;

import com.moxos.uab.util.Convert;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Data
public class PersonaCursosRealizadosModel {

    private Integer id_persona_kardex;
    private Integer id_cursos_realizados;
    @NotBlank(message = "Debe ingresar la institucion o universidad donde asistio")
    private String expedido_institucion;
    private Date fechapresentacion;
    private String text_fechapresentacion;
    @NotBlank(message = "Debe ingresar el numero del titulo del certificado de evento")
    private String nrotitulo;
    @NotBlank(message = "Debe ingresar la descripcion del evento")
    private String detalle;
    private String url_cursos;
    private String horas_academicas;
    @NotBlank(message = "Debe seleccionar el tipo de evento que asistio")
    private String tipo_eventos;
    private String duracion;
    private String id_estado;
    private Boolean aprobado;
    @NotBlank(message = "Debe seleccionar el tipo de evento organizacion que asistio")
    private String tipoorganizacion;
    private Boolean mostrar;
    private String text_mostrar;
    @NotBlank(message = "Debe seleccionar el objetivo del curso")
    private String objetivo_curso;
    private MultipartFile Image;
    private String root;
    private Integer id_persona;
    private String UUID;

    public PersonaCursosRealizadosModel() {
        this.setAprobado(Boolean.FALSE);
        this.setDetalle("");
        this.setId_persona_kardex(id_persona_kardex);
        this.setExpedido_institucion("");
        this.setFechapresentacion(new Date());
        this.setHoras_academicas("");
        this.setNrotitulo("");
        this.setTipo_eventos("");
        this.setUrl_cursos("image.png");
        this.setMostrar(Boolean.TRUE);
        this.setObjetivo_curso("");
        this.setText_fechapresentacion(Convert.ToString(fechapresentacion, "yyyy-MM-dd"));
    }
}
