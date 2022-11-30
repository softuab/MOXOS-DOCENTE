/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.poi;

import java.util.List;

/**
 *
 * @author FNZABALETAA
 */
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
            parrafo += "<p style=\"text-align: justify; font-size: 12pt; line-height: 107%; font-family: arial, helvetica, sans-serif; \"><strong>" + TextUtils.encodeHtml(p.getTitle().toUpperCase()) + "</strong> " + TextUtils.encodeHtml(p.getContenido()) + " </p>";
        }
        Html = "<!DOCTYPE html><html><head></head><body>" + parrafo + "</body></html>";
        return Html;
    }
}
