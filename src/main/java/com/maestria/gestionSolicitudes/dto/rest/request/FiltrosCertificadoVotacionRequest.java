package com.maestria.gestionSolicitudes.dto.rest.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FiltrosCertificadoVotacionRequest {
    private String estado_solicitud;
    private String estado_estudiante;
}
