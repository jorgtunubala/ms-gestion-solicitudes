package com.maestria.gestionSolicitudes.dto.client;

import lombok.Data;

@Data
public class AsignaturaExternaResponseDto {
    private Integer idAsignatura;
    private String nombre;
    private Integer creditos;
    private Integer intensidadHoraria;
    private Double calificacion;
    private String programa;
    private String institucion;
    private String contenidoProgramatico;
}
