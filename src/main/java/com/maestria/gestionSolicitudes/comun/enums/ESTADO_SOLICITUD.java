package com.maestria.gestionSolicitudes.comun.enums;

public enum ESTADO_SOLICITUD {
    PENDIENTE("Pendiente"),
    EN_PROGRESO("En progreso"),
    COMPLETADO("Completado"),
    CANCELADO("Cancelado");

    private final String descripcion;

    ESTADO_SOLICITUD(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}