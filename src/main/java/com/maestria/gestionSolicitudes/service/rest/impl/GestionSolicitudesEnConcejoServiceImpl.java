package com.maestria.gestionSolicitudes.service.rest.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maestria.gestionSolicitudes.comun.enums.ESTADO_SOLICITUD;
import com.maestria.gestionSolicitudes.domain.DocumentosConcejo;
import com.maestria.gestionSolicitudes.domain.Solicitudes;
import com.maestria.gestionSolicitudes.domain.SolicitudesEnConcejo;
import com.maestria.gestionSolicitudes.dto.rest.response.SolicitudEnConcejoResponse;
import com.maestria.gestionSolicitudes.repository.DocumentosConcejoRepository;
import com.maestria.gestionSolicitudes.repository.SolicitudesEnConcejoRepository;
import com.maestria.gestionSolicitudes.repository.SolicitudesRepository;
import com.maestria.gestionSolicitudes.service.rest.GestionSolicitudesEnConcejoService;
import com.maestria.gestionSolicitudes.service.rest.GestionSolicitudesService;

@Service
public class GestionSolicitudesEnConcejoServiceImpl implements GestionSolicitudesEnConcejoService {

    @Autowired
    private SolicitudesRepository solicitudesRepository;
    @Autowired
    private SolicitudesEnConcejoRepository solicitudesEnConcejoRepository;
    @Autowired
    private DocumentosConcejoRepository documentosConcejoRepository;
    @Autowired
    private GestionSolicitudesService gestionSolicitudesService;


    @Override
    public SolicitudEnConcejoResponse obtenerSolicitudEnConcejo(Integer idSolicitud) {
        SolicitudEnConcejoResponse solicitudesEnConcejoRes = new SolicitudEnConcejoResponse();
        Solicitudes solicitud = solicitudesRepository.findById(idSolicitud).get();
        Optional<SolicitudesEnConcejo> optionalSolicitudesEnConcejo = solicitudesEnConcejoRepository.findBySolicitud(solicitud);
        if (optionalSolicitudesEnConcejo.isPresent()) {
            SolicitudesEnConcejo solicitudConcejo = optionalSolicitudesEnConcejo.get();
            solicitudesEnConcejoRes.setEnConcejo(true);
            solicitudesEnConcejoRes.setIdSolicitud(idSolicitud);
            solicitudesEnConcejoRes.setAvaladoConcejo(solicitudConcejo.getAvaladoConcejo());
            solicitudesEnConcejoRes.setConceptoConcejo(solicitudConcejo.getConceptoConcejo());
            solicitudesEnConcejoRes.setNumeroActa(solicitudConcejo.getNumeroActa());
            if (solicitudConcejo.getFechaAval() != null){
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");        
                solicitudesEnConcejoRes.setFechaAval(formatter.format(solicitudConcejo.getFechaAval()));
            }
            List<DocumentosConcejo> documentosConcejo = documentosConcejoRepository.findBySolicitudConcejo(solicitudConcejo);
            List<String> documentos = new ArrayList<>();
            for (DocumentosConcejo documentoConcejo : documentosConcejo) { 
                documentos.add(documentoConcejo.getDocumento());                
            } 
            solicitudesEnConcejoRes.setDocumentosConcejo(documentos);
        }
        return solicitudesEnConcejoRes;
    }

    @Override
    public Boolean guardarSolicitudEnConcejo(SolicitudEnConcejoResponse datosSolicitudEnConcejo) {
        try{            
            Solicitudes solicitud = solicitudesRepository.findById(datosSolicitudEnConcejo.getIdSolicitud()).get();
            Optional<SolicitudesEnConcejo> solicitudConcejoOptional = solicitudesEnConcejoRepository.findBySolicitud(solicitud);
            SolicitudesEnConcejo solicitudConcejo;
            if (solicitudConcejoOptional.isPresent()) {
                solicitudConcejo = solicitudConcejoOptional.get();
            } else {
                solicitudConcejo = new SolicitudesEnConcejo();
                solicitud.setEstado(ESTADO_SOLICITUD.EN_CONCEJO.getDescripcion());
                solicitudesRepository.save(solicitud);
                gestionSolicitudesService.registrarHistoricoSolicitud(solicitud);
            }
            solicitudConcejo.setAvaladoConcejo(datosSolicitudEnConcejo.getAvaladoConcejo());
            solicitudConcejo.setSolicitud(solicitud);
            solicitudConcejo.setConceptoConcejo(datosSolicitudEnConcejo.getConceptoConcejo());
            solicitudConcejo.setNumeroActa(datosSolicitudEnConcejo.getNumeroActa());
            if (datosSolicitudEnConcejo.getFechaAval() != null){
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                solicitudConcejo.setFechaAval(formatter.parse(datosSolicitudEnConcejo.getFechaAval()));
            }
            solicitudesEnConcejoRepository.save(solicitudConcejo);             
            if(datosSolicitudEnConcejo.getDocumentosConcejo() != null) {
                List<DocumentosConcejo> documentosConcejo = new ArrayList<>();
                for (String documento : datosSolicitudEnConcejo.getDocumentosConcejo()) {
                    DocumentosConcejo documentoConcejo = new DocumentosConcejo();
                    documentoConcejo.setSolicitudConcejo(solicitudConcejo);
                    documentoConcejo.setDocumento(documento);
                    documentosConcejo.add(documentoConcejo);
                }
                documentosConcejoRepository.saveAll(documentosConcejo);
            }            
            return Boolean.TRUE;
        } catch (EntityNotFoundException | ParseException e) {
            System.out.println(e.getMessage());
            return Boolean.FALSE;
        }       
    }
    
}
