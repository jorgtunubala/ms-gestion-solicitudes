package com.maestria.gestionSolicitudes.dto.rest.request;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class AvalPasantiaInvRequest {
    private String lugarPasantia;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaInicio;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaFin;
    private List<String> documentosAdjuntos;
}
