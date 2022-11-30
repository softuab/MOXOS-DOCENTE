/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.model.Kardex;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.fautapo.util.Convert;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class PersonaProduccionCientificaModel {

    private Integer id_produccion_cientifica;
    private Integer id_persona_kardex;
    @NotBlank(message = "Debe seleccionar el tipo de categoria de produccion cientifica")
    private String categoria;
    @NotBlank(message = "Debe ingresar el nombre de producto de produccion cientifica")
    private String nombre_producto;
    private Date fecha_certificacion;
    private String text_fecha_certificacion;
    private String url_portada_libro;
    private String id_estado;
    private Boolean aprobado;
    private Boolean mostrar;
    private String text_mostrar;
    @NotBlank(message = "Debe seleccionar tipo de produccion cientifica")
    private String tipo_produccion;
    private Boolean publicado;
    private String text_publicado;
    @NotBlank(message = "Debe seleccionar los programas a la cual esta asociado la produccion cientifica")
    private String id_programas;
    @NotBlank(message = "Debe seleccionar el objetivo de la produccion cientifica")
    private String tipo_de_recurso;
    private MultipartFile Image;
    private String root;
    private Integer id_persona;
    private String UUID;

    public PersonaProduccionCientificaModel() {
        this.setAprobado(Boolean.FALSE);
        this.setCategoria("");
        this.setFecha_certificacion(new Date());
        this.setText_fecha_certificacion(Convert.ToString(fecha_certificacion, "dd/MM/yyyy"));
        this.setId_persona_kardex(id_persona_kardex);
        this.setNombre_producto("");
        this.setUrl_portada_libro("image.png");
        this.setMostrar(Boolean.TRUE);
        this.setTipo_produccion("");
        this.setPublicado(Boolean.FALSE);
        this.setId_programas("");
        this.setTipo_de_recurso("");
    }
}
