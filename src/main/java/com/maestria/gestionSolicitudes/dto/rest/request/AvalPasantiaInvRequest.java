package com.maestria.gestionSolicitudes.dto.rest.request;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class AvalPasantiaInvRequest {
    private String lugarPasantia;
    private Date fechaInicio;
    private Date fechaFin;
    private List<String> documentosAdjuntos;
}
