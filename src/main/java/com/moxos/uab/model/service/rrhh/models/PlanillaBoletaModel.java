package com.moxos.uab.model.service.rrhh.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class PlanillaBoletaModel {

    @JsonProperty("ID")
    private Long ID;
    @JsonProperty("FechaIngreso")
    private Date FechaIngreso;
    @JsonProperty("Rubro")
    private String Rubro;
    @JsonProperty("CI")
    private String CI;
    @JsonProperty("NombreCompleto")
    private String NombreCompleto;
    @JsonProperty("Cargo")
    private String Cargo;
    @JsonProperty("Dependendencia")
    private String Dependendencia;
    @JsonProperty("Localidad")
    private String Localidad;
    @JsonProperty("TotGanado")
    private Double TotGanado;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Date getFechaIngreso() {
        return FechaIngreso;
    }

    public void setFechaIngreso(Date FechaIngreso) {
        this.FechaIngreso = FechaIngreso;
    }

    public String getRubro() {
        return Rubro;
    }

    public void setRubro(String Rubro) {
        this.Rubro = Rubro;
    }

    public String getCI() {
        return CI;
    }

    public void setCI(String CI) {
        this.CI = CI;
    }

    public String getNombreCompleto() {
        return NombreCompleto;
    }

    public void setNombreCompleto(String NombreCompleto) {
        this.NombreCompleto = NombreCompleto;
    }

    public String getCargo() {
        return Cargo;
    }

    public void setCargo(String Cargo) {
        this.Cargo = Cargo;
    }

    public String getDependendencia() {
        return Dependendencia;
    }

    public void setDependendencia(String Dependendencia) {
        this.Dependendencia = Dependendencia;
    }

    public String getLocalidad() {
        return Localidad;
    }

    public void setLocalidad(String Localidad) {
        this.Localidad = Localidad;
    }

    public Double getTotGanado() {
        return TotGanado;
    }

    public void setTotGanado(Double TotGanado) {
        this.TotGanado = TotGanado;
    }

}

