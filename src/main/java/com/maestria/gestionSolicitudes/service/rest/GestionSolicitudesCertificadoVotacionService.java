package com.maestria.gestionSolicitudes.service.rest;

import org.springframework.http.ResponseEntity;
import java.util.List;
import com.maestria.gestionSolicitudes.dto.rest.response.SolicitudCertificadoVotacionResponse;
import com.maestria.gestionSolicitudes.dto.rest.response.DocumentoCertificadoVotacionResponse;
import com.maestria.gestionSolicitudes.dto.rest.response.EstudiantesResponse;

public interface GestionSolicitudesCertificadoVotacionService {
    List<SolicitudCertificadoVotacionResponse> obtenerSolicitudesCertificadoVotacion() throws Exception;
    List<EstudiantesResponse> obtenerEstudiantesPeriodoIngreso() throws Exception;
    byte[] obtenerDocumentosZipFiltrados(String period, List<Integer> certificateIds) throws Exception;

}