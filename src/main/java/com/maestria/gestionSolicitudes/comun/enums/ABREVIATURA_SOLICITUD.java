package com.maestria.gestionSolicitudes.comun.enums;

public enum ABREVIATURA_SOLICITUD {
    AD_ASIG("Adición Asignaturas"),
    CA_ASIG("Cancelación Asignaturas"),
    HO_ASIG_ESP("Homologación Asignaturas"),
    HO_ASIG_POS("Homologación Asignaturas"),
    AP_SEME("Aplazamiento Semestre"),
    CU_ASIG("Cursos Externos"),
    AV_PASA_INV("Aval Pasantía"),
    AP_ECON_INV("Apoyo Económico");

    private final String descripcion;

    ABREVIATURA_SOLICITUD(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
