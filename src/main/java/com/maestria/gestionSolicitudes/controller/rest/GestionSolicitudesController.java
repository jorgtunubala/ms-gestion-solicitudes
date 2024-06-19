package com.maestria.gestionSolicitudes.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maestria.gestionSolicitudes.dto.client.InformacionPersonalDto;
import com.maestria.gestionSolicitudes.dto.rest.request.DatosAvalarSolicitudDto;
import com.maestria.gestionSolicitudes.dto.rest.request.EstadoSolicitudRequest;
import com.maestria.gestionSolicitudes.dto.rest.request.SolicitudRequestDto;
import com.maestria.gestionSolicitudes.dto.rest.request.TipoSolicitudDto;
import com.maestria.gestionSolicitudes.dto.rest.response.DatosGestionSolicitudResponse;
import com.maestria.gestionSolicitudes.dto.rest.response.DocumentoRequeridoSolicitudDto;
import com.maestria.gestionSolicitudes.dto.rest.response.DocumentosRequeridosResponse;
import com.maestria.gestionSolicitudes.dto.rest.response.InformacionPersonalResponse;
import com.maestria.gestionSolicitudes.dto.rest.response.SolicitudHistoricoResponse;
import com.maestria.gestionSolicitudes.dto.rest.response.SolicitudPendientesAval;
import com.maestria.gestionSolicitudes.dto.rest.response.TipoSolicitudResponse;
import com.maestria.gestionSolicitudes.dto.rest.response.TutorDto;
import com.maestria.gestionSolicitudes.dto.rest.response.TutorResponse;
import com.maestria.gestionSolicitudes.service.rest.GestionSolicitudesService;

@RestController
@RequestMapping("/gestionSolicitud")
public class GestionSolicitudesController {
    
    @Autowired
    private GestionSolicitudesService gestionSolicitudesService;

    @GetMapping("/tiposSolicitud")
    public TipoSolicitudResponse obtenerTiposSolicitud() {
        TipoSolicitudResponse tipoSolicitudResponse = new TipoSolicitudResponse();
        try {
            List<TipoSolicitudDto> tipoSolicitudDtos = gestionSolicitudesService.obtenerTiposSolicitudes();            
            tipoSolicitudResponse.setTipoSolicitudDto(tipoSolicitudDtos);   
        } catch (Exception e) {
            tipoSolicitudResponse.setMensaje(e.getMessage());;
        }
        return tipoSolicitudResponse;
    }

    @GetMapping("/requisitoSolicitud/{codigo}")
    public DocumentosRequeridosResponse obtenerRequisitos(@PathVariable String codigo) {
        DocumentosRequeridosResponse dRequeridosResponse = new DocumentosRequeridosResponse();
        try {
            DocumentoRequeridoSolicitudDto dRequeridoSolicitudDto = gestionSolicitudesService.getRequisitoSolicitudAndDocumentosAndNotasPorSolicitudId(codigo);
            dRequeridosResponse.setDoRequeridoSolicitudDto(dRequeridoSolicitudDto);
        } catch (Exception e) {
            dRequeridosResponse.setMensaje(e.getMessage());
        }
        return dRequeridosResponse;
    }

    @GetMapping("/obtenerInfoPersonal/{correo}")
    public InformacionPersonalResponse obtenerInformacionPersonal(@PathVariable String correo) {
        InformacionPersonalResponse response = new InformacionPersonalResponse();
        try {
            InformacionPersonalDto informacionPersonalDto = gestionSolicitudesService.obtenerInformacionPersonal(correo);
            response.setInformacionPersonalDto(informacionPersonalDto);
        } catch (Exception e) {
            response.setMensaje(e.getMessage());
        }
        return response;
    }

    @GetMapping("/obtenerTutores")
    public TutorResponse obtenerTutores() {
        TutorResponse response = new TutorResponse();
        try {
            List<TutorDto> tutoresDto = gestionSolicitudesService.obtenerTutotes();
            response.setTutores(tutoresDto);
        } catch (Exception e) {
            response.setMensaje(e.getMessage());
        }
        return response;
    }

    @PostMapping("/save")
    public String registrarSolicitud(@RequestBody SolicitudRequestDto datosSolicitud) throws Exception {
        return gestionSolicitudesService.registrarSolicitud(datosSolicitud);
    }

    @GetMapping("/obtener-solicitudes-pendientes/{identificador}")
    public List<SolicitudPendientesAval> obtenerSolicitudesPendientes(@PathVariable String identificador) throws Exception {
        return gestionSolicitudesService.obtenerSolicitudesPendientes(identificador);
    }

    @GetMapping("/obtener-datos-solicitud/{idSolicitud}")
    public DatosGestionSolicitudResponse obtenerDatosSolicitud(@PathVariable Integer idSolicitud) throws Exception {
        return gestionSolicitudesService.obtenerDatosSolicitud(idSolicitud);
    }

    @PostMapping("/save/firmas")
    public Boolean registrarFirmasSolicitud(@RequestBody DatosAvalarSolicitudDto dAvalarSolicitudDto) throws Exception {
        return gestionSolicitudesService.registrarFirmasPendientes(dAvalarSolicitudDto);
    }

    @GetMapping("/historial/solicitud")
    public List<SolicitudHistoricoResponse> obtenerHistorialSolicitud(@RequestBody EstadoSolicitudRequest estadoSolicitudRequest) throws Exception {
        return gestionSolicitudesService.obtenerHistorialSeguimiento(estadoSolicitudRequest);
    }
}

