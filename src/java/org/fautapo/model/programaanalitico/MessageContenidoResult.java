/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.model.programaanalitico;

import org.fautapo.model.MessageResult;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageContenidoResult extends MessageResult {

    private Integer id_prg_a_contenido;
    private String title;
}
