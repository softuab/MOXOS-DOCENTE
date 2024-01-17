package com.moxos.uab.model.models.programaanalitico.bibliografia;

import com.moxos.uab.mybatis.entity.ListViewItem;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
public class ReferenciaDocumentos {

    private Integer id_prg_a_bibliografia;
    private Integer id_dct_programa_analitico;
    private Integer tipo_referncia;
    @NotBlank(message = "Debe ingresar la ubicacion fisica del documento en la universidad")
    private String ubicacion;
    @NotBlank(message = "Debe ingresar el nombre del autor")
    private String autor;
    @NotBlank(message = "Debe ingresar el año del libro")
    @Pattern(regexp = "[0-9]+", message = "Debe ingresar el formato correcto del año del libro")
    private String anio;
    @NotBlank(message = "Debe ingresar el titulo del libro")
    private String titulo;
    @NotBlank(message = "Debe ingresar la edicion del libro")
    private String edicion;
    @NotBlank(message = "Debe ingresar el lugar del libro")
    private String lugar;
    @NotBlank(message = "Debe ingresar la editorial del libro")
    private String editorial;
    private String id_estado;
    private Integer ult_usuario;
    @NotBlank(message = "Debe ingresar el tipo de bibliografia")
    private String tipobibliografia;
    private List<ListViewItem> tipos;
}
