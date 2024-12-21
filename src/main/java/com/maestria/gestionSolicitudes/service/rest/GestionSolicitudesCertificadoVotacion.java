package com.maestria.gestionSolicitudes.service.rest;

import com.maestria.gestionSolicitudes.dto.rest.response.SolicitudCertificadoVotacionResponse;

public interface GestionSolicitudesCertificadoVotacion {
    SolicitudCertificadoVotacionResponse obtenerSolicitudCertificadoVotacion(Integer id) throws Exception;
    Boolean guardarSolicitudCertificadoVotacion(SolicitudCertificadoVotacionResponse solicitudResponse) throws Exception;
}