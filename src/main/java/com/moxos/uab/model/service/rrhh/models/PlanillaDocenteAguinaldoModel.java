package com.moxos.uab.model.service.rrhh.models;

public class PlanillaDocenteAguinaldoModel {

    private int iDGestion;
    private int iDAguinaldo;
    private String carnet;
    private String tipo;

    public int getiDGestion() {
        return iDGestion;
    }

    public void setiDGestion(int iDGestion) {
        this.iDGestion = iDGestion;
    }

    public int getiDAguinaldo() {
        return iDAguinaldo;
    }

    public void setiDAguinaldo(int iDAguinaldo) {
        this.iDAguinaldo = iDAguinaldo;
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
