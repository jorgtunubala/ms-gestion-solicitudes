package com.maestria.gestionSolicitudes.comun.enums;

public enum ESTADO_DESCRIPCION {
    RADICADA("La solicitud se ha creado correctamente y se ha enviado una copia al tutor/director relacionado para su revisión y aval."),
    AVALADA_TUTOR("La solicitud fue avalada por el tutor."),
    AVALADA_DIRECTOR("La solicitud fue avalada por el director del programa."),
    EN_COORDINACIÓN("La solicitud está siendo evaluada por el Coordinador del programa."),
    NO_AVALADA("La solicitud fue revisada por tutor/director relacionado y no fue avalada. El proceso se ha detenido."),
    EN_COMITE("La solicitud ha sido enviada al comité evaluador para su análisis final."),
    RECHAZADA("La solicitud ha sido rechazada por el comité evaluador."),
    APROBADA("La solicitud ha sido aprobada por el comité evaluador."),
    NO_APROBADA("La solicitud no ha sido aprobada por el comité evaluador.");

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
