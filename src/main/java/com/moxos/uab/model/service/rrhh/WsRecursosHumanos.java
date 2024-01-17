package com.moxos.uab.model.service.rrhh;

public enum WsRecursosHumanos {
    LISTAR_FUNCIONES("/api/boletas/WSListarfuncionesMes"),
    LISTAR_PLANILLAS("/api/boletas/WSPlanillas"),
    INFORMACION("/api/boletas/Wsinfo"),
    BOLETAS("/api/boletas/WsBoleta"),
    INFORMACION_AGUINALDO("/api/aguinaldo/Wsinfo"),
    LISTAR_FUNCIONES_AGUINALDO("/api/aguinaldo/WSListarfunciones"),
    BOLETAS_AGUINALDO("/api/aguinaldo/WsBoleta");
    private String api;

    WsRecursosHumanos(String api) {
        this.api = api;
    }

    public String getApi() {
        return api;
    }
}
