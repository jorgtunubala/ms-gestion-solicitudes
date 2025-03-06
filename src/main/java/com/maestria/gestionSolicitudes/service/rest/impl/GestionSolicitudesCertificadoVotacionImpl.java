package com.maestria.gestionSolicitudes.service.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.Base64;
import java.util.Map;

import com.maestria.gestionSolicitudes.domain.Estudiante;
import com.maestria.gestionSolicitudes.domain.DocumentosCertificadoVotacion;
import com.maestria.gestionSolicitudes.domain.SolicitudesCertificadoVotacion;
import com.maestria.gestionSolicitudes.domain.TiposSolicitud;
import com.maestria.gestionSolicitudes.dto.rest.request.SolicitudPorFechaRequest;
import com.maestria.gestionSolicitudes.dto.rest.request.EstadoSolicitudRequest;
import com.maestria.gestionSolicitudes.dto.rest.response.DocumentoCertificadoVotacionResponse;
import com.maestria.gestionSolicitudes.dto.rest.response.SolicitudCertificadoVotacionResponse;
import com.maestria.gestionSolicitudes.dto.rest.response.EstadoEstudianteResponse;
import com.maestria.gestionSolicitudes.dto.rest.response.FechaActualResponse;
import com.maestria.gestionSolicitudes.repository.DocumentoCertificadoVotacionRepository;
import com.maestria.gestionSolicitudes.repository.SolicitudesCertificadoVotacionRepository;
import com.maestria.gestionSolicitudes.repository.TiposSolicitudRepository;
import com.maestria.gestionSolicitudes.repository.EstadoEstudianteRepository;
import com.maestria.gestionSolicitudes.service.rest.GestionSolicitudesCertificadoVotacionService;

@Service
public class GestionSolicitudesCertificadoVotacionImpl implements GestionSolicitudesCertificadoVotacionService {

    @Autowired
    private SolicitudesCertificadoVotacionRepository solicitudesCertificadoVotacionRepository;

    @Autowired
    private TiposSolicitudRepository tipoSolicitudRepository;
    
    @Autowired
    private DocumentoCertificadoVotacionRepository documentoCertificadoVotacionRepository;

    @Autowired
    private EstadoEstudianteRepository estadoEstudianteRepository;

    @Override
    public List<SolicitudCertificadoVotacionResponse> obtenerSolicitudesCertificadoVotacion() throws Exception{
    List<SolicitudCertificadoVotacionResponse> listaSolicitudes = new ArrayList<>();
        try {
            // Obtener todas las solicitudes de certificado de votación
            List<SolicitudesCertificadoVotacion> solicitudes = solicitudesCertificadoVotacionRepository.findAllSolicitudesOrderByFechaModificacion();
    
                if (solicitudes.isEmpty()) {
                    throw new Exception("No se encontraron solicitudes de certificado de votación");
                }
    
                // Convertir cada solicitud a su response correspondiente
                for (SolicitudesCertificadoVotacion solicitud : solicitudes) {
                    SolicitudCertificadoVotacionResponse solicitudResponse = convertirAResponse(solicitud);
                    listaSolicitudes.add(solicitudResponse);
                }
                
                return listaSolicitudes;
                
            } catch (Exception e) {
            throw new Exception("Error al obtener las solicitudes de certificado de votación: " + e.getMessage());
        }
    }

    @Override
    public List<EstadoEstudianteResponse> obtenerEstadoEstudiante() throws Exception{
    List<EstadoEstudianteResponse> listaEstadoEstudiante = new ArrayList<>();
        try {
            // Obtener todas los estudiantes por su estado
            List<Estudiante> estudiantes = estadoEstudianteRepository.findAllEstadoEstudiantesOrderByFechaModificacion();
    
                if (estudiantes.isEmpty()) {
                    throw new Exception("No se encontraron estudiantes");
                }
    
                // Convertir cada estudiante a su response correspondiente
                for (Estudiante estudiante : estudiantes) {
                    EstadoEstudianteResponse estudianteResponse = convertirAResponse(estudiante);
                    listaEstadoEstudiante.add(estudianteResponse);
                }
                
                return listaEstadoEstudiante;
                
            } catch (Exception e) {
            throw new Exception("Error al obtener los estudianes por estado: " + e.getMessage());
        }
    }

