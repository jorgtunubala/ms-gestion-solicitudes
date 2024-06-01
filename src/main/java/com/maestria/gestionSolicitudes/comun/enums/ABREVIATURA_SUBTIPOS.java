package com.maestria.gestionSolicitudes.comun.enums;

public enum ABREVIATURA_SUBTIPOS {
    DIS_CUR_PREG("Diseño Curricular Pregrado"),
    DIS_CUR_POSG("Diseño Curricular Posgrado"),
    PRE_CUR_PREG("Preparación Curso Pregrado"),
    PRE_CUR_POSG("Preparación Curso Posgrado"),
    DOC_CUR_PREG("Docencia curso pregrado"),
    DOC_CUR_POSG("Docencia curso posgrado"),
    CUR_COR_SEM("Seminario actualización"),
    MON_CUR("Monitorías"),
    ELA_MAT_APOY("Elaboración material de apoyo"),
    DIR_TRA_PREG("Dirección pregrado"),
    DIR_TRA_POSG("Dirección posgrado"),
    JUR_TRA_PREG("Jurado pregrado"),
    JUR_ANT_MAE("Jurado anteproyecto maestría"),
    JUR_TRA_MAE("Jurado maestría"),
    ASE_PAS_EMP("Pasantía empresarial"),
    EVA_JUR_EMP("Evaluación jurado pasantía"),
    EVA_TRA_EMP("Evaluación plan de trabajo pasantía"),
    EVA_ANT_PREG_DEP("Evaluación Anteproyectos"),
    EVA_PRO_INT_PON("Productividad Intelectual (Ponencias)"),
    EVA_PRO_INT_LIB("Productividad Intelectual (Libros)"),
    EVA_INF_SAB("Informe sabático"),
    PAR_COM_PRO("Participación comité"),
    ACT_APO_DEP("Otras actividades apoyo");

    private final String descripcion;

    ABREVIATURA_SUBTIPOS(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static String getDescripcionPorCodigo(String codigo) {
        for (ABREVIATURA_SUBTIPOS tipo : ABREVIATURA_SUBTIPOS.values()) {
            if (tipo.name().equals(codigo)) {
                return tipo.getDescripcion();
            }
        }
        return null; // O lanza una excepción si prefieres
    }
}
