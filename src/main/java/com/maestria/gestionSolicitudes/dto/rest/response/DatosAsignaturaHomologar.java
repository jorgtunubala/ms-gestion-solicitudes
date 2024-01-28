package com.maestria.gestionSolicitudes.dto.rest.response;

import lombok.Data;

@Data
public class DatosAsignaturaHomologar {
    private String nombreAsignatura;
    private Integer creditos;
    private Integer intensidadHoraria;
    private Double calificacion;    
}
