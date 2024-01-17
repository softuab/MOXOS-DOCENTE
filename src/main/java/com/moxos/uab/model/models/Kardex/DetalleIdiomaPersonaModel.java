package com.moxos.uab.model.models.Kardex;

import lombok.Getter;
import lombok.Setter;

public class DetalleIdiomaPersonaModel {

    @Getter
    @Setter
    private Integer id_idioma;
    @Getter
    @Setter
    private String descripcion_idioma;
    @Getter
    @Setter
    private Boolean lee;
    @Getter
    @Setter
    private Boolean escribe;
    @Getter
    @Setter
    private Integer id_persona_kardex;
    @Getter
    @Setter
    private String url_idioma;
    @Getter
    @Setter
    private Boolean aprobado;
    @Getter
    @Setter
    private String id_estado;
    @Getter
    @Setter
    private String tipo_idioma;
    @Getter
    @Setter
    private Boolean mostrar;
   
}
