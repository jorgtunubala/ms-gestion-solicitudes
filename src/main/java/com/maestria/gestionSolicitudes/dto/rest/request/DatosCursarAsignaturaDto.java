package com.maestria.gestionSolicitudes.dto.rest.request;

import java.util.List;

import lombok.Data;

@Data
public class DatosCursarAsignaturaDto {
    private String motivo;    
    private List<AsignaturaExternaRequest> listaAsignaturasCursar;
    private String codigoAsignatura;
    private String grupo;
    private String nombreDocente;
    private String tituloDocente;
    private String cartaAceptacion;
}
