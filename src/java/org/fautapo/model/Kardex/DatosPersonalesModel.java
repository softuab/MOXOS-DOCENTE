/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.model.Kardex;

import java.util.Date;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.fautapo.validation.DateFormat;
import org.hibernate.validator.constraints.NotBlank;

@Getter
@Setter
public class DatosPersonalesModel {

    private Integer id_persona_kardex;
    private Integer id_persona;
    @NotBlank(message = "El detalle del nombre es obligatorio")
    private String nombre;
    private String segundonombre;
    @NotBlank(message = "El detalle del apellido paterno es obligatorio")
    private String apellidopaterno;
    private String apellidomaterno;
    @NotNull(message = "El detalle del lugar de nacimiento es obligatorio")
    private Integer id_localidad;
    private Date fechanacimiento;
    @NotBlank(message = "El detalle de la fecha nacimiento es obligatorio")
    @DateFormat(format = "dd/MM/yyyy", message = "El formato de la fecha nacimiento es incorrecto de contener el formato dd/MM/yyyy")
    private String text_fechanacimiento;
    private Boolean sexo;
    private String text_sexo;
    @NotBlank(message = "El detalle del estado civil es obligatorio")
    private String estadocivil;
}
