/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.model.Kardex;

import java.util.Date;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.fautapo.util.Convert;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class PersonaModalidadIngresoModel {
    
    private Integer id_modalidadingreso;
    private Integer id_persona_kardex;
    @NotNull(message = "Debe seleccionar la carrera en la que ingreso")
    private Integer id_programa;
    @NotBlank(message = "Debe seleccionar una modalidad de ingreso")
    private String modalidadingreso;
    private Date fechaingreso;
    private String text_fechaingreso;
    private String url_modalidadingreso;
    private Boolean aprobado;
    private String id_estado;
    private Boolean mostrar;
    private String text_mostrar;
    private MultipartFile Image;
    private String root;
    private Integer id_persona;
    private String UUID;
    
    public PersonaModalidadIngresoModel() {
        this.setAprobado(Boolean.FALSE);
        this.setModalidadingreso("");
        this.setFechaingreso(new Date());
        this.setText_fechaingreso(Convert.ToString(fechaingreso, "dd/MM/yyyy"));
        this.setId_persona_kardex(id_persona_kardex);
        this.setId_programa(1);        
        this.setUrl_modalidadingreso("image.png");
        this.setMostrar(Boolean.TRUE);
    }
}
