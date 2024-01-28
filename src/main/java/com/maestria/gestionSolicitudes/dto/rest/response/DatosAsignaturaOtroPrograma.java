package com.maestria.gestionSolicitudes.dto.rest.response;

import lombok.Data;

@Data
public class DatosAsignaturaOtroPrograma {
    private String nombre;
    private String codigo;
    private Integer creditos;
    private Integer intensidadHoraria;
    private String grupo;
    private String nombrePrograma;
    private String nombreDocente;
}
