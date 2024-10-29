package com.maestria.gestionSolicitudes.dto.rest.request;

import java.util.List;

import lombok.Data;

@Data
public class AvalPasantiaInvRequest {
    private String lugarPasantia;
    private String fechaInicio;
    private String fechaFin;
    private String universidadResidencia;
    private String grupoUniversidadResidencia;
    private String nombreDocenteExterno;
    private List<String> documentosAdjuntos;
}
