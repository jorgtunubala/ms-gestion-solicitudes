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
import com.maestria.gestionSolicitudes.domain.ActividadesRealizadasPracticaDocente;
import com.maestria.gestionSolicitudes.domain.AdicionarAsignatura;
import com.maestria.gestionSolicitudes.domain.AsignaturaAdicionada;
import com.maestria.gestionSolicitudes.domain.AsignaturaCancelada;
import com.maestria.gestionSolicitudes.domain.AsignaturasHomologadas;
import com.maestria.gestionSolicitudes.domain.AvalComitePrograma;
import com.maestria.gestionSolicitudes.domain.CancelarAsignatura;
import com.maestria.gestionSolicitudes.domain.CursarAsignatura;
import com.maestria.gestionSolicitudes.domain.DatosCursarAsignatura;
import com.maestria.gestionSolicitudes.domain.Homologaciones;
import com.maestria.gestionSolicitudes.domain.Solicitudes;
import com.maestria.gestionSolicitudes.domain.SolicitudesEnComite;
import com.maestria.gestionSolicitudes.dto.client.AsignaturaExternaResponseDto;
import com.maestria.gestionSolicitudes.dto.client.InformacionPersonalDto;
import com.maestria.gestionSolicitudes.dto.rest.request.AprobarAsignaturaRequest;
import com.maestria.gestionSolicitudes.dto.rest.request.AsignaturaOtroPrograma.AprobarAsignaturaOPRequest;
import com.maestria.gestionSolicitudes.dto.rest.request.AvalComite.AprobarAvalComiteRequest;
import com.maestria.gestionSolicitudes.dto.rest.request.homologaciones.AprobarHomologacionRequest;
import com.maestria.gestionSolicitudes.dto.rest.response.SolicitudEnComiteResponse;
import com.maestria.gestionSolicitudes.repository.ActividadesRealizadasPracticaDocenteRepository;
import com.maestria.gestionSolicitudes.repository.AdicionarAsignaturaRepository;
import com.maestria.gestionSolicitudes.repository.AsignaturaAdicionadaRepository;
import com.maestria.gestionSolicitudes.repository.AsignaturaCanceladaRepository;
import com.maestria.gestionSolicitudes.repository.AsignaturasHomologadasRepository;
import com.maestria.gestionSolicitudes.repository.AvalComiteProgramaRepository;
import com.maestria.gestionSolicitudes.repository.CancelarAsignaturaRepository;
import com.maestria.gestionSolicitudes.repository.CursarAsignaturaRepository;
import com.maestria.gestionSolicitudes.repository.DatosCursarAsignaturaRepository;
import com.maestria.gestionSolicitudes.repository.HomologacionesRepository;
import com.maestria.gestionSolicitudes.repository.ReconocimientoCreditosRepository;
import com.maestria.gestionSolicitudes.repository.SolicitudesEnComiteRepository;
import com.maestria.gestionSolicitudes.repository.SolicitudesRepository;
import com.maestria.gestionSolicitudes.service.client.GestionAsignaturasService;
import com.maestria.gestionSolicitudes.service.client.GestionDocentesEstudiantesService;
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
    @Autowired
    private AvalComiteProgramaRepository avalComiteProgramaRepository;
    @Autowired
    private ActividadesRealizadasPracticaDocenteRepository aPracticaDocenteRepository;


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
        return obtenerDataComite(solicitud, solicitudesEnComiteRes);
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
                solicitud.setEstado(ESTADO_SOLICITUD.EN_COMITE.getDescripcion());
                solicitudesRepository.save(solicitud);
                gestionSolicitudesService.registrarHistoricoSolicitud(solicitud);
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
            guardarDataComite(solicitud, datosSolicitudEnComite);
            return Boolean.TRUE;
        } catch (EntityNotFoundException | ParseException e) {
            System.out.println(e.getMessage());
            return Boolean.FALSE;
        }       
    }

    private SolicitudEnComiteResponse obtenerDataComite(Solicitudes solicitud, SolicitudEnComiteResponse solicitudesEnComiteRes){
        List<AprobarAsignaturaRequest> asignaturasAprobadas = null;
        List<AprobarHomologacionRequest> homologacionesAprobadas = null;
        List<AprobarAsignaturaOPRequest> asignaturasOPAprobadas = null;
        List<AprobarAvalComiteRequest> avalActPracticaDocente = null;
        List<AprobarAvalComiteRequest> reconocimientoCreditosPD = null;
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
                        .setAprobado(asignaturaAdicionada.getAprobadoComite());
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
                asignaturas.setGrupo(asignaturaCancelada.getGrupo());
                InformacionPersonalDto infoDocente = gestionDocentesEstudiantesService
                                .obtenerTutor(asignaturaCancelada.getIdDocente().toString());
                asignaturas.setNombreDocente(infoDocente.obtenerNombreCompleto());
                asignaturas.setAprobado(
                        asignaturaCancelada.getAprobadoComite());
                asignaturasAprobadas.add(asignaturas);
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
                homologaciones.setAprobado(asignaturaH.getAprobadoComite());
                homologacionesAprobadas.add(homologaciones);
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
                asignaturaOP.setAprobado(
                    datosCursarAsignatura.getAprobadoComite());
                asignaturasOPAprobadas.add(asignaturaOP);
            }
        } else if (solicitud.getTipoSolicitud().getCodigo().equals("AV_COMI_PR")) {
            avalActPracticaDocente = new ArrayList<>();
            List<AvalComitePrograma> avalComiteProgramaList = avalComiteProgramaRepository.findBySolicitud(solicitud);
            for (AvalComitePrograma avalComitePrograma : avalComiteProgramaList) {
                AprobarAvalComiteRequest aval = new AprobarAvalComiteRequest();
                aval.setIdSubtipo(avalComitePrograma.getSubTiposSolicitud().getId());
                aval.setNombreActividad(avalComitePrograma.getSubTiposSolicitud().getNombre());
                aval.setHorasReconocer(avalComitePrograma.getHorasReconocer());
                aval.setAprobado(
                    avalComitePrograma.getAprobadoComite());
                avalActPracticaDocente.add(aval);
            }
        } else if (solicitud.getTipoSolicitud().getCodigo().equals("RE_CRED_PR_DOC")) {
            reconocimientoCreditosPD = new ArrayList<>();
            List<ActividadesRealizadasPracticaDocente> actReaPracDocenteList = aPracticaDocenteRepository.findBySolicitud(solicitud);
            for (ActividadesRealizadasPracticaDocente actividades : actReaPracDocenteList) {
                AprobarAvalComiteRequest aval = new AprobarAvalComiteRequest();
                aval.setIdSubtipo(actividades.getSubTiposSolicitud().getId());
                aval.setNombreActividad(actividades.getSubTiposSolicitud().getNombre());
                aval.setHorasReconocer(actividades.getHorasReconocer());
                aval.setAprobado(
                    actividades.getAprobadoComite());
                reconocimientoCreditosPD.add(aval);
            }
        } 
        solicitudesEnComiteRes.setAsignaturasAprobadas(asignaturasAprobadas); 
        solicitudesEnComiteRes.setAsignaturasHomologadas(homologacionesAprobadas);
        solicitudesEnComiteRes.setAsignaturasOtroPrograma(asignaturasOPAprobadas);
        solicitudesEnComiteRes.setAvalActPracticaDocente(avalActPracticaDocente);
        solicitudesEnComiteRes.setReconocimientoCreditosPD(reconocimientoCreditosPD);
        return solicitudesEnComiteRes;
    }

    private void guardarDataComite(Solicitudes solicitud, SolicitudEnComiteResponse datosSolicitudEnComite) {
        if (solicitud.getTipoSolicitud().getCodigo().equals("AD_ASIG")) {
            AdicionarAsignatura adicionarAsignatura = adicionarAsignaturaRepository.findBySolicitud(solicitud);
            List<AsignaturaAdicionada> asignaturaAdicionadas = asignaturaAdicionadaRepository
                    .findByAdicionarAsignatura(adicionarAsignatura);
            datosSolicitudEnComite.getAsignaturasAprobadas().forEach(asignaturaAprobar -> asignaturaAdicionadas.stream()
                    .filter(asignaturaAdicionada -> asignaturaAdicionada.getId()
                            .equals(asignaturaAprobar.getIdAsignatura()))
                    .findFirst()
                    .ifPresent(asignaturaAdicionada -> asignaturaAdicionada
                            .setAprobadoComite(asignaturaAprobar.getAprobado())));
            asignaturaAdicionadaRepository.saveAll(asignaturaAdicionadas);
        } else if (solicitud.getTipoSolicitud().getCodigo().equals("CA_ASIG")) {
            CancelarAsignatura cancelarAsignatura = cancelarAsignaturaRepository.findBySolicitud(solicitud);
            List<AsignaturaCancelada> asignaturaCanceladas = asignaturaCanceladaRepository
                    .findByCancelarAsignatura(cancelarAsignatura);
            datosSolicitudEnComite.getAsignaturasAprobadas().forEach(asignaturaAprobar -> asignaturaCanceladas.stream()
                    .filter(asignaturaCancelada -> asignaturaCancelada.getId()
                            .equals(asignaturaAprobar.getIdAsignatura()))
                    .findFirst()
                    .ifPresent(asignaturaCancelada -> asignaturaCancelada
                            .setAprobadoComite(asignaturaAprobar.getAprobado())));
            asignaturaCanceladaRepository.saveAll(asignaturaCanceladas);
        } else if (solicitud.getTipoSolicitud().getCodigo().equals("HO_ASIG_ESP") ||
                solicitud.getTipoSolicitud().getCodigo().equals("HO_ASIG_POS")) {
            Homologaciones homologacion = homologacionesRepository.findBySolicitud(solicitud);
            List<AsignaturasHomologadas> asignaturaHomologadas = asignaturasHomologadasRepository
                    .findAllByHomologacion(homologacion);
            datosSolicitudEnComite.getAsignaturasHomologadas()
                    .forEach(asignaturaAprobar -> asignaturaHomologadas.stream()
                            .filter(asignaturaHomologada -> asignaturaHomologada.getId()
                                    .equals(asignaturaAprobar.getIdHomologacion()))
                            .findFirst()
                            .ifPresent(asignaturaHomologada -> asignaturaHomologada
                                    .setAprobadoComite(asignaturaAprobar.getAprobado())));
            asignaturasHomologadasRepository.saveAll(asignaturaHomologadas);
        } else if (solicitud.getTipoSolicitud().getCodigo().equals("CU_ASIG")) {
            CursarAsignatura cursarAsignatura = cursarAsignaturaRepository.findBySolicitud(solicitud);
            List<DatosCursarAsignatura> datosCursarAsignaturaList = datosCursarAsignaturaRepository
                    .findAllByCursarAsignatura(cursarAsignatura);
            datosSolicitudEnComite.getAsignaturasOtroPrograma()
                    .forEach(asignaturaAprobar -> datosCursarAsignaturaList.stream()
                            .filter(datosCursarAsignatura -> datosCursarAsignatura.getId()
                                    .equals(asignaturaAprobar.getIdCursarAsignatura()))
                            .findFirst()
                            .ifPresent(datosCursarAsignatura -> datosCursarAsignatura
                                    .setAprobadoComite(asignaturaAprobar.getAprobado())));
            datosCursarAsignaturaRepository.saveAll(datosCursarAsignaturaList);
        } else if (solicitud.getTipoSolicitud().getCodigo().equals("AV_COMI_PR")) {
            List<AvalComitePrograma> avalComiteProgramaList = avalComiteProgramaRepository.findBySolicitud(solicitud);
            datosSolicitudEnComite.getAvalActPracticaDocente().forEach(avalAprobar -> avalComiteProgramaList.stream()
                    .filter(avalComitePrograma -> avalComitePrograma.getSubTiposSolicitud().getId()
                            .equals(avalAprobar.getIdSubtipo()))
                    .findFirst()
                    .ifPresent(avalComitePrograma -> avalComitePrograma.setAprobadoComite(avalAprobar.getAprobado())));
            avalComiteProgramaRepository.saveAll(avalComiteProgramaList);
        } else if (solicitud.getTipoSolicitud().getCodigo().equals("RE_CRED_PR_DOC")) {
            List<ActividadesRealizadasPracticaDocente> actReaPracDocenteList = aPracticaDocenteRepository
                    .findBySolicitud(solicitud);
            datosSolicitudEnComite.getReconocimientoCreditosPD().forEach(avalAprobar -> actReaPracDocenteList.stream()
                    .filter(actividadPracticaDocente -> actividadPracticaDocente.getSubTiposSolicitud().getId()
                            .equals(avalAprobar.getIdSubtipo()))
                    .findFirst()
                    .ifPresent(actividadPracticaDocente -> actividadPracticaDocente
                            .setAprobadoComite(avalAprobar.getAprobado())));
            aPracticaDocenteRepository.saveAll(actReaPracDocenteList);
        }
    }
    
}
