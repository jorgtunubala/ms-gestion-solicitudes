package com.maestria.gestionSolicitudes.dto.rest.request;

import lombok.Data;

@Data
public class AplazarSemestreRequest {
    private String semestre;
    private String motivo;
    private String documentoAdjunto;
}
