package com.moxos.uab.model.models.Kardex;

import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.moxos.uab.validation.CheckDetailValid;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@CheckDetailValid(first = "text_jubilado", second = "ren", message = "Debe ingresar del detalle de Numero de registro de jubilacion")
public class DatosformacionLaboralPersonalModel {

    private Integer id_persona_kardex;
    private Integer id_persona;
    @NotBlank(message = "El detalle del tipo nua es obligatorio")
    private String tiponua;
    @NotBlank(message = "El detalle del nua obligatorio")
    private String nua;
    @NotNull(message = "Debe selecionar banco")
    private int id_banco;
    @NotBlank(message = "El detalle del numero de cuenta es obligatorio")
    private String cuentacorriente;
    private Boolean sindicato;
    private String text_sindicato;
    private Boolean jubilado;
    private String text_jubilado;
    private String ren;
    private Boolean discapacidad;
    private String text_discapacidad;
    private String nrodiscpacitado;
    private String numerodeseguro;
    private Boolean ley1178;
    private String text_ley1178;
    private String nrotitulo;
    private String promedio;
    private Date fechacurso1178;
    private String text_fechacurso1178;
    private Boolean sippase;
    private String text_sippase;
    private Date fechaemisionsippase;
    private String text_fechaemisionsippase;
    @NotBlank(message = "Debe ingresar el detalle de Idioma Nativo")
    private String idiomanativo;
    private Date fechaemision;
    private String text_fechaemision;

}
