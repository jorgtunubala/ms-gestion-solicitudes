package com.maestria.gestionSolicitudes.service.rest.impl;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maestria.gestionSolicitudes.comun.enums.ABREVIATURA_SOLICITUD;
import com.maestria.gestionSolicitudes.comun.enums.ESTADO_SOLICITUD;
import com.maestria.gestionSolicitudes.domain.*;
import com.maestria.gestionSolicitudes.dto.client.InformacionPersonalDto;
import com.maestria.gestionSolicitudes.dto.rest.request.*;
import com.maestria.gestionSolicitudes.dto.rest.response.*;
import com.maestria.gestionSolicitudes.repository.*;
import com.maestria.gestionSolicitudes.service.client.GestionDocentesEstudiantesService;
import com.maestria.gestionSolicitudes.service.rest.GestionSolicitudesService;
import com.maestria.gestionSolicitudes.service.rest.SolicitudesHomologacionService;

@Service
public class GestionSolicitudesServiceImpl implements GestionSolicitudesService {

    private static final Logger logger = LoggerFactory.getLogger(GestionSolicitudesServiceImpl.class);

    @Autowired
    private TiposSolicitudRepository tipoSolicitudRepository;
    @Autowired
    private SolicitudesRepository solicitudesRepository;
    @Autowired
    private RequisitoSolicitudRepository requisitoSolicitudRepository;
    @Autowired
    private DocumentoRequisitoSolicitudRepository dSolicitudRepository;
    @Autowired
    private NotaDocumentoRequeridoRepository notaDocumentoRequeridoRepository;
    @Autowired
    private GestionDocentesEstudiantesService gestionDocentesEstudiantesService;
    @Autowired
    private SolicitudesHomologacionService solicitudesHomologacionService;

    @Override
    public List<TipoSolicitudDto> obtenerTiposSolicitudes() {
        List<TiposSolicitud> tiposSolicitudes = tipoSolicitudRepository.findAll();
        List<TipoSolicitudDto> tiposSolicitudDtos = new ArrayList<>();
        for (TiposSolicitud tipoSolicitud : tiposSolicitudes) {
            TipoSolicitudDto tiposSolicitudDto = new TipoSolicitudDto();
            tiposSolicitudDto.setIdSolicitud(tipoSolicitud.getId());
            tiposSolicitudDto.setCodigoSolicitud(tipoSolicitud.getCodigo());
            tiposSolicitudDto.setNombreSolicitud(tipoSolicitud.getNombre());
            tiposSolicitudDtos.add(tiposSolicitudDto);
        }
        return tiposSolicitudDtos;
    }

    @Override
    public DocumentoRequeridoSolicitudDto getRequisitoSolicitudAndDocumentosAndNotasPorSolicitudId(String codigo) throws Exception {
        Optional<RequisitoSolicitud> optionalRequisitoSolicitud = requisitoSolicitudRepository.findByCodigo(codigo);
        DocumentoRequeridoSolicitudDto dSolicitudDto = new DocumentoRequeridoSolicitudDto();
        if (optionalRequisitoSolicitud.isPresent()){
            RequisitoSolicitud requisitoSolicitud = optionalRequisitoSolicitud.get();
            dSolicitudDto.setTituloDocumento(requisitoSolicitud.getTituloDocumento());
            dSolicitudDto.setDescripcion(requisitoSolicitud.getDescripcion());
            dSolicitudDto.setTenerEnCuenta(requisitoSolicitud.getTenerEnCuenta());
            dSolicitudDto.setArticulo(requisitoSolicitud.getArticulo());
            List<DocumentoRequisitoSolicitud> lRequisitoSolicituds = dSolicitudRepository.findByRequisitoSolicitudId(requisitoSolicitud.getId());
            List<String> lDocumentos = new ArrayList<>();
            for (DocumentoRequisitoSolicitud documentoRequisitoSolicitud : lRequisitoSolicituds) {
                lDocumentos.add(documentoRequisitoSolicitud.getNombreDocumento());
            }
            dSolicitudDto.setDocumentosRequeridos(lDocumentos);
            List<NotaDocumentoRequerido> lNotaDocumentoRequeridos = notaDocumentoRequeridoRepository.findByRequisitoSolicitudId(requisitoSolicitud.getTipoSolicitud().getId());
            List<String> notas = new ArrayList<>();
            for (NotaDocumentoRequerido notaDocumentoRequerido : lNotaDocumentoRequeridos) {
                notas.add(notaDocumentoRequerido.getNota());
            }
            dSolicitudDto.setNotas(notas);
            return dSolicitudDto;
        } else {
            return null;
        }        
    }

