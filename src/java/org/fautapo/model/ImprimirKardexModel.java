/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.model;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.fautapo.domain.CategoriaDocente;
import org.fautapo.domain.KardexPersonal;
import org.fautapo.domain.PersonaCursosRealizados;
import org.fautapo.domain.PersonaEvaluacionDocente;
import org.fautapo.domain.PersonaExperienciaLaboral;
import org.fautapo.domain.PersonaFormacionAcademica;
import org.fautapo.domain.PersonaIdioma;
import org.fautapo.domain.PersonaModalidadIngreso;
import org.fautapo.domain.PersonaProduccionCientifica;
import org.fautapo.model.Kardex.DetalleFormacionAcademicaPersonaModel;
import org.fautapo.model.Kardex.DetalleIdiomaPersonaModel;
import org.fautapo.model.Kardex.DetallePersonaExperienciaLaboral;
import org.fautapo.model.Kardex.DetallePersonaModalidadIngresoModel;
import org.fautapo.util.ListViewItem;

/**
 *
 * @author FNZABALETAA
 */
public class ImprimirKardexModel {

    private KardexPersonal kardexpersona;
    private HttpServletResponse response;
    private Font fonttitulotabla;
    private Font fontcontenttabla;
    private Font fonttitulo2;
    private Font fonttitulo3;
    private Font fontcontentreprobadotabla;
    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    public ImprimirKardexModel(KardexPersonal libreta, HttpServletResponse response) {
        this.kardexpersona = libreta;
        this.response = response;
    }

    public ImprimirKardexModel(HttpServletResponse response) {
        this.response = response;
    }

