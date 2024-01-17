package com.moxos.uab.model.service.rrhh.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PlanillaAdministrativoAguinaldoModel {
    @JsonProperty("IDGestion")
    private Integer iDGestion;
    @JsonProperty("Gestion")
    private List<ItemViewModel> gestion;
    @JsonProperty("IDAguinaldo")
    private int iDAguinaldo;
    @JsonProperty("Aguinaldo")
    private List<ItemViewModel> aguinaldo;
    @JsonProperty("Carnet")
    private String carnet;
    @JsonProperty("Tipo")
    private String tipo;

    public Integer getiDGestion() {
        return iDGestion;
    }

    public void setiDGestion(Integer iDGestion) {
        this.iDGestion = iDGestion;
    }

    public List<ItemViewModel> getGestion() {
        return gestion;
    }

    public void setGestion(List<ItemViewModel> gestion) {
        this.gestion = gestion;
    }

    public int getiDAguinaldo() {
        return iDAguinaldo;
    }

    public void setiDAguinaldo(int iDAguinaldo) {
        this.iDAguinaldo = iDAguinaldo;
    }

    public List<ItemViewModel> getAguinaldo() {
        return aguinaldo;
    }

    public void setAguinaldo(List<ItemViewModel> aguinaldo) {
        this.aguinaldo = aguinaldo;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
