/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.model.programaanalitico;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProgramaAnaliticoCreacionModel {

    private Integer id_asignacion;
    private Integer id_materia;
    private String materia;
    private Integer id_grupo;
    private String grupo;
    private Integer id_programa;
    private Integer id_fase;
    private Integer id_tipo_evaluacion;
    private String tipo_evaluacion;
    private Integer gestion;
    private Integer periodo;
    private Integer id_docente;
    private String id_plan;
    private Integer id_departamento;
    private Integer id_mencion;
    @NotNull(message = "Debe seleccionar el programa analitico creado anteriormente")
    private Integer id_dct_programa_analitico;

}
