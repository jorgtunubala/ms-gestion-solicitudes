package com.maestria.gestionSolicitudes.service.rest;

import java.util.List;

import com.maestria.gestionSolicitudes.domain.Solicitudes;
import com.maestria.gestionSolicitudes.dto.rest.response.InfoAdicionAsignaturaResponse;

public interface AdicionAsignaturaService {
    List<InfoAdicionAsignaturaResponse> listarDocentesAsignaturas();
    Boolean registrarAdicionAsignaturas(Solicitudes solicitud, List<Integer> listaAsignaturas) throws Exception;
}
