/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moxos.uab.mybatis.entity;

import java.util.Date;

import lombok.Data;

@Data
public class BiBliografia {
    private Integer id_prg_a_bibliografia;
    private Integer id_dct_programa_analitico;
    private Integer tipo_referncia;
    private String autor;
    private String anio;
    private Date fecha_publicacion;
    private String titulo;
    private String edicion;
    private String titulo_documento;
    private String lugar;
    private String editorial;
    private String volumen;
    private String numero;
    private String paginas;
    private Date fecha_consulta;
    private String url;
    private String ubicacion;
    private String id_estado;
    private Date fec_registro;
    private Date fec_modificacion;
    private Integer ult_usuario;
    private String tipobibliografia;
}
