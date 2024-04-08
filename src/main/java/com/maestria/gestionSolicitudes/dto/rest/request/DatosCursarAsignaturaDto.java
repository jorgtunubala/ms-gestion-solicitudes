package com.maestria.gestionSolicitudes.dto.rest.request;

import java.util.List;

import lombok.Data;

@Data
public class DatosCursarAsignaturaDto {
    private String motivo;    
    private List<AsignaturaExternaRequest> listaAsignaturasCursar;
}
