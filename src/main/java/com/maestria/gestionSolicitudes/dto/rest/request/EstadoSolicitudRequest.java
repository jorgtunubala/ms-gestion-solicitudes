package com.maestria.gestionSolicitudes.dto.rest.request;

import lombok.Data;

@Data
public class EstadoSolicitudRequest {
    private String codigo;
    private String estado;
}
