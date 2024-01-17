package com.moxos.uab.mybatis.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Roles extends Clientes {

    /* Private Fields */
    private int id_usr_rol;
    private int id_rol_padre;
    private String descripcion;
    private Date fec_expiracion;

}