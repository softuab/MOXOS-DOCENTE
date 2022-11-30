/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.model.Kardex;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileKardexModel {

    private Integer id_persona;
    private Integer id_docente;
    private Integer id_programa;
    private Integer id_asignacion;
    private Integer periodo;
    private Integer gestion;
    private Integer id_tipo_evaluacion;
}
