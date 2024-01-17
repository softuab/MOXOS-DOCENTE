package com.moxos.uab.mybatis.entity;

import java.util.Date;
import java.util.List;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BarcodePDF417;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KardexPersonal {

    private String carrera;
    private String facultad;
    private String nombreinstitucion;
    private String lugarimpresion;
    private String nombre_completo;
    private int id_designacion;
    private Boolean mostrarcarrera;
    private String detalle_profesion;
    private String lugar_nacimiento;
    private String nacionalidad;
    private String tipodocumento;
    private String numerodocumento;
    private String libreta_militar;
    private String estadocivil;
    private String direccion;
    private String telefonocelular;
    private String correoinsitucional;
    private Date fechaingresodocente;
    private int id_docente;
    private int id_persona;
    private int id_persona_kardex;
    private List<CategoriaDocente> categoria;
    private List<DetallePersonaModalidadIngreso> modalidad;
    private List<PersonaCursosRealizados> cursosrealizados;
    private List<PersonaEvaluacionDocente> evaluaciondocente;
    private List<DetallePersonaExperienciaLaboral> experiencialaboral;
    private List<DetalleFormacionAcademicaPersonal> formacionacademica;
    private List<DetalleIdiomaPersonal> idioma;
    private List<PersonaProduccionCientifica> produccioncientifica;
    private List<ListViewItem> posgrado;
    private List<ListViewItem> tipoexperiencia;
    private List<ListViewItem> tipoevento;
    private Image image;

    public Image GetBarcode(PdfContentByte cb, float mh, float mw) throws BadElementException {
        BarcodePDF417 pf = new BarcodePDF417();
        pf.setText(this.getNombre_completo() + "|" + this.getCarrera() + "|" + this.getFacultad());
        Rectangle size = pf.getBarcodeSize();
        PdfTemplate template = cb.createTemplate(mw * size.getWidth(), mh * size.getHeight());
        pf.placeBarcode(template, BaseColor.BLACK, mh, mw);
        return Image.getInstance(template);
    }
}
