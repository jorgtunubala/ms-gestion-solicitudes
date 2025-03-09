package com.maestria.gestionSolicitudes.service.rest;

import java.util.List;
import com.maestria.gestionSolicitudes.dto.rest.response.SolicitudCertificadoVotacionResponse;
import com.maestria.gestionSolicitudes.dto.rest.request.EstadoSolicitudRequest;
import com.maestria.gestionSolicitudes.dto.rest.request.SolicitudPorFechaRequest;
import com.maestria.gestionSolicitudes.dto.rest.response.DocumentoCertificadoVotacionResponse;
import com.maestria.gestionSolicitudes.dto.rest.response.EstadoEstudianteResponse;
import com.maestria.gestionSolicitudes.dto.rest.response.FechaActualResponse;

public interface GestionSolicitudesCertificadoVotacionService {
    List<SolicitudCertificadoVotacionResponse> obtenerSolicitudesCertificadoVotacion() throws Exception;
    List<EstadoEstudianteResponse> obtenerEstadoEstudiante() throws Exception;
    List<EstadoSolicitudRequest> actualizarEstadoSolicitud(EstadoSolicitudRequest estadoSolicitud) throws Exception;
    byte[] obtenerDocumentosZipFiltrados(String estado_solicitud, String estado_estudiante) throws Exception;
    List<SolicitudPorFechaRequest> actualizarFechaSolicitud(SolicitudPorFechaRequest datosFechaSolicitud);

}