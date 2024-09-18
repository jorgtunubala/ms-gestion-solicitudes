package com.maestria.gestionSolicitudes.service.rest;

import com.maestria.gestionSolicitudes.dto.rest.response.SolicitudEnComiteResponse;

public interface GestionSolicitudesEnComiteService {

    SolicitudEnComiteResponse obtenerSolicitudEnComite(Integer idSolicitud);

    Boolean guardarSolicitudEnComite(SolicitudEnComiteResponse datosSolicitudEnComite);
    
}
