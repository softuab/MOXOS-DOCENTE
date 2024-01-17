package com.moxos.uab.model.service.rrhh.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlanillaDocenteModel {

    @JsonProperty("Tipo")
    private String tipo;
    @JsonProperty("IDPlanilla")
    private Integer iDPlanilla;
    @JsonProperty("Carnet")
    private String carnet;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getiDPlanilla() {
        return iDPlanilla;
    }

    public void setiDPlanilla(Integer iDPlanilla) {
        this.iDPlanilla = iDPlanilla;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

}
