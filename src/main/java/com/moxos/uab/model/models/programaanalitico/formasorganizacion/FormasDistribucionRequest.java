package com.moxos.uab.model.models.programaanalitico.formasorganizacion;

import com.moxos.uab.mybatis.entity.FormasOrganizacionDistribucion;
import lombok.Data;

import java.util.List;

@Data
public class FormasDistribucionRequest {
    private List<ContenidosFormasRequest> distribucion;
    private List<FormasOrganizacionDistribucion> formasdistribucion;
    private Integer id_dct_programa_analitico;
    private Integer totalHoras;
    private Integer gestion;
    private Integer periodo;
    private String grupo;
    private String materia;
}
