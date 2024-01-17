package com.moxos.uab.model.models.programaanalitico.bibliografia;

import com.moxos.uab.mybatis.entity.ListViewItem;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class ReferenciaRevista {
    private Integer id_prg_a_bibliografia;
    private Integer id_dct_programa_analitico;
    private Integer tipo_referncia;
    @NotBlank(message = "Debe ingresar la ubicacion fisica del documento en la universidad")
    private String ubicacion;
    @NotBlank(message = "Debe ingresar el nombre del autor")
    private String autor;
    private Date fecha_publicacion;
    @NotBlank(message = "Debe ingresar la fecha de a publicacion de la revista")
    private String text_fecha_publicacion;
    @NotBlank(message = "Debe ingresar el titulo del articulo  de la revista")
    private String titulo;
    @NotBlank(message = "Debe ingresar el titulo de la revista  de la revista")
    private String titulo_documento;
    @NotBlank(message = "Debe ingresar el volumen  de la revista")
    private String volumen;
    @NotBlank(message = "Debe ingresar el numero  de la revista")
    private String numero;
    @NotBlank(message = "Debe ingresar pagina  de la revista")
    private String paginas;
    private String id_estado;
    private Integer ult_usuario;
    @NotBlank(message = "Debe ingresar el tipo de bibliografia")
    private String tipobibliografia;
    private List<ListViewItem> tipos;
}
