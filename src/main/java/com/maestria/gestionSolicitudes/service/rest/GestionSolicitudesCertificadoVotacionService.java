package com.maestria.gestionSolicitudes.service.rest;

import org.springframework.http.ResponseEntity;
import java.util.List;
import com.maestria.gestionSolicitudes.dto.rest.response.SolicitudCertificadoVotacionResponse;
import com.maestria.gestionSolicitudes.dto.rest.response.DocumentoCertificadoVotacionResponse;

public interface GestionSolicitudesCertificadoVotacionService {
    List<SolicitudCertificadoVotacionResponse> obtenerSolicitudesCertificadoVotacion() throws Exception;
    byte[] obtenerTodosDocumentosZip() throws Exception;
}