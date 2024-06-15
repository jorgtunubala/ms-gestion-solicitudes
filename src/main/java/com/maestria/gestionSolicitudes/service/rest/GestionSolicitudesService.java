package com.maestria.gestionSolicitudes.service.rest;

import java.util.List;

import com.maestria.gestionSolicitudes.dto.client.InformacionPersonalDto;
import com.maestria.gestionSolicitudes.dto.rest.request.DatosAvalarSolicitudDto;
import com.maestria.gestionSolicitudes.dto.rest.request.SolicitudRequestDto;
import com.maestria.gestionSolicitudes.dto.rest.request.TipoSolicitudDto;
import com.maestria.gestionSolicitudes.dto.rest.response.*;

public interface GestionSolicitudesService {
    
    List<TipoSolicitudDto> obtenerTiposSolicitudes();

    DocumentoRequeridoSolicitudDto getRequisitoSolicitudAndDocumentosAndNotasPorSolicitudId(String codigo) throws Exception;

    InformacionPersonalDto obtenerInformacionPersonal(String correo) throws Exception;

    List<TutorDto> obtenerTutotes() throws Exception;

    String registrarSolicitud(SolicitudRequestDto datosSolicitud) throws Exception;

    List<SolicitudPendientesAval> obtenerSolicitudesPendientes(String correo) throws Exception;

    DatosGestionSolicitudResponse obtenerDatosSolicitud(Integer idSolicitud) throws Exception;

    Boolean registrarFirmasPendientes(DatosAvalarSolicitudDto dAvalarSolicitudDto) throws Exception;
}
