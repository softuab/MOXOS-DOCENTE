/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.model.Kardex;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class PersonaProyectoDocenteModel {

    private Integer id_personas_proyecto;
    @NotBlank(message = "Debe ingresar el nombre del proyecto")
    private String nombreproyecto;
    private String inversion;
    @NotBlank(message = "Debe ingresar el detalle del financiador del proyecto")
    private String financiador;
    @NotBlank(message = "Debe ingresar el origen del proyecto")
    private String origendelproyectoproyecto;
    @NotNull(message = "Debe ingresar la gestion del proyecto")
    private Integer gestion;
    @NotBlank(message = "Debe seleccionar el estado del proyecto")
    private String estado;
    @NotBlank(message = "Debe seleccionar la funcion que realiza el docente en el proyecto")
    private String funcion;
    @NotBlank(message = "Debe seleccionar la modalidad graduacion del proyecto academico si corresponde")
    private String modalidad;
    @NotBlank(message = "Debe ingresar la duracion del proyecto")
    private String duracionproyecto;
    @NotBlank(message = "Debe seleccionar el tipo del proyecto")
    private String tipodeproyecto;
    private Boolean representaciondirigencial;
    private String text_representaciondirigencial;
    private String documento;
    private Boolean mostrar;
    private String text_mostrar;
    private Integer id_persona_kardex;
    private String id_estado;
    private Boolean aprobado;
    private MultipartFile Image;
    private String root;
    private Integer id_persona;
    private String UUID;

    public PersonaProyectoDocenteModel() {
        this.setAprobado(Boolean.FALSE);
        this.setDocumento("");
        this.setDuracionproyecto("");
        this.setEstado("");
        this.setFinanciador("");
        this.setFuncion("");
        this.setInversion("");
        this.setModalidad("");
        this.setNombreproyecto("");
        this.setOrigendelproyectoproyecto("");
        this.setRepresentaciondirigencial(Boolean.FALSE);
        this.setMostrar(Boolean.TRUE);
    }
}
