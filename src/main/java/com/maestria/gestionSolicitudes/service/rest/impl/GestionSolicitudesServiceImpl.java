package com.maestria.gestionSolicitudes.service.rest.impl;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maestria.gestionSolicitudes.comun.enums.*;
import com.maestria.gestionSolicitudes.comun.util.TokenAleatorio;
import com.maestria.gestionSolicitudes.domain.*;
import com.maestria.gestionSolicitudes.dto.client.AsignaturaExternaDto;
import com.maestria.gestionSolicitudes.dto.client.AsignaturaExternaResponseDto;
import com.maestria.gestionSolicitudes.dto.client.EmailRequest;
import com.maestria.gestionSolicitudes.comun.util.Base64;
import com.maestria.gestionSolicitudes.dto.client.InformacionPersonalDto;
import com.maestria.gestionSolicitudes.dto.rest.request.*;
import com.maestria.gestionSolicitudes.dto.rest.response.*;
import com.maestria.gestionSolicitudes.mapper.ApoyoEconomicoCongresoMapper;
import com.maestria.gestionSolicitudes.mapper.ApoyoEconomicoMapper;
import com.maestria.gestionSolicitudes.mapper.ApoyoEconomicoPublicacionEventoMapper;
import com.maestria.gestionSolicitudes.mapper.AvalPasantiaInvMapper;
import com.maestria.gestionSolicitudes.repository.*;
import com.maestria.gestionSolicitudes.service.client.GestionAsignaturasService;
import com.maestria.gestionSolicitudes.service.client.GestionDocentesEstudiantesService;
import com.maestria.gestionSolicitudes.service.client.MensajeriaService;
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
    @Autowired
    private ApoyoEconomicoInvestigacionRepository apoyoEconomicoInvestigacionRepository;
    @Autowired
    private DocumentosApoyoEconomicoRepository documentosApoyoEconomicoRepository;
    @Autowired
    private AvalSeminarioActRepository avalSeminarioActRepository;
    @Autowired
    private DocumentosAvalSeminarioActRepository dAvalSeminarioActRepository;
    @Autowired
    private ReconocimientoCreditosRepository reconocimientoCreditosRepository;
    @Autowired
    private DocumentosRecCreditosRepository documentosRecCreditosRepository;
    @Autowired
    private ApoyoEconomicoCongresoRepository apoyoEconomicoCongresoRepository;
    @Autowired
    private DocumentosApoyoEconomicoCongresoRepository documentosApoyoEconomicoCongresoRepository;
    @Autowired
    private ApoyoEconomicoPublicacionEventoRepository apoyoEconomicoPublicacionEventoRepository;
    @Autowired
    private DocumentosApoyoEconomicoPublicacionEventoRepository documentosApoyoEconomicoPublicacionEventoRepository;
    @Autowired
    private SubTiposSolicitudRepository subTiposSolicitudRepository;
    @Autowired
    private ActividadesRealizadasPracticaDocenteRepository actividadesRealizadasPracticaDocenteRepository;
    @Autowired
    private DocumentosActividadesRealizadasRepository documentosActividadesRealizadasRepository;
    @Autowired
    private EnlacesActividadesRealizadasRepository enlacesActividadesRealizadasRepository;
    @Autowired
    private AvalComiteProgramaRepository avalComiteProgramaRepository;
    @Autowired
    private HistorialEstadoSolicitudesRepository historialEstadoSolicitudesRepository;
    @Autowired
    private SolicitudBecaDescuentoRepository solicitudBecaRepository;
    @Autowired
    private EnlaceTipoSolicitudRepository enlaceTipoSolicitudRepository;
    @Autowired
    private MensajeriaService mensajeriaService;

    private final ApoyoEconomicoMapper apoyoEconomicoMapper;
    private final AvalPasantiaInvMapper avalPasantiaInvMapper;
    private final ApoyoEconomicoCongresoMapper apoyoEconomicoCongresoMapper;
    private final ApoyoEconomicoPublicacionEventoMapper apoyoEconomicoPublicacionEventoMapper;

    public GestionSolicitudesServiceImpl(ApoyoEconomicoMapper apoyoEconomicoMapper, AvalPasantiaInvMapper avalPasantiaInvMapper, 
                ApoyoEconomicoCongresoMapper apoyoEconomicoCongresoMapper, ApoyoEconomicoPublicacionEventoMapper apoyoEconomicoPublicacionEventoMapper) {
        this.apoyoEconomicoMapper = apoyoEconomicoMapper;
        this.avalPasantiaInvMapper = avalPasantiaInvMapper;
        this.apoyoEconomicoCongresoMapper = apoyoEconomicoCongresoMapper;
        this.apoyoEconomicoPublicacionEventoMapper = apoyoEconomicoPublicacionEventoMapper;
    }
    
    @Override
    public List<TipoSolicitudDto> obtenerTiposSolicitudes() {
        /*
         * Función que se encarga de retornar los tipos de solcitudes activas.
        */
        
        // Buscamos en la tabla tipos_solicitudes y retornamos los tipos de solicitudes en esatado ACTIVO
        List<TiposSolicitud> tiposSolicitudes = tipoSolicitudRepository.findByEstadoOrderByNombreAsc("ACTIVO");
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
            List<DocumentoRequerido> lDocumentos = new ArrayList<>();
            for (DocumentoRequisitoSolicitud documentoRequisitoSolicitud : lRequisitoSolicituds) {
                DocumentoRequerido doc = new DocumentoRequerido();
                doc.setNombre(documentoRequisitoSolicitud.getNombreDocumento());
                doc.setAdjuntarDocumento(documentoRequisitoSolicitud.getAdjuntarDocumento());
                lDocumentos.add(doc);
            }
            dSolicitudDto.setDocumentosRequeridos(lDocumentos);
            // Buscamos Notas que pueden estar asociadas a la solicitud.
            List<NotaDocumentoRequerido> lNotaDocumentoRequeridos = notaDocumentoRequeridoRepository.findByRequisitoSolicitudId(requisitoSolicitud.getTipoSolicitud().getId());
            List<String> notas = new ArrayList<>();
            for (NotaDocumentoRequerido notaDocumentoRequerido : lNotaDocumentoRequeridos) {
                notas.add(notaDocumentoRequerido.getNota());
            }
            dSolicitudDto.setNotas(notas);
            TiposSolicitud tiposSolicitud = tipoSolicitudRepository.findByCodigo(codigo);
            List<EnlaceTipoSolicitud> enlaces = enlaceTipoSolicitudRepository.findByTiposSolicitud(tiposSolicitud.getId());
            List<EnlacesTiposSolicitud> listaEnlaces = new ArrayList<>();
            for (EnlaceTipoSolicitud enlace : enlaces) {
                EnlacesTiposSolicitud enlacesTiposSolicitud = new EnlacesTiposSolicitud();
                enlacesTiposSolicitud.setNombre(enlace.getNombre());
                enlacesTiposSolicitud.setUrlAcortada(enlace.getUrlAcortada());
                listaEnlaces.add(enlacesTiposSolicitud);
            }
            dSolicitudDto.setEnlaces(listaEnlaces);
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
    public String registrarSolicitud(SolicitudRequestDto datosSolicitud) throws Exception {
        /*
         * Función que se encarga de registrar toda la información de cada solicitud de cualquier tipo.
        */
        Boolean registro = Boolean.FALSE;
        String radicado;
        Solicitudes registroSolicitud = new Solicitudes();
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
            solicitud.setEstado(ESTADO_SOLICITUD.RADICADA.getDescripcion());
            solicitud.setRequiereFirmaDirector(datosSolicitud.getRequiereFirmaDirector());
            solicitud.setIdDirector(datosSolicitud.getIdDirector());
            solicitud.setDocumentoFirmado(datosSolicitud.getOficioPdf());
            registroSolicitud = solicitudesRepository.save(solicitud);

            // Utilizamos la siguiente función para guardar otros datos de la solicitud según su tipo.
            boolean datosTipoSolicitudRegistrados = registrarDatosTipoSolicitud(datosSolicitud, registroSolicitud.getId(), tipoSolicitud.getCodigo());
            
            // Utilizamos la siguiente función para garantizar la firma del estudiante en la solicitud.
            boolean firmaEstudianteRegistrada = registrarFirmaEstudiante(registroSolicitud, datosSolicitud);
            
            // Verificar si todas las operaciones fueron exitosas
            if (datosTipoSolicitudRegistrados && firmaEstudianteRegistrada) {
                registro = Boolean.TRUE;
                radicado = TokenAleatorio.generarCodigoAleatorio();
                registroSolicitud.setRadicado(radicado);
                //solicitudesRepository.save(registroSolicitud);
                logger.info("Se registró correctamente la solicitud.");

                // registrar en el historico
                registrarHistoricoSolicitud(registroSolicitud);
            } else {                
                logger.error("Ocurrió un error al registrar la solicitud.");
                throw new Exception("Error al registrar la solicitud.");
            }
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado al registrar la solicitud.", e);
            throw e;
        }
        if (registro) {            
            // Crear CompletableFutures para cada correo
            CompletableFuture<Void> correoEstudiante = enviarCorreoAsincrono(crearEmailRequest(crearDatosEnvioCorreo(registroSolicitud, DESTINATARIO_CORREO.ESTUDIANTE)));
            CompletableFuture<Void> correoTutor = enviarCorreoAsincrono(crearEmailRequest(crearDatosEnvioCorreo(registroSolicitud, DESTINATARIO_CORREO.TUTOR)));
            CompletableFuture<Void> correoDirector = null;
            if (registroSolicitud.getRequiereFirmaDirector()) {
                correoDirector = enviarCorreoAsincrono(crearEmailRequest(crearDatosEnvioCorreo(registroSolicitud, DESTINATARIO_CORREO.DIRECTOR)));
            }
            return radicado;
        } else {
            logger.info("Error al registrar la solicitud o enviando correo.");
            return null;
        }
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

            case "AP_ECON_INV":
                try {
                    boolean registroApoyoEconomico = registrarApoyoEconimico(idSolicitud, datosSolicitud.getDatosApoyoEconomico());
                    if (registroApoyoEconomico) {
                        logger.info("Se registraron los datos para el apoyo económico correctamente.");
                        registro = true;
                    }
                } catch (Exception e) {
                    logger.error("Ocurrió un error inesperado al guardar los datos de la solicitud para apoyo económico.", e);
                    registro = false;
                    throw e;
                }
                break;

            case "RE_CRED_PR_DOC":
                    try {
                        boolean registroActDocente = registrarActividadesPracticaDocente(idSolicitud, datosSolicitud.getDatosActividadDocenteRequest());
                        if (registroActDocente) {
                            logger.info("Se registraron los datos para el reconocimiento de créditos de práctica docente correctamente.");
                            registro = true;
                        }
                    } catch (Exception e) {
                        logger.error("Ocurrió un error inesperado al guardar los datos de la solicitud para reconocimiento de créditos por práctica docente.", e);
                        registro = false;
                        throw e;
                    }
                    break;
            case "RE_CRED_PAS":
            case "RE_CRED_DIS":
            case "PR_CURS_TEO":
            case "AS_CRED_DO":
            case "RE_CRED_SEM":
            case "AS_CRED_MON":
            case "AS_CRED_MAT":
            case "TG_PREG_POS":
            case "JU_PREG_POS":
            case "EV_ANTE_PRE":
            case "EV_PROD_INT":
            case "EV_INFO_SAB":
            case "PA_COMI_PRO":
            case "OT_ACTI_APO":
            case "RE_CRED_PUB":
                try {
                    boolean registroRecCreditos = registrarReconocimientoCreditos(idSolicitud, datosSolicitud.getDatosReconocimientoCreditos());
                    if (registroRecCreditos) {
                        logger.info("Se registraron los datos para el reconocimiento de créditos correctamente.");
                        registro = true;
                    }
                } catch (Exception e) {
                    logger.error("Ocurrió un error inesperado al guardar los datos de la solicitud para reconocimiento de créditos.", e);
                    registro = false;
                    throw e;
                }
                break;

            case "AV_SEMI_ACT":
                try {
                    boolean registroAvalSeminario = registrarAvalSeminario(idSolicitud, datosSolicitud.getDatosAvalSeminario());
                    if (registroAvalSeminario) {
                        logger.info("Se registraron los datos para el aval seminario actualización correctamente.");
                        registro = true;
                    }
                } catch (Exception e) {
                    logger.error("Ocurrió un error inesperado al guardar los datos de la solicitud para aval seminario actualización.", e);
                    registro = false;
                    throw e;
                }
                break;

            case "AP_ECON_ASI":
                try {
                    boolean registroApoyoEconomicoCongreso = registrarApoyoEconimicoCongreso(idSolicitud, datosSolicitud.getDatosApoyoEconomicoCongreso());
                    if (registroApoyoEconomicoCongreso) {
                        logger.info("Se registraron los datos para el apoyo económico congreso correctamente.");
                        registro = true;
                    }
                } catch (Exception e) {
                    logger.error("Ocurrió un error inesperado al guardar los datos de la solicitud para apoyo económico congreso.", e);
                    registro = false;
                    throw e;
                }
                break;

            case "PA_PUBL_EVE":
                try {
                    boolean registroApoyoEconomicoPubEvento = registrarApoyoEconimicoPublicacionEvento(idSolicitud, datosSolicitud.getDatosApoyoEconomicoPublicacion());
                    if (registroApoyoEconomicoPubEvento) {
                        logger.info("Se registraron los datos para el apoyo económico pago publicación evento correctamente.");
                        registro = true;
                    }
                } catch (Exception e) {
                    logger.error("Ocurrió un error inesperado al guardar los datos de la solicitud para apoyo económico pago publicación evento.", e);
                    registro = false;
                    throw e;
                }
                break;

            case "AV_COMI_PR":
                try {
                    boolean registroAvales = registrarAvalComitePrograma(idSolicitud, datosSolicitud.getDatosAvalComite());
                    if (registroAvales) {
                        logger.info("Se registraron los datos para el aval comite de programa correctamente.");
                        registro = true;
                    }
                } catch (Exception e) {
                    logger.error("Ocurrió un error inesperado al guardar los datos del aval de comite de programa.", e);
                    registro = false;
                    throw e;
                }
                break;

            case "SO_BECA":
            case "SO_DESC":
                try {
                    boolean registroBeca = registrarSolicitudBecaDescuento(idSolicitud, datosSolicitud.getDatosSolicitudBeca());
                    if (registroBeca) {
                        logger.info("Se registraron los datos para la solicitud de beca correctamente.");
                        registro = true;
                    }
                } catch (Exception e) {
                    logger.error("Ocurrió un error inesperado al guardar los datos de la solicitud de beca.", e);
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
        List<Solicitudes> solicitudesPendientes = solicitudesRepository.findAllByIdTutorOrderByFechaCreacionAsc(infoTutor.getId(), ESTADO_SOLICITUD.RADICADA.getDescripcion());
        for (Solicitudes solicitud : solicitudesPendientes) {
            TiposSolicitud tipoSolicitud = tipoSolicitudRepository.findById(solicitud.getTipoSolicitud().getId()).get();
            InformacionPersonalDto estudiante = gestionDocentesEstudiantesService
                    .obtenerInformacionEstudiantePorId(solicitud.getIdEstudiante());
            SolicitudPendientesAval solicitudPendiente = new SolicitudPendientesAval();
            solicitudPendiente.setIdSolicitud(solicitud.getId());
            solicitudPendiente.setRadicado(solicitud.getRadicado());
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
                datosComun.setRadicado(solicitud.getRadicado());
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
                datosComun.setNumPaginaTutor(firmaSolicitud.getNumPaginaTutor());
                datosComun.setNumPaginaDirector(firmaSolicitud.getNumPaginaDirector());
                datosComun.setPosXTutor(firmaSolicitud.getPosXTutor());
                datosComun.setPosYTutor(firmaSolicitud.getPosYTutor());
                datosComun.setPosXDirector(firmaSolicitud.getPosXDirector());
                datosComun.setPosYDirector(firmaSolicitud.getPosYDirector());
                datosComun.setOficioPdf(solicitud.getDocumentoFirmado());
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

                    case "AP_ECON_INV":
                        ApoyoEconomicoInvestigacion apoyoEconomicoInvestigacion = apoyoEconomicoInvestigacionRepository.findBySolicitud(solicitud);
                        if (apoyoEconomicoInvestigacion != null){
                            ApoyoEconomicoRequest responseApoyoEconomico = apoyoEconomicoMapper.entidadAdto(apoyoEconomicoInvestigacion);
                            InformacionPersonalDto infoDocente = gestionDocentesEstudiantesService.obtenerTutor(responseApoyoEconomico.getIdDirectorGrupo().toString());
                            responseApoyoEconomico.setNombreDirectorGrupo(infoDocente.obtenerNombreCompleto());
                            List<DocumentosApoyoEconomico> documentosApoyosEconomicos = documentosApoyoEconomicoRepository.
                                    findAllByApoyoEconomicoInvestigacion(apoyoEconomicoInvestigacion);
                            List<String> documentos = new ArrayList<>();
                            for (DocumentosApoyoEconomico documento : documentosApoyosEconomicos) {
                                documentos.add(documento.getDocumento());
                            }
                            responseApoyoEconomico.setDocumentosAdjuntos(documentos);
                            response.setDatosApoyoEconomico(responseApoyoEconomico);
                        }
                        break;

                    case "RE_CRED_PR_DOC":
                        List<ActividadesRealizadasPracticaDocente> actividadPracticaDocente = 
                            actividadesRealizadasPracticaDocenteRepository.findBySolicitud(solicitud);                        
                        List<DatosActividadDocenteResponse> infoActividadesDocentes = new ArrayList<>();
                        
                        for (ActividadesRealizadasPracticaDocente actividad : actividadPracticaDocente) {
                            DatosActividadDocenteResponse datosActividadDocente = new DatosActividadDocenteResponse();
                            datosActividadDocente.setNombreActividad(actividad.getSubTiposSolicitud().getNombre());
                            datosActividadDocente.setHorasReconocer(actividad.getHorasReconocer());

                            //Agregamos los documentos de la actividad 
                            List<DocumentosActividadesRealizadas> documentos = 
                                documentosActividadesRealizadasRepository
                                    .findByActividadRealizadaAndSubTiposSolicitud(actividad, actividad.getSubTiposSolicitud());
                            List<String> documentosAdjuntosActRealizadas = new ArrayList<>();
                            for (DocumentosActividadesRealizadas documento : documentos) {
                                documentosAdjuntosActRealizadas.add(documento.getDocumento());
                            }
                            datosActividadDocente.setDocumentos(documentosAdjuntosActRealizadas);

                            //Agregamos los enlaces de la actividad
                            List<EnlacesActividadesRealizadas> enlaces = 
                                enlacesActividadesRealizadasRepository
                                    .findByActividadRealizadaAndSubTiposSolicitud(actividad, actividad.getSubTiposSolicitud());
                            List<String> enlacesAdjuntosActRealizadas = new ArrayList<>();
                            for (EnlacesActividadesRealizadas enlace : enlaces) {
                                enlacesAdjuntosActRealizadas.add(enlace.getEnlace());
                            }
                            datosActividadDocente.setEnlaces(enlacesAdjuntosActRealizadas);
                            infoActividadesDocentes.add(datosActividadDocente);
                        }                        
                        response.setDatosActividadDocente(infoActividadesDocentes);
                        break;

                    case "RE_CRED_PAS":
                    case "RE_CRED_DIS":
                    case "PR_CURS_TEO":
                    case "AS_CRED_DO":
                    case "RE_CRED_SEM":
                    case "AS_CRED_MON":
                    case "AS_CRED_MAT":
                    case "TG_PREG_POS":
                    case "JU_PREG_POS":
                    case "EV_ANTE_PRE":
                    case "EV_PROD_INT":
                    case "EV_INFO_SAB":
                    case "PA_COMI_PRO":
                    case "OT_ACTI_APO":
                    case "RE_CRED_PUB":
                        ReconocimientoCreditos reconocimientoCreditos = reconocimientoCreditosRepository
                                    .findBySolicitudAndTipoReconocimiento(solicitud, solicitud.getTipoSolicitud().getCodigo());
                        if (reconocimientoCreditos != null){
                            ReconocimientoCreditosRequest reconocimientoCreditosRequest = new ReconocimientoCreditosRequest();
                            List<DocumentosRecCreditos> docsRecCreditos = documentosRecCreditosRepository
                                    .findAllByReconocimientoCreditos(reconocimientoCreditos);
                            List<String> documentos = new ArrayList<>();
                            for (DocumentosRecCreditos documento : docsRecCreditos) {
                                documentos.add(documento.getDocumento());
                            }
                            reconocimientoCreditosRequest.setDocumentosAdjuntos(documentos);
                            response.setDatosReconocimientoCreditos(reconocimientoCreditosRequest);
                        }
                        break;
                    
                    case "AV_SEMI_ACT":
                        AvalSeminarioActualizacion avalSeminarioActualizacion = avalSeminarioActRepository
                                    .findBySolicitud(solicitud);
                        if (avalSeminarioActualizacion != null){
                            AvalSeminarioActRequest avalSeminarioActRequest = new AvalSeminarioActRequest();
                            List<DocumentosAvalSeminarioAct> docsAvalSeminario = dAvalSeminarioActRepository
                                    .findAllByAvalSeminarioActualizacion(avalSeminarioActualizacion);
                            List<String> documentos = new ArrayList<>();
                            for (DocumentosAvalSeminarioAct documento : docsAvalSeminario) {
                                documentos.add(documento.getDocumento());
                            }
                            avalSeminarioActRequest.setDocumentosAdjuntos(documentos);
                            response.setDatosAvalSeminario(avalSeminarioActRequest);
                        }
                        break;

                    case "AP_ECON_ASI":
                        ApoyoEconomicoCongreso apoyoEconomicoCongreso = apoyoEconomicoCongresoRepository.findBySolicitud(solicitud);
                        if (apoyoEconomicoCongreso != null){
                            ApoyoEconomicoCongresoRequest responseApoyoEconomicoCongreso = apoyoEconomicoCongresoMapper.entidadAdto(apoyoEconomicoCongreso);
                            InformacionPersonalDto infoDocente = gestionDocentesEstudiantesService
                                            .obtenerTutor(responseApoyoEconomicoCongreso.getIdDirectorGrupo().toString());
                            responseApoyoEconomicoCongreso.setNombreDirectorGrupo(infoDocente.obtenerNombreCompleto());
                            List<DocumentosApoyoEconomicoCongreso> documentosApoyosEconomicoCongreso = documentosApoyoEconomicoCongresoRepository.
                                    findAllByApoyoEconomicoCongreso(apoyoEconomicoCongreso);
                            List<String> documentos = new ArrayList<>();
                            for (DocumentosApoyoEconomicoCongreso documento : documentosApoyosEconomicoCongreso) {
                                documentos.add(documento.getDocumento());
                            }
                            responseApoyoEconomicoCongreso.setDocumentosAdjuntos(documentos);
                            response.setDatosApoyoEconomicoCongreso(responseApoyoEconomicoCongreso);
                        }
                        break;

                    case "PA_PUBL_EVE":
                        ApoyoEconomicoPublicacionEvento apoyoEconomicoPublicacionEvento = apoyoEconomicoPublicacionEventoRepository
                                    .findBySolicitud(solicitud);
                        if (apoyoEconomicoPublicacionEvento != null){
                            ApoyoEconomicoPublicacionEventoRequest responseApoyoEconomicoPubEvento = apoyoEconomicoPublicacionEventoMapper.entidadAdto(apoyoEconomicoPublicacionEvento);
                            InformacionPersonalDto infoDocente = gestionDocentesEstudiantesService
                                            .obtenerTutor(responseApoyoEconomicoPubEvento.getIdDirectorGrupo().toString());
                            responseApoyoEconomicoPubEvento.setNombreDirectorGrupo(infoDocente.obtenerNombreCompleto());
                            List<DocumentosApoyoEconomicoPublicacionEvento> documentosApoyosEconomicoPubEvento = documentosApoyoEconomicoPublicacionEventoRepository.
                                    findAllByApoyoEconomicoPublicacionEvento(apoyoEconomicoPublicacionEvento);
                            List<String> documentos = new ArrayList<>();
                            for (DocumentosApoyoEconomicoPublicacionEvento documento : documentosApoyosEconomicoPubEvento) {
                                documentos.add(documento.getDocumento());
                            }
                            responseApoyoEconomicoPubEvento.setDocumentosAdjuntos(documentos);
                            response.setDatosApoyoEconomicoPublicacion(responseApoyoEconomicoPubEvento);
                        }
                        break;

                    case "AV_COMI_PR":
                        List<AvalComitePrograma> avalesComite = 
                            avalComiteProgramaRepository.findBySolicitud(solicitud);                        
                        List<DatosAvalComiteResponse> infoAvales = new ArrayList<>();
                        
                        for (AvalComitePrograma aval : avalesComite) {
                            DatosAvalComiteResponse datosAvales = new DatosAvalComiteResponse();
                            datosAvales.setNombreActividad(aval.getSubTiposSolicitud().getNombre());
                            datosAvales.setHorasReconocer(aval.getHorasReconocer());
                            infoAvales.add(datosAvales);
                        }
                        response.setDatosAvalComite(infoAvales);
                        break;     
                        
                    case "SO_BECA":
                    case "SO_DESC":
                        SolicitudBecaDescuento solicitudBeca = 
                            solicitudBecaRepository.findBySolicitud(solicitud);                        
                        SolicitudBecaRequest solicitudBecaDto = new SolicitudBecaRequest();
                        solicitudBecaDto.setMotivo(solicitudBeca.getMotivo());
                        solicitudBecaDto.setTipo(solicitudBeca.getTipo());
                        solicitudBecaDto.setFormatoSolicitudBeca(solicitudBeca.getFormatoSolicitudBeca());
                        response.setDatoSolicitudBeca(solicitudBecaDto);
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
    private Boolean registrarFirmaEstudiante(Solicitudes solicitud, SolicitudRequestDto datosSolicitud) throws Exception{
        try {
            FirmaSolicitud firmaSolicitud = new FirmaSolicitud();
            firmaSolicitud.setSolicitud(solicitud);
            firmaSolicitud.setFirmaEstudiante(datosSolicitud.getFirmaEstudiante());
            firmaSolicitud.setNumPaginaTutor(datosSolicitud.getNumPaginaTutor());
            firmaSolicitud.setPosXTutor(datosSolicitud.getPosXTutor());
            firmaSolicitud.setPosYTutor(datosSolicitud.getPosYTutor());
            firmaSolicitud.setNumPaginaDirector(datosSolicitud.getNumPaginaDirector());
            firmaSolicitud.setPosXDirector(datosSolicitud.getPosXDirector());
            firmaSolicitud.setPosYDirector(datosSolicitud.getPosYDirector());
            firmaSolicitudRepository.save(firmaSolicitud);
            return  Boolean.TRUE;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado al guardar la firma del estudiante.", e);            
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean registrarFirmasPendientes(DatosAvalarSolicitudDto dAvalarSolicitudDto) throws Exception {        
        Boolean registroFirma = Boolean.FALSE;
        Solicitudes solicitud = solicitudesRepository.findById(dAvalarSolicitudDto.getIdSolicitud()).get();
        FirmaSolicitud firmas = firmaSolicitudRepository.findBySolicitud(solicitud);
        Boolean firmaTutor = dAvalarSolicitudDto.getFirmaTutor();
        Boolean firmaDirector = dAvalarSolicitudDto.getFirmaDirector();
        if (solicitud.getRequiereFirmaDirector()){
            if (firmaTutor && firmaDirector) {
                firmas.setFirmaTutor(firmaTutor);
                firmas.setFirmaDirector(firmaDirector);
                firmaSolicitudRepository.save(firmas);                
                registroFirma = Boolean.TRUE;                
            } else if (firmaTutor){
                firmas.setFirmaTutor(firmaTutor);
                firmaSolicitudRepository.save(firmas);
                registroFirma = Boolean.TRUE;                
            } else if (firmaDirector){
                firmas.setFirmaDirector(firmaDirector);
                firmaSolicitudRepository.save(firmas);
                registroFirma = Boolean.TRUE;                
            }
        } else {
            if (firmaTutor) {
                firmas.setFirmaTutor(firmaTutor);
                firmaSolicitudRepository.save(firmas);                
                registroFirma = Boolean.TRUE;
            }
        }
        if ((firmas.getFirmaTutor() && firmas.getFirmaDirector()) || 
            (firmas.getFirmaTutor() && !solicitud.getRequiereFirmaDirector())) {
            registrarHistoricoSolicitud(solicitud);
            solicitud.setDocumentoFirmado(dAvalarSolicitudDto.getDocumentoPdfSolicitud());
            solicitud.setEstado(ESTADO_SOLICITUD.AVALADA.getDescripcion());
            solicitudesRepository.save(solicitud);
            registrarHistoricoSolicitud(solicitud);
            DatosEnvioCorreo datosCorreo = new DatosEnvioCorreo();
            datosCorreo.setTipoSolicitud(solicitud.getTipoSolicitud().getNombre());
            datosCorreo.setDirigidoA(DESTINATARIO_CORREO.COORDINADOR.getDescripcion());
            Map<String, String> documentos = Base64.obtenerBase64(solicitud.getDocumentoFirmado());
            datosCorreo.setDocumentos(documentos);
            InformacionPersonalDto estudiante = gestionDocentesEstudiantesService
                    .obtenerInformacionEstudiantePorId(solicitud.getIdEstudiante());
            datosCorreo.setNombreEstudiante(estudiante.obtenerNombreCompleto());
            InformacionPersonalDto infoTutor = gestionDocentesEstudiantesService.obtenerTutor(solicitud.getIdTutor().toString());
            datosCorreo.setNombreTutor(infoTutor.obtenerNombreCompleto());
            if (solicitud.getRequiereFirmaDirector()) {
                InformacionPersonalDto infoDirector = gestionDocentesEstudiantesService.obtenerTutor(solicitud.getIdDirector().toString());
                datosCorreo.setNombreDirector(infoDirector.obtenerNombreCompleto());
            } else {
                datosCorreo.setNombreDirector(null);
            }
            CompletableFuture<Void> correoCoordiandor = enviarCorreoAsincrono(crearEmailRequest(crearDatosEnvioCorreo(solicitud, DESTINATARIO_CORREO.COORDINADOR)));
        } else {
            registrarHistoricoSolicitud(solicitud);
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
                datosCursarAsignatura.setEstado(ESTADO_SOLICITUD.RADICADA.getDescripcion());
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
            AvalPasantiaInvestigacion avalPasantiaInvestigacion = avalPasantiaInvMapper.dtoToEntity(avalPasantiaInvRequest);
            avalPasantiaInvestigacion.setSolicitud(solicitud);
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

    private boolean registrarApoyoEconimico(Integer idSolicitud, ApoyoEconomicoRequest apoyoEconomicoRequest) {
        boolean registro = false;
        try{
            Solicitudes solicitud = solicitudesRepository.findById(idSolicitud).get();
            ApoyoEconomicoInvestigacion apoyoEconomicoInvestigacion = apoyoEconomicoMapper.dtoToEntity(apoyoEconomicoRequest);
            apoyoEconomicoInvestigacion.setSolicitud(solicitud);
            apoyoEconomicoInvestigacion = apoyoEconomicoInvestigacionRepository.save(apoyoEconomicoInvestigacion);

            // Procedemos a guardar los ducumentos adjuntos de la solicitud
            for (String documento : apoyoEconomicoRequest.getDocumentosAdjuntos()) {
                DocumentosApoyoEconomico documentosApoyoEconomico = new DocumentosApoyoEconomico();
                documentosApoyoEconomico.setApoyoEconomicoInvestigacion(apoyoEconomicoInvestigacion);
                documentosApoyoEconomico.setDocumento(documento);                
                documentosApoyoEconomicoRepository.save(documentosApoyoEconomico);
            }
            registro = true;
        } catch (Exception e){
            logger.error("Ocurrió un error al intentar guardar los datos de apoyo economico investigación.", e);
            registro = false;
        }
        return registro;
    }

    private boolean registrarReconocimientoCreditos(Integer idSolicitud, ReconocimientoCreditosRequest recCreditosPasantiaRequest) {
        boolean registro = false;
        try{
            Solicitudes solicitud = solicitudesRepository.findById(idSolicitud).get();
            ReconocimientoCreditos reconocimientoCreditos = new ReconocimientoCreditos();
            reconocimientoCreditos.setSolicitud(solicitud);
            reconocimientoCreditos.setTipoReconocimiento(solicitud.getTipoSolicitud().getCodigo());
            reconocimientoCreditos = reconocimientoCreditosRepository.save(reconocimientoCreditos);

            // Procedemos a guardar los ducumentos adjuntos de la solicitud
            for (String documento : recCreditosPasantiaRequest.getDocumentosAdjuntos()) {
                DocumentosRecCreditos docRecCreditos = new DocumentosRecCreditos();
                docRecCreditos.setReconocimientoCreditos(reconocimientoCreditos);
                docRecCreditos.setDocumento(documento);
                documentosRecCreditosRepository.save(docRecCreditos);
            }
            registro = true;
        } catch (Exception e){
            logger.error("Ocurrió un error al intentar guardar los datos de reconocimiento de créditos.", e);
            registro = false;
        }
        return registro;
    }

    private boolean registrarAvalSeminario(Integer idSolicitud, AvalSeminarioActRequest avalSeminarioActRequest) {
        boolean registro = false;
        try{
            Solicitudes solicitud = solicitudesRepository.findById(idSolicitud).get();
            AvalSeminarioActualizacion avalSeminarioActualizacion = new AvalSeminarioActualizacion();
            avalSeminarioActualizacion.setSolicitud(solicitud);            
            avalSeminarioActualizacion = avalSeminarioActRepository.save(avalSeminarioActualizacion);

            // Procedemos a guardar los ducumentos adjuntos de la solicitud
            for (String documento : avalSeminarioActRequest.getDocumentosAdjuntos()) {
                DocumentosAvalSeminarioAct documentosAvalSeminarioAct = new DocumentosAvalSeminarioAct();
                documentosAvalSeminarioAct.setAvalSeminarioActualizacion(avalSeminarioActualizacion);
                documentosAvalSeminarioAct.setDocumento(documento);
                dAvalSeminarioActRepository.save(documentosAvalSeminarioAct);
            }
            registro = true;
        } catch (Exception e){
            logger.error("Ocurrió un error al intentar guardar los datos de aval seminario actualización.", e);
            registro = false;
        }
        return registro;
    }

    private boolean registrarApoyoEconimicoCongreso(Integer idSolicitud, ApoyoEconomicoCongresoRequest apoyoEconomicoCongresoRequest) {
        boolean registro = false;
        try{
            Solicitudes solicitud = solicitudesRepository.findById(idSolicitud).get();
            ApoyoEconomicoCongreso apoyoEconomicoCongreso = apoyoEconomicoCongresoMapper.dtoToEntity(apoyoEconomicoCongresoRequest);
            apoyoEconomicoCongreso.setSolicitud(solicitud);
            apoyoEconomicoCongreso = apoyoEconomicoCongresoRepository.save(apoyoEconomicoCongreso);

            // Procedemos a guardar los ducumentos adjuntos de la solicitud
            for (String documento : apoyoEconomicoCongresoRequest.getDocumentosAdjuntos()) {
                DocumentosApoyoEconomicoCongreso documentosApoyoEconomicoCongreso = new DocumentosApoyoEconomicoCongreso();
                documentosApoyoEconomicoCongreso.setApoyoEconomicoCongreso(apoyoEconomicoCongreso);
                documentosApoyoEconomicoCongreso.setDocumento(documento);                
                documentosApoyoEconomicoCongresoRepository.save(documentosApoyoEconomicoCongreso);
            }
            registro = true;
        } catch (Exception e){
            logger.error("Ocurrió un error al intentar guardar los datos de apoyo económico asistencia al congreso.", e);
            registro = false;
        }
        return registro;
    }

    private boolean registrarApoyoEconimicoPublicacionEvento(Integer idSolicitud, ApoyoEconomicoPublicacionEventoRequest apoyoEconomicoPublicacionEventoRequest) {
        boolean registro = false;
        try{
            Solicitudes solicitud = solicitudesRepository.findById(idSolicitud).get();
            ApoyoEconomicoPublicacionEvento apoyoEconomicoPublicacionEvento = apoyoEconomicoPublicacionEventoMapper.dtoToEntity(apoyoEconomicoPublicacionEventoRequest);
            apoyoEconomicoPublicacionEvento.setSolicitud(solicitud);
            apoyoEconomicoPublicacionEvento = apoyoEconomicoPublicacionEventoRepository.save(apoyoEconomicoPublicacionEvento);

            // Procedemos a guardar los ducumentos adjuntos de la solicitud
            for (String documento : apoyoEconomicoPublicacionEventoRequest.getDocumentosAdjuntos()) {
                DocumentosApoyoEconomicoPublicacionEvento documentosApoyoEconomicoPubEvento = new DocumentosApoyoEconomicoPublicacionEvento();
                documentosApoyoEconomicoPubEvento.setApoyoEconomicoPublicacionEvento(apoyoEconomicoPublicacionEvento);
                documentosApoyoEconomicoPubEvento.setDocumento(documento);
                documentosApoyoEconomicoPublicacionEventoRepository.save(documentosApoyoEconomicoPubEvento);
            }
            registro = true;
        } catch (Exception e){
            logger.error("Ocurrió un error al intentar guardar los datos de apoyo económico pago publicación evento.", e);
            registro = false;
        }
        return registro;
    }

    private boolean registrarActividadesPracticaDocente(Integer idSolicitud, List<DatosActividadDocenteRequest> infoActividadesDocente) {
        boolean registro = false;
        try{            
            Solicitudes solicitud = solicitudesRepository.findById(idSolicitud).get();            
            
            for (DatosActividadDocenteRequest actividadDocente : infoActividadesDocente) {

                ActividadesRealizadasPracticaDocente actPracticaDocente = new ActividadesRealizadasPracticaDocente();
                actPracticaDocente.setSolicitud(solicitud);
                SubTiposSolicitud subtipo = subTiposSolicitudRepository.findByCodigo(actividadDocente.getCodigoSubtipo());
                actPracticaDocente.setSubTiposSolicitud(subtipo);
                if (actividadDocente.getIntensidadHoraria() != null) {
                    actPracticaDocente.setIntensidadHoraria(actividadDocente.getIntensidadHoraria());
                }
                actPracticaDocente.setHorasReconocer(actividadDocente.getHorasReconocer());

                // Guardar datos de la entidad
                actPracticaDocente = actividadesRealizadasPracticaDocenteRepository.save(actPracticaDocente);

                // Procedemos a guardar los ducumentos adjuntos de la solicitud
                for (String documento : actividadDocente.getDocumentosAdjuntos()) {
                    DocumentosActividadesRealizadas documentos = new DocumentosActividadesRealizadas();
                    documentos.setActividadRealizada(actPracticaDocente);
                    documentos.setSubTiposSolicitud(subtipo);
                    documentos.setDocumento(documento);
                    documentosActividadesRealizadasRepository.save(documentos);
                }

                // Procedemos a guardar los enlaces adjuntos de la solicitud
                for (String enlace : actividadDocente.getEnlacesAdjuntos()) {
                    EnlacesActividadesRealizadas enlaces = new EnlacesActividadesRealizadas();
                    enlaces.setActividadRealizada(actPracticaDocente);
                    enlaces.setSubTiposSolicitud(subtipo);
                    enlaces.setEnlace(enlace);
                    enlacesActividadesRealizadasRepository.save(enlaces);
                }
            }            
            registro = true;
        } catch (Exception e){
            logger.error("Ocurrió un error al intentar guardar los datos de la práctiva actividad docente.", e);
            registro = false;
        }
        return registro;
    }

    private boolean registrarAvalComitePrograma(Integer idSolicitud, List<AvalComiteRequest> datosAvalComite) {
        boolean registro = false;
        try{            
            Solicitudes solicitud = solicitudesRepository.findById(idSolicitud).get();            
            
            for (AvalComiteRequest avales : datosAvalComite) {

                AvalComitePrograma aval = new AvalComitePrograma();
                aval.setSolicitud(solicitud);
                SubTiposSolicitud subtipo = subTiposSolicitudRepository.findByCodigo(avales.getCodigoSubtipo());
                aval.setSubTiposSolicitud(subtipo);                
                aval.setIntensidadHoraria(avales.getIntensidadHoraria());                
                aval.setHorasReconocer(avales.getHorasReconocer());

                // Guardar datos de la entidad
                aval = avalComiteProgramaRepository.save(aval);
                
            }            
            registro = true;
        } catch (Exception e){
            logger.error("Ocurrió un error al intentar guardar los datos del aval comite de programa.", e);
            registro = false;
        }
        return registro;
    }

    private void registrarHistoricoSolicitud(Solicitudes solicitud) {
        HistorialEstadoSolicitudes historico = new HistorialEstadoSolicitudes();
        historico.setSolicitud(solicitud);
        String estado = validarEstadoHistorico(solicitud);
        historico.setEstado(estado);
        if (!solicitud.getEstado().equals(ESTADO_SOLICITUD.NO_AVALADA.getDescripcion())
                && !solicitud.getEstado().equals(ESTADO_SOLICITUD.NO_APROBADA.getDescripcion())
                && !solicitud.getEstado().equals(ESTADO_SOLICITUD.RECHAZADA.getDescripcion())
                && !estado.equals("Avalada Tutor") 
                && !estado.equals("Avalada Director")
                && !estado.equals("En Coordinación")) {
            historico.setPdfBase64(solicitud.getDocumentoFirmado());
        }                
        historico.setDescripcion(ESTADO_DESCRIPCION.getDescripcionPorCodigo(estado.toUpperCase().replace(" ", "_")));
        historico.setComentarios(solicitud.getComentario());
        historialEstadoSolicitudesRepository.save(historico);
    }

    @Override
    public List<SolicitudHistoricoResponse> obtenerHistorialSeguimiento(String radicado) {
        List<SolicitudHistoricoResponse> response = new ArrayList<>();
        Solicitudes solicitud = solicitudesRepository
                .findByRadicado(radicado).orElse(null);
        if(solicitud != null){
            List<HistorialEstadoSolicitudes> historial = historialEstadoSolicitudesRepository
                                .findBySolicitudOrderByFechaCreacionAsc(solicitud);
            SolicitudHistoricoResponse info;
            for (HistorialEstadoSolicitudes historico : historial) {
                info = new SolicitudHistoricoResponse();
                info.setRadicado(radicado);
                info.setEstadoSolicitud(historico.getEstado());
                info.setFechaHora(historico.getFechaCreacion().toString());
                info.setPdfBase64(historico.getPdfBase64());
                info.setDescripcion(historico.getDescripcion());
                info.setComentarios(historico.getComentarios());
                response.add(info);
            }            
               
        }
        return response;
    }

    @Override
    public List<SolicitudPendientesAval> obtenerDatosSolicitudPendientesCoordinador(String estado) throws Exception {
        List<SolicitudPendientesAval> solicitudes = new ArrayList<>();
        List<Solicitudes> solicitudesPendientes = solicitudesRepository.
                    findByEstado(ESTADO_SOLICITUD.getDescripcionPorCodigo(estado));
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

    private boolean registrarSolicitudBecaDescuento(Integer idSolicitud, SolicitudBecaRequest datosSolicitudBeca) {
        boolean registro = false;
        try{            
            Solicitudes solicitud = solicitudesRepository.findById(idSolicitud).get();
            SolicitudBecaDescuento beca = new SolicitudBecaDescuento();
            beca.setSolicitud(solicitud);
            beca.setTipo(datosSolicitudBeca.getTipo());
            if (datosSolicitudBeca.getMotivo() != null) {
                beca.setMotivo(datosSolicitudBeca.getMotivo());
            }
            beca.setFormatoSolicitudBeca(datosSolicitudBeca.getFormatoSolicitudBeca());

            // Guardar datos de la entidad
            beca = solicitudBecaRepository.save(beca);
            registro = true;        
        } catch (Exception e){
            logger.error("Ocurrió un error al intentar guardar los datos de la solicitud de beca.", e);
            registro = false;
        }
        return registro;
    }

    @Override
    public boolean rechazarSolicitud(RechazarSolicitudRequest rechazarSolicitudRequest) throws Exception {
        Optional<Solicitudes> optionalSolicitud = solicitudesRepository.findById(rechazarSolicitudRequest.getIdSolicitud());
        InformacionPersonalDto tutor = gestionDocentesEstudiantesService.obtenerTutor(rechazarSolicitudRequest.getEmailRevisor());
        if (optionalSolicitud.isPresent()){
            Solicitudes solicitud = optionalSolicitud.get();
            String mensajeComentario = tutor.obtenerNombreCompleto() + " rechazó la solicutd indicando lo siguiente: ";
            mensajeComentario+=rechazarSolicitudRequest.getComentario();
            solicitud.setComentario(mensajeComentario);
            solicitud.setEstado(ESTADO_SOLICITUD.getDescripcionPorCodigo(rechazarSolicitudRequest.getEstado()));
            solicitud.setIdRevisor(tutor.getId());
            solicitudesRepository.save(solicitud);
            registrarHistoricoSolicitud(solicitud);
            return true;
        } else {
            return false;
        }
        
    }

    private String validarEstadoHistorico(Solicitudes solicitud) {
        String estado = solicitud.getEstado();
        FirmaSolicitud firmas = firmaSolicitudRepository.findBySolicitud(solicitud);
        List<HistorialEstadoSolicitudes> historico = historialEstadoSolicitudesRepository.findBySolicitudOrderByFechaCreacionAsc(solicitud);
        if (estado.equals(ESTADO_SOLICITUD.RADICADA.getDescripcion())) {
            if (firmas.getFirmaTutor() != null && firmas.getFirmaTutor()  
                && firmas.getFirmaDirector()!=null && !firmas.getFirmaDirector()) {
                estado = "Avalada Tutor";
            } else if (firmas.getFirmaDirector() != null && firmas.getFirmaDirector() 
                && firmas.getFirmaTutor() != null && !firmas.getFirmaTutor()) {
                estado = "Avalada Director";
            } else {
                String estadoFinal = "";
                if (historico.size() > 1){
                    estadoFinal = historico.get(historico.size() - 1).getEstado();
                    if (estadoFinal.equals("Avalada Tutor")){
                        estado = "Avalada Director";
                    } else if (estadoFinal.equals("Avalada Director")){
                        estado = "Avalada Tutor";
                    }
                }
            }
        } else if(estado.equals(ESTADO_SOLICITUD.AVALADA.getDescripcion())){
            estado = "En Coordinación";
        } else if (estado.equals(ESTADO_SOLICITUD.NO_AVALADA.getDescripcion())) {
            estado = solicitud.getIdTutor().equals(solicitud.getIdRevisor()) ? "No Avalada Tutor"
                    : "No Avalada Director";
        } else if (estado.equals(ESTADO_SOLICITUD.RECHAZADA.getDescripcion())) {
            estado = "Rechazada Coordinador";
        }
        return estado;
    }  

    private EmailRequest crearEmailRequest(DatosEnvioCorreo datosCorreo) {        
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setDocumentos(new HashMap<>());
        ArrayList<String> correos = new ArrayList<>();
        String asunto = "";
        String mensaje = "";        
        if (datosCorreo.getDirigidoA()==DESTINATARIO_CORREO.ESTUDIANTE.getDescripcion()){
            correos.add(datosCorreo.getCorreoEstudiante());
            asunto = "Confirmación de radicación de solicitud";
            mensaje = "Estimado/a " + datosCorreo.getNombreEstudiante() + ".<br>" 
                +  "Le informamos que su solicitud ha sido registrada satisfactoriamente en nuestro sistema.<br>"
                + "El código de seguimiento de su solicitud es: " + datosCorreo.getRadicado() + ".<br>"
                + "Puede utilizar este código para consultar el estado de su trámite en cualquier momento.<br>";            
        } else if (datosCorreo.getDirigidoA()==DESTINATARIO_CORREO.TUTOR.getDescripcion()) {
            correos.add(datosCorreo.getCorreoTutor());        
            asunto = "Solicitud de aval para " + datosCorreo.getNombreEstudiante();
            mensaje = "Estimado/a " + datosCorreo.getNombreTutor() + ".<br>" +
                "Por medio de este correo, le informo que " + datosCorreo.getNombreEstudiante() + " ha generado la solicitud de " + datosCorreo.getTipoSolicitud() + ".<br>" +
                "Solicitamos su amable colaboración para revisar y firmar la solicitud.<br>" +
                "<br>Agradecemos su pronta atención a este asunto.";
        } else if (datosCorreo.getDirigidoA()==DESTINATARIO_CORREO.DIRECTOR.getDescripcion()) {
            correos.add(datosCorreo.getCorreoDirector());        
            asunto = "Solicitud de aval para " + datosCorreo.getNombreEstudiante();
            mensaje = "Estimado/a " + datosCorreo.getNombreDirector() + ".<br>" +
                "Por medio de este correo, le informo que " + datosCorreo.getNombreEstudiante() + " ha generado la solicitud de " + datosCorreo.getTipoSolicitud() + ".<br>" +
                "Solicitamos su amable colaboración para revisar y firmar la solicitud.<br>" +
                "<br>Agradecemos su pronta atención a este asunto.";                
        }else if (datosCorreo.getDirigidoA()==DESTINATARIO_CORREO.COORDINADOR.getDescripcion()){
            correos.add(datosCorreo.getCorreoCoordiandor());        
            asunto = "Solicitud de aval a coordinación";
            mensaje = "Estimado/a " + datosCorreo.getNombreCoordinador() + ".<br>" +
                "Por medio de la presente, se le informa que se ha registrado en el sistema una solicitud de " + datosCorreo.getTipoSolicitud() + " a nombre de " + datosCorreo.getNombreEstudiante() + ".<br>";
                if (datosCorreo.getNombreDirector() != null){
                    mensaje +="Esta solicitud ha sido debidamente firmada por el tutor " + datosCorreo.getNombreTutor() + " y el director " + datosCorreo.getNombreDirector() + ".<br>";
                } else {
                    mensaje +="Esta solicitud ha sido debidamente firmada por el tutor " + datosCorreo.getNombreTutor() + ".<br>";
                }
                mensaje+="<br>" +
                "Solicitamos su amable autorización para continuar con el trámite correspondiente.<br>" +
                "<br>Agradecemos su atención a este asunto.";
        }
        emailRequest.setCorreos(correos);
        emailRequest.setAsunto(asunto);
        emailRequest.setMensaje(mensaje);        
        return emailRequest;        
    }

    // Método asíncrono para enviar el correo
    @Async
    public CompletableFuture<Void> enviarCorreoAsincrono(EmailRequest emailRequest) {
        logger.info("Se va enviar correo de la solicitud radicada.");
        return CompletableFuture.runAsync(() -> {
            try {
                mensajeriaService.enviarEmail(emailRequest);
                logger.info("Envio correo exitoso.");
            } catch (Exception e) {
                logger.error("Error al enviar correo", e);
            }
        });
    }

    private DatosEnvioCorreo crearDatosEnvioCorreo(Solicitudes registroSolicitud, DESTINATARIO_CORREO destinatario){
        DatosEnvioCorreo datosCorreo = new DatosEnvioCorreo();
        datosCorreo.setTipoSolicitud(registroSolicitud.getTipoSolicitud().getNombre());
        datosCorreo.setRadicado(registroSolicitud.getRadicado());        
        InformacionPersonalDto estudiante = gestionDocentesEstudiantesService
                .obtenerInformacionEstudiantePorId(registroSolicitud.getIdEstudiante());
        datosCorreo.setNombreEstudiante(estudiante.obtenerNombreCompleto());
        InformacionPersonalDto infoTutor = gestionDocentesEstudiantesService.obtenerTutor(registroSolicitud.getIdTutor().toString());
        datosCorreo.setNombreTutor(infoTutor.obtenerNombreCompleto());
        if (registroSolicitud.getRequiereFirmaDirector()) {
            InformacionPersonalDto infoDirector = gestionDocentesEstudiantesService.obtenerTutor(registroSolicitud.getIdTutor().toString());
            datosCorreo.setNombreDirector(infoDirector.obtenerNombreCompleto());
        } else {
            datosCorreo.setNombreDirector(null);
        }
        if (destinatario.equals(DESTINATARIO_CORREO.ESTUDIANTE)){            
            datosCorreo.setDirigidoA(destinatario.getDescripcion());
        } else if(destinatario.equals(DESTINATARIO_CORREO.TUTOR)){
            datosCorreo.setDirigidoA(destinatario.getDescripcion());
        } else if(destinatario.equals(DESTINATARIO_CORREO.DIRECTOR)){
            datosCorreo.setDirigidoA(destinatario.getDescripcion());
        }
        return datosCorreo;
    }        
}
