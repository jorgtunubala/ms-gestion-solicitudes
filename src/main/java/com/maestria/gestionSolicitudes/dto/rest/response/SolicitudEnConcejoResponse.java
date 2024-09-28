package com.maestria.gestionSolicitudes.dto.rest.response;

import java.util.List;

import lombok.Data;

@Data
public class SolicitudEnConcejoResponse {
    private Integer idSolicitud;
    private Boolean enConcejo;
    private String avaladoConcejo;
    private String conceptoConcejo;
    private String numeroActa;
    private String fechaAval;
    private List<String> documentosConcejo;
}
