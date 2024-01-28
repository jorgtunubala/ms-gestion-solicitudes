package com.maestria.gestionSolicitudes.dto.rest.response;

import java.util.List;

import lombok.Data;

@Data
public class DatosSolicitudCursarAsignaturas {
    private List<DatosAsignaturaOtroPrograma> datosAsignaturaOtroProgramas;
    private String motivo;
    private List<String> documentosAdjuntos;
    
}