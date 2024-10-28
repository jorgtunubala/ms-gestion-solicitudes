
package com.maestria.gestionSolicitudes.dto.rest.request.AsignaturaOtroPrograma;

import lombok.Data;

@Data
public class AprobarAsignaturaOPRequest {

    private Integer idCursarAsignatura;
    private String nombreAsignatura;
    private String codigo;
    private Integer creditos;
    private Integer intensidadHoraria;
    private String grupo;
    private String nombreInstitucion;
    private String nombrePrograma;
    private String tituloDocente;
    private String nombreDocente;
    private Boolean aprobado;
}