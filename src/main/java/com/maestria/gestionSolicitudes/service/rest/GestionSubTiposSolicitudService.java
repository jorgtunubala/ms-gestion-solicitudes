package com.maestria.gestionSolicitudes.service.rest;

import java.util.List;

import com.maestria.gestionSolicitudes.dto.rest.response.SubTiposSolicitudResponse;

public interface GestionSubTiposSolicitudService {
    
    List<SubTiposSolicitudResponse> obtenerSubtiposPorTipoSolicitud(Integer id); 
}
