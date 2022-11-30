package org.fautapo.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.fautapo.domain.Provincias;

@Getter
@Setter
public class Localidades extends Provincias {

    /* Private Fields */
    private int id_localidad;
    private String localidad;

}
