package com.moxos.uab.util;

public enum AccionEvento {
    INICIO_SESION("INICIO SESION"), CERRAR_SESION("CERRAR SESION"), CAMBIAR_PASSWORD("CAMBIAR CONTRASEÑA"), RECUPERAR_PASSWORD("RECUPERAR CONTRASEÑA");
    private String value;

    AccionEvento(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
