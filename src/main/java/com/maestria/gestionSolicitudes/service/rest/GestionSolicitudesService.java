package com.maestria.gestionSolicitudes.service.rest;

import java.util.List;

import com.maestria.gestionSolicitudes.dto.client.InformacionPersonalDto;
import com.maestria.gestionSolicitudes.dto.rest.DocumentoRequeridoSolicitudDto;
import com.maestria.gestionSolicitudes.dto.rest.TipoSolicitudDto;
import com.maestria.gestionSolicitudes.dto.rest.TutorDto;

public interface GestionSolicitudesService {
    
    List<TipoSolicitudDto> obtenerTiposSolicitudes();

    DocumentoRequeridoSolicitudDto getRequisitoSolicitudAndDocumentosAndNotasPorSolicitudId(String codigo) throws Exception;

    InformacionPersonalDto obtenerInformacionPersonal(String correo) throws Exception;

    List<TutorDto> obtenerTutotes() throws Exception;
}
