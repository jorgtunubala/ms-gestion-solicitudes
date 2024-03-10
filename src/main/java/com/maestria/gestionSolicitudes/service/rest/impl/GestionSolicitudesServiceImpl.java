package com.maestria.gestionSolicitudes.service.rest.impl;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maestria.gestionSolicitudes.comun.enums.*;
import com.maestria.gestionSolicitudes.domain.*;
import com.maestria.gestionSolicitudes.dto.client.AsignaturaExternaResponseDto;
import com.maestria.gestionSolicitudes.dto.client.DocentesAsignaturasResponse;
import com.maestria.gestionSolicitudes.dto.client.InformacionPersonalDto;
import com.maestria.gestionSolicitudes.dto.rest.request.*;
import com.maestria.gestionSolicitudes.dto.rest.response.*;
import com.maestria.gestionSolicitudes.repository.*;
import com.maestria.gestionSolicitudes.service.client.GestionAsignaturasService;
import com.maestria.gestionSolicitudes.service.client.GestionDocentesEstudiantesService;
import com.maestria.gestionSolicitudes.service.rest.*;

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
    @Autowired
    private HomologacionesRepository homologacionRepository;
    @Autowired
    private AsignaturasHomologadasRepository asignaturasHomologadasRepository;
    @Autowired
    private DocumentosAdjuntosHomologacionRepository documentosAdjuntosHomologacionRepository;
    @Autowired
    private GestionAsignaturasService gestionAsignaturasService;
    @Autowired
    private FirmaSolicitudRepository firmaSolicitudRepository;
    @Autowired
    private AdicionAsignaturaService adicionAsignaturaService;
    @Autowired
    private AdicionarAsignaturaRepository adicionarAsignaturaRepository;
    @Autowired
    private AsignaturaAdicionadaRepository asignaturaAdicionadaRepository;

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
    @Transactional
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
            solicitud.setRequiereFirmaDirector(datosSolicitud.getRequiereFirmaDirector());
            Solicitudes registroSolicitud = solicitudesRepository.save(solicitud);
            boolean datosTipoSolicitudRegistrados = registrarDatosTipoSolicitud(datosSolicitud, registroSolicitud.getId(), tipoSolicitud.getCodigo());
            boolean firmaEstudianteRegistrada = registrarFirmaEstudiante(registroSolicitud, datosSolicitud.getFirmaEstudiante());
            // Verificar si todas las operaciones fueron exitosas
            if (datosTipoSolicitudRegistrados && firmaEstudianteRegistrada) {
                registro = Boolean.TRUE;
                logger.info("Se registró correctamente la solicitud.");
            } else {                
                logger.error("Ocurrió un error al registrar la solicitud.");
                throw new Exception("Error al registrar la solicitud.");
            }
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado al registrar la solicitud.", e);
            throw e;
        }
        return registro;
    }

    @Transactional
    private boolean registrarDatosTipoSolicitud(SolicitudRequestDto datosSolicitud, Integer idSolicitud, String tipoSolicitud) throws Exception{
        boolean registro = false;
        switch (tipoSolicitud) {
            case "AD_ASIG":
                try {
                    boolean registroAdicion = registrarAdicionAsignatura(idSolicitud, datosSolicitud.getDatosAdicionAsignatura());
                    if (registroAdicion) {
                        logger.info("Se registraron los datos de la adición asignaturas satisfactoriamente.");
                        registro = true;
                    }
                } catch (Exception e) {
                    logger.error("Ocurrió un error inesperado al guardar los datos de la adición asignaturas.", e);
                    registro = false;
                    throw e;
                }
                break;
            case "HO_ASIG_ESP":                
            case "HO_ASIG_POS":
                try {
                    boolean registroHomologacion = solicitudesHomologacionService.
                        registrarSolicitudHomologacion(idSolicitud, datosSolicitud.getDatosHomologacion());
                    if (registroHomologacion) {
                        logger.info("Se registraron los datos de la homologación satisfactoriamente.");
                        registro = true;
                    }
                } catch (Exception e) {
                    logger.error("Ocurrió un error inesperado al guardar los datos de la homologación.", e);
                    registro = false;
                    throw e;
                }
                break;
        
            default:
                logger.info("No se encontro el tipo de solicitud a registrar.");
                registro = false;
                throw new Exception("Tipo de solicitud no encontrado: " + tipoSolicitud); // Lanzar excepción para revertir la transacción
        }
        return registro;
    }

    @Override
    public List<SolicitudPendientesAval> obtenerSolicitudesPendientes(String correo) throws Exception {
        List<SolicitudPendientesAval> solicitudes = new ArrayList<>();
        InformacionPersonalDto infoTutor = gestionDocentesEstudiantesService.obtenerTutor(correo);
        List<Solicitudes> solicitudesPendientes = solicitudesRepository.findAllByIdTutorOrderByFechaCreacionAsc(infoTutor.getId(), ESTADO_SOLICITUD.EN_PROGRESO.getDescripcion());
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

    @Override
    public DatosGestionSolicitudResponse obtenerDatosSolicitud(Integer idSolicitud) throws Exception {
        DatosGestionSolicitudResponse response = new DatosGestionSolicitudResponse();
        try {
            Optional<Solicitudes> solicitudOpt = solicitudesRepository.findById(idSolicitud);
            if (solicitudOpt.isPresent()){
                Solicitudes solicitud = solicitudOpt.get();
                DatosComunSolicitud datosComun = new DatosComunSolicitud();
                InformacionPersonalDto tutor = gestionDocentesEstudiantesService.obtenerTutor(solicitud.getIdTutor().toString());
                InformacionPersonalDto estudiante = gestionDocentesEstudiantesService.obtenerInformacionEstudiantePorId(solicitud.getIdEstudiante());
                datosComun.setTipoSolicitud(solicitud.getTipoSolicitud().getNombre());
                // Formatear la fecha y hora en el formato deseado
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                datosComun.setFechaEnvioSolicitud(solicitud.getFechaCreacion().format(formatter));
                datosComun.setNombreSolicitante(estudiante.getNombres());
                datosComun.setApellidoSolicitante(estudiante.getApellidos());
                datosComun.setCodigoSolicitante(estudiante.getCodigoAcademico());
                datosComun.setEmailSolicitante(estudiante.getCorreo());
                datosComun.setCelularSolicitante(estudiante.getCelular());
                datosComun.setTipoIdentSolicitante(estudiante.getTipoDocumento());
                datosComun.setNumeroIdentSolicitante(estudiante.getNumeroDocumento());
                datosComun.setNombreTutor(tutor.obtenerNombreCompleto());
                datosComun.setRequiereFirmaDirector(solicitud.getRequiereFirmaDirector());
                FirmaSolicitud firmaSolicitud = firmaSolicitudRepository.findBySolicitud(solicitud);
                datosComun.setFirmaSolicitante(firmaSolicitud.getFirmaEstudiante());
                datosComun.setFirmaTutor(firmaSolicitud.getFirmaTutor());
                datosComun.setFirmaDirector(firmaSolicitud.getFirmaDirector());
                datosComun.setEstadoSolicitud(solicitud.getEstado());
                response.setDatosComunSolicitud(datosComun);
                switch (solicitud.getTipoSolicitud().getCodigo()) {
                    case "AD_ASIG":                        
                        AdicionarAsignatura adicionarAsignatura = adicionarAsignaturaRepository.findBySolicitud(solicitud);
                        List<AsignaturaAdicionada> asignaturaAdicionadas = asignaturaAdicionadaRepository
                                        .findByAdicionarAsignatura(adicionarAsignatura);
                        List<Integer> lista = new ArrayList<>();
                        for (AsignaturaAdicionada asignaturaAdicionada : asignaturaAdicionadas) {
                            lista.add(asignaturaAdicionada.getIdAsignatura());
                        }
                        List<DocentesAsignaturasResponse> lAsignaturasResponses = gestionAsignaturasService
                                .obtenerDocentesAsignaturas(lista);
                        DatosSolicitudAdicionCancelacionAsignatura dAdicionCancelacionAsignatura = new DatosSolicitudAdicionCancelacionAsignatura();
                        List<InfoAdicionCancelacion> lInfoAdicionCancelacions = new ArrayList<>();
                        for (DocentesAsignaturasResponse dAsignaturasResponse : lAsignaturasResponses) {                            
                            InfoAdicionCancelacion info = new InfoAdicionCancelacion();
                            info.setNombreAsignatura(dAsignaturasResponse.getNombreAsignatura());
                            info.setGrupo(dAsignaturasResponse.getCodigoAsignatura());
                            lInfoAdicionCancelacions.add(info);
                        }
                        dAdicionCancelacionAsignatura.setListaAsignaturas(lInfoAdicionCancelacions);
                        response.setDAdicionCancelacionAsignatura(dAdicionCancelacionAsignatura);
                        break;
                    case "HO_ASIG_ESP":
                    case "HO_ASIG_POS":
                        DatosSolicitudHomologacion datosHomologacion = new DatosSolicitudHomologacion();
                        Homologaciones homologacion = homologacionRepository.findBySolicitud(solicitud);
                        if (homologacion != null) {
                            List<AsignaturasHomologadas> asignaturasHomologadas = asignaturasHomologadasRepository
                                .findAllByHomologacion(homologacion);
                                List<DatosAsignaturaHomologar> datosAsignaturaHomologar = new ArrayList<>();

                                String programa = "";
                                String institucion = "";
                                for (AsignaturasHomologadas asignatura : asignaturasHomologadas) {
                                    DatosAsignaturaHomologar datosAsignatura = new DatosAsignaturaHomologar();
                                    AsignaturaExternaResponseDto asignaturaExternaDto = gestionAsignaturasService
                                        .obtenerAsignaturaExterna(asignatura.getAsignaturaExterna());
                                    datosAsignatura.setCalificacion(asignatura.getCalificacionObtenida());
                                    datosAsignatura.setCreditos(asignaturaExternaDto.getCreditos());
                                    datosAsignatura.setIntensidadHoraria(asignaturaExternaDto.getIntensidadHoraria());
                                    datosAsignatura.setNombreAsignatura(asignaturaExternaDto.getNombre());
                                    programa = asignaturaExternaDto.getPrograma();
                                    institucion = asignaturaExternaDto.getInstitucion();
                                    datosAsignaturaHomologar.add(datosAsignatura);
                                }
                                List<String> datosAdjuntos = documentosAdjuntosHomologacionRepository
                                    .findDocumentosByHomologacion(homologacion);
                                datosHomologacion.setInstitutoProcedencia(institucion);
                                datosHomologacion.setProgramaProcedencia(programa);
                                datosHomologacion.setDatosAsignatura(datosAsignaturaHomologar);
                                datosHomologacion.setDocumentosAdjuntos(datosAdjuntos);                                
                                response.setDatosSolicitudHomologacion(datosHomologacion);
                        }
                        break;
                
                    default:
                        logger.info("No se encontró tipo de solicitud para retornar la información de la solicitud.");
                        break;
                }
                logger.info("Se obtienen los datos de la solicitud satisfactoriamente.");
            } else {
                logger.info("No se obtuvo ningun dato para la solicitud con id: " + idSolicitud);
            }
        } catch (Exception e) {
            logger.error("Ocurrió un error al obtener los datos de la solicitud. ", e);
        }
        return response;
    }

    @Transactional
    private Boolean registrarFirmaEstudiante(Solicitudes solicitud, String firmaEstudiante) throws Exception{
        try {
            FirmaSolicitud firmaSolicitud = new FirmaSolicitud();
            firmaSolicitud.setSolicitud(solicitud);
            firmaSolicitud.setFirmaEstudiante(firmaEstudiante);
            firmaSolicitudRepository.save(firmaSolicitud);
            return  Boolean.TRUE;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado al guardar la firma del estudiante.", e);            
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean registrarFirmasPendientes(DatosAvalarSolicitudDto dAvalarSolicitudDto) throws Exception {
        Boolean registroDocumento = Boolean.FALSE;
        Boolean registroFirma = Boolean.FALSE;
        Solicitudes solicitud = solicitudesRepository.findById(dAvalarSolicitudDto.getIdSolicitud()).get();
        FirmaSolicitud firmas = firmaSolicitudRepository.findBySolicitud(solicitud);
        String firmaTutor = dAvalarSolicitudDto.getFirmaTutor();
        String firmaDirector = dAvalarSolicitudDto.getFirmaDirector();
        if (solicitud.getRequiereFirmaDirector()){
            if (StringUtils.isNotBlank(firmaTutor) && StringUtils.isNotBlank(firmaDirector)) {
                firmas.setFirmaTutor(firmaTutor);
                firmas.setFirmaDirector(firmaDirector);
                firmaSolicitudRepository.save(firmas);
                registroDocumento = Boolean.TRUE;
                registroFirma = Boolean.TRUE;
            } else if (StringUtils.isNotBlank(firmaTutor)){
                firmas.setFirmaTutor(firmaTutor);
                firmaSolicitudRepository.save(firmas);
                registroFirma = Boolean.TRUE;
            } else if (StringUtils.isNotBlank(firmaDirector)){
                firmas.setFirmaDirector(firmaDirector);
                firmaSolicitudRepository.save(firmas);
                registroFirma = Boolean.TRUE;
            }
        } else {
            if (StringUtils.isNotBlank(firmaTutor)) {
                firmas.setFirmaTutor(firmaTutor);
                firmaSolicitudRepository.save(firmas);
                registroDocumento = Boolean.TRUE;
                registroFirma = Boolean.TRUE;
            }
        }
        if (registroDocumento) {
            solicitud.setDocumentoFirmado(dAvalarSolicitudDto.getDocumentoPdfSolicitud());
            solicitud.setEstado(ESTADO_SOLICITUD.AVALADO.getDescripcion());
            solicitudesRepository.save(solicitud);
        }
        return registroFirma;
    }

    private boolean registrarAdicionAsignatura(Integer idSolicitud, List<Integer> listaAsignaturas) throws Exception {
        Solicitudes solicitud = solicitudesRepository.findById(idSolicitud).get();
        return adicionAsignaturaService.registrarAdicionAsignaturas(solicitud, listaAsignaturas);
    }
}
