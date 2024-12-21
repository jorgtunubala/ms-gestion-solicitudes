package com.maestria.gestionSolicitudes.service.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.text.SimpleDateFormat;

import com.maestria.gestionSolicitudes.domain.SolicitudesCertificadoVotacion;
import com.maestria.gestionSolicitudes.dto.rest.response.SolicitudCertificadoVotacionResponse;
import com.maestria.gestionSolicitudes.repository.SolicitudesCertificadoVotacionRepository;
import com.maestria.gestionSolicitudes.service.rest.GestionSolicitudesCertificadoVotacion;

import java.util.Optional;

@Service
public class GestionSolicitudesCertificadoVotacionImpl implements GestionSolicitudesCertificadoVotacion {

    @Autowired
    private SolicitudesCertificadoVotacionRepository solicitudesCertificadoVotacionRepository;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public SolicitudCertificadoVotacionResponse obtenerSolicitudCertificadoVotacion(Integer id) throws Exception {
        try {
            Optional<SolicitudesCertificadoVotacion> solicitudOpt = solicitudesCertificadoVotacionRepository.findById(id);
            
            if (solicitudOpt.isEmpty()) {
                throw new Exception("No se encontró la solicitud con ID: " + id);
            }
            
            return convertirAResponse(solicitudOpt.get());
        } catch (Exception e) {
            throw new Exception("Error al obtener la solicitud de certificado de votación: " + e.getMessage());
        }
    }

    @Override
    public Boolean guardarSolicitudCertificadoVotacion(SolicitudCertificadoVotacionResponse solicitudResponse) throws Exception {
        try {
            Optional<SolicitudesCertificadoVotacion> solicitudOpt = solicitudesCertificadoVotacionRepository.findById(solicitudResponse.getId());
            
            if (solicitudOpt.isEmpty()) {
                throw new Exception("No se encontró la solicitud para actualizar");
            }

            SolicitudesCertificadoVotacion solicitud = solicitudOpt.get();
            actualizarSolicitud(solicitud, solicitudResponse);
            solicitudesCertificadoVotacionRepository.save(solicitud);
            
            return true;
        } catch (Exception e) {
            throw new Exception("Error al guardar la solicitud de certificado de votación: " + e.getMessage());
        }
    }

    private SolicitudCertificadoVotacionResponse convertirAResponse(SolicitudesCertificadoVotacion solicitud) {
        SolicitudCertificadoVotacionResponse response = new SolicitudCertificadoVotacionResponse();
        response.setId(solicitud.getId());
        response.setId_Estudiante(solicitud.getIdEstudiante());
        response.setEstado(solicitud.getEstado());
        response.setFecha_creacion(dateFormat.format(solicitud.getFechaCreacion()));
        response.setFecha_modificacion(dateFormat.format(solicitud.getFechaModificacion()));
        response.setDocumento_Firmado(solicitud.getDocumentoFirmado());
        return response;
    }

    private void actualizarSolicitud(SolicitudesCertificadoVotacion solicitud, SolicitudCertificadoVotacionResponse solicitudResponse) throws Exception {
        try {
            solicitud.setEstado(solicitudResponse.getEstado());
            solicitud.setDocumentoFirmado(solicitudResponse.getDocumento_Firmado());
            solicitud.setFechaModificacion(new Date(dateFormat.parse(solicitudResponse.getFecha_modificacion()).getTime()));
        } catch (Exception e) {
            throw new Exception("Error al actualizar la solicitud: " + e.getMessage());
        }
    }
}