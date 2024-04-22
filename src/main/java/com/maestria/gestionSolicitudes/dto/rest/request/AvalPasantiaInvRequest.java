package com.maestria.gestionSolicitudes.dto.rest.request;

import java.util.Date;

import lombok.Data;

@Data
public class AvalPasantiaInvRequest {
    private String lugarPasantia;
    private Date fechaInicio;
    private Date fechaFin;
}
