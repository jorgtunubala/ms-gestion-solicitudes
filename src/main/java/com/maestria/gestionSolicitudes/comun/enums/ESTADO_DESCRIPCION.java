package com.maestria.gestionSolicitudes.comun.enums;

public enum ESTADO_DESCRIPCION {
    RADICADA("La solicitud se ha creado correctamente y se ha enviado una copia al tutor/director relacionado para su revisión y aval."),
    AVALADA_TUTOR("La solicitud fue avalada por el tutor."),
    AVALADA_DIRECTOR("La solicitud fue avalada por el director del programa."),
    EN_COORDINACIÓN("La solicitud fue avalada y fue enviada para su revisión al  Coordinador del programa."),
    NO_AVALADA("La solicitud fue revisada por tutor/director relacionado y no fue avalada."),
    EN_COMITE("En cómite"),
    RECHAZADA("Rechazada"),
    APROBADA("Aprobada"),
    NO_APROBADA("No Aprobada");

    private final String descripcion;

    ESTADO_DESCRIPCION(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static String getDescripcionPorCodigo(String codigo) {
        for (ESTADO_DESCRIPCION tipo : ESTADO_DESCRIPCION.values()) {
            if (tipo.name().equals(codigo)) {
                return tipo.getDescripcion();
            }
        }
        return null; // O lanza una excepción si prefieres
    }
}
