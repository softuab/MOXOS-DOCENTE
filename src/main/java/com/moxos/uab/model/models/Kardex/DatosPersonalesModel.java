package com.moxos.uab.model.models.Kardex;
import com.moxos.uab.validation.DateFormat;
import lombok.Data;

import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
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
    @DateFormat(format = "yyyy-MM-dd", message = "El formato de la fecha nacimiento es incorrecto de contener el formato dd/MM/yyyy")
    private String text_fechanacimiento;
    private Boolean sexo;
    private String text_sexo;
    @NotBlank(message = "El detalle del estado civil es obligatorio")
    private String estadocivil;
}
