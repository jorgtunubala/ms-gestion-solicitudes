package com.maestria.gestionSolicitudes.dto.rest.request;

import lombok.Data;

@Data
public class SolicitudBecaRequest {
    private String tipo;
    private String motivo;
    private String formatoSolicitudBeca;
}
