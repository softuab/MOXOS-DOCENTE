package com.moxos.uab.model.models.programaanalitico.bibliografia;

import com.moxos.uab.mybatis.entity.ListViewItem;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Data
public class ReferenciaElectronica {
    private Integer id_prg_a_bibliografia;
    private Integer id_dct_programa_analitico;
    private Integer tipo_referncia;
    @NotBlank(message = "Debe ingresar la ubicacion fisica del documento en la universidad")
    private String ubicacion;
    @NotBlank(message = "Debe ingresar el nombre del autor  del documento")
    private String autor;
    private Date fecha_publicacion;
    @NotBlank(message = "Debe ingresar la fecha de publicacion del documento")
    private String text_fecha_publicacion;
    @NotBlank(message = "Debe ingresar el titulo del documento")
    private String titulo_documento;
    private Date fecha_consulta;
    @NotBlank(message = "Debe ingresar la fecha de consulta que se realizo al sitio")
    private String text_fecha_consulta;
    @NotBlank(message = "Debe ingresar la direccion web del documento")
    private String url;
    private String id_estado;
    private Integer ult_usuario;
    @NotBlank(message = "Debe ingresar el tipo de bibliografia")
    private String tipobibliografia;
    private List<ListViewItem> tipos;
}
