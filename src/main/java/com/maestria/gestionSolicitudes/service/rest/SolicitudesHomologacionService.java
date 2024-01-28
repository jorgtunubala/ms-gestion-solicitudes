package com.maestria.gestionSolicitudes.service.rest;

import java.util.List;

import com.maestria.gestionSolicitudes.dto.rest.request.DatosSolicitudHomologacionDto;
import com.maestria.gestionSolicitudes.dto.rest.response.DatosSolicitudHomologacion;

public interface SolicitudesHomologacionService {
    
    boolean registrarSolicitudHomologacion(DatosSolicitudHomologacionDto dHomologacionDto);

    List<DatosSolicitudHomologacion> obtenerTodasHomologaciones();
}
