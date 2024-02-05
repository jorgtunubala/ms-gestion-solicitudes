package com.maestria.gestionSolicitudes.service.rest.impl;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maestria.gestionSolicitudes.comun.enums.ABREVIATURA_SOLICITUD;
import com.maestria.gestionSolicitudes.comun.enums.ESTADO_SOLICITUD;
import com.maestria.gestionSolicitudes.domain.AsignaturasHomologadas;
import com.maestria.gestionSolicitudes.domain.DocumentosAdjuntosHomologacion;
import com.maestria.gestionSolicitudes.domain.Homologaciones;
import com.maestria.gestionSolicitudes.domain.TiposSolicitud;
import com.maestria.gestionSolicitudes.dto.client.AsignaturaExternaDto;
import com.maestria.gestionSolicitudes.dto.client.AsignaturaExternaResponseDto;
import com.maestria.gestionSolicitudes.dto.client.InformacionPersonalDto;
import com.maestria.gestionSolicitudes.dto.rest.request.DatosAsignaturaHomologacionDto;
import com.maestria.gestionSolicitudes.dto.rest.request.DatosHomologacionDto;
import com.maestria.gestionSolicitudes.dto.rest.request.DatosSolicitudHomologacionDto;
import com.maestria.gestionSolicitudes.dto.rest.response.DatosAsignaturaHomologar;
import com.maestria.gestionSolicitudes.dto.rest.response.DatosComunSolicitud;
import com.maestria.gestionSolicitudes.dto.rest.response.DatosSolicitudHomologacion;
import com.maestria.gestionSolicitudes.dto.rest.response.SolicitudPendientesAval;
import com.maestria.gestionSolicitudes.repository.AsignaturasHomologadasRepository;
import com.maestria.gestionSolicitudes.repository.DocumentosAdjuntosHomologacionRepository;
import com.maestria.gestionSolicitudes.repository.HomologacionesRepository;
import com.maestria.gestionSolicitudes.repository.TiposSolicitudRepository;
import com.maestria.gestionSolicitudes.service.client.GestionAsignaturasService;
import com.maestria.gestionSolicitudes.service.client.GestionDocentesEstudiantesService;
import com.maestria.gestionSolicitudes.service.rest.SolicitudesHomologacionService;

@Service
public class SolicitudesHomologacionServiceImpl implements SolicitudesHomologacionService {

    @Autowired
    private TiposSolicitudRepository solicitudRepository;
    @Autowired
    private HomologacionesRepository homologacionesRepository;
    @Autowired
    private GestionAsignaturasService gestionAsignaturasService;
    @Autowired
    private AsignaturasHomologadasRepository asignaturasHomologadasRepository;
    @Autowired
    private DocumentosAdjuntosHomologacionRepository documentosAdjuntosHomologacionRepository;
    @Autowired
    private GestionDocentesEstudiantesService gestionDocentesEstudiantesService;    
    

    @Override
    public boolean registrarSolicitudHomologacion(Integer tipoSolicitud, DatosSolicitudHomologacionDto dHomologacionDto) {
        try {
            TiposSolicitud solicitud = solicitudRepository.findById(tipoSolicitud).get();
            Homologaciones homologacion = new Homologaciones();
            homologacion.setSolicitud(solicitud);
            Homologaciones registroHomologacion = homologacionesRepository.save(homologacion); 
            boolean registroAsignaturasHomologar = registrarAsignaturasHomologadas(registroHomologacion,dHomologacionDto.getDatosHomologacionDto());
            if (registroAsignaturasHomologar) {
                return registrarDocumentosAdjuntos(registroHomologacion, dHomologacionDto.getDocumentosAdjuntos());
            }
            return Boolean.FALSE;
        } catch (Exception e) {
            System.out.println("Ocurrió un error al registrar la solicitud de homologación, ");
            e.printStackTrace();
            return Boolean.FALSE;
        }
        
    }
    
    private AsignaturaExternaResponseDto registrarAsignaturasExternas(String programa, String institucion, DatosAsignaturaHomologacionDto datosAsignatura){
        try {
            AsignaturaExternaDto asignaturaExternaDto = new AsignaturaExternaDto();
            asignaturaExternaDto.setInstitutoProcedencia(institucion);
            asignaturaExternaDto.setProgramaProcedencia(programa);
            asignaturaExternaDto.setNombreAsignatura(datosAsignatura.getNombreAsignatura());
            asignaturaExternaDto.setNumeroCreditos(datosAsignatura.getNumeroCreditos());
            asignaturaExternaDto.setIntensidadHoraria(datosAsignatura.getIntensidadHoraria());
            asignaturaExternaDto.setContenidoProgramatico(datosAsignatura.getContenidoProgramatico());
            asignaturaExternaDto.setCalificacion(datosAsignatura.getCalificacion());            
            return gestionAsignaturasService.registrarAsignaturasExternas(asignaturaExternaDto);
        } catch (Exception e) {
            System.out.println("Ocurrió un error al registrar la solicitud de asignaturas a homologar, ");
            e.printStackTrace();
            return null;
        }
    }

    private boolean registrarAsignaturasHomologadas(Homologaciones homologacion, DatosHomologacionDto asignaturasHomologar){
        try {
            AsignaturasHomologadas asignaturasHomologadas;
            for (DatosAsignaturaHomologacionDto datosAsignaturaHomologacionDto : asignaturasHomologar.getListaAsignaturas()) {
                AsignaturaExternaResponseDto asignaturaExternaResponseDto = registrarAsignaturasExternas(asignaturasHomologar.getProgramaProcedencia(),
                    asignaturasHomologar.getInstitucionProcedencia(), datosAsignaturaHomologacionDto);
                asignaturasHomologadas = new AsignaturasHomologadas();
                asignaturasHomologadas.setHomologacion(homologacion);
                asignaturasHomologadas.setAsignaturaHomologar(null);
                asignaturasHomologadas.setAsignaturaExterna(asignaturaExternaResponseDto.getIdAsignatura());
                asignaturasHomologadas.setCalificacionObtenida(datosAsignaturaHomologacionDto.getCalificacion());
                asignaturasHomologadas.setEstado(ESTADO_SOLICITUD.PENDIENTE.getDescripcion());
                asignaturasHomologadasRepository.save(asignaturasHomologadas);
            }
            return Boolean.TRUE;
        } catch (Exception e) {
            System.out.println("Ocurrió un error al registrar la solicitud de asignaturas a homologar, ");
            e.printStackTrace();
            return Boolean.FALSE;
        }        
    }

    private boolean registrarDocumentosAdjuntos(Homologaciones homologaciones, List<String> documentos){
        try {
            for (String doc : documentos) {
                DocumentosAdjuntosHomologacion documento = new DocumentosAdjuntosHomologacion();
                documento.setHomologacion(homologaciones);
                documento.setDocumento(doc);
                documentosAdjuntosHomologacionRepository.save(documento);
            }
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

}
