package com.maestria.gestionSolicitudes.dto.rest.request;

import lombok.Data;

@Data
public class EnvioCorreoRequest {
    private String destinatario;
    private String oficio;
}
