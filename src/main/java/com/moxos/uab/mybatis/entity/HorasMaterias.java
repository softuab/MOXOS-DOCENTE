package com.moxos.uab.mybatis.entity;

import lombok.Data;

@Data
public class HorasMaterias {
    private Integer hrs_teoricas;
    private Integer hrs_practicas;
    private Integer total;
    private Integer totalsemana;
    private String programa;
    private String tipo_materia;
}
