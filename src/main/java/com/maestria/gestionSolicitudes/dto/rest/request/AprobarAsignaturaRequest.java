package com.maestria.gestionSolicitudes.dto.rest.request;

import lombok.Data;

@Data
public class AprobarAsignaturaRequest {
    private Integer idAsignatura;
    private String nombre;
    private String grupo;
    private String nombreDocente;
    private Boolean aprobado;
}