    @Override
    public InformacionPersonalDto obtenerInformacionPersonal(String correo) throws Exception {
        return gestionDocentesEstudiantesService.obtenerInformacionEstudiante(correo);
    }

    @Override
    public List<TutorDto> obtenerTutotes() throws Exception {
        List<TutorDto> tutorDtos = gestionDocentesEstudiantesService.obtenerDocentesActivos("ACTIVO")
        .stream()
        .map(docente -> {
            TutorDto tutorDto = new TutorDto();
            tutorDto.setId(docente.getId());
            tutorDto.setCodigoTutor(docente.getCodigoAcademico());
            tutorDto.setNombreTutor(docente.obtenerNombreCompleto());
            return tutorDto;
        })
        .collect(Collectors.toList());
        return tutorDtos;
    }

    @Override
    public Boolean registrarSolicitud(SolicitudRequestDto datosSolicitud) throws Exception {
        Boolean registro = Boolean.FALSE;
        try {
            logger.info("Inicia proceso registrar solicitud...");
            TiposSolicitud tipoSolicitud = tipoSolicitudRepository
                .findById(datosSolicitud.getIdTipoSolicitud()).get();
            Solicitudes solicitud = new Solicitudes();
            solicitud.setIdEstudiante(datosSolicitud.getIdEstudiante());
            solicitud.setTipoSolicitud(tipoSolicitud);
            solicitud.setIdTutor(datosSolicitud.getIdTutor());
            solicitud.setEstado(ESTADO_SOLICITUD.EN_PROGRESO.getDescripcion());
            solicitudesRepository.save(solicitud);
            registrarDatosTipoSolicitud(datosSolicitud, tipoSolicitud);
            logger.info("Se registró correctamente la solicitud.");
            registro = true;

        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado al registrar la solicitud.", e);
        }
        return registro;
    }

    private boolean registrarDatosTipoSolicitud(SolicitudRequestDto datosSolicitud, TiposSolicitud tipoSolicitud){
        boolean registro = false;
        switch (tipoSolicitud.getCodigo()) {
            case "HO_ASIG_POS":
                try {
                    boolean registroHomologacion = solicitudesHomologacionService.
                        registrarSolicitudHomologacion(datosSolicitud.getIdTipoSolicitud(), datosSolicitud.getDatosHomologacion());
                    if (registroHomologacion) {
                        logger.info("Se registraron los datos de la homologación satisfactoriamente.");
                        registro = true;
                    }
                } catch (Exception e) {
                    logger.error("Ocurrió un error inesperado al guardar los datos de la homologación.", e);
                    registro = false;
                }
                break;
        
            default:
                logger.info("No se encontro el tipo de solicitud a registrar.");
                registro = false;
                break;
        }
        return registro;
    }

    @Override
    public List<SolicitudPendientesAval> obtenerSolicitudesPendientes(String correo) throws Exception {
        List<SolicitudPendientesAval> solicitudes = new ArrayList<>();
        InformacionPersonalDto infoTutor = gestionDocentesEstudiantesService.obtenerTutor(correo);
        List<Solicitudes> solicitudesPendientes = solicitudesRepository.findAllByIdTutorOrderByFechaCreacionAsc(infoTutor.getId());
        for (Solicitudes solicitud : solicitudesPendientes) {
            TiposSolicitud tipoSolicitud = tipoSolicitudRepository.findById(solicitud.getTipoSolicitud().getId()).get();
            InformacionPersonalDto estudiante = gestionDocentesEstudiantesService
                    .obtenerInformacionEstudiantePorId(solicitud.getIdEstudiante());
            SolicitudPendientesAval solicitudPendiente = new SolicitudPendientesAval();
            solicitudPendiente.setIdSolicitud(solicitud.getId());
            solicitudPendiente.setCodigoSolicitud(tipoSolicitud.getCodigo());
            solicitudPendiente.setNombreTipoSolicitud(tipoSolicitud.getNombre());
            solicitudPendiente.setAbreviatura(ABREVIATURA_SOLICITUD.valueOf(tipoSolicitud.getCodigo()).getDescripcion());
            solicitudPendiente.setNombreEstudiante(estudiante.obtenerNombreCompleto());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            solicitudPendiente.setFecha(solicitud.getFechaCreacion().format(formatter));
            solicitudes.add(solicitudPendiente);
        }
        return solicitudes;
    }

}
