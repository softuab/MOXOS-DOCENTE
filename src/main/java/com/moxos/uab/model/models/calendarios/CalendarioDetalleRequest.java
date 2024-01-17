package com.moxos.uab.model.models.calendarios;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class CalendarioDetalleRequest {

    private Date fecha_inicio;
    private Date fecha_fin;
    private String fechainicio;
    private String fechafin;
    private int gestion;
    private int periodo;
    private String titulo;
    private String carrera;
    private String observacion;
    private String tipoNota;
    private boolean esAmpliacion;

    public CalendarioDetalleRequest(Date fecha_inicio, Date fecha_fin) {
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
    }

    public boolean isEsAmpliacion() {
        return esAmpliacion;
    }

    public void setEsAmpliacion(boolean esAmpliacion) {
        this.esAmpliacion = esAmpliacion;
    }

    public String getTipoNota() {
        return tipoNota;
    }

    public void setTipoNota(String tipoNota) {
        this.tipoNota = tipoNota;
    }

    public int getGestion() {
        return gestion;
    }

    public void setGestion(int gestion) {
        this.gestion = gestion;
    }

    public int getPeriodo() {
        return periodo;
    }

    public String getFechainicio() {
        Calendar c = Calendar.getInstance();
        c.setTime(this.fecha_inicio);
        String dayOfWeek = c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, new Locale("ES")).toUpperCase();
        fechainicio = dayOfWeek + " " + String.valueOf(c.get(Calendar.DAY_OF_MONTH)) + "/" + Mes(c.get(Calendar.MONTH) + 1);
        return fechainicio;
    }

    public String getFechafin() {
        Calendar c = Calendar.getInstance();
        c.setTime(this.fecha_fin);
        String dayOfWeek = c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, new Locale("ES")).toUpperCase();
        fechafin = dayOfWeek + " " + String.valueOf(c.get(Calendar.DAY_OF_MONTH)) + "/" + Mes(c.get(Calendar.MONTH) + 1);
        return fechafin;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    private String Mes(int mes) {

        switch (mes) {
            case 1:
                return "ENE";
            case 2:
                return "FEB";
            case 3:
                return "MAR";
            case 4:
                return "ABR";
            case 5:
                return "MAY";
            case 6:
                return "JUN";
            case 7:
                return "JUL";
            case 8:
                return "AGO";
            case 9:
                return "SEP";
            case 10:
                return "OCT";
            case 11:
                return "NOV";
            case 12:
                return "DIC";
            default:
                return "";
        }
    }
}
