package com.maestria.gestionSolicitudes.dto.rest.response;

import lombok.Data;

@Data
public class SolicitudPendientesAval {
    private Integer idSolicitud;
    private String codigoSolicitud;
    private String nombreEstudiante;
    private String nombreTipoSolicitud;
    private String abreviatura;
    private String fecha;
    private Boolean requiereFirmaDirector;
}
