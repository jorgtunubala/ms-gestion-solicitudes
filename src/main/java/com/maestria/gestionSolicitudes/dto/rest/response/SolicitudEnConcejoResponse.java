package com.maestria.gestionSolicitudes.dto.rest.response;

import java.util.List;

import com.maestria.gestionSolicitudes.dto.rest.request.AprobarAsignaturaRequest;
import com.maestria.gestionSolicitudes.dto.rest.request.AsignaturaOtroPrograma.AprobarAsignaturaOPRequest;
import com.maestria.gestionSolicitudes.dto.rest.request.homologaciones.AprobarHomologacionRequest;

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
    private List<AprobarHomologacionRequest> asignaturasHomologadas;
    private List<AprobarAsignaturaOPRequest> asignaturasOtroPrograma;
}
