package com.maestria.gestionSolicitudes.dto.rest.request;

import java.util.List;

import lombok.Data;

@Data
public class AvalSeminarioActRequest {
    private List<String> documentosAdjuntos;
}
