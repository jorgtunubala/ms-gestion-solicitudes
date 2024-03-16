package com.maestria.gestionSolicitudes.dto.rest.request;

import java.util.List;

import lombok.Data;

@Data
public class SolicitudRequestDto {
    private Integer idTipoSolicitud;
    private Integer idEstudiante;
    private Integer idTutor;
    private DatosSolicitudHomologacionDto datosHomologacion;
    private List<InfoAdicionAsignaturaRequest> datosAdicionAsignatura;
    private CancelarAsignaturaRequest datosCancelarAsignatura;
    private Boolean requiereFirmaDirector;
    private String firmaEstudiante;
}
