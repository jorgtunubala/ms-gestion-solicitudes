package com.maestria.gestionSolicitudes.comun.enums;

public enum DESTINATARIO_CORREO {
    ESTUDIANTE("Estudiante"),
    TUTOR("Tutor"),
    DIRECTOR("Director"),
    COORDINADOR("Coordinador");

    private final String descripcion;

    DESTINATARIO_CORREO(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static String getDescripcionPorCodigo(String codigo) {
        for (DESTINATARIO_CORREO tipo : DESTINATARIO_CORREO.values()) {
            if (tipo.name().equals(codigo)) {
                return tipo.getDescripcion();
            }
        }
        return null; // O lanza una excepción si prefieres
    }
}
