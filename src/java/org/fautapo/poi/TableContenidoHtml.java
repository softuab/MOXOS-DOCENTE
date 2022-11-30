/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.poi;

import java.util.ArrayList;
import java.util.List;
import org.fautapo.domain.BiBliografia;
import org.fautapo.domain.Contenidos;
import org.fautapo.domain.Cronograma;
import org.fautapo.domain.DistribucionTiempos;
import org.fautapo.domain.FormasDistribucion;
import org.fautapo.domain.FormasOrganizacion;
import org.fautapo.domain.FormasTrabajoAula;

/**
 *
 * @author FNZABALETAA
 */
public class TableContenidoHtml {

    private static String TABLE = "<table border=1 cellspacing=0 cellpadding=0 style='width=1040px; border-collapse:collapse;border:none;mso-border-alt:solid windowtext .5pt;'>";
    private static String TITLE = "<tr> <td width=100% colspan=3 style='width:100%;border:solid windowtext 1.0pt;background:#D9D9D9;padding:0.5cm 5.4pt 0.5cm 5.4pt;font-size:12.0pt;font-family:\"Arial\",sans-serif'><strong>${title}</strong></td></tr>";
    private static String CAPTION = "<tr><td width=33.33% valign=top style='width:33.33%;border:solid windowtext 1.0pt;background:#D9D9D9;padding:0.5cm 5.4pt 0.5cm 5.4pt;font-size:12.0pt;font-family:\"Arial\",sans-serif;text-align:center;'><strong>SISTEMA DE<br/> CONOCIMIENTOS</strong></td><td width=33.33% valign=top style='width:33.33%;border:solid windowtext 1.0pt;background:#D9D9D9;padding:0.5cm 5.4pt 0.5cm 5.4pt;font-size:12.0pt;font-family:\"Arial\",sans-serif;text-align:center;'><strong>SISTEMA DE<br/> HABILIDADES</strong></td><td width=33.33% valign=top style='width:33.33%;border:solid windowtext 1.0pt;background:#D9D9D9;padding:0.5cm 5.4pt 0.5cm 5.4pt;font-size:12.0pt;font-family:\"Arial\",sans-serif;text-align:center;'><strong>SISTEMA DE<br/> VALORES</strong></td></tr>";
    private static String CONTENIDO = "<tr ><td width=33.33% valign=top style='width:33.33%;border:solid windowtext 1.0pt;padding:0.5cm 5.4pt 0.5cm 5.4pt;font-size:12.0pt;font-family:\"Arial\",sans-serif;text-align: justify;'>${contenido1}</td><td width=33.33% valign=top style='width:33.33%;border:solid windowtext 1.0pt;padding:0.5cm 5.4pt 0.5cm 5.4pt;font-size:12.0pt;font-family:\"Arial\",sans-serif;text-align: justify;'>${contenido2}</td><td width=33.33% valign=top style='width:33.33%;border:solid windowtext 1.0pt;padding:0.5cm 5.4pt 0.5cm 5.4pt;font-size:12.0pt;font-family:\"Arial\",sans-serif;text-align: justify;'>${contenido3}</td></tr>";
    private static String ENDTABLE = "</table>";

    public static String ConvertTableHtml(List<Contenidos> contenidos) {
        String html = TABLE, title = "", contenido = "";
        int i = 1;
        for (Contenidos c : contenidos) {
            title = TITLE;
            title = title.replace("${title}", TextUtils.encodeHtml("UNIDAD TEMÁTICA " + i + ": " + c.getContenido()));
            contenido = CONTENIDO;
            contenido = contenido.replace("${contenido1}", GetParrafos(c.getConocimientos()));
            contenido = contenido.replace("${contenido2}", GetParrafos(c.getHabilidades()));
            contenido = contenido.replace("${contenido3}", GetParrafos(c.getValores()));
            html += (title + CAPTION + contenido);
            i++;
        }
        html += ENDTABLE;
        return html;
    }

