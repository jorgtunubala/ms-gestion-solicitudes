package com.maestria.gestionSolicitudes.dto.rest.request;

import java.util.List;

import lombok.Data;

@Data
public class DatosSolicitudCursarAsignaturaDto {
    private DatosCursarAsignaturaDto datosCursarAsignaturaDto;
    private List<String> documentosAdjuntos;
}
