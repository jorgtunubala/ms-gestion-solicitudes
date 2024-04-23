package com.maestria.gestionSolicitudes.dto.rest.response;

import java.util.List;

import lombok.Data;

@Data
public class AvalPasantiaInvResponse {
    private String lugarPasantia;
    private String fechaInicio;
    private String fechaFin;
    private List<String> documentosAdjuntos;
}
