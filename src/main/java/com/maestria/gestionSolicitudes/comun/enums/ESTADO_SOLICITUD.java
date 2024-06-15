package com.maestria.gestionSolicitudes.comun.enums;

public enum ESTADO_SOLICITUD {
    RADICADA("Radicada"),
    AVALADA("Avalada"),
    NO_AVALADA("No Avalada"),
    EN_COMITE("En cómite"),
    RECHAZADA("Rechazada"),
    APROBADA("Aprobada"),
    NO_APROBADA("No Aprobada");

    private final String descripcion;

    ESTADO_SOLICITUD(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
