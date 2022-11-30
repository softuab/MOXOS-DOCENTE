/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.model.programaanalitico;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContenidosModel {

    private Integer id_prg_a_contenido;
    private Integer id_dct_programa_analitico;
    private String contenido;
    private String objetivo_instructivo;
    private String conocimientos;
    private String habilidades;
    private String valores;
    private String id_estado;
    private int ult_usuario;
    private String mapa;
}
