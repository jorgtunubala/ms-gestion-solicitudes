package com.maestria.gestionSolicitudes.dto.rest.response;

import java.util.List;

import com.maestria.gestionSolicitudes.dto.rest.request.AprobarAsignaturaRequest;

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
    private List<AprobarAsignaturaRequest> asignaturasAprobadas;
}
