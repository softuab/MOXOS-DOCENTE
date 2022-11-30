package org.fautapo.domain;

import lombok.Getter;
import lombok.Setter;
import org.fautapo.domain.Instituciones;

@Getter
@Setter
public class Universidades extends Instituciones {

    /* Private Fields */
    private int id_universidad;
    private String universidad;
}
