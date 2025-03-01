package com.maestria.gestionSolicitudes.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maestria.gestionSolicitudes.dto.client.InformacionPersonalDto;
import com.maestria.gestionSolicitudes.dto.rest.request.*;
import com.maestria.gestionSolicitudes.dto.rest.response.*;
import com.maestria.gestionSolicitudes.service.client.MensajeriaService;
import com.maestria.gestionSolicitudes.service.rest.GestionSolicitudesCertificadoVotacionService;
import com.maestria.gestionSolicitudes.service.rest.GestionSolicitudesEnComiteService;
import com.maestria.gestionSolicitudes.service.rest.GestionSolicitudesEnConcejoService;
import com.maestria.gestionSolicitudes.service.rest.GestionSolicitudesService;
import org.springframework.http.HttpHeaders;
import java.util.Map;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/gestionSolicitud")
public class GestionSolicitudesController {
    
    @Autowired
    private GestionSolicitudesService gestionSolicitudesService;

    @Autowired
    private GestionSolicitudesEnComiteService gestionSolicitudesEnComiteService;
    @Autowired
    private GestionSolicitudesEnConcejoService gestionSolicitudesEnConcejoService;
    @Autowired
    private GestionSolicitudesCertificadoVotacionService gestionDocumentosCertificadoVotacionService;
    @Autowired
    private GestionSolicitudesCertificadoVotacionService gestionSolicitudesCertificadoVotacionService;
    @Autowired
    private GestionSolicitudesCertificadoVotacionService gestionEstudiantesPeriodoIngresoService;
    @Autowired
    private MensajeriaService mensajeriaService;

    @GetMapping("/tiposSolicitud")
    public TipoSolicitudResponse obtenerTiposSolicitud() {
        TipoSolicitudResponse tipoSolicitudResponse = new TipoSolicitudResponse();
        try {
            List<TipoSolicitudDto> tipoSolicitudDtos = gestionSolicitudesService.obtenerTiposSolicitudes();            
            tipoSolicitudResponse.setTipoSolicitudDto(tipoSolicitudDtos);   
        } catch (Exception e) {
            tipoSolicitudResponse.setMensaje(e.getMessage());;
        }
        return tipoSolicitudResponse;
    }

    @GetMapping("/requisitoSolicitud/{codigo}")
    public DocumentosRequeridosResponse obtenerRequisitos(@PathVariable String codigo) {
        DocumentosRequeridosResponse dRequeridosResponse = new DocumentosRequeridosResponse();
        try {
            DocumentoRequeridoSolicitudDto dRequeridoSolicitudDto = gestionSolicitudesService.getRequisitoSolicitudAndDocumentosAndNotasPorSolicitudId(codigo);
            dRequeridosResponse.setDoRequeridoSolicitudDto(dRequeridoSolicitudDto);
        } catch (Exception e) {
            dRequeridosResponse.setMensaje(e.getMessage());
        }
        return dRequeridosResponse;
    }

    @GetMapping("/obtenerInfoPersonal/{correo}")
    public InformacionPersonalResponse obtenerInformacionPersonal(@PathVariable String correo) {
        InformacionPersonalResponse response = new InformacionPersonalResponse();
        try {
            InformacionPersonalDto informacionPersonalDto = gestionSolicitudesService.obtenerInformacionPersonal(correo);
            response.setInformacionPersonalDto(informacionPersonalDto);
        } catch (Exception e) {
            response.setMensaje(e.getMessage());
        }
        return response;
    }

    @GetMapping("/obtenerTutores")
    public TutorResponse obtenerTutores() {
        TutorResponse response = new TutorResponse();
        try {
            List<TutorDto> tutoresDto = gestionSolicitudesService.obtenerTutotes();
            response.setTutores(tutoresDto);
        } catch (Exception e) {
            response.setMensaje(e.getMessage());
        }
        return response;
    }

    @PostMapping("/save")
    public String registrarSolicitud(@RequestBody SolicitudRequestDto datosSolicitud) throws Exception {
        return gestionSolicitudesService.registrarSolicitud(datosSolicitud);
    }
    
    @PutMapping("update/fechas")
    public List<SolicitudPorFechaDto> registrarFechaSolicitud(@RequestBody SolicitudPorFechaDto datosFechaSolicitud) throws Exception {
        return gestionSolicitudesCertificadoVotacionService.registrarFechaSolicitud(datosFechaSolicitud);
    }

    @GetMapping("/fechaActual")
    public FechaActualResponse getCurrentDate() {
        return gestionSolicitudesCertificadoVotacionService.obtenerFechaActual();
    }

    @GetMapping("/obtener-solicitudes-pendientes/{identificador}")
    public List<SolicitudPendientesAval> obtenerSolicitudesPendientes(@PathVariable String identificador) throws Exception {
        return gestionSolicitudesService.obtenerSolicitudesPendientes(identificador);
    }

