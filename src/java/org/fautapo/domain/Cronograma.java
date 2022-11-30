/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.domain; 
import java.util.Date;

/**
 *
 * @author FNZABALETAA
 */
public class Cronograma {

    private int id_prg_a_cronograma;
    private int id_dct_programa_analitico;
    private String tipo_de_clase;
    private String titulo_de_clase;
    private String tiempo_a_emplear;
    private String observaciones;
    private String id_estado;
    private Date fec_registro;
    private Date fec_modificacion;
    private int ult_usuario;
    private String dia_fecha;

    public String getDia_fecha() {
        return dia_fecha;
    }

    public void setDia_fecha(String dia_fecha) {
        this.dia_fecha = dia_fecha;
    }

    public int getId_prg_a_cronograma() {
        return id_prg_a_cronograma;
    }

    public void setId_prg_a_cronograma(int id_prg_a_cronograma) {
        this.id_prg_a_cronograma = id_prg_a_cronograma;
    }

    public int getId_dct_programa_analitico() {
        return id_dct_programa_analitico;
    }

    public void setId_dct_programa_analitico(int id_dct_programa_analitico) {
        this.id_dct_programa_analitico = id_dct_programa_analitico;
    }

    public String getTipo_de_clase() {
        return tipo_de_clase;
    }

    public void setTipo_de_clase(String tipo_de_clase) {
        this.tipo_de_clase = tipo_de_clase;
    }

    public String getTitulo_de_clase() {
        return titulo_de_clase;
    }

    public void setTitulo_de_clase(String titulo_de_clase) {
        this.titulo_de_clase = titulo_de_clase;
    }

    public String getTiempo_a_emplear() {
        return tiempo_a_emplear;
    }

    public void setTiempo_a_emplear(String tiempo_a_emplear) {
        this.tiempo_a_emplear = tiempo_a_emplear;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getId_estado() {
        return id_estado;
    }

    public void setId_estado(String id_estado) {
        this.id_estado = id_estado;
    }

    public Date getFec_registro() {
        return fec_registro;
    }

    public void setFec_registro(Date fec_registro) {
        this.fec_registro = fec_registro;
    }

    public Date getFec_modificacion() {
        return fec_modificacion;
    }

    public void setFec_modificacion(Date fec_modificacion) {
        this.fec_modificacion = fec_modificacion;
    }

    public int getUlt_usuario() {
        return ult_usuario;
    }

    public void setUlt_usuario(int ult_usuario) {
        this.ult_usuario = ult_usuario;
    }

}
