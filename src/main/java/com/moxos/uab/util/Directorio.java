package com.moxos.uab.util;

import org.springframework.beans.factory.annotation.Value;

import java.io.File;

public enum Directorio {

    DIRECTORIO_CONTENIDOS("mapacontenido"),
    DIRECTORIO_CARNET("documentosidentificacion"),
    DIRECTORIO_LIBRETAMILITAR("libretamilitar"),
    DIRECTORIO_TITULOPROVISIONNACIONAL("tituloprovisionnacional"),
    DIRECTORIO_TITULOPOSGRADOEDUCACIONSUPERIOR("tituloposgrado"),
    DIRECTORIO_FORMACIONACADEMICA("formacionacademica"),
    DIRECTORIO_EXPERIENCIALABORAL("experiencialaboral"),
    DIRECTORIO_CURSOSREALIZADOS("cursosrealizados"),
    DIRECTORIO_PRODUCCIONCIENTIFICA("produccioncientifica"),
    DIRECTORIO_EVALUACIONDOCENTE("evaluaciondocente"),
    DIRECTORIO_PROYECTODOCENTE("proyectodocente"),
    DIRECTORIO_IDIOMAS("idiomas"),
    DIRECTORIO_ACTIVIDADES_ACADEMICAS("actividadesacademicas"),
    DIRECTORIO_MODALIDADES("modalidades");
    private String key;

    private Directorio(String key) {
        this.key = key;
    }

    public String key() {
        String rootPath =  File.separator + "persona" + File.separator + this.key;
        return rootPath;
    }
}
