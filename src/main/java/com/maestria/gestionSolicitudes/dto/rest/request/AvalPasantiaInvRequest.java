package com.maestria.gestionSolicitudes.dto.rest.request;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class AvalPasantiaInvRequest {
    private String lugarPasantia;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private List<String> documentosAdjuntos;
}
