package com.moxos.uab.model.service.rrhh.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PlanillaAdministrativoModel {

    @JsonProperty("IDGestion")
    private Integer iDGestion;
    @JsonProperty("Gestion")
    private List<ItemViewModel> gestion;
    @JsonProperty("IdMes")
    private String idMes;
    @JsonProperty("Mes")
    private List<ItemViewModel> mes;
    @JsonProperty("Tipo")
    private String tipo;
    @JsonProperty("IDPlanilla")
    private Integer iDPlanilla;
    @JsonProperty("Carnet")
    private String carnet;

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

    public String getIdMes() {
        return idMes;
    }

    public void setIdMes(String idMes) {
        this.idMes = idMes;
    }

    public List<ItemViewModel> getMes() {
        return mes;
    }

    public void setMes(List<ItemViewModel> mes) {
        this.mes = mes;
    }

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
