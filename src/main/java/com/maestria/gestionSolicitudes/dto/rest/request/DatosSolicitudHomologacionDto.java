package com.maestria.gestionSolicitudes.dto.rest.request;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DatosSolicitudHomologacionDto {
    private Integer idSolicitud;
    private Integer idEstudiante;
    private DatosHomologacionDto datosHomologacionDto;
    private Integer idTutor;
    private List<String> documentosAdjuntos;
}
