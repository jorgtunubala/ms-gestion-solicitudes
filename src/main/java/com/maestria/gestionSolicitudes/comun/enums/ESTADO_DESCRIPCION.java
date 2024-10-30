package com.maestria.gestionSolicitudes.comun.enums;

public enum ESTADO_DESCRIPCION {
    CREADA("La solicitud se ha creado correctamente y se ha enviado una copia al tutor/director relacionado para su revisión y aval."),
    AVALADA_TUTOR("La solicitud fue avalada por el tutor."),
    AVALADA_DIRECTOR("La solicitud fue avalada por el director del grupo de investigación."),
    EN_COORDINACIÓN("La solicitud ha sido radicada en la coordinación del programa y está pendiente de la revisión por parte del coordinador."),
    NO_AVALADA("La solicitud fue revisada por tutor/director relacionado y no fue avalada. La solicitud ha sido cerrada."),
    EN_COMITÉ("La solicitud ha sido enviada al comité del programa para su evaluación."),
    RECHAZADA("La solicitud ha sido rechazada por el coordinador(a) del programa."),
    APROBADA("La solicitud ha sido aprobada."),
    NO_APROBADA("La solicitud no ha sido aprobada.");

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
