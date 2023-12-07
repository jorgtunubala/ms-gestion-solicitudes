package com.maestria.gestionSolicitudes.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maestria.gestionSolicitudes.dto.rest.DocumentoRequeridoSolicitudDto;
import com.maestria.gestionSolicitudes.dto.rest.InformacionPersonalDto;
import com.maestria.gestionSolicitudes.dto.rest.TipoSolicitudDto;
import com.maestria.gestionSolicitudes.dto.rest.TutorDto;
import com.maestria.gestionSolicitudes.dto.rest.response.DocumentosRequeridosResponse;
import com.maestria.gestionSolicitudes.dto.rest.response.InformacionPersonalResponse;
import com.maestria.gestionSolicitudes.dto.rest.response.TipoSolicitudResponse;
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
}