    public static String addFormasOrganizacion(List<FormasTrabajoAula> listaformastrabajoaula, List<FormasOrganizacion> listaformasorganizacion, List<DistribucionTiempos> listardistribuciontiempos, List<FormasDistribucion> distribucionsubtotal) {
        String aula = "";
        String distribucion = "";
        for (FormasTrabajoAula a : listaformastrabajoaula) {
            aula += (a.getSigla_formas() + " - " + a.getFormas()) + "<br>";
        }
        String formas = "";
        for (FormasOrganizacion a : listaformasorganizacion) {
            formas += (a.getSigla_formas() + " - " + a.getFormas()) + "<br>";
        }
        distribucion = "<p style='font-size:12.0pt;font-family:\"Arial\",sans-serif;'><strong>" + TextUtils.encodeHtml("Distribución de tiempo (en horas clases)") + "</strong></p>";
        distribucion += addFormasDistibucion(listaformastrabajoaula, listaformasorganizacion, listardistribuciontiempos, distribucionsubtotal);
        return "<!DOCTYPE html><html><head></head><body><p style='font-size:12.0pt;font-family:\"Arial\",sans-serif;'><strong>Tipolog&iacute;a de clases a utilizar:</strong></p>" + "<p style='font-size:12.0pt;font-family:\"Arial\",sans-serif;'>" + formas + "</p>" + "<p style='font-size:12.0pt;font-family:\"Arial\",sans-serif;'><strong>Trabajo fuera del aula</strong></p>" + "<p style='font-size:12.0pt;font-family:\"Arial\",sans-serif;'>" + aula + "</p>" + distribucion + "</body></html>";
    }

