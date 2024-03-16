package com.maestria.gestionSolicitudes.service.rest;

import java.util.List;

import com.maestria.gestionSolicitudes.domain.Solicitudes;
import com.maestria.gestionSolicitudes.dto.rest.request.CancelarAsignaturaRequest;
import com.maestria.gestionSolicitudes.dto.rest.request.InfoAdicionAsignaturaRequest;
import com.maestria.gestionSolicitudes.dto.rest.response.InfoAdicionAsignaturaResponse;

public interface AdicionAsignaturaService {
    List<InfoAdicionAsignaturaResponse> listarDocentesAsignaturas();
    Boolean registrarAdicionAsignaturas(Solicitudes solicitud, List<InfoAdicionAsignaturaRequest> listaAsignaturas) throws Exception;
    Boolean registrarCancelarAsignaturas(Solicitudes solicitud, CancelarAsignaturaRequest datosCancelarAsignatura) throws Exception;
}
