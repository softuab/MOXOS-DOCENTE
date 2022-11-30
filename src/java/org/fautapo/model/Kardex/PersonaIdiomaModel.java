/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.model.Kardex;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class PersonaIdiomaModel {

    private Integer id_persona_kardex;
    private Integer id_persona;
    private String UUID;
    private Integer id_idioma;
    @NotBlank(message = "Debe ingresar el detalle del curso idioma realizado")
    private String descripcion_idioma;
    private Boolean lee;
    private String text_lee;
    private Boolean escribe;
    private String text_escribe;
    private Boolean aprobado;
    private String text_aprobado;
    @NotBlank(message = "Debe seleccionar el tipo de lengua que habla")
    private String tipo_idioma;
    private Boolean mostrar;
    private String text_mostrar;
    private String url_idioma;
    private MultipartFile Image;
    private String root;

    public PersonaIdiomaModel() {
        this.setAprobado(Boolean.FALSE);
        this.setDescripcion_idioma("");
        this.setEscribe(Boolean.FALSE);
        this.setId_persona_kardex(id_persona_kardex);
        this.setLee(Boolean.FALSE);
        this.setUrl_idioma("image.png");
        this.setTipo_idioma("");
        this.setMostrar(Boolean.TRUE);
    }
}