    private static String addFormasDistibucion(List<FormasTrabajoAula> listaformastrabajoaula, List<FormasOrganizacion> listaformasorganizacion, List<DistribucionTiempos> listardistribuciontiempos, List<FormasDistribucion> distribucionsubtotal) {
        String distribucionhtml = "";
        List<Float> columnWidths = new ArrayList<>();
        columnWidths.add(30.0f);
        int cantidad = listaformasorganizacion.size();
        if (cantidad != 0) {
            float tamcolumn = 50 / cantidad;
            for (int i = 0; i < cantidad; i++) {
                columnWidths.add(tamcolumn);
            }
        }
        columnWidths.add(10.0f);
        columnWidths.add(10.0f);
        int totalcolumnas = columnWidths.size() > 3 ? columnWidths.size() - 3 : 0;
        distribucionhtml = "<table style=\"border-collapse: collapse; width: 100%;\" border=\"1\"><tbody>";
        distribucionhtml += "<tr style=\"height: 50px;\">";
        distribucionhtml += "<td style=\"width: " + columnWidths.get(0) + "% ;\" rowspan=\"2\"><p style='font-size:12.0pt;font-family:\"Arial\",sans-serif;text-align: center;'><strong>UNIDAD TEM&Aacute;TICA</strong></p></td>";
        if (totalcolumnas > 0) {
            distribucionhtml += "<td style=\"width: " + columnWidths.get(1) + "%; height: 50px;\" colspan=\"" + totalcolumnas + "\"> <p style='font-size:12.0pt;font-family:\"Arial\",sans-serif;text-align: center;'><strong>TIPOS DE CLASES A UTILIZAR EN LA ASIGNATURA</strong></p> </td>";
        }
        distribucionhtml += "<td style=\"width: " + columnWidths.get(columnWidths.size() - 2) + "%; height: 68px;\" rowspan=\"2\"><p style='font-size:12.0pt;font-family:\"Arial\",sans-serif;text-align: center;'><strong>TRABAJO FUERA DEL AULA</strong></p></td>";
        distribucionhtml += "<td style=\"width: " + columnWidths.get(columnWidths.size() - 1) + "%; height: 68px;\" rowspan=\"2\"><p style='font-size:12.0pt;font-family:\"Arial\",sans-serif;text-align: center;'><strong>TOTAL, HORAS</strong></p></td>";
        distribucionhtml += "</tr>";
        distribucionhtml += "<tr style=\"height: 18px;\">";
        if (totalcolumnas > 0) {
            int j = 1;
            for (FormasOrganizacion formas : listaformasorganizacion) {
                distribucionhtml += "<td style=\"width: " + columnWidths.get(j) + "%; height: 18px;\"><p style='font-size:12.0pt;font-family:\"Arial\",sans-serif; text-align: center;'> <strong>" + formas.getSigla_formas() + "</strong></p> </td>";
                j++;
            }
        }
        distribucionhtml += "</tr>";
        int totalhoras = 0;
        for (DistribucionTiempos distribucion : listardistribuciontiempos) {
            distribucionhtml += "<tr style=\"height: 15px;\">";
            distribucionhtml += "<td style=\"width: " + columnWidths.get(0) + "%;\"><p style='font-size:12.0pt;font-family:\"Arial\",sans-serif; text-align: center;'> <strong>" + distribucion.getContenidos().getContenido() + "</strong></p> </td>";
            if (totalcolumnas > 0) {
                int j = 1;
                totalhoras = 0;
                for (FormasOrganizacion formas : listaformasorganizacion) {
                    FormasDistribucion dist = distribucion.getFormasdistribucion().stream().filter(p -> p.getId_prg_a_formas() == formas.getId_prg_a_formas()).count() == 0 ? null : distribucion.getFormasdistribucion().stream().filter(p -> p.getId_prg_a_formas() == formas.getId_prg_a_formas()).findFirst().get(); //.stream().filter(p -> p.getId_prg_a_formas() == f.getId_prg_a_formas()).findFirst().get()
                    if (dist != null) {
                        distribucionhtml += "<td style=\"width: " + columnWidths.get(j) + "%;\"><p style='font-size:12.0pt;font-family:\"Arial\",sans-serif; text-align: center;'>" + String.valueOf(dist.getHoras()) + " Hr." + "</p> </td>";
                        totalhoras += dist.getHoras();
                    } else {
                        distribucionhtml += "<td style=\"width: " + columnWidths.get(j) + "%;\"><p style='font-size:12.0pt;font-family:\"Arial\",sans-serif; text-align: center;'>0 Hr." + "</p> </td>";
                        totalhoras += 0;
                    }
                    j++;
                }
            }
            String trabajaaula = "";
            for (FormasTrabajoAula aulas : distribucion.getFormatrabajoaula()) {
                trabajaaula += aulas.getSigla_formas() + "<br>";
            }
            distribucionhtml += "<td style=\"width: " + columnWidths.get(columnWidths.size() - 2) + "%;\"><p style='font-size:12.0pt;font-family:\"Arial\",sans-serif; text-align: center;'>" + trabajaaula + "</p> </td>";
            distribucionhtml += "<td style=\"width: " + columnWidths.get(columnWidths.size() - 1) + "%;\"><p style='font-size:12.0pt;font-family:\"Arial\",sans-serif; text-align: center;'>" + totalhoras + " Hrs. </p> </td>";
            distribucionhtml += "</tr>";
        }
        distribucionhtml += "<tr style=\"height: 15px;\">";
        distribucionhtml += "<td style=\"width: " + columnWidths.get(0) + "%;\"><p style='font-size:12.0pt;font-family:\"Arial\",sans-serif; text-align: center;'> <strong>TOTAL, HORAS</strong></p> </td>";
        int subtotalhoras = 0;
        if (totalcolumnas > 0) {
            int j = 1;
            for (FormasOrganizacion formas : listaformasorganizacion) {
                int subtotal = (int) distribucionsubtotal.stream().filter(p -> p.getId_prg_a_formas() == formas.getId_prg_a_formas()).mapToDouble(p -> p.getHoras()).sum();
                distribucionhtml += "<td style=\"width: " + columnWidths.get(j) + "%;\"><p style='font-size:12.0pt;font-family:\"Arial\",sans-serif; text-align: center;'>" + subtotal + " Hr." + "</p> </td>";
                subtotalhoras += subtotal;
                j++;
            }
        }
        distribucionhtml += "<td style=\"width: " + columnWidths.get(columnWidths.size() - 2) + "%;\"><p style='font-size:12.0pt;font-family:\"Arial\",sans-serif; text-align: center;'></p> </td>";
        distribucionhtml += "<td style=\"width: " + columnWidths.get(columnWidths.size() - 1) + "%;\"><p style='font-size:12.0pt;font-family:\"Arial\",sans-serif; text-align: center;'>" + subtotalhoras + " Hrs. </p> </td>";
        distribucionhtml += "</tr>";
        distribucionhtml += "</tbody></table>";
        return distribucionhtml;
    }

    public static String addBibliografia(List<BiBliografia> detallebibliografia) {
        String bibliografia = "";
        bibliografia += "<ol>";
        for (BiBliografia b : detallebibliografia) {
            bibliografia += "<li>";
            bibliografia += "<p style='font-size:12.0pt;font-family:\"Arial\",sans-serif;'>" + b.getAutor() + ". (" + b.getAnio() + "), " + " <cite>" + b.getTitulo() + "</cite>, " + ", " + b.getLugar_edicion() + "(paginas " + b.getPaginas() + ")" + "<br> Ubicacion del recurso: " + b.getUbicacion() + "</p>";
            bibliografia += "</li>";
        }
        bibliografia += "</ol>";
        return "<!DOCTYPE html><html><head></head><body>" + bibliografia + "</body></html>";
    }

