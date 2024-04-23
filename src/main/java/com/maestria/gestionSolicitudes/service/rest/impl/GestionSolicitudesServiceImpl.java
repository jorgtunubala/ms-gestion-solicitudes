package com.maestria.gestionSolicitudes.service.rest.impl;

import java.time.LocalDateTime;
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
import com.maestria.gestionSolicitudes.dto.client.AsignaturaExternaDto;
import com.maestria.gestionSolicitudes.dto.client.AsignaturaExternaResponseDto;
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
    @Autowired
    private CancelarAsignaturaRepository cancelarAsignaturaRepository;
    @Autowired
    private AsignaturaCanceladaRepository asignaturaCanceladaRepository;
    @Autowired
    private AplazarSemestreRepository aplazarSemestreRepository;
    @Autowired
    private CursarAsignaturaRepository cursarAsignaturaRepository;
    @Autowired
    private DatosCursarAsignaturaRepository datosCursarAsignaturaRepository;
    @Autowired
    private DocumentosCursarAsignaturaRepository documentosCursarAsignaturaRepository;
    @Autowired
    private AvalPasantiaInvestigacionRepository avalPasantiaInvestigacionRepository;
    @Autowired
    private DocumentosAvalPasantiaRepository documentosAvalPasantiaRepository;

    @Override
    public List<TipoSolicitudDto> obtenerTiposSolicitudes() {
        /*
         * Función que se encarga de retornar los tipos de solcitudes activas.
        */
        
        // Buscamos en la tabla tipos_solicitudes y retornamos los tipos de solicitudes en esatado ACTIVO
        List<TiposSolicitud> tiposSolicitudes = tipoSolicitudRepository.findByEstado("ACTIVO");
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
        /* 
         * Función que se encarga de retornar toda la información de los requisitos 
         * requeridos por cada solicitud.
        */
        
        // Buscamos los requisitos requeridos asociados a la solicitud.
        Optional<RequisitoSolicitud> optionalRequisitoSolicitud = requisitoSolicitudRepository.findByCodigo(codigo);
        DocumentoRequeridoSolicitudDto dSolicitudDto = new DocumentoRequeridoSolicitudDto();
        if (optionalRequisitoSolicitud.isPresent()){
            RequisitoSolicitud requisitoSolicitud = optionalRequisitoSolicitud.get();
            dSolicitudDto.setTituloDocumento(requisitoSolicitud.getTituloDocumento());
            dSolicitudDto.setDescripcion(requisitoSolicitud.getDescripcion());
            dSolicitudDto.setTenerEnCuenta(requisitoSolicitud.getTenerEnCuenta());
            dSolicitudDto.setArticulo(requisitoSolicitud.getArticulo());
            // Anexamos los documentos requeridos de la solicitud
            List<DocumentoRequisitoSolicitud> lRequisitoSolicituds = dSolicitudRepository.findByRequisitoSolicitudId(requisitoSolicitud.getId());
            List<String> lDocumentos = new ArrayList<>();
            for (DocumentoRequisitoSolicitud documentoRequisitoSolicitud : lRequisitoSolicituds) {
                lDocumentos.add(documentoRequisitoSolicitud.getNombreDocumento());
            }
            dSolicitudDto.setDocumentosRequeridos(lDocumentos);
            // Buscamos Notas que pueden estar asociadas a la solicitud.
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
        /*
         * Función que se encarga de retornar la información personal de un Estudiante
         * a partir de su correo.
         * La función consume el servicio suministrado por el microservicio ms-gestion-docentes-estudiantes
        */
        return gestionDocentesEstudiantesService.obtenerInformacionEstudiante(correo);
    }

    @Override
    public List<TutorDto> obtenerTutotes() throws Exception {
        /*
         * Función que se encarga de retornar la información personal de los docentes activos.         
         * La función consume el servicio suministrado por el microservicio ms-gestion-docentes-estudiantes
        */
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
        /*
         * Función que se encarga de registrar toda la información de cada solicitud de cualquier tipo.
        */
        Boolean registro = Boolean.FALSE;
        try {
            logger.info("Inicia proceso registrar solicitud...");
            // Buscamos el tipo de solciitud a asociar en el regsitro de la solicitud.
            TiposSolicitud tipoSolicitud = tipoSolicitudRepository
                .findById(datosSolicitud.getIdTipoSolicitud()).get();
            Solicitudes solicitud = new Solicitudes();
            // Asignamos los datos necesarios de la solicitud.
            solicitud.setIdEstudiante(datosSolicitud.getIdEstudiante());
            solicitud.setTipoSolicitud(tipoSolicitud);
            solicitud.setIdTutor(datosSolicitud.getIdTutor());
            solicitud.setEstado(ESTADO_SOLICITUD.EN_PROGRESO.getDescripcion());
            solicitud.setRequiereFirmaDirector(datosSolicitud.getRequiereFirmaDirector());
            Solicitudes registroSolicitud = solicitudesRepository.save(solicitud);

            // Utilizamos la siguiente función para guardar otros datos de la solicitud según su tipo.
            boolean datosTipoSolicitudRegistrados = registrarDatosTipoSolicitud(datosSolicitud, registroSolicitud.getId(), tipoSolicitud.getCodigo());
            
            // Utilizamos la siguiente función para garantizar la firma del estudiante en la solicitud.
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
            case "CA_ASIG":
                try {
                    boolean registroCancelar = registrarCancelarAsignatura(idSolicitud, datosSolicitud.getDatosCancelarAsignatura());
                    if (registroCancelar) {
                        logger.info("Se registraron los datos de la cancelación de asignaturas satisfactoriamente.");
                        registro = true;
                    }
                } catch (Exception e) {
                    logger.error("Ocurrió un error inesperado al guardar los datos de la cancelación de asignaturas.", e);
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
        
            case "AP_SEME":
                try {
                    boolean registroAplazar = registrarAplazarSemestre(idSolicitud, datosSolicitud.getDatosAplazarSemestre());
                    if (registroAplazar) {
                        logger.info("Se registraron los datos de la adición asignaturas satisfactoriamente.");
                        registro = true;
                    }
                } catch (Exception e) {
                    logger.error("Ocurrió un error inesperado al guardar los datos de la adición asignaturas.", e);
                    registro = false;
                    throw e;
                }                
                break;

            case "CU_ASIG":
                try {
                    boolean registroCursarAsignatura = registrarCursarAsignatura(idSolicitud, datosSolicitud.getDatosCursarAsignatura());
                    if (registroCursarAsignatura) {
                        logger.info("Se registraron los datos de la adición asignaturas satisfactoriamente.");
                        registro = true;
                    }
                } catch (Exception e) {
                    logger.error("Ocurrió un error inesperado al guardar los datos de la adición asignaturas.", e);
                    registro = false;
                    throw e;
                }                
                break;
            
            case "AV_PASA_INV":
                try {
                    boolean registroAvalPasantia = registrarAvalPasantiaInvestigacion(idSolicitud, datosSolicitud.getDatosAvalPasantiaInv());
                    if (registroAvalPasantia) {
                        logger.info("Se registraron los datos aval pasantia investigación correctamente.");
                        registro = true;
                    }
                } catch (Exception e) {
                    logger.error("Ocurrió un error inesperado al guardar los datos de la solicitud aval pasantia investigación.", e);
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
                        DatosSolicitudAdicionCancelacionAsignatura datosAdicionAsignatura = new DatosSolicitudAdicionCancelacionAsignatura();
                        List<InfoAdicionCancelacion> lInfoAdiciones = new ArrayList<>();
                        for (AsignaturaAdicionada asignaturasAdicionadas : asignaturaAdicionadas) {                            
                            InfoAdicionCancelacion info = new InfoAdicionCancelacion();
                            info.setNombreAsignatura(asignaturasAdicionadas.getNombreAsignatura());
                            InformacionPersonalDto infoDocente = gestionDocentesEstudiantesService
                                .obtenerTutor(asignaturasAdicionadas.getIdDocente().toString());
                            info.setDocenteAsignatura(infoDocente.obtenerNombreCompleto());
                            lInfoAdiciones.add(info);
                        }
                        datosAdicionAsignatura.setListaAsignaturas(lInfoAdiciones);
                        response.setDAdicionCancelacionAsignatura(datosAdicionAsignatura);
                        break;
                    case "CA_ASIG":                        
                        CancelarAsignatura cancelarAsignatura = cancelarAsignaturaRepository.findBySolicitud(solicitud);
                        List<AsignaturaCancelada> asignaturaCanceladas = asignaturaCanceladaRepository
                                        .findByCancelarAsignatura(cancelarAsignatura);                                                
                        DatosSolicitudAdicionCancelacionAsignatura datosCancelacionAsignatura = new DatosSolicitudAdicionCancelacionAsignatura();
                        datosCancelacionAsignatura.setMotivo(cancelarAsignatura.getMotivo());
                        List<InfoAdicionCancelacion> lInfoCancelacion = new ArrayList<>();
                        for (AsignaturaCancelada asignaturasCanceladas : asignaturaCanceladas) {                            
                            InfoAdicionCancelacion info = new InfoAdicionCancelacion();
                            info.setNombreAsignatura(asignaturasCanceladas.getNombreAsignatura());
                            InformacionPersonalDto infoDocente = gestionDocentesEstudiantesService
                                .obtenerTutor(asignaturasCanceladas.getIdDocente().toString());
                            info.setDocenteAsignatura(infoDocente.obtenerNombreCompleto());
                            lInfoCancelacion.add(info);
                        }
                        datosCancelacionAsignatura.setListaAsignaturas(lInfoCancelacion);
                        response.setDAdicionCancelacionAsignatura(datosCancelacionAsignatura);
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
                    case "AP_SEME":
                        AplazarSemestre aplazarSemestre = aplazarSemestreRepository.findBySolicitud(solicitud);
                        if (aplazarSemestre != null){
                            DatosSolicitudAplazarSemestre datosAplazarS = new DatosSolicitudAplazarSemestre();
                            datosAplazarS.setSemestre(aplazarSemestre.getSemestre());
                            datosAplazarS.setMotivo(aplazarSemestre.getMotivo());
                            response.setDatosSolicitudAplazarSemestre(datosAplazarS);
                        }
                        break;

                    case "CU_ASIG":
                        CursarAsignatura cursarAsignatura = cursarAsignaturaRepository.findBySolicitud(solicitud);
                        DatosSolicitudCursarAsignaturas datosSolicitudCursarAsignaturas = new DatosSolicitudCursarAsignaturas();
                        datosSolicitudCursarAsignaturas.setMotivo(cursarAsignatura.getMotivo());
                        List<DatosCursarAsignatura> listDatosCursarAsignaturas = datosCursarAsignaturaRepository
                                                                        .findAllByCursarAsignatura(cursarAsignatura);
                        List<DatosAsignaturaOtroPrograma> listAsignaturaOtroPrograma = new ArrayList<>();
                        for (DatosCursarAsignatura infoDatosCursarAsig : listDatosCursarAsignaturas) {
                            DatosAsignaturaOtroPrograma infoAsigOtroPrograma = new DatosAsignaturaOtroPrograma();
                            AsignaturaExternaResponseDto asignaturaExterna = gestionAsignaturasService.
                                                                obtenerAsignaturaExterna(infoDatosCursarAsig.getIdAsignaturaExterna());
                            infoAsigOtroPrograma.setNombre(asignaturaExterna.getNombre());
                            infoAsigOtroPrograma.setCodigo(infoDatosCursarAsig.getCodigoAsignatura());
                            infoAsigOtroPrograma.setCreditos(asignaturaExterna.getCreditos());
                            infoAsigOtroPrograma.setIntensidadHoraria(asignaturaExterna.getIntensidadHoraria());
                            infoAsigOtroPrograma.setNombrePrograma(asignaturaExterna.getPrograma());
                            infoAsigOtroPrograma.setNombreDocente(infoDatosCursarAsig.getNombreDocente());
                            listAsignaturaOtroPrograma.add(infoAsigOtroPrograma);
                        }
                        datosSolicitudCursarAsignaturas.setDatosAsignaturaOtroProgramas(listAsignaturaOtroPrograma);
                        List<DocumentosCursarAsignatura> listDocumentosCursarAsignatura = documentosCursarAsignaturaRepository
                                                                .findAllByCursarAsignatura(cursarAsignatura);
                        List<String> documentosAdjuntos = new ArrayList<>();
                        for (DocumentosCursarAsignatura documentos : listDocumentosCursarAsignatura) {
                            documentosAdjuntos.add(documentos.getDocumento());
                        }
                        datosSolicitudCursarAsignaturas.setDocumentosAdjuntos(documentosAdjuntos);
                        response.setDatosSolicitudCursarAsignaturas(datosSolicitudCursarAsignaturas);
                        break;

                    case "AV_PASA_INV":
                        AvalPasantiaInvestigacion avalPasantiaInvestigacion = avalPasantiaInvestigacionRepository.findBySolicitud(solicitud);                        
                        if (avalPasantiaInvestigacion != null){
                            AvalPasantiaInvResponse datosAvalPasantia = new AvalPasantiaInvResponse();
                            datosAvalPasantia.setLugarPasantia(avalPasantiaInvestigacion.getLugarPasantia());
                            datosAvalPasantia.setFechaInicio(avalPasantiaInvestigacion.getFechaInicio().format(formatter));
                            datosAvalPasantia.setFechaFin(avalPasantiaInvestigacion.getFechaFin().format(formatter));
                            List<DocumentosAvalPasantia> documentosAvalPasantias = documentosAvalPasantiaRepository.
                                    findAllByAvalPasantia(avalPasantiaInvestigacion);
                            List<String> documentos = new ArrayList<>();
                            for (DocumentosAvalPasantia documento : documentosAvalPasantias) {
                                documentos.add(documento.getDocumento());
                            }
                            datosAvalPasantia.setDocumentosAdjuntos(documentos);
                            response.setDatoAvalPasantiaInv(datosAvalPasantia);
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

    private boolean registrarAdicionAsignatura(Integer idSolicitud, List<InfoAdicionAsignaturaRequest> listaAsignaturas) throws Exception {
        Solicitudes solicitud = solicitudesRepository.findById(idSolicitud).get();
        return adicionAsignaturaService.registrarAdicionAsignaturas(solicitud, listaAsignaturas);
    }

    private boolean registrarCancelarAsignatura(Integer idSolicitud, CancelarAsignaturaRequest datosCancelarAsignatura) throws Exception {
        Solicitudes solicitud = solicitudesRepository.findById(idSolicitud).get();
        return adicionAsignaturaService.registrarCancelarAsignaturas(solicitud, datosCancelarAsignatura);
    }

    private boolean registrarAplazarSemestre(Integer idSolicitud, AplazarSemestreRequest datosAplazarSemestre) {
        boolean registro = false;
        try{
            Solicitudes solicitud = solicitudesRepository.findById(idSolicitud).get();
            AplazarSemestre aplazarSemestre = new AplazarSemestre();
            aplazarSemestre.setSolicitud(solicitud);
            aplazarSemestre.setSemestre(datosAplazarSemestre.getSemestre());
            aplazarSemestre.setMotivo(datosAplazarSemestre.getMotivo());        
            aplazarSemestreRepository.save(aplazarSemestre);
            registro = true;
        } catch (Exception e){
            logger.error("Ocurrió un error al intentar guardar los datos de aplazar semestre.", e);
            registro = false;
        }
        return registro;
    }

    private boolean registrarCursarAsignatura(Integer idSolicitud, DatosSolicitudCursarAsignaturaDto datosCursarAsignaturaDto) {
        CursarAsignatura cursarAsignatura = new CursarAsignatura();
        try {
            Solicitudes solicitud = solicitudesRepository.findById(idSolicitud).get();
            cursarAsignatura.setSolicitud(solicitud);
            cursarAsignatura.setMotivo(datosCursarAsignaturaDto.getDatosCursarAsignaturaDto().getMotivo());
            cursarAsignatura = cursarAsignaturaRepository.save(cursarAsignatura);

            // Se procede a guardar la información de la asignatura externa a cursar
            List<AsignaturaExternaRequest> asignaturasExternasCursar = datosCursarAsignaturaDto
                                                    .getDatosCursarAsignaturaDto().getListaAsignaturasCursar();
            for (AsignaturaExternaRequest infoAsignaturaExterna : asignaturasExternasCursar) {
                AsignaturaExternaResponseDto info = registrarAsignaturasExternas(infoAsignaturaExterna);
                // Procedemos a guardar la informacion de los datos de la solicitud asignatura a cursar
                DatosCursarAsignatura datosCursarAsignatura = new DatosCursarAsignatura();
                datosCursarAsignatura.setCursarAsignatura(cursarAsignatura);
                datosCursarAsignatura.setIdAsignaturaExterna(info.getIdAsignatura());
                datosCursarAsignatura.setCodigoAsignatura(infoAsignaturaExterna.getCodigoAsignatura());
                datosCursarAsignatura.setGrupo(infoAsignaturaExterna.getGrupo());
                datosCursarAsignatura.setNombreDocente(infoAsignaturaExterna.getNombreDocente());
                datosCursarAsignatura.setTituloDocente(infoAsignaturaExterna.getTituloDocente());
                datosCursarAsignatura.setCartaAceptacion(infoAsignaturaExterna.getCartaAceptacion());
                datosCursarAsignatura.setEstado(ESTADO_SOLICITUD.PENDIENTE_AVAL.getDescripcion());
                datosCursarAsignaturaRepository.save(datosCursarAsignatura);
            }
            
            // Procedemos a guardar los ducumentos adjuntos de la solicitud
            // Se suspende ya que no se tienen documentos adjuntos
            /* for (String documento : datosCursarAsignaturaDto.getDocumentosAdjuntos()) {
                DocumentosCursarAsignatura documentosCursarAsignatura = new DocumentosCursarAsignatura();
                documentosCursarAsignatura.setCursarAsignatura(cursarAsignatura);
                documentosCursarAsignatura.setDocumento(documento);
                documentosCursarAsignaturaRepository.save(documentosCursarAsignatura);
            } */

        } catch (Exception e) {
            logger.error("Ocurrió un error al intentar guardar los datos de cursar asignaturas.", e);
        }        
        return true;
    }

    private AsignaturaExternaResponseDto registrarAsignaturasExternas(AsignaturaExternaRequest datosAsignatura){
        try {
            AsignaturaExternaDto asignaturaExternaDto = new AsignaturaExternaDto();
            asignaturaExternaDto.setInstitutoProcedencia(datosAsignatura.getInstitutoProcedencia());
            asignaturaExternaDto.setProgramaProcedencia(datosAsignatura.getProgramaProcedencia());
            asignaturaExternaDto.setNombreAsignatura(datosAsignatura.getNombreAsignatura());
            asignaturaExternaDto.setNumeroCreditos(datosAsignatura.getNumeroCreditos());
            asignaturaExternaDto.setIntensidadHoraria(datosAsignatura.getIntensidadHoraria());
            asignaturaExternaDto.setContenidoProgramatico(datosAsignatura.getContenidoProgramatico());            
            return gestionAsignaturasService.registrarAsignaturasExternas(asignaturaExternaDto);
            
        } catch (Exception e) {
            System.out.println("Ocurrió un error al registrar la asignatura externa a cursar en otro programa, ");
            e.printStackTrace();
            return null;
        }
    }

    private boolean registrarAvalPasantiaInvestigacion(Integer idSolicitud, AvalPasantiaInvRequest avalPasantiaInvRequest) {
        boolean registro = false;
        try{
            Solicitudes solicitud = solicitudesRepository.findById(idSolicitud).get();
            AvalPasantiaInvestigacion avalPasantiaInvestigacion = new AvalPasantiaInvestigacion();
            avalPasantiaInvestigacion.setSolicitud(solicitud);
            avalPasantiaInvestigacion.setLugarPasantia(avalPasantiaInvRequest.getLugarPasantia());
            avalPasantiaInvestigacion.setFechaInicio(avalPasantiaInvRequest.getFechaInicio());
            avalPasantiaInvestigacion.setFechaFin(avalPasantiaInvRequest.getFechaFin());
            avalPasantiaInvestigacion = avalPasantiaInvestigacionRepository.save(avalPasantiaInvestigacion);           

            // Procedemos a guardar los ducumentos adjuntos de la solicitud
            for (String documento : avalPasantiaInvRequest.getDocumentosAdjuntos()) {
                DocumentosAvalPasantia documentosAvalPasantia = new DocumentosAvalPasantia();
                documentosAvalPasantia.setAvalPasantia(avalPasantiaInvestigacion);
                documentosAvalPasantia.setDocumento(documento);                
                documentosAvalPasantiaRepository.save(documentosAvalPasantia);
            }
            registro = true;
        } catch (Exception e){
            logger.error("Ocurrió un error al intentar guardar los datos de aval pasantia investigación.", e);
            registro = false;
        }
        return registro;
    }
}
