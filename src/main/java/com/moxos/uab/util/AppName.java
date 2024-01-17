package com.moxos.uab.util;

public enum AppName {
    DOCENTE("PLATAFORMA DOCENTE"), ADMIN("PLATAFORMA ADMIN"), RECOVERY("PLATAFORMA RECUPERACION CONTRASEÑA");

    private String value;

    AppName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
