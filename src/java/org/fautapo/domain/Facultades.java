package org.fautapo.domain;

import lombok.Getter;
import lombok.Setter;
import org.fautapo.domain.Universidades;

@Getter
@Setter
public class Facultades extends Universidades {

    /* Private Fields */
    private int id_facultad;
    private int id_sede;
    private String facultad;
    private String codigo;
    private String decano;

}
