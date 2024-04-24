package com.maestria.gestionSolicitudes.dto.rest.request;

import java.util.List;

import lombok.Data;

@Data
public class AvalPasantiaInvRequest {
    private String lugarPasantia;
    private String fechaInicio;
    private String fechaFin;
    private List<String> documentosAdjuntos;
}
