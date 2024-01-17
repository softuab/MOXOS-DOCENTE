package com.moxos.uab.model.models.Kardex;

import com.moxos.uab.mybatis.entity.PersonaIdioma;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

public class PersonaKardexRequest {

    @Getter
    @Setter
    private Integer id_persona_kardex;
    @Getter
    @Setter
    private String numerodocumento;
    @Getter
    @Setter
    private String tipodocumento;
    @Getter
    @Setter
    private String nombre;
    @Getter
    @Setter
    private String segundonombre;
    @Getter
    @Setter
    private String apellidopaterno;
    @Getter
    @Setter
    private String apellidomaterno;
    @Getter
    @Setter
    private String imagen;
    @Getter
    @Setter
    private Date fechanacimiento;
    @Getter
    @Setter
    private String direccion;
    @Getter
    @Setter
    private String telefonocelular;
    @Getter
    @Setter
    private String estadocivil;
    @Getter
    @Setter
    private String correoinsitucional;
    @Getter
    @Setter
    private Integer id_localidad;
    @Getter
    @Setter
    private String nua;
    @Getter
    @Setter
    private String tiponua;
    @Getter
    @Setter
    private Boolean sexo;
    private String detalle_sexo;
    @Getter
    @Setter
    private Date fechacontratoinicial;
    @Getter
    @Setter
    private Boolean sindicato;
    @Getter
    @Setter
    private Boolean jubilado;
    @Getter
    @Setter
    private String ren;
    @Getter
    @Setter
    private Boolean discapacidad;
    @Getter
    @Setter
    private String numerolibreta;
    @Getter
    @Setter
    private String numerodeseguro;
    @Getter
    @Setter
    private Date fechaingresodocente;
    @Getter
    @Setter
    private Date fechaexpiracioncarnet;
    @Getter
    @Setter
    private String matriculalibreta;
    @Getter
    @Setter
    private String escalon;
    @Getter
    @Setter
    private String aserviciomilitar;
    @Getter
    @Setter
    private String nrodiscpacitado;
    @Getter
    @Setter
    private Integer ult_usuario;
    @Getter
    @Setter
    private Integer id_banco;
    @Getter
    @Setter
    private String cuentacorriente;
    @Getter
    @Setter
    private Integer id_nivelestudio;
    @Getter
    @Setter
    private Integer id_profesiones;
    @Getter
    @Setter
    private Integer id_colegio_profesionales;
    @Getter
    @Setter
    private String numeroregistroprofesionales;
    @Getter
    @Setter
    private Date fechatituloprofesion;
    @Getter
    @Setter
    private Boolean ley1178;
    @Getter
    @Setter
    private String nrotitulo;
    @Getter
    @Setter
    private String promedio;
    @Getter
    @Setter
    private String idiomanativo;
    @Getter
    @Setter
    private String imagelibretamilitar;
    @Getter
    @Setter
    private String imagecarnetidentidad;
    @Getter
    @Setter
    private Date declaracionjurada;
    @Getter
    @Setter
    private String declaracionjurabienesrentas;
    @Getter
    @Setter
    private Date fechacurso1178;
    @Getter
    @Setter
    private String id_estado;
    @Getter
    @Setter
    private String universidad;
    @Getter
    @Setter
    private Date fechaemision;
    @Getter
    @Setter
    private String numerotituloprovision;
    @Getter
    @Setter
    private Boolean sippase;
    @Getter
    @Setter
    private Date fechaemisionsippase;
    @Getter
    @Setter
    private Integer id_persona;
    @Getter
    @Setter
    private String prefijo_profesional;
    @Getter
    @Setter
    private String emision_documento;
    @Getter
    @Setter
    private String detalle_estadocivil;
    @Getter
    @Setter
    private String detalle_localidad;
    @Getter
    @Setter
    private String detalle_banco;
    private String detalle_sindicato;
    private String detalle_jubilado;
    private String detalle_discapacitado;
    @Getter
    @Setter
    private String detalle_nivelestudio;
    @Getter
    @Setter
    private String detalle_profesion;
    @Getter
    @Setter
    private String detalle_colegio_profesionales;
    private String detalle_sippase;
    private String detalle_ley1178;
    @Setter
    private String numero;
    private String alfanumerico;
    @Getter
    @Setter
    private Integer id_nivelestudio_posgrado;
    @Getter
    @Setter
    private Date fechaemisionposgrado;
    @Getter
    @Setter
    private String tituloposgrado;
    @Getter
    @Setter
    private String emisiontituloposgrado;
    @Getter
    @Setter
    private String imagetituloposgrado;
    @Getter
    @Setter
    private List<PersonaIdioma> personaidioma;
    @Getter
    @Setter
    private Integer number;
    @Getter
    @Setter
    private String nivelestudio_posgrado;

