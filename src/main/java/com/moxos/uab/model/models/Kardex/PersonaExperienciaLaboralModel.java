package com.moxos.uab.model.models.Kardex;

import java.util.Date;

import com.moxos.uab.util.Convert;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Data
public class PersonaExperienciaLaboralModel {

    private int id_experiencia_laboral;
    private int id_persona_kardex;
    @NotBlank(message = "Debe ingresar la institucion o universidad donde trabajo")
    private String institucion;
    @NotBlank(message = "Debe ingresar la descripcion del cargo")
    private String detalle;
    @NotBlank(message = "Debe ingresar el cargo que ocupo")
    private String cargoocupado;
    @NotBlank(message = "Debe ingresar la referencia del cargo")
    private String refrencia;
    @NotBlank(message = "Debe ingresar la calificacion de años de servicio del cargo")
    private String calificacion;
    private String url_experiencia;
    private Date inicio;
    private String text_inicio;
    private Date fin;
    private String text_fin;
    @NotBlank(message = "Debe ingresar la gestion cuando ingreso al cargo")
    private String gestion;
    @NotBlank(message = "Debe seleccionar el tipo de experiencia laboral")
    private String tipo_experiencia_laboral;
    private Boolean aprobado;
    private Boolean mostrar;
    private String text_mostrar;
    private MultipartFile Image;
    private String root;
    private Integer id_persona;
    private String UUID;

    public PersonaExperienciaLaboralModel() {
        this.setAprobado(Boolean.FALSE);
        this.setCalificacion("");
        this.setId_persona_kardex(id_persona_kardex);
        this.setCargoocupado("");
        this.setDetalle("");
        this.setFin(new Date());
        this.setGestion("");
        this.setInicio(new Date());
        this.setInstitucion("");
        this.setRefrencia("");
        this.setTipo_experiencia_laboral("");
        this.setUrl_experiencia("image.png");
        this.setMostrar(Boolean.TRUE);
        this.setText_fin(Convert.ToString(fin, "yyyy-MM-dd"));
        this.setText_inicio(Convert.ToString(inicio, "yyyy-MM-dd"));
    }
}
