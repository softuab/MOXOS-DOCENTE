/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.model.Kardex;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

@Getter
@Setter
public class DatosInformacionContactoModel {

    private Integer id_persona_kardex;
    private Integer id_persona;
    @NotBlank(message = "El detalle de la direccion es obligatorio")
    private String direccion;
    @NotBlank(message = "El detalle del telefono celular es obligatorio")
    private String telefonocelular;
    @NotBlank(message = "El detalle del correo institucional es obligatorio")
    private String correoinsitucional;
}
