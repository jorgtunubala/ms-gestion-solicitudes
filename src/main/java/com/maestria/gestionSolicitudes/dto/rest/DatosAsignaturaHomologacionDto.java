package com.maestria.gestionSolicitudes.dto.rest;

import lombok.Data;

@Data
public class DatosAsignaturaHomologacionDto {
    private String nombreAsignatura;
    private String numeroCreditos;
    private Integer intensidadHoraria;
    private Double calificacion;
    private String contenidoProgramatico;
}
