package com.maestria.gestionSolicitudes.service.rest;

import com.maestria.gestionSolicitudes.dto.rest.response.SolicitudEnConcejoResponse;

public interface GestionSolicitudesEnConcejoService {

    SolicitudEnConcejoResponse obtenerSolicitudEnConcejo(Integer idSolicitud);

    Boolean guardarSolicitudEnConcejo(SolicitudEnConcejoResponse datosSolicitudEnConcejo);
    
}
