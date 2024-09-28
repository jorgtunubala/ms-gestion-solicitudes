package com.maestria.gestionSolicitudes.dto.rest.request;

import lombok.Data;

@Data
public class AprobarAsignaturaRequest {
    private Integer idAsignatura;
    private String nombre;
    private Boolean aprobado;
}
