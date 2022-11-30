/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.model.Kardex;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetallePersonaExperienciaLaboral {

    private int id_experiencia_laboral;
    private int id_persona_kardex;
    private String institucion;
    private String detalle;
    private String cargoocupado;
    private String refrencia;
    private String calificacion;
    private String url_experiencia;
    private Date inicio;
    private Date fin;
    private String gestion;
    private String tipo_experiencia_laboral;
    private String id_estado;
    private Boolean aprobado;
    private Boolean mostrar;
}
