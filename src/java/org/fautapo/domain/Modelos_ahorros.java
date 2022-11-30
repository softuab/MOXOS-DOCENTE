package org.fautapo.domain;

import lombok.Getter;
import lombok.Setter;
import org.fautapo.domain.Materias;

@Getter
@Setter
public class Modelos_ahorros extends Materias {

    /* Private Fields */ 
    private int ponderacion_modelo;
    private int ponderacion_materia;
    private double nota_aprobacion;
    private String pago;
    private String id_plan;
    private int id_programa;
    private int id_fase_resolucion;

}
