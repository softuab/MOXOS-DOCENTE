package com.moxos.uab.mybatis.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Adjuntos extends Tramites {

    /* Private Fields */
    private int id_adjunto;
    private String adjunto;
    private String archivo;

}