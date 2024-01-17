package com.moxos.uab.mybatis.entity;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
 */
@Getter
@Setter
public class Accesos implements Serializable {

    /* Private Fields */
    private int id_acceso;
    private String acceso;
    private List<Programas> listaFacultades;
    private List<Programas> listaProgramas;
    private List<Departamentos> listaDepartamentos;
    private List<Planes> listaPlanes;

}