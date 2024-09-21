package com.maestria.gestionSolicitudes.service.rest.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maestria.gestionSolicitudes.domain.Solicitudes;
import com.maestria.gestionSolicitudes.domain.SolicitudesEnComite;
import com.maestria.gestionSolicitudes.dto.rest.response.SolicitudEnComiteResponse;
import com.maestria.gestionSolicitudes.repository.SolicitudesEnComiteRepository;
import com.maestria.gestionSolicitudes.repository.SolicitudesRepository;
import com.maestria.gestionSolicitudes.service.rest.GestionSolicitudesEnComiteService;

@Service
public class GestionSolicitudesEnComiteServiceImpl implements GestionSolicitudesEnComiteService {

    @Autowired
    private SolicitudesEnComiteRepository solicitudesEnComiteRepository;
    
    @Autowired
    private SolicitudesRepository solicitudesRepository;


    @Override
    public SolicitudEnComiteResponse obtenerSolicitudEnComite(Integer idSolicitud) {
        SolicitudEnComiteResponse solicitudesEnComiteRes = new SolicitudEnComiteResponse();
        Solicitudes solicitud = solicitudesRepository.findById(idSolicitud).get();
        Optional<SolicitudesEnComite> optionalSolicitudesEnComite = solicitudesEnComiteRepository.findBySolicitud(solicitud);
        if (optionalSolicitudesEnComite.isPresent()) {
            SolicitudesEnComite solicitudComite = optionalSolicitudesEnComite.get();
            solicitudesEnComiteRes.setEnComite(true);
            solicitudesEnComiteRes.setIdSolicitud(idSolicitud);
            solicitudesEnComiteRes.setAvaladoComite(solicitudComite.getAvaladoComite());
            solicitudesEnComiteRes.setConceptoComite(solicitudComite.getConceptoComite());
            solicitudesEnComiteRes.setNumeroActa(solicitudComite.getNumeroActa());
            if (solicitudComite.getFechaAval() != null){
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");        
                solicitudesEnComiteRes.setFechaAval(formatter.format(solicitudComite.getFechaAval()));
            }
        }
        return solicitudesEnComiteRes;
    } 

    @Override
    public Boolean guardarSolicitudEnComite(SolicitudEnComiteResponse datosSolicitudEnComite){
        try{            
            Solicitudes solicitud = solicitudesRepository.findById(datosSolicitudEnComite.getIdSolicitud()).get();
            Optional<SolicitudesEnComite> solicitudComiteOptional = solicitudesEnComiteRepository.findBySolicitud(solicitud);
            SolicitudesEnComite solicitudComite;
            if (solicitudComiteOptional.isPresent()) {
                solicitudComite = solicitudComiteOptional.get();
            } else {
                solicitudComite = new SolicitudesEnComite();
            }
            solicitudComite.setAvaladoComite(datosSolicitudEnComite.getAvaladoComite());
            solicitudComite.setSolicitud(solicitud);
            solicitudComite.setConceptoComite(datosSolicitudEnComite.getConceptoComite());
            solicitudComite.setNumeroActa(datosSolicitudEnComite.getNumeroActa());
            if (datosSolicitudEnComite.getFechaAval() != null){
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                solicitudComite.setFechaAval(formatter.parse(datosSolicitudEnComite.getFechaAval()));
            }
            solicitudesEnComiteRepository.save(solicitudComite); 
            return Boolean.TRUE;       
        } catch (EntityNotFoundException | ParseException e) {            
            System.out.println(e.getMessage());            
            return Boolean.FALSE;
        }       
    }
    
}
