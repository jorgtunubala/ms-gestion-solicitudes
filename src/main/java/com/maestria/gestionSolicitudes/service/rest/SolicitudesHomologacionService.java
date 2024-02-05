package com.maestria.gestionSolicitudes.service.rest;

import com.maestria.gestionSolicitudes.dto.rest.request.DatosSolicitudHomologacionDto;

public interface SolicitudesHomologacionService {
    
    boolean registrarSolicitudHomologacion(Integer tipoSolicitud, DatosSolicitudHomologacionDto dHomologacionDto);

}
