package com.moxos.uab.util.word;


import java.util.List;

public class ParrafoBookMarks {

    private String title;
    private String contenido;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public static String ConvertHTML(List<ParrafoBookMarks> parrafos) {
        String Html = "", parrafo = "";
        for (ParrafoBookMarks p : parrafos) {
            parrafo += "<p style=\"text-align: justify; font-size: 12pt; line-height: 107%; font-family: arial, helvetica, sans-serif; \"><strong>" + p.getTitle().toUpperCase() + "</strong> " + p.getContenido() + " </p>";
        }
        Html = "<!DOCTYPE html><html><head></head><body>" + parrafo + "</body></html>";
        return Html;
    }
}
