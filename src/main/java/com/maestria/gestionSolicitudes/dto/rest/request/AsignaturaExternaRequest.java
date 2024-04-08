package com.maestria.gestionSolicitudes.dto.rest.request;

import lombok.Data;

@Data
public class AsignaturaExternaRequest {
    private String programaProcedencia;
    private String institutoProcedencia;
    private String nombreAsignatura;
    private Integer numeroCreditos;
    private Integer intensidadHoraria;    
    private String contenidoProgramatico;
    private String codigoAsignatura;
    private String grupo;
    private String nombreDocente;
    private String tituloDocente;
    private String cartaAceptacion;
}
