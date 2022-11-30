/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.domain;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import com.itextpdf.text.Image;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BarcodePDF417;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import org.fautapo.model.Kardex.DetalleFormacionAcademicaPersonaModel;
import org.fautapo.model.Kardex.DetalleIdiomaPersonaModel;
import org.fautapo.model.Kardex.DetallePersonaExperienciaLaboral;
import org.fautapo.model.Kardex.DetallePersonaModalidadIngresoModel;
import org.fautapo.util.ListViewItem;

/**
 *
 * @author FNZABALETAA
 */
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
    private List<DetallePersonaModalidadIngresoModel> modalidad;
    private List<PersonaCursosRealizados> cursosrealizados;
    private List<PersonaEvaluacionDocente> evaluaciondocente;
    private List<DetallePersonaExperienciaLaboral> experiencialaboral;
    private List<DetalleFormacionAcademicaPersonaModel> formacionacademica;
    private List<DetalleIdiomaPersonaModel> idioma;
    private List<PersonaProduccionCientifica> produccioncientifica;
    private List<ListViewItem> posgrado;
    private List<ListViewItem> tipoexperiencia;
    private List<ListViewItem> tipoevento;

    public List<ListViewItem> getTipoevento() {
        return tipoevento;
    }

    public void setTipoevento(List<ListViewItem> tipoevento) {
        this.tipoevento = tipoevento;
    }
    
    

    public List<ListViewItem> getTipoexperiencia() {
        return tipoexperiencia;
    }

    public void setTipoexperiencia(List<ListViewItem> tipoexperiencia) {
        this.tipoexperiencia = tipoexperiencia;
    }

    public List<ListViewItem> getPosgrado() {
        return posgrado;
    }

    public void setPosgrado(List<ListViewItem> posgrado) {
        this.posgrado = posgrado;
    }

    public List<DetallePersonaModalidadIngresoModel> getModalidad() {
        return modalidad;
    }

    public void setModalidad(List<DetallePersonaModalidadIngresoModel> modalidad) {
        this.modalidad = modalidad;
    }

    public List<PersonaCursosRealizados> getCursosrealizados() {
        return cursosrealizados;
    }

    public void setCursosrealizados(List<PersonaCursosRealizados> cursosrealizados) {
        this.cursosrealizados = cursosrealizados;
    }

    public List<PersonaEvaluacionDocente> getEvaluaciondocente() {
        return evaluaciondocente;
    }

    public void setEvaluaciondocente(List<PersonaEvaluacionDocente> evaluaciondocente) {
        this.evaluaciondocente = evaluaciondocente;
    }

    public List<DetallePersonaExperienciaLaboral> getExperiencialaboral() {
        return experiencialaboral;
    }

public void setExperiencialaboral(List<DetallePersonaExperienciaLaboral> experiencialaboral) {
        this.experiencialaboral = experiencialaboral;
    }

    public List<DetalleFormacionAcademicaPersonaModel> getFormacionacademica() {
        return formacionacademica;
    }

    public void setFormacionacademica(List<DetalleFormacionAcademicaPersonaModel> formacionacademica) {
        this.formacionacademica = formacionacademica;
    }

    public List<DetalleIdiomaPersonaModel> getIdioma() {
        return idioma;
    }

    public void setIdioma(List<DetalleIdiomaPersonaModel> idioma) {
        this.idioma = idioma;
    }

    public List<PersonaProduccionCientifica> getProduccioncientifica() {
        return produccioncientifica;
    }

    public void setProduccioncientifica(List<PersonaProduccionCientifica> produccioncientifica) {
        this.produccioncientifica = produccioncientifica;
    }

    public List<CategoriaDocente> getCategoria() {
        return categoria;
    }

    public void setCategoria(List<CategoriaDocente> categoria) {
        this.categoria = categoria;
    }

    public int getId_persona_kardex() {
        return id_persona_kardex;
    }

    public void setId_persona_kardex(int id_persona_kardex) {
        this.id_persona_kardex = id_persona_kardex;
    }

    public int getId_persona() {
        return id_persona;
    }

    public void setId_persona(int id_persona) {
        this.id_persona = id_persona;
    }

    private Image image;

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public String getDetalle_profesion() {
        return detalle_profesion;
    }

    public void setDetalle_profesion(String detalle_profesion) {
        this.detalle_profesion = detalle_profesion;
    }

    public String getLugar_nacimiento() {
        return lugar_nacimiento;
    }

    public void setLugar_nacimiento(String lugar_nacimiento) {
        this.lugar_nacimiento = lugar_nacimiento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getTipodocumento() {
        return tipodocumento;
    }

    public void setTipodocumento(String tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

    public String getNumerodocumento() {
        return numerodocumento;
    }

    public void setNumerodocumento(String numerodocumento) {
        this.numerodocumento = numerodocumento;
    }

    public String getLibreta_militar() {
        return libreta_militar;
    }

    public void setLibreta_militar(String libreta_militar) {
        this.libreta_militar = libreta_militar;
    }

    public String getEstadocivil() {
        return estadocivil;
    }

    public void setEstadocivil(String estadocivil) {
        this.estadocivil = estadocivil;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefonocelular() {
        return telefonocelular;
    }

    public void setTelefonocelular(String telefonocelular) {
        this.telefonocelular = telefonocelular;
    }

    public String getCorreoinsitucional() {
        return correoinsitucional;
    }

    public void setCorreoinsitucional(String correoinsitucional) {
        this.correoinsitucional = correoinsitucional;
    }

    public Date getFechaingresodocente() {
        return fechaingresodocente;
    }

    public void setFechaingresodocente(Date fechaingresodocente) {
        this.fechaingresodocente = fechaingresodocente;
    }

    public int getId_docente() {
        return id_docente;
    }

    public void setId_docente(int id_docente) {
        this.id_docente = id_docente;
    }

    public Boolean getMostrarcarrera() {
        return mostrarcarrera;
    }

    public void setMostrarcarrera(Boolean mostrarcarrera) {
        this.mostrarcarrera = mostrarcarrera;
    }

    public int getId_designacion() {
        return id_designacion;
    }

    public void setId_designacion(int id_designacion) {
        this.id_designacion = id_designacion;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String docente) {
        this.nombre_completo = docente;
    }

    public String getLugarimpresion() {
        return lugarimpresion;
    }

    public void setLugarimpresion(String lugarimpresion) {
        this.lugarimpresion = lugarimpresion;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public String getNombreinstitucion() {
        return nombreinstitucion;
    }

    public void setNombreinstitucion(String nombreinstitucion) {
        this.nombreinstitucion = nombreinstitucion;
    }

    public Image GetBarcode(PdfContentByte cb, float mh, float mw) throws BadElementException {
        BarcodePDF417 pf = new BarcodePDF417();
        pf.setText(this.getNombre_completo() + "|" + this.getCarrera() + "|" + this.getFacultad());
        Rectangle size = pf.getBarcodeSize();
        PdfTemplate template = cb.createTemplate(mw * size.getWidth(), mh * size.getHeight());
        pf.placeBarcode(template, BaseColor.BLACK, mh, mw);
        return Image.getInstance(template);
    }
}
