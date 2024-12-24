package com.maestria.gestionSolicitudes.service.rest;

import java.util.List;
import com.maestria.gestionSolicitudes.dto.rest.response.SolicitudCertificadoVotacionResponse;

public interface GestionSolicitudesCertificadoVotacionService {
    List<SolicitudCertificadoVotacionResponse> obtenerSolicitudesCertificadoVotacion() throws Exception;
}