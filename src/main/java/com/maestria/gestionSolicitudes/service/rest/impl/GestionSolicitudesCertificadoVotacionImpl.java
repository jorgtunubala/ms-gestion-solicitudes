package com.maestria.gestionSolicitudes.service.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.sql.Timestamp;

import com.maestria.gestionSolicitudes.domain.SolicitudesCertificadoVotacion;
import com.maestria.gestionSolicitudes.domain.Solicitudes;
import com.maestria.gestionSolicitudes.dto.rest.response.SolicitudCertificadoVotacionResponse;
import com.maestria.gestionSolicitudes.dto.rest.response.SolicitudEnComiteResponse;
import com.maestria.gestionSolicitudes.repository.SolicitudesCertificadoVotacionRepository;
import com.maestria.gestionSolicitudes.service.rest.GestionSolicitudesCertificadoVotacionService;

@Service
public class GestionSolicitudesCertificadoVotacionImpl implements GestionSolicitudesCertificadoVotacionService {

    @Autowired
    private SolicitudesCertificadoVotacionRepository solicitudesCertificadoVotacionRepository;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public List<SolicitudCertificadoVotacionResponse> obtenerSolicitudesCertificadoVotacion() throws Exception{
    List<SolicitudCertificadoVotacionResponse> listaSolicitudes = new ArrayList<>();
    
        try {
            // Obtener todas las solicitudes de certificado de votación
            List<SolicitudesCertificadoVotacion> solicitudes = solicitudesCertificadoVotacionRepository.findAllSolicitudesOrderByFechaModificacion();
            
            if (solicitudes.isEmpty()) {
                throw new Exception("No se encontraron solicitudes de certificado de votación");
            }
            
            // Convertir cada solicitud a su response correspondiente
            for (SolicitudesCertificadoVotacion solicitud : solicitudes) {
                SolicitudCertificadoVotacionResponse solicitudResponse = convertirAResponse(solicitud);
                listaSolicitudes.add(solicitudResponse);
            }
            
            return listaSolicitudes;
            
        } catch (Exception e) {
            throw new Exception("Error al obtener las solicitudes de certificado de votación: " + e.getMessage());
        }
    }

    private SolicitudCertificadoVotacionResponse convertirAResponse(SolicitudesCertificadoVotacion solicitud) {
        SolicitudCertificadoVotacionResponse response = new SolicitudCertificadoVotacionResponse();
        response.setId(solicitud.getId());
        response.setId_Estudiante(solicitud.getIdEstudiante());
        response.setEstado(solicitud.getEstado());
        response.setFecha_creacion(solicitud.getFechaCreacion());
        response.setFecha_modificacion(solicitud.getFechaModificacion());
        response.setId_tipo_solicitud(solicitud.getIdTipoSolicitud());
        return response;
    }
}