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
    RE_CRED_PAS("Reconocimiento Créditos Pasantia"),
    RE_CRED_PR_DOC("Reconocimiento Práctica Docente"),
    RE_CRED_DIS("Reconocimiento Créditos Diseño Curricular"),
    PR_CURS_TEO("Reconocimiento Créditos Cursos Teóricos"),
    AS_CRED_DO("Asignación Créditos por Docencia"),
    RE_CRED_SEM("Reconocimiento Créditos Seminario Actualización"),
    AS_CRED_MON("Asignación Créditos Monitorias Cursos"),
    AS_CRED_MAT("Asignación Créditos Elaboración Material Apoyo"),
    TG_PREG_POS("Asignación Créditos Dirección Trabajo Grado"),
    JU_PREG_POS("Reconocimiento Créditos Jurado Trabajo Grado"),
    EV_ANTE_PRE("Reconocimiento Créditos Evaluación Anteproyecto"),
    EV_PROD_INT("Reconocimiento Créditos Evaluación Productividad Intelectual"),
    EV_INFO_SAB("Reconocimiento Créditos Evaluación Informe Sabático"),
    PA_COMI_PRO("Reconocimiento Créditos Participación Comité Programa"),
    OT_ACTI_APO("Reconocimiento Créditos Realización Otras Actividades Apoyo"),
    RE_CRED_PUB("Reconocimiento Créditos Publicaciones"),
    AV_SEMI_ACT("Aval Seminario Actualización"),
    AP_ECON_ASI("Apoyo Económico"),
    PA_PUBL_EVE("Apoyo Económico"),
    AV_COMI_PR("Aval Comite Programa"),
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
