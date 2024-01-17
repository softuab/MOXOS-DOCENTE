package com.moxos.uab.model.models.programaanalitico.formasorganizacion;

import com.moxos.uab.model.models.utility.ListGroupViewItem;
import com.moxos.uab.model.models.utility.ListViewItemSelected;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class FormasOrganizacionRequest {
    private Integer id_prg_a_distribucion;
    private Integer id_dct_programa_analitico;
    private List<ListViewItemSelected> tipo;
    private List<ListGroupViewItem> items;
    private Integer id_prg_a_contenido;
    @NotNull(message = "Debe seleccionar el tipo de forma")
    private Integer id_prg_a_formas;
    @NotNull(message = "Debe ingresar cantidad de hora")
    private Integer horas;
    private Integer totalHoras;
    private String id_estado;
    private String tipo_forma;
    private Integer ult_usuario;
}
