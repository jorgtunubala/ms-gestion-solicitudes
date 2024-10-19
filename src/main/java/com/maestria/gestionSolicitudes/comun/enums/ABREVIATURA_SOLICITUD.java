package com.maestria.gestionSolicitudes.comun.enums;

public enum ABREVIATURA_SOLICITUD {
    AD_ASIG("Adición Asignaturas"),
    CA_ASIG("Cancelación Asignaturas"),
    HO_ASIG_ESP("Homologación Asignaturas"),
    HO_ASIG_POS("Homologación Asignaturas"),
    AP_SEME("Aplazamiento Semestre"),
    CU_ASIG("Cursos Externos"),
    AV_PASA_INV("Aval Pasantía"),
    AP_ECON_INV("Apoyo Económico"),
    RE_CRED_PAS("Reconocimiento Créditos"),
    RE_CRED_PR_DOC("Reconocimiento Práctica Docente"),
    RE_CRED_DIS("Reconocimiento Créditos"),
    PR_CURS_TEO("Reconocimiento Créditos"),
    AS_CRED_DO("Asignación Créditos por Docencia"),
    RE_CRED_SEM("Reconocimiento Créditos"),
    AS_CRED_MON("Asignación Créditos"),
    AS_CRED_MAT("Asignación Créditos"),
    TG_PREG_POS("Asignación Créditos"),
    JU_PREG_POS("Reconocimiento Créditos"),
    EV_ANTE_PRE("Reconocimiento Créditos"),
    EV_PROD_INT("Reconocimiento Créditos"),
    EV_INFO_SAB("Reconocimiento Créditos"),
    PA_COMI_PRO("Reconocimiento Créditos"),
    OT_ACTI_APO("Reconocimiento Créditos"),
    RE_CRED_PUB("Reconocimiento Créditos"),
    AV_SEMI_ACT("Aval Seminario Actualización"),
    AP_ECON_ASI("Apoyo Económico"),
    PA_PUBL_EVE("Apoyo Económico"),
    AV_COMI_PR("Aval Práctica Docente"),
    SO_BECA("Solicitud de Beca"),
    SO_DESC("Solicitud de Descuento");

    private final String descripcion;

    ABREVIATURA_SOLICITUD(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
