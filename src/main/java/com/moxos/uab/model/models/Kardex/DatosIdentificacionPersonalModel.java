package com.moxos.uab.model.models.Kardex;

import java.util.Date;

import com.moxos.uab.validation.DocumentoValido;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Data
@DocumentoValido(tipodocumento = "tipodocumento", dip = "numerodocumento", message = "El documento de carnet no es valido debe contener el formato correcto del carnet (XXXXXXX O XXXXXXX-1B)")
public class DatosIdentificacionPersonalModel {

    private Integer id_persona_kardex;
    private Integer id_persona;
    @NotBlank(message = "El detalle del numro de documento es obligatorio")
    private String numerodocumento;
    @NotBlank(message = "El detalle del tipo de documento es obligatorio")
    private String tipodocumento;
    private Date fechaexpiracioncarnet;
    @NotBlank(message = "El detalle de la fecha de expiracion es obligatorio")
    private String text_fechaexpiracioncarnet;
    private String imagecarnetidentidad;
    private MultipartFile Image;
    @NotBlank(message = "El detalle de la emision es obligatorio")
    private String emision_documento;

}
