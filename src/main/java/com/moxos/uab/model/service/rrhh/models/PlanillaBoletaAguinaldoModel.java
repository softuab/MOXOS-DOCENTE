package com.moxos.uab.model.service.rrhh.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlanillaBoletaAguinaldoModel {

    @JsonProperty("ID")
    public long ID;
    @JsonProperty("FechaIngreso")
    public String FechaIngreso;
    @JsonProperty("Rubro")
    public String Rubro;
    @JsonProperty("CI")
    public String CI;
    @JsonProperty("NombreCompleto")
    public String NombreCompleto;
    @JsonProperty("Cargo")
    public String Cargo;
    @JsonProperty("Dependendencia")
    public String Dependendencia;
    @JsonProperty("Localidad")
    public String Localidad;
    @JsonProperty("TotGanado")
    public double TotGanado;

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getFechaIngreso() {
        return FechaIngreso;
    }

    public void setFechaIngreso(String FechaIngreso) {
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

    public double getTotGanado() {
        return TotGanado;
    }

    public void setTotGanado(double TotGanado) {
        this.TotGanado = TotGanado;
    }
}