    public String getNumero() {
        String[] split = numerodocumento.split("-");
        if (split.length == 0) {
            numero = "";
        } else {
            if (split.length == 1) {
                numero = numerodocumento;
            } else {
                numero = split[0];
            }
        }
        return numero;
    }

    public String getAlfanumerico() {
        String[] split = numerodocumento.split("-");
        if (split.length == 0) {
            alfanumerico = "";
        } else {
            if (split.length == 1) {
                alfanumerico = "";
            } else {
                alfanumerico = split[1];
            }
        }
        return alfanumerico;
    }

    public String getDetalle_sippase() {
        if (sippase) {
            detalle_sippase = "SI";
        } else {
            detalle_sippase = "NO";
        }
        return detalle_sippase;
    }

    public String getDetalle_ley1178() {
        if (ley1178) {
            detalle_ley1178 = "SI";
        } else {
            detalle_ley1178 = "NO";
        }
        return detalle_ley1178;
    }

    public String getDetalle_sindicato() {
        if (sindicato) {
            detalle_sindicato = "SI";
        } else {
            detalle_sindicato = "NO";
        }
        return detalle_sindicato;
    }

    public String getDetalle_jubilado() {
        if (jubilado) {
            detalle_jubilado = "SI";
        } else {
            detalle_jubilado = "NO";
        }
        return detalle_jubilado;
    }

    public String getDetalle_discapacitado() {
        if (discapacidad) {
            detalle_discapacitado = "SI";
        } else {
            detalle_discapacitado = "NO";
        }
        return detalle_discapacitado;
    }

    public String getDetalle_sexo() {

        if (sexo) {
            detalle_sexo = "MASCULINO";
        } else {
            detalle_sexo = "FEMENINO";
        }
        return detalle_sexo;
    }

    public PersonaKardexRequest() {
        this.setApellidomaterno("");
        this.setApellidopaterno("");
        this.setAserviciomilitar("");
        this.setCorreoinsitucional("");
        this.setCuentacorriente("");
        this.setDeclaracionjurabienesrentas("");
        this.setDeclaracionjurada(new Date());
        this.setDireccion("");
        this.setDiscapacidad(Boolean.FALSE);
        this.setEmision_documento("");
        this.setEscalon("");
        this.setEstadocivil("");
        this.setFechacontratoinicial(new Date());
        this.setFechacurso1178(new Date());
        this.setFechaemision(new Date());
        this.setFechaemisionsippase(new Date());
        this.setFechaexpiracioncarnet(new Date());
        this.setFechaingresodocente(new Date());
        this.setFechanacimiento(new Date());
        this.setFechatituloprofesion(new Date());
        this.setId_banco(1);
        this.setId_colegio_profesionales(1);
        this.setId_estado("A");
        this.setId_localidad(1);
        this.setId_nivelestudio(1);
        this.setId_profesiones(1);
        this.setIdiomanativo("");
        this.setImagecarnetidentidad("image.png");
        this.setImagelibretamilitar("image.png");
        this.setImagen("image.png");
        this.setJubilado(Boolean.FALSE);
        this.setLey1178(Boolean.FALSE);
        this.setMatriculalibreta("");
        this.setNrodiscpacitado("");
        this.setNombre("");
        this.setNrotitulo("");
        this.setNua("");
        this.setNumerodeseguro("");
        this.setNumerodocumento("");
        this.setNumerolibreta("");
        this.setNumeroregistroprofesionales("");
        this.setNumerotituloprovision("");
        this.setPrefijo_profesional("");
        this.setPromedio("");
        this.setRen("");
        this.setSegundonombre("");
        this.setSexo(Boolean.FALSE);
        this.setSindicato(Boolean.FALSE);
        this.setSippase(Boolean.FALSE);
        this.setTelefonocelular("");
        this.setTipodocumento("");
        this.setTiponua("");
        this.setUniversidad("");
        this.setId_nivelestudio_posgrado(1);
        this.setFechaemisionposgrado(new Date());
        this.setTituloposgrado("");
        this.setEmisiontituloposgrado("");
        this.setImagetituloposgrado("image.png");
    }
}
