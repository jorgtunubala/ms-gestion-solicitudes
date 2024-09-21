package com.maestria.gestionSolicitudes.comun.enums;

public enum ESTADO_SOLICITUD {
    RADICADA("Radicada"),
    AVALADA("Avalada"),
    NO_AVALADA("No Avalada"),
    EN_COMITE("En comité"),
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

    public static String getDescripcionPorCodigo(String codigo) {
        for (ESTADO_SOLICITUD tipo : ESTADO_SOLICITUD.values()) {
            if (tipo.name().equals(codigo)) {
                return tipo.getDescripcion();
            }
        }
        return null; // O lanza una excepción si prefieres
    }
}
