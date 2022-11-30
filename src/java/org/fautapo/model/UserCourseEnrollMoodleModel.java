/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCourseEnrollMoodleModel {
    
    private Integer id_materia;
    private Integer id_grupo;
    private Integer id_programa;
    private Integer gestion;
    private Integer periodo;
    private Integer idcurso;
    private String materia;
    private String seleccionados;
}