    public static String addCronograma(List<Cronograma> cronograma) {
        String cronogramahtml = "";
        cronogramahtml += "<table style=\"border-collapse: collapse; width: 100%; height: 18px;\" border=\"1\"><tbody>";
        cronogramahtml += "<tr style=\"height: 18px;\">\n"
                + "<td style='width: 5.94545%; height: 18px; text-align: center;font-size:12.0pt;font-family:\"Arial\",sans-serif; background:#D9D9D9;'><strong>N&ordm;</strong></td>\n"
                + "<td style='width: 19.3958%; height: 18px; text-align: center;font-size:12.0pt;font-family:\"Arial\",sans-serif; background:#D9D9D9;'><strong>D&iacute;a y Fecha</strong></td>\n"
                + "<td style='width: 12.3782%; height: 18px; text-align: center;font-size:12.0pt;font-family:\"Arial\",sans-serif; background:#D9D9D9;'><strong>Tipo de clase</strong></td>\n"
                + "<td style='width: 41.423%; height: 18px; text-align: center;font-size:12.0pt;font-family:\"Arial\",sans-serif; background:#D9D9D9;'><strong>T&iacute;tulo de la clase (asunto)</strong></td>\n"
                + "<td style='width: 19.0059%; height: 18px; text-align: center;font-size:12.0pt;font-family:\"Arial\",sans-serif; background:#D9D9D9;'><strong>Tiempo a emplear (en horas)</strong></td>\n"
                + "<td style='width: 1.94932%; height: 18px; text-align: center;font-size:12.0pt;font-family:\"Arial\",sans-serif; background:#D9D9D9;'><strong>Observaciones y Cumplimiento1 2</strong></td>\n"
                + "</tr>";
        int i = 1;
        for (Cronograma b : cronograma) {
            cronogramahtml += "<tr style=\"height: 18px;\">\n"
                    + "<td style='width: 5.94545%;padding:0.5cm 5.4pt 0.5cm 5.4pt; text-align: justify; font-size:12.0pt;font-family:\"Arial\",sans-serif;'>" + i + "</td>\n"
                    + "<td style='width: 19.3958%; padding:0.5cm 5.4pt 0.5cm 5.4pt; text-align: justify; font-size:12.0pt;font-family:\"Arial\",sans-serif;'>" + TextUtils.encodeHtml(b.getDia_fecha()) + "</td>\n"
                    + "<td style='width: 12.3782%;padding:0.5cm 5.4pt 0.5cm 5.4pt; text-align: justify; font-size:12.0pt;font-family:\"Arial\",sans-serif;'>" + TextUtils.encodeHtml(b.getTipo_de_clase()) + "</td>\n"
                    + "<td style='width: 41.423%; padding:0.5cm 5.4pt 0.5cm 5.4pt; text-align: justify; font-size:12.0pt;font-family:\"Arial\",sans-serif;'>" + TextUtils.encodeHtml(b.getTitulo_de_clase()) + "</td>\n"
                    + "<td style='width: 19.0059%; padding:0.5cm 5.4pt 0.5cm 5.4pt; text-align: justify; font-size:12.0pt;font-family:\"Arial\",sans-serif;'>" + TextUtils.encodeHtml(b.getTiempo_a_emplear()) + "</td>\n"
                    + "<td style='width: 1.94932%; padding:0.5cm 5.4pt 0.5cm 5.4pt; text-align: justify; font-size:12.0pt;font-family:\"Arial\",sans-serif;'>" + TextUtils.encodeHtml(b.getObservaciones()) + "</td>\n"
                    + "</tr>";
            i++;
        }
        cronogramahtml += "</tbody></table>";
        return "<!DOCTYPE html><html><head></head><body>" + cronogramahtml + "</body></html>";
    }

    private static String GetParrafos(String contenido) {
        String[] list = contenido.split("\n");
        StringBuilder builder = new StringBuilder();
        for (String s : list) {
            builder.append("<p><span style=\"color: #000000;\">" + s + "</span></p>");
        }
        return builder.toString();
    }
}
