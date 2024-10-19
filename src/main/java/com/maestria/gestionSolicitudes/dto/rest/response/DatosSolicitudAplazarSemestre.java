package com.maestria.gestionSolicitudes.dto.rest.response;

import lombok.Data;

@Data
public class DatosSolicitudAplazarSemestre {
    private String semestre;
    private String motivo;
    private String documentoAdjunto;
}
