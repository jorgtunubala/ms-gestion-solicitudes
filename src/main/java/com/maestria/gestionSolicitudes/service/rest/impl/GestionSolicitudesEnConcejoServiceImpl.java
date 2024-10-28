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
import com.maestria.gestionSolicitudes.domain.AdicionarAsignatura;
import com.maestria.gestionSolicitudes.domain.AsignaturaAdicionada;
import com.maestria.gestionSolicitudes.domain.AsignaturaCancelada;
import com.maestria.gestionSolicitudes.domain.AsignaturasHomologadas;
import com.maestria.gestionSolicitudes.domain.CancelarAsignatura;
import com.maestria.gestionSolicitudes.domain.CursarAsignatura;
import com.maestria.gestionSolicitudes.domain.DatosCursarAsignatura;
import com.maestria.gestionSolicitudes.domain.DocumentosConcejo;
import com.maestria.gestionSolicitudes.domain.Homologaciones;
import com.maestria.gestionSolicitudes.domain.Solicitudes;
import com.maestria.gestionSolicitudes.domain.SolicitudesEnConcejo;
import com.maestria.gestionSolicitudes.dto.client.AsignaturaExternaResponseDto;
import com.maestria.gestionSolicitudes.dto.client.InformacionPersonalDto;
import com.maestria.gestionSolicitudes.dto.rest.request.AprobarAsignaturaRequest;
import com.maestria.gestionSolicitudes.dto.rest.request.AsignaturaOtroPrograma.AprobarAsignaturaOPRequest;
import com.maestria.gestionSolicitudes.dto.rest.request.homologaciones.AprobarHomologacionRequest;
import com.maestria.gestionSolicitudes.dto.rest.response.SolicitudEnConcejoResponse;
import com.maestria.gestionSolicitudes.repository.AdicionarAsignaturaRepository;
import com.maestria.gestionSolicitudes.repository.AsignaturaAdicionadaRepository;
import com.maestria.gestionSolicitudes.repository.AsignaturaCanceladaRepository;
import com.maestria.gestionSolicitudes.repository.AsignaturasHomologadasRepository;
import com.maestria.gestionSolicitudes.repository.CancelarAsignaturaRepository;
import com.maestria.gestionSolicitudes.repository.CursarAsignaturaRepository;
import com.maestria.gestionSolicitudes.repository.DatosCursarAsignaturaRepository;
import com.maestria.gestionSolicitudes.repository.DocumentosConcejoRepository;
import com.maestria.gestionSolicitudes.repository.HomologacionesRepository;
import com.maestria.gestionSolicitudes.repository.SolicitudesEnConcejoRepository;
import com.maestria.gestionSolicitudes.repository.SolicitudesRepository;
import com.maestria.gestionSolicitudes.service.client.GestionAsignaturasService;
import com.maestria.gestionSolicitudes.service.client.GestionDocentesEstudiantesService;
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
    @Autowired
    private AdicionarAsignaturaRepository adicionarAsignaturaRepository;
    @Autowired
    private AsignaturaAdicionadaRepository asignaturaAdicionadaRepository;
    @Autowired
    private CancelarAsignaturaRepository cancelarAsignaturaRepository;
    @Autowired
    private AsignaturaCanceladaRepository asignaturaCanceladaRepository;
    @Autowired
    private GestionDocentesEstudiantesService gestionDocentesEstudiantesService;
    @Autowired
    private HomologacionesRepository homologacionesRepository;
    @Autowired
    private AsignaturasHomologadasRepository asignaturasHomologadasRepository;
    @Autowired
    private GestionAsignaturasService gestionAsignaturasService;
    @Autowired
    private CursarAsignaturaRepository cursarAsignaturaRepository;
    @Autowired
    private DatosCursarAsignaturaRepository datosCursarAsignaturaRepository;


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
        return obtenerDataEnConcejo(solicitud, solicitudesEnConcejoRes);
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
                List<DocumentosConcejo> documentosC = documentosConcejoRepository.findBySolicitudConcejo(solicitudConcejo);
                if (!documentosC.isEmpty()) {
                    documentosConcejoRepository.deleteAll(documentosC);
                }
                List<DocumentosConcejo> documentosConcejo = new ArrayList<>();
                for (String documento : datosSolicitudEnConcejo.getDocumentosConcejo()) {
                    DocumentosConcejo documentoConcejo = new DocumentosConcejo();
                    documentoConcejo.setSolicitudConcejo(solicitudConcejo);
                    documentoConcejo.setDocumento(documento);
                    documentosConcejo.add(documentoConcejo);
                }
                documentosConcejoRepository.saveAll(documentosConcejo);
            }   
            guardarDataEnConcejo(solicitud, datosSolicitudEnConcejo); 
            return Boolean.TRUE;
        } catch (EntityNotFoundException | ParseException e) {
            System.out.println(e.getMessage());
            return Boolean.FALSE;
        }       
    }
    
    private SolicitudEnConcejoResponse obtenerDataEnConcejo(Solicitudes solicitud, SolicitudEnConcejoResponse solicitudesEnConcejoRes) {
        List<AprobarAsignaturaRequest> asignaturasAprobadas = null;
        List<AprobarHomologacionRequest> homologacionesAprobadas = null;
        List<AprobarAsignaturaOPRequest> asignaturasOPAprobadas = null;
        if (solicitud.getTipoSolicitud().getCodigo().equals("AD_ASIG")) {
            asignaturasAprobadas = new ArrayList<>();
            AdicionarAsignatura adicionarAsignatura = adicionarAsignaturaRepository.findBySolicitud(solicitud);
            List<AsignaturaAdicionada> asignaturaAdicionadas = asignaturaAdicionadaRepository
                    .findByAdicionarAsignatura(adicionarAsignatura);
            for (AsignaturaAdicionada asignaturaAdicionada : asignaturaAdicionadas) {
                AprobarAsignaturaRequest asignaturas = new AprobarAsignaturaRequest();
                asignaturas.setIdAsignatura(asignaturaAdicionada.getId());
                asignaturas.setNombre(asignaturaAdicionada.getNombreAsignatura());
                asignaturas.setGrupo(asignaturaAdicionada.getGrupo());
                InformacionPersonalDto infoDocente = gestionDocentesEstudiantesService
                                .obtenerTutor(asignaturaAdicionada.getIdDocente().toString());
                asignaturas.setNombreDocente(infoDocente.obtenerNombreCompleto());
                asignaturas
                        .setAprobado(asignaturaAdicionada.getAprobadoConcejo());
                if (asignaturaAdicionada.getAprobadoComite()){ //Solo muestra en concejo las aprobadas por comite
                    asignaturasAprobadas.add(asignaturas);
                }
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
                asignaturas.setGrupo(asignaturaCancelada.getGrupo());
                InformacionPersonalDto infoDocente = gestionDocentesEstudiantesService
                                .obtenerTutor(asignaturaCancelada.getIdDocente().toString());
                asignaturas.setNombreDocente(infoDocente.obtenerNombreCompleto());
                asignaturas
                        .setAprobado(asignaturaCancelada.getAprobadoConcejo());
                if (asignaturaCancelada.getAprobadoComite()){ //Solo muestra en concejo las aprobadas por comite
                    asignaturasAprobadas.add(asignaturas);
                }
            }
        } else if (solicitud.getTipoSolicitud().getCodigo().equals("HO_ASIG_ESP") ||
                    solicitud.getTipoSolicitud().getCodigo().equals("HO_ASIG_POS")) {
            homologacionesAprobadas = new ArrayList<>();
            Homologaciones homologacion = homologacionesRepository.findBySolicitud(solicitud);
            List<AsignaturasHomologadas> asignaturaHomologadas = asignaturasHomologadasRepository
                    .findAllByHomologacion(homologacion);
            for (AsignaturasHomologadas asignaturaH : asignaturaHomologadas) {
                AprobarHomologacionRequest homologaciones = new AprobarHomologacionRequest();
                homologaciones.setIdHomologacion(asignaturaH.getId());
                AsignaturaExternaResponseDto asignaturaExternaDto = gestionAsignaturasService
                                        .obtenerAsignaturaExterna(asignaturaH.getAsignaturaExterna());
                homologaciones.setNombreAsignatura(asignaturaExternaDto.getNombre());
                homologaciones.setCreditos(asignaturaExternaDto.getCreditos());
                homologaciones.setIntensidadHoraria(asignaturaExternaDto.getIntensidadHoraria());
                homologaciones.setCalificacion(asignaturaH.getCalificacionObtenida());
                homologaciones.setNombrePrograma(asignaturaExternaDto.getPrograma());
                homologaciones.setNombreInstitucion(asignaturaExternaDto.getInstitucion());
                homologaciones
                        .setAprobado(asignaturaH.getAprobadoConcejo());
                if (asignaturaH.getAprobadoComite()){ //Solo muestra en concejo las aprobadas por comite
                    homologacionesAprobadas.add(homologaciones);
                }                
            }
        } else if (solicitud.getTipoSolicitud().getCodigo().equals("CU_ASIG")) {
            asignaturasOPAprobadas = new ArrayList<>();
            CursarAsignatura cursarAsignatura = cursarAsignaturaRepository.findBySolicitud(solicitud);
            List<DatosCursarAsignatura> datosCursarAsignaturaList = datosCursarAsignaturaRepository.findAllByCursarAsignatura(cursarAsignatura);                        
            for (DatosCursarAsignatura datosCursarAsignatura : datosCursarAsignaturaList) {
                AprobarAsignaturaOPRequest asignaturaOP = new AprobarAsignaturaOPRequest();
                AsignaturaExternaResponseDto asignaturaExternaDto = gestionAsignaturasService
                                        .obtenerAsignaturaExterna(datosCursarAsignatura.getIdAsignaturaExterna());
                asignaturaOP.setIdCursarAsignatura(datosCursarAsignatura.getId());
                asignaturaOP.setNombreAsignatura(asignaturaExternaDto.getNombre());
                asignaturaOP.setCodigo(datosCursarAsignatura.getCodigoAsignatura());
                asignaturaOP.setCreditos(asignaturaExternaDto.getCreditos());
                asignaturaOP.setIntensidadHoraria(asignaturaExternaDto.getIntensidadHoraria());
                asignaturaOP.setGrupo(datosCursarAsignatura.getGrupo());
                asignaturaOP.setNombreInstitucion(asignaturaExternaDto.getInstitucion());
                asignaturaOP.setNombrePrograma(asignaturaExternaDto.getPrograma());
                asignaturaOP.setTituloDocente(datosCursarAsignatura.getTituloDocente());
                asignaturaOP.setNombreDocente(datosCursarAsignatura.getNombreDocente());
                asignaturaOP
                        .setAprobado(datosCursarAsignatura.getAprobadoConcejo());
                if (datosCursarAsignatura.getAprobadoComite()){ //Solo muestra en concejo las aprobadas por comite
                    asignaturasOPAprobadas.add(asignaturaOP);
                }    
            }
        } 
        solicitudesEnConcejoRes.setAsignaturasAprobadas(asignaturasAprobadas);
        solicitudesEnConcejoRes.setAsignaturasHomologadas(homologacionesAprobadas);
        solicitudesEnConcejoRes.setAsignaturasOtroPrograma(asignaturasOPAprobadas);
        return solicitudesEnConcejoRes;
    }

    private void guardarDataEnConcejo(Solicitudes solicitud, SolicitudEnConcejoResponse datosSolicitudEnConcejo){
        if (datosSolicitudEnConcejo.getAsignaturasAprobadas() != null) {
            if (solicitud.getTipoSolicitud().getCodigo().equals("AD_ASIG")) {
                AdicionarAsignatura adicionarAsignatura = adicionarAsignaturaRepository.findBySolicitud(solicitud);
                List<AsignaturaAdicionada> asignaturaAdicionadas = asignaturaAdicionadaRepository
                .findByAdicionarAsignatura(adicionarAsignatura);                    
            datosSolicitudEnConcejo.getAsignaturasAprobadas().forEach(asignaturaAprobar -> 
                asignaturaAdicionadas.stream()
                    .filter(asignaturaAdicionada -> asignaturaAdicionada.getId().equals(asignaturaAprobar.getIdAsignatura()))
                    .findFirst()
                    .ifPresent(asignaturaAdicionada -> {
                        asignaturaAdicionada.setAprobadoConcejo(asignaturaAprobar.getAprobado());
                        asignaturaAdicionada.setEstado(asignaturaAprobar.getAprobado() ? 
                            ESTADO_SOLICITUD.APROBADA.getDescripcion() : ESTADO_SOLICITUD.NO_APROBADA.getDescripcion());
                    })
                );
                asignaturaAdicionadaRepository.saveAll(asignaturaAdicionadas);
            } else if (solicitud.getTipoSolicitud().getCodigo().equals("CA_ASIG")) {
                CancelarAsignatura cancelarAsignatura = cancelarAsignaturaRepository.findBySolicitud(solicitud);
                List<AsignaturaCancelada> asignaturaCanceladas = asignaturaCanceladaRepository
                        .findByCancelarAsignatura(cancelarAsignatura);   
                        datosSolicitudEnConcejo.getAsignaturasAprobadas().forEach(asignaturaAprobar -> 
                    asignaturaCanceladas.stream()
                        .filter(asignaturaCancelada -> asignaturaCancelada.getId().equals(asignaturaAprobar.getIdAsignatura()))
                        .findFirst()
                        .ifPresent(asignaturaCancelada -> {
                            asignaturaCancelada.setAprobadoConcejo(asignaturaAprobar.getAprobado());
                            asignaturaCancelada.setEstado(asignaturaAprobar.getAprobado() ? 
                                ESTADO_SOLICITUD.APROBADA.getDescripcion() : ESTADO_SOLICITUD.NO_APROBADA.getDescripcion());
                        })
                );
                asignaturaCanceladaRepository.saveAll(asignaturaCanceladas);
            } else if (solicitud.getTipoSolicitud().getCodigo().equals("HO_ASIG_ESP") ||
                    solicitud.getTipoSolicitud().getCodigo().equals("HO_ASIG_POS")) {
                Homologaciones homologacion = homologacionesRepository.findBySolicitud(solicitud);
                List<AsignaturasHomologadas> asignaturaHomologadas = asignaturasHomologadasRepository
                        .findAllByHomologacion(homologacion);
                datosSolicitudEnConcejo.getAsignaturasHomologadas().forEach(asignaturaAprobar -> 
                    asignaturaHomologadas.stream()
                        .filter(asignaturaHomologada -> asignaturaHomologada.getId().equals(asignaturaAprobar.getIdHomologacion()))
                        .findFirst()
                        .ifPresent(asignaturaHomologada -> {
                            asignaturaHomologada.setAprobadoConcejo(asignaturaAprobar.getAprobado());
                            asignaturaHomologada.setEstado(asignaturaAprobar.getAprobado() ? 
                                ESTADO_SOLICITUD.APROBADA.getDescripcion() : ESTADO_SOLICITUD.NO_APROBADA.getDescripcion());
                        })
                );
                asignaturasHomologadasRepository.saveAll(asignaturaHomologadas);
            } else if (solicitud.getTipoSolicitud().getCodigo().equals("CU_ASIG")) {
                CursarAsignatura cursarAsignatura = cursarAsignaturaRepository.findBySolicitud(solicitud);
                List<DatosCursarAsignatura> datosCursarAsignaturaList = datosCursarAsignaturaRepository.findAllByCursarAsignatura(cursarAsignatura);                                    
                datosSolicitudEnConcejo.getAsignaturasOtroPrograma().forEach(asignaturaAprobar -> 
                    datosCursarAsignaturaList.stream()
                        .filter(datosCursarAsignatura -> datosCursarAsignatura.getId().equals(asignaturaAprobar.getIdCursarAsignatura()))
                        .findFirst()
                        .ifPresent(datosCursarAsignatura -> {
                            datosCursarAsignatura.setAprobadoConcejo(asignaturaAprobar.getAprobado());
                            datosCursarAsignatura.setEstado(asignaturaAprobar.getAprobado() ? 
                                ESTADO_SOLICITUD.APROBADA.getDescripcion() : ESTADO_SOLICITUD.NO_APROBADA.getDescripcion());
                        })
                );
                datosCursarAsignaturaRepository.saveAll(datosCursarAsignaturaList);
            } 
        }
    }
}
