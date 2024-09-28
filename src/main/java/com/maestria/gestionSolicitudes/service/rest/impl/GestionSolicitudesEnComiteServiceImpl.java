package com.maestria.gestionSolicitudes.service.rest.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maestria.gestionSolicitudes.comun.enums.ESTADO_SOLICITUD;
import com.maestria.gestionSolicitudes.domain.AdicionarAsignatura;
import com.maestria.gestionSolicitudes.domain.AsignaturaAdicionada;
import com.maestria.gestionSolicitudes.domain.AsignaturaCancelada;
import com.maestria.gestionSolicitudes.domain.CancelarAsignatura;
import com.maestria.gestionSolicitudes.domain.Solicitudes;
import com.maestria.gestionSolicitudes.domain.SolicitudesEnComite;
import com.maestria.gestionSolicitudes.dto.rest.request.AprobarAsignaturaRequest;
import com.maestria.gestionSolicitudes.dto.rest.response.SolicitudEnComiteResponse;
import com.maestria.gestionSolicitudes.repository.AdicionarAsignaturaRepository;
import com.maestria.gestionSolicitudes.repository.AsignaturaAdicionadaRepository;
import com.maestria.gestionSolicitudes.repository.AsignaturaCanceladaRepository;
import com.maestria.gestionSolicitudes.repository.CancelarAsignaturaRepository;
import com.maestria.gestionSolicitudes.repository.SolicitudesEnComiteRepository;
import com.maestria.gestionSolicitudes.repository.SolicitudesRepository;
import com.maestria.gestionSolicitudes.service.rest.GestionSolicitudesEnComiteService;
import com.maestria.gestionSolicitudes.service.rest.GestionSolicitudesService;

@Service
public class GestionSolicitudesEnComiteServiceImpl implements GestionSolicitudesEnComiteService {

    @Autowired
    private SolicitudesEnComiteRepository solicitudesEnComiteRepository;
    
    @Autowired
    private SolicitudesRepository solicitudesRepository;
    @Autowired
    private AdicionarAsignaturaRepository adicionarAsignaturaRepository;
    @Autowired
    private AsignaturaAdicionadaRepository asignaturaAdicionadaRepository;
    @Autowired
    private CancelarAsignaturaRepository cancelarAsignaturaRepository;
    @Autowired
    private AsignaturaCanceladaRepository asignaturaCanceladaRepository;
    @Autowired
    private GestionSolicitudesService gestionSolicitudesService;


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
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");        
                solicitudesEnComiteRes.setFechaAval(formatter.format(solicitudComite.getFechaAval()));
            }            
        }
        List<AprobarAsignaturaRequest> asignaturasAprobadas = null;
        if (solicitud.getTipoSolicitud().getCodigo().equals("AD_ASIG")) {
            asignaturasAprobadas = new ArrayList<>();
            AdicionarAsignatura adicionarAsignatura = adicionarAsignaturaRepository.findBySolicitud(solicitud);
            List<AsignaturaAdicionada> asignaturaAdicionadas = asignaturaAdicionadaRepository
                    .findByAdicionarAsignatura(adicionarAsignatura);
            for (AsignaturaAdicionada asignaturaAdicionada : asignaturaAdicionadas) {
                AprobarAsignaturaRequest asignaturas = new AprobarAsignaturaRequest();
                asignaturas.setIdAsignatura(asignaturaAdicionada.getId());
                asignaturas.setNombre(asignaturaAdicionada.getNombreAsignatura());
                asignaturas
                        .setAprobado(asignaturaAdicionada.getEstado().equals(ESTADO_SOLICITUD.APROBADA.getDescripcion())
                                ? Boolean.TRUE
                                : Boolean.FALSE);
                asignaturasAprobadas.add(asignaturas);
            }

        } else if (solicitud.getTipoSolicitud().getCodigo().equals("CA_ASIG")) {
            asignaturasAprobadas = new ArrayList<>();
            CancelarAsignatura cancelarAsignatura = cancelarAsignaturaRepository.findBySolicitud(solicitud);
            List<AsignaturaCancelada> asignaturaCanceladas = asignaturaCanceladaRepository
                    .findByCancelarAsignatura(cancelarAsignatura);
            for (AsignaturaCancelada asignaturaCancelada : asignaturaCanceladas) {
                AprobarAsignaturaRequest asignaturas = new AprobarAsignaturaRequest();
                asignaturas.setIdAsignatura(asignaturaCancelada.getId());
                asignaturas.setNombre(asignaturaCancelada.getNombreAsignatura());
                asignaturas.setAprobado(
                        asignaturaCancelada.getEstado().equals(ESTADO_SOLICITUD.APROBADA.getDescripcion())
                                ? Boolean.TRUE
                                : Boolean.FALSE);
                asignaturasAprobadas.add(asignaturas);
            }
        }
        solicitudesEnComiteRes.setAsignaturasAprobadas(asignaturasAprobadas);
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
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                solicitudComite.setFechaAval(formatter.parse(datosSolicitudEnComite.getFechaAval()));
            }
            solicitudesEnComiteRepository.save(solicitudComite); 
            solicitud.setEstado(ESTADO_SOLICITUD.EN_COMITE.getDescripcion());
            if (datosSolicitudEnComite.getAsignaturasAprobadas() != null) {
                if (solicitud.getTipoSolicitud().getCodigo().equals("AD_ASIG")) {
                    AdicionarAsignatura adicionarAsignatura = adicionarAsignaturaRepository.findBySolicitud(solicitud);
                    List<AsignaturaAdicionada> asignaturaAdicionadas = asignaturaAdicionadaRepository
                        .findByAdicionarAsignatura(adicionarAsignatura);                    
                    datosSolicitudEnComite.getAsignaturasAprobadas().forEach(asignaturaAprobar -> 
                        asignaturaAdicionadas.stream()
                            .filter(asignaturaAdicionada -> asignaturaAdicionada.getId().equals(asignaturaAprobar.getIdAsignatura()))
                            .findFirst()
                            .ifPresent(asignaturaAdicionada -> asignaturaAdicionada.setEstado(asignaturaAprobar.getAprobado() ? 
                                ESTADO_SOLICITUD.APROBADA.getDescripcion() : ESTADO_SOLICITUD.NO_APROBADA.getDescripcion()))
                    );
                } else if (solicitud.getTipoSolicitud().getCodigo().equals("CA_ASIG")) {
                    CancelarAsignatura cancelarAsignatura = cancelarAsignaturaRepository.findBySolicitud(solicitud);
                    List<AsignaturaCancelada> asignaturaCanceladas = asignaturaCanceladaRepository
                            .findByCancelarAsignatura(cancelarAsignatura);   
                    datosSolicitudEnComite.getAsignaturasAprobadas().forEach(asignaturaAprobar -> 
                        asignaturaCanceladas.stream()
                            .filter(asignaturaCancelada -> asignaturaCancelada.getId().equals(asignaturaAprobar.getIdAsignatura()))
                            .findFirst()
                            .ifPresent(asignaturaCancelada -> asignaturaCancelada.setEstado(asignaturaAprobar.getAprobado() ? 
                                ESTADO_SOLICITUD.APROBADA.getDescripcion() : ESTADO_SOLICITUD.NO_APROBADA.getDescripcion()))
                    );
                } 
            }
            solicitudesRepository.save(solicitud);
            gestionSolicitudesService.registrarHistoricoSolicitud(solicitud);
            return Boolean.TRUE;
        } catch (EntityNotFoundException | ParseException e) {
            System.out.println(e.getMessage());
            return Boolean.FALSE;
        }       
    }
    
}