    public void Createkardex() throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=kardex " + kardexpersona.getNombre_completo() + ".pdf");
        OutputStream out = response.getOutputStream();
        try {
            try {
                Document document = new Document(PageSize.LETTER, 50, 50, 90, 36);
                PdfWriter writer = PdfWriter.getInstance(document, out);
                // add header and footer
                HeaderFooterPageEvent event = new HeaderFooterPageEvent(kardexpersona);
                writer.setPageEvent(event);
                // write to document
                document.open();
                document.setMargins(40, 40, 210, 70);
                document.newPage();
                Paragraph part = new Paragraph();
                fonttitulotabla = new Font(Font.FontFamily.TIMES_ROMAN, 7.5f, Font.BOLD, BaseColor.DARK_GRAY);
                fontcontenttabla = new Font(Font.FontFamily.TIMES_ROMAN, 7.5f, Font.NORMAL, BaseColor.DARK_GRAY);
                fontcontentreprobadotabla = new Font(Font.FontFamily.TIMES_ROMAN, 7.5f, Font.NORMAL, BaseColor.RED);

                fonttitulo2 = new Font(Font.FontFamily.TIMES_ROMAN, 12.0f, Font.NORMAL, BaseColor.DARK_GRAY);
                fonttitulo3 = new Font(Font.FontFamily.TIMES_ROMAN, 12.0f, Font.BOLD, BaseColor.DARK_GRAY);
                part.add(new Phrase("  ", fonttitulotabla));

                Paragraph titulo1 = new Paragraph();
                titulo1.add(new Phrase("I DATOS PERSONALES", fonttitulo3));
                titulo1.add(Chunk.NEWLINE);

                float[] f1 = new float[]{2f, 3f, 4f};
                PdfPTable tableencabezado = new PdfPTable(f1);
                tableencabezado.setHorizontalAlignment(Element.ALIGN_CENTER);
                creardatospersonales(tableencabezado);
                titulo1.add(tableencabezado);

                Paragraph titulo2 = new Paragraph();
                titulo2.add(new Phrase("II LUGAR DE TRABAJO ACTUAL", fonttitulo3));
                titulo2.add(Chunk.NEWLINE);

                float[] f2 = new float[]{5f, 5f};
                PdfPTable tableinstitucion = new PdfPTable(f2);
                tableinstitucion.setHorizontalAlignment(Element.ALIGN_CENTER);
                crearDatosinstitucion(tableinstitucion);
                titulo2.add(tableinstitucion);

                Paragraph titulo3 = new Paragraph();
                titulo3.add(new Phrase("III CATEGORIA DOCENTE(ACTUAL)", fonttitulo3));
                titulo3.add(Chunk.NEWLINE);

                float[] f3 = new float[]{3f, 3f, 3f};
                PdfPTable tablecategoria = new PdfPTable(f3);
                tablecategoria.setHorizontalAlignment(Element.ALIGN_CENTER);
                crearDatoscategoria(tablecategoria);
                titulo3.add(tablecategoria);

                Paragraph titulo4 = new Paragraph();
                titulo4.add(new Phrase("IV MODALIDAD DE INGRESO", fonttitulo3));
                titulo4.add(Chunk.NEWLINE);

                float[] f4 = new float[]{5f, 5f};
                PdfPTable tablemodalidad = new PdfPTable(f4);
                tablemodalidad.setHorizontalAlignment(Element.ALIGN_CENTER);
                crearDatosModalidad(tablemodalidad);
                titulo4.add(tablemodalidad);

                Paragraph titulo5 = new Paragraph();
                titulo5.add(new Phrase("V IDIOMA Y LENGUAS ORIGINARIAS", fonttitulo3));
                titulo5.add(Chunk.NEWLINE);

                float[] f5 = new float[]{4f, 4f, 1f, 1f};
                PdfPTable tableidioma = new PdfPTable(f5);
                tableidioma.setHorizontalAlignment(Element.ALIGN_CENTER);
                crearDatosIdioma(tableidioma);
                titulo5.add(tableidioma);

                Paragraph titulo6 = new Paragraph();
                titulo6.add(new Phrase("VI FORMACION ACADEMICA PREGRADO", fonttitulo3));
                titulo6.add(Chunk.NEWLINE);

                float[] f6 = new float[]{0.5f, 2.3f, 2.3f, 2.3f, 2.3f};
                PdfPTable tablepregrado = new PdfPTable(f6);
                tablepregrado.setHorizontalAlignment(Element.ALIGN_CENTER);
                crearDatosFormacionPregrado(tablepregrado);
                titulo6.add(tablepregrado);

                Paragraph titulo8 = new Paragraph();
                titulo8.add(new Phrase("VII EXPERIENCIA PROFESIONAL/LABORAL", fonttitulo3));
                titulo8.add(Chunk.NEWLINE);
                float[] f8 = new float[]{2f, 2f, 2f, 1f, 1f};

                Paragraph titulo9 = new Paragraph();
                titulo9.add(new Phrase("VIII EVEMTOS ACADEMICOS CIENTIFICOS", fonttitulo3));
                titulo9.add(Chunk.NEWLINE);
                float[] f9 = new float[]{3f, 3f, 3f};

                Paragraph titulo10 = new Paragraph();
                titulo10.add(new Phrase("IX PRODUCCION CIENTIFICA", fonttitulo3));
                titulo10.add(Chunk.NEWLINE);

                float[] f10 = new float[]{1f, 3f, 3f, 3f};
                PdfPTable tableproduccion = new PdfPTable(f10);
                tableproduccion.setHorizontalAlignment(Element.ALIGN_CENTER);
                crearDatosProduccion(tableproduccion);
                titulo10.add(tableproduccion);

                Paragraph titulo11 = new Paragraph();
                titulo11.add(new Phrase("X EVALUACION DOCENTE", fonttitulo3));
                titulo11.add(Chunk.NEWLINE);

                float[] f11 = new float[]{3f, 3f, 3f};
                PdfPTable tableevaluacion = new PdfPTable(f11);
                tableevaluacion.setHorizontalAlignment(Element.ALIGN_CENTER);
                crearDatosEvaluacion(tableevaluacion);
                titulo11.add(tableevaluacion);

                float[] f12 = new float[]{5f, 5f};
                PdfPTable piepagina = new PdfPTable(f12);
                piepagina.setHorizontalAlignment(Element.ALIGN_CENTER);
                crearPiepagina(piepagina);

                document.add(titulo1);
                document.add(Chunk.NEWLINE);

                document.add(titulo2);
                document.add(Chunk.NEWLINE);

                document.add(titulo3);
                document.add(Chunk.NEWLINE);

                document.add(titulo4);
                document.add(Chunk.NEWLINE);

                document.add(titulo5);
                document.add(Chunk.NEWLINE);

                document.add(titulo6);
                document.add(Chunk.NEWLINE);

                Paragraph titulo7 = new Paragraph();
                titulo7.add(new Phrase("VI FORMACION ACADEMICA POSGRADO", fonttitulo3));
                titulo7.add(Chunk.NEWLINE);
                document.add(titulo7);
                float[] f7 = new float[]{0.5f, 2.3f, 2.3f, 2.3f, 2.3f};
                crearDatosPosgrado(document, f7);

                document.add(titulo8);
                crearDatosExperiencia(document, f8);

                document.add(titulo9);
                crearDatosEventos(document, f9);

                document.add(titulo10);
                document.add(Chunk.NEWLINE);

                document.add(titulo11);
                document.add(Chunk.NEWLINE);

                document.add(part);
                document.add(Chunk.NEWLINE);
                document.add(piepagina);
                document.close();
                out.flush();
                response.getOutputStream().flush();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                ex.getMessage();
            }
        } finally {
            out.close();
        }
        response.getOutputStream().flush();
    }

    private void creardatospersonales(PdfPTable table) {
        table.setWidthPercentage(100);
        table.setSpacingBefore(0f);
        table.setSpacingAfter(0f);

        table.addCell(createImageCell(kardexpersona.getImage(), 1, 10, PdfPCell.BOX));
        table.addCell(createCell("Nombre y Apellidos", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_LEFT, BaseColor.LIGHT_GRAY));
        table.addCell(createCell(kardexpersona.getNombre_completo(), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
        table.addCell(createCell("Profesion", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_LEFT, BaseColor.LIGHT_GRAY));
        table.addCell(createCell(kardexpersona.getDetalle_profesion(), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
        table.addCell(createCell("Lugar y Fecha de Nacimiento", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_LEFT, BaseColor.LIGHT_GRAY));
        table.addCell(createCell(kardexpersona.getLugar_nacimiento(), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
        table.addCell(createCell("Nacionalidad", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_LEFT, BaseColor.LIGHT_GRAY));
        table.addCell(createCell(kardexpersona.getNacionalidad(), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
        table.addCell(createCell("Carnet de Identidad", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_LEFT, BaseColor.LIGHT_GRAY));
        table.addCell(createCell(kardexpersona.getNumerodocumento(), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
        table.addCell(createCell("Libreta de Servicio Militar", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_LEFT, BaseColor.LIGHT_GRAY));
        table.addCell(createCell(kardexpersona.getLibreta_militar(), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
        table.addCell(createCell("Estado Civil", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_LEFT, BaseColor.LIGHT_GRAY));
        table.addCell(createCell(kardexpersona.getEstadocivil(), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
        table.addCell(createCell("Direccion", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_LEFT, BaseColor.LIGHT_GRAY));
        table.addCell(createCell(kardexpersona.getDireccion(), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
        table.addCell(createCell("Nro de Celular", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_LEFT, BaseColor.LIGHT_GRAY));
        table.addCell(createCell(kardexpersona.getTelefonocelular(), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
        table.addCell(createCell("Correo Electronico", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_LEFT, BaseColor.LIGHT_GRAY));
        table.addCell(createCell(kardexpersona.getCorreoinsitucional(), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
    }

    private void crearDatosinstitucion(PdfPTable table) {
        table.setWidthPercentage(100);
        table.setSpacingBefore(0f);
        table.setSpacingAfter(0f);
        table.addCell(createCell("Institucion", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("Fecha de Inicio", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell(kardexpersona.getNombreinstitucion(), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
        table.addCell(createCell(format.format(kardexpersona.getFechaingresodocente()), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
    }

    private PdfPCell createCell(String content, int colspan, int rowspan, int border, Font font, int aling, BaseColor backgroundColor) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setColspan(colspan);
        cell.setRowspan(rowspan);
        cell.setBorder(border);
        cell.setBackgroundColor(backgroundColor);
        cell.setHorizontalAlignment(aling);
        return cell;
    }

    private PdfPCell createImageCell(Image content, int colspan, int rowspan, int border) {
        content.setAlignment(Element.ALIGN_CENTER);
        PdfPCell cell = new PdfPCell(content);
        cell.setColspan(colspan);
        cell.setRowspan(rowspan);
        cell.setBorder(border);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }

    private PdfPCell createElementCell(Paragraph content, int colspan, int rowspan, int border) {
        content.setAlignment(Element.ALIGN_CENTER);
        PdfPCell cell = new PdfPCell(content);
        cell.setColspan(colspan);
        cell.setRowspan(rowspan);
        cell.setBorder(border);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }

    private void crearDatoscategoria(PdfPTable table) {
        table.setWidthPercentage(100);
        table.setSpacingBefore(0f);
        table.setSpacingAfter(0f);
        table.addCell(createCell("Categoria", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("Materias", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("Tiempo de dedicacion", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));

        for (CategoriaDocente cat : kardexpersona.getCategoria()) {
            table.addCell(createCell(cat.getTipo_docente(), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
            table.addCell(createCell(cat.getSigla() + " - " + cat.getMateria(), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
            table.addCell(createCell(cat.getTipo_asignacion(), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
        }
    }

    private void crearDatosModalidad(PdfPTable table) {
        table.setWidthPercentage(100);
        table.setSpacingBefore(0f);
        table.setSpacingAfter(0f);
        table.addCell(createCell("Modalidad", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("Fecha de ingreso", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));

        for (DetallePersonaModalidadIngresoModel modalidad : kardexpersona.getModalidad()) {
            table.addCell(createCell(modalidad.getModalidadingreso(), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
            table.addCell(createCell(format.format(modalidad.getFechaingreso()), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
        }
    }

    private void crearDatosIdioma(PdfPTable table) {
        table.setWidthPercentage(100);
        table.setSpacingBefore(0f);
        table.setSpacingAfter(0f);
        table.addCell(createCell("Tipo de lengua", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("Idioma", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("Habla", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("Escribe", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));

        for (DetalleIdiomaPersonaModel idioma : kardexpersona.getIdioma()) {
            table.addCell(createCell(idioma.getTipo_idioma(), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
            table.addCell(createCell(idioma.getDescripcion_idioma(), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
            table.addCell(createCell(idioma.getLee() ? "SI" : "NO", 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
            table.addCell(createCell(idioma.getEscribe() ? "SI" : "NO", 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
        }
    }

    private void crearDatosFormacionPregrado(PdfPTable table) {
        table.setWidthPercentage(100);
        table.setSpacingBefore(0f);
        table.setSpacingAfter(0f);
        table.addCell(createCell("N°", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("Nivel academico", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("Descripcion:", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("Institucion", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("Fecha de emision", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        int i = 1;
        List<DetalleFormacionAcademicaPersonaModel> listformacionacademica = kardexpersona.getFormacionacademica().stream().filter(p -> p.getTipotitulo().equals("PREGRADO")).collect(Collectors.toList());
        for (DetalleFormacionAcademicaPersonaModel formacionacademica : listformacionacademica) {
            table.addCell(createCell(String.valueOf(i), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
            table.addCell(createCell(formacionacademica.getNivelestudio(), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
            table.addCell(createCell(formacionacademica.getDescripcion(), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
            table.addCell(createCell(formacionacademica.getExpedido(), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
            table.addCell(createCell(format.format(formacionacademica.getFechaemision()), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
            i++;
        }
    }

    private void crearDatosPosgrado(Document document, float[] f7) throws DocumentException {
        char numerar = 'a';
        for (ListViewItem view : kardexpersona.getPosgrado()) {
            List<DetalleFormacionAcademicaPersonaModel> listformacionacademica = kardexpersona.getFormacionacademica().stream().filter(p -> p.getId_nivelestudio() == Integer.valueOf(view.getId())).collect(Collectors.toList());
            Paragraph titulo = new Paragraph();
            titulo.add(new Phrase(String.valueOf(numerar) + "). " + view.getValue(), fonttitulo3));
            titulo.add(Chunk.NEWLINE);
            PdfPTable posgrado = new PdfPTable(f7);
            posgrado.setHorizontalAlignment(Element.ALIGN_CENTER);
            crearDatosDetallePosgrado(posgrado, listformacionacademica);
            titulo.add(posgrado);
            document.add(titulo);
            document.add(Chunk.NEWLINE);
            numerar++;
        }
    }

    private void crearDatosDetallePosgrado(PdfPTable table, List<DetalleFormacionAcademicaPersonaModel> listformacionacademica) {
        table.setWidthPercentage(100);
        table.setSpacingBefore(0f);
        table.setSpacingAfter(0f);
        if (listformacionacademica.size() > 0) {
            table.addCell(createCell("N°", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
            table.addCell(createCell("Nivel academico", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
            table.addCell(createCell("Descripcion:", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
            table.addCell(createCell("Institucion", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
            table.addCell(createCell("Fecha de emision", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
            int i = 1;
            for (DetalleFormacionAcademicaPersonaModel formacionacademica : listformacionacademica) {
                table.addCell(createCell(String.valueOf(i), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
                table.addCell(createCell(formacionacademica.getNivelestudio(), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
                table.addCell(createCell(formacionacademica.getDescripcion(), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
                table.addCell(createCell(formacionacademica.getExpedido(), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
                table.addCell(createCell(format.format(formacionacademica.getFechaemision()), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
                i++;
            }
        }
    }

    private void crearDatosExperiencia(Document document, float[] f8) throws DocumentException {
        char numerar = 'a';
        for (ListViewItem view : kardexpersona.getTipoexperiencia()) {
            List<DetallePersonaExperienciaLaboral> listexperiencia = kardexpersona.getExperiencialaboral().stream().filter(p -> p.getTipo_experiencia_laboral().equals(view.getValue())).collect(Collectors.toList());
            Paragraph titulo = new Paragraph();
            titulo.add(new Phrase(String.valueOf(numerar) + "). " + view.getValue(), fonttitulo3));
            titulo.add(Chunk.NEWLINE);
            PdfPTable posgrado = new PdfPTable(f8);
            posgrado.setHorizontalAlignment(Element.ALIGN_CENTER);
            crearDatosDetalleExperiencia(posgrado, listexperiencia, view.getId());
            titulo.add(posgrado);
            document.add(titulo);
            document.add(Chunk.NEWLINE);
            numerar++;
        }
    }

    private void crearDatosDetalleExperiencia(PdfPTable table, List<DetallePersonaExperienciaLaboral> listexperiencia, String column) {
        table.setWidthPercentage(100);
        table.setSpacingBefore(0f);
        table.setSpacingAfter(0f);
        if (listexperiencia.size() > 0) {
            table.addCell(createCell("Institucion", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
            table.addCell(createCell(column, 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
            table.addCell(createCell("Detalle:", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
            table.addCell(createCell("Inicio", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
            table.addCell(createCell("Fin", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));

            for (DetallePersonaExperienciaLaboral experiencia : listexperiencia) {
                table.addCell(createCell(experiencia.getInstitucion(), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
                table.addCell(createCell(experiencia.getCargoocupado(), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
                table.addCell(createCell(experiencia.getDetalle(), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
                table.addCell(createCell(format.format(experiencia.getInicio()), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
                table.addCell(createCell(format.format(experiencia.getFin()), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));

            }
        }
    }

    private void crearDatosEventos(Document document, float[] f9) throws DocumentException {
        char numerar = 'a';
        for (ListViewItem view : kardexpersona.getTipoevento()) {
            List<PersonaCursosRealizados> listcursoseventos = kardexpersona.getCursosrealizados().stream().filter(p -> p.getTipo_eventos().equals(view.getValue())).collect(Collectors.toList());
            Paragraph titulo = new Paragraph();
            titulo.add(new Phrase(String.valueOf(numerar) + "). " + view.getValue(), fonttitulo3));
            titulo.add(Chunk.NEWLINE);
            PdfPTable posgrado = new PdfPTable(f9);
            posgrado.setHorizontalAlignment(Element.ALIGN_CENTER);
            crearDatosDetalleCursos(posgrado, listcursoseventos, view.getId());
            titulo.add(posgrado);
            document.add(titulo);
            document.add(Chunk.NEWLINE);
            numerar++;
        }
    }

    private void crearDatosDetalleCursos(PdfPTable table, List<PersonaCursosRealizados> listcursoseventos, String column) {
        table.setWidthPercentage(100);
        table.setSpacingBefore(0f);
        table.setSpacingAfter(0f);
        if (listcursoseventos.size() > 0) {
            table.addCell(createCell(column, 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
            table.addCell(createCell("Lugar de evento", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
            table.addCell(createCell("fecha", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));

            for (PersonaCursosRealizados experiencia : listcursoseventos) {
                table.addCell(createCell(experiencia.getDetalle(), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
                table.addCell(createCell(experiencia.getExpedido_institucion(), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
                table.addCell(createCell(format.format(experiencia.getFechapresentacion()), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
            }
        }
    }

    private void crearDatosProduccion(PdfPTable table) {
        table.setWidthPercentage(100);
        table.setSpacingBefore(0f);
        table.setSpacingAfter(0f);
        table.addCell(createCell("N°", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("Categoria", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("Nombre del Producto", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("Fecha", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        int i = 1;
        List<PersonaProduccionCientifica> listproduccion = kardexpersona.getProduccioncientifica();
        for (PersonaProduccionCientifica produccion : listproduccion) {
            table.addCell(createCell(String.valueOf(i), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
            table.addCell(createCell(produccion.getCategoria(), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
            table.addCell(createCell(produccion.getNombre_producto(), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
            table.addCell(createCell(format.format(produccion.getFecha_certificacion()), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
            i++;
        }
    }

    private void crearDatosEvaluacion(PdfPTable table) {
        table.setWidthPercentage(100);
        table.setSpacingBefore(0f);
        table.setSpacingAfter(0f);
        table.addCell(createCell("Gestion", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("Asignatura o area", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        table.addCell(createCell("Puntaje", 1, 1, PdfPCell.BOX, fonttitulotabla, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
        List<PersonaEvaluacionDocente> listevaluacion = kardexpersona.getEvaluaciondocente();
        for (PersonaEvaluacionDocente evaluacion : listevaluacion) {
            table.addCell(createCell(evaluacion.getPeriodo() + "/" + evaluacion.getGestion(), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
            table.addCell(createCell(evaluacion.getAsignatura(), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
            table.addCell(createCell(evaluacion.getPuntaje(), 1, 1, PdfPCell.BOX, fontcontenttabla, Element.ALIGN_LEFT, BaseColor.WHITE));
        }
    }

    private void crearPiepagina(PdfPTable table) {
        table.setWidthPercentage(100);
        table.setSpacingBefore(0f);
        table.setSpacingAfter(0f);
        Paragraph para = new Paragraph();
        para.add(new Phrase("______________________________________", new Font(Font.FontFamily.HELVETICA, 12)));
        para.add(Chunk.NEWLINE);
        para.add(Chunk.NEWLINE);
        para.add(new Phrase(kardexpersona.getNombre_completo(), new Font(Font.FontFamily.HELVETICA, 8)));
        para.setAlignment(Element.ALIGN_CENTER);
        table.addCell(createElementCell(para, 1, 1, PdfPCell.NO_BORDER));

        Paragraph parasello = new Paragraph();
        parasello.add(new Phrase("______________________________________", new Font(Font.FontFamily.HELVETICA, 12)));
        parasello.add(Chunk.NEWLINE);
        parasello.add(Chunk.NEWLINE);
        parasello.add(new Phrase("FIRMA DEL INMEDIATO SUPERIOR", new Font(Font.FontFamily.HELVETICA, 8)));
        parasello.setAlignment(Element.ALIGN_CENTER);
        table.addCell(createElementCell(parasello, 1, 1, PdfPCell.NO_BORDER));
    }
}
