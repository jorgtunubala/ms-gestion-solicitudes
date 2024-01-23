package com.maestria.gestionSolicitudes.dto.rest.request;

import lombok.Data;

@Data
public class DatosAsignaturaHomologacionDto {
    private String nombreAsignatura;
    private Integer numeroCreditos;
    private Integer intensidadHoraria;
    private Double calificacion;
    private String contenidoProgramatico;
}