    @GetMapping("/obtener-datos-solicitud/{idSolicitud}")
    public DatosGestionSolicitudResponse obtenerDatosSolicitud(@PathVariable Integer idSolicitud) throws Exception {
        return gestionSolicitudesService.obtenerDatosSolicitud(idSolicitud);
    }

    @PostMapping("/save/firmas")
    public Boolean registrarFirmasSolicitud(@RequestBody DatosAvalarSolicitudDto dAvalarSolicitudDto) throws Exception {
        return gestionSolicitudesService.registrarFirmasPendientes(dAvalarSolicitudDto);
    }

    @GetMapping("/historial/solicitud/{radicado}")
    public List<SolicitudHistoricoResponse> obtenerHistorialSolicitud(@PathVariable String radicado) throws Exception {
        return gestionSolicitudesService.obtenerHistorialSeguimiento(radicado);
    }

    @GetMapping("/obtener-solicitudes-pendientes-coordinador/{estado}")
    public List<SolicitudPendientesAval> obtenerDatosSolicitudPendientesCoordinador(@PathVariable String estado) throws Exception {
        return gestionSolicitudesService.obtenerDatosSolicitudPendientesCoordinador(estado);
    }

    @PostMapping("/rechazar-solicitud")
    public Boolean rechazarSolicitud(@RequestBody RechazarSolicitudRequest rechazarSolicitudRequest) throws Exception {
        return gestionSolicitudesService.rechazarSolicitud(rechazarSolicitudRequest);
    }

    @GetMapping("/obtener-solicitudes-en-comite/{idSolicitud}")
    public SolicitudEnComiteResponse obtenerSolicitudesEnComite(@PathVariable Integer idSolicitud) throws Exception {
        return gestionSolicitudesEnComiteService.obtenerSolicitudEnComite(idSolicitud);
    }

    @PostMapping("/save-solicitud-en-comite")
    public Boolean registrarSolicitudEnComite(@RequestBody SolicitudEnComiteResponse datosSolicitudComite) throws Exception {
        return gestionSolicitudesEnComiteService.guardarSolicitudEnComite(datosSolicitudComite);
    }    
    
    @GetMapping("/obtener-solicitudes-certificado-votacion")
    public List<SolicitudCertificadoVotacionResponse> obtenerSolicitudesCertificadoVotacion() throws Exception {
        return gestionSolicitudesCertificadoVotacionService.obtenerSolicitudesCertificadoVotacion();
    }

    @PostMapping("/documentos-certificado-votacion/zip")
    public ResponseEntity<byte[]> generarZipDocumentos(@RequestBody Map<String, Object> filtros) {
        System.out.println("Recibiendo petición con filtros: " + filtros);
        try {
            String period = (String) filtros.get("period");
            List<Integer> certificateIds = (List<Integer>) filtros.get("certificateIds");

            byte[] zipFile = gestionDocumentosCertificadoVotacionService.obtenerDocumentosZipFiltrados(period, certificateIds);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "certificados.zip");
            
            return new ResponseEntity<>(zipFile, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/obtener-estudiantes-periodo-ingreso")
    public List<EstudiantesResponse> obtenerEstudiantesPeriodoIngreso() throws Exception {
        return gestionEstudiantesPeriodoIngresoService.obtenerEstudiantesPeriodoIngreso();
    }

    @GetMapping("/obtener-solicitudes-en-concejo/{idSolicitud}")
    public SolicitudEnConcejoResponse obtenerSolicitudesEnConcejo(@PathVariable Integer idSolicitud) throws Exception {
        return gestionSolicitudesEnConcejoService.obtenerSolicitudEnConcejo(idSolicitud);
    }

    @PostMapping("/save-solicitud-en-concejo")
    public Boolean registrarSolicitudEnConcejo(@RequestBody SolicitudEnConcejoResponse datosSolicitudConcejo) throws Exception {
        return gestionSolicitudesEnConcejoService.guardarSolicitudEnConcejo(datosSolicitudConcejo);
    }

    @PostMapping("/save/solicitud/{idSolicitud}/{estado}")
    public Boolean actualizarSolicitud(@PathVariable Integer idSolicitud, @PathVariable String estado) throws Exception {
        return gestionSolicitudesService.actualizarSolicitud(idSolicitud, estado);
    }

    @GetMapping("/solicitud/requiere-director/{idSolicitud}/{correoElectronico}")
    public ResponseEntity<Boolean> verificarExistenciaSolicitud(
            @PathVariable Integer idSolicitud, 
            @PathVariable String correoElectronico) {
        
        boolean existe = gestionSolicitudesService.verificarExistenciaSolicitud(idSolicitud, correoElectronico);
        return ResponseEntity.ok(existe);
    }
}