    //@Override
    public List<SolicitudPorFechaRequest> actualizarFechaSolicitud(SolicitudPorFechaRequest datosFechaSolicitud) {   
        List<TiposSolicitud> tiposSolicitudes = tipoSolicitudRepository.findByEstadoOrderByNombreAsc("ACTIVO");
        List<SolicitudPorFechaRequest> solicitudFechas = new ArrayList<>();

        for (TiposSolicitud tipoSolicitud : tiposSolicitudes) {
            // Si el ID de la solicitud coincide con el DTO recibido, actualizamos las fechas
            if (tipoSolicitud.getCodigo().equals(datosFechaSolicitud.getCodigo())) {
                tipoSolicitud.setFechaInicio(datosFechaSolicitud.getFechaInicio());
                tipoSolicitud.setFechaFinal(datosFechaSolicitud.getFechaFinal());
                tipoSolicitudRepository.save(tipoSolicitud);
            }
            SolicitudPorFechaRequest solicitudFecha = new SolicitudPorFechaRequest();
            solicitudFecha.setCodigo(tipoSolicitud.getCodigo());            
            solicitudFecha.setFechaInicio(tipoSolicitud.getFechaInicio());   
            solicitudFecha.setFechaFinal(tipoSolicitud.getFechaFinal());    
            solicitudFechas.add(solicitudFecha);
        }
        System.out.println("Fechas actualizadas correctamente");
        return solicitudFechas;
    }

    
    public List<EstadoSolicitudRequest> actualizarEstadoSolicitud(EstadoSolicitudRequest estadoSolicitud) throws Exception{
        List<SolicitudesCertificadoVotacion> solicitudes = solicitudesCertificadoVotacionRepository.findAllSolicitudesOrderByFechaModificacion();
        List<EstadoSolicitudRequest> listaEstudiantes = new ArrayList<>();

        for (SolicitudesCertificadoVotacion solicitud : solicitudes) {
            // Si el ID de la solicitud coincide con el DTO recibido, actualizamos las fechas
                if (solicitud.getIdTipoSolicitud().equals(estadoSolicitud.getCodigo())) {
                    solicitud.setEstado_solicitud(estadoSolicitud.getEstado());
                    solicitudesCertificadoVotacionRepository.save(solicitud);
                }

                EstadoSolicitudRequest estadoSolicitudes = new EstadoSolicitudRequest();
                estadoSolicitudes.setCodigo(solicitud.getIdTipoSolicitud());            
                estadoSolicitudes.setEstado(solicitud.getEstado_solicitud());
  
                listaEstudiantes.add(estadoSolicitudes);
            }
            System.out.println("Fechas actualizadas correctamente");
            return listaEstudiantes;
    }

    @Override
    public byte[] obtenerDocumentosZipFiltrados(String period, List<Integer> certificateIds) throws Exception {
        List<DocumentosCertificadoVotacion> documentos;
    
        // Obtener documentos según los filtros
        if (period != null && certificateIds != null && !certificateIds.isEmpty()) {
            documentos = documentoCertificadoVotacionRepository.findByPeriodAndIds(period, certificateIds);
        } else if (period != null) {
            documentos = documentoCertificadoVotacionRepository.findByPeriod(period);
        } else if (certificateIds != null && !certificateIds.isEmpty()) {
            documentos = documentoCertificadoVotacionRepository.findByIds(certificateIds);
        } else {
            documentos = documentoCertificadoVotacionRepository.findAllDocmentosSolicitudesCer_votOrderByFechaModificacion();
        }
    
        if (documentos.isEmpty()) {
            throw new Exception("No se encontraron documentos para procesar");
        }
    
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        
        try {
            int documentosValidos = 0;
            
            for (DocumentosCertificadoVotacion doc : documentos) {
                byte[] documentoPDF = doc.getDocumentoPDF();
                
                if (documentoPDF != null && documentoPDF.length > 0) {
                    try {
                        // Convertir bytes a String para procesar el contenido
                        String contenido = new String(documentoPDF, StandardCharsets.UTF_8);
                        
                        // Eliminar el prefijo del nombre del archivo si existe
                        int indexSeparador = contenido.indexOf(":");
                        if (indexSeparador != -1) {
                            contenido = contenido.substring(indexSeparador + 1).trim();
                        }
                        
                        // Decodificar el contenido base64
                        byte[] pdfDecodificado = Base64.getDecoder().decode(contenido);
                        
                        // Crear entrada ZIP con nombre único
                        String nombreArchivo = String.format("certificado_%d.pdf", doc.getId());
                        ZipEntry zipEntry = new ZipEntry(nombreArchivo);
                        zos.putNextEntry(zipEntry);
                        
                        // Escribir contenido PDF al ZIP
                        zos.write(pdfDecodificado);
                        zos.closeEntry();
                        
                        documentosValidos++;
                    } catch (IllegalArgumentException e) {
                        System.err.println("Error procesando documento " + doc.getId() + ": " + e.getMessage());
                    }
                }
            }
            
            if (documentosValidos == 0) {
                throw new Exception("No se encontraron documentos PDF válidos para procesar");
            }
            
            zos.close();
            return baos.toByteArray();
            
        } catch (IOException e) {
            throw new Exception("Error al crear el archivo ZIP: " + e.getMessage());
        } finally {
            try {
                zos.close();
                baos.close();
            } catch (IOException e) {
                System.err.println("Error cerrando streams: " + e.getMessage());
            }
        }
    }   
    
    private SolicitudCertificadoVotacionResponse convertirAResponse(SolicitudesCertificadoVotacion solicitud) {
        SolicitudCertificadoVotacionResponse response = new SolicitudCertificadoVotacionResponse();
        response.setId(solicitud.getId());
        response.setId_Estudiante(solicitud.getIdEstudiante());
        response.setEstado(solicitud.getEstado_solicitud());
        response.setFecha_creacion(solicitud.getFechaCreacion());
        response.setFecha_modificacion(solicitud.getFechaModificacion());
        response.setId_tipo_solicitud(solicitud.getIdTipoSolicitud());
        return response;
    }

    private EstadoEstudianteResponse convertirAResponse(Estudiante estudiante){
        EstadoEstudianteResponse response = new EstadoEstudianteResponse();
        response.setId(estudiante.getId());
        response.setEstado_maestria(estudiante.getEstado_maestria());
        return response;
    }

}