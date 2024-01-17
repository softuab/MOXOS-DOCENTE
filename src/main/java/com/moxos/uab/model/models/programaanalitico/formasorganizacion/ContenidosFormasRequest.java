package com.moxos.uab.model.models.programaanalitico.formasorganizacion;

import com.moxos.uab.mybatis.entity.FormasOrganizacionDistribucion;
import lombok.Data;

import java.util.List;

@Data
public class ContenidosFormasRequest {
    private Integer id_prg_a_contenido;
    private String contenido;
    private List<FormasOrganizacionDistribucion> distribuciones;
}
