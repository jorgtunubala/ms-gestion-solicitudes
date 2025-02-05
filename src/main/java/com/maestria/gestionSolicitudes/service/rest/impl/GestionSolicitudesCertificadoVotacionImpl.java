package com.maestria.gestionSolicitudes.service.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.Base64;

import com.maestria.gestionSolicitudes.domain.Estudiantes;
import com.maestria.gestionSolicitudes.domain.DocumentosCertificadoVotacion;
import com.maestria.gestionSolicitudes.domain.SolicitudesCertificadoVotacion;
import com.maestria.gestionSolicitudes.domain.TiposSolicitud;
import com.maestria.gestionSolicitudes.dto.rest.request.SolicitudPorFechaDto;
import com.maestria.gestionSolicitudes.dto.rest.response.DocumentoCertificadoVotacionResponse;
import com.maestria.gestionSolicitudes.dto.rest.response.SolicitudCertificadoVotacionResponse;
import com.maestria.gestionSolicitudes.dto.rest.response.EstudiantesResponse;
import com.maestria.gestionSolicitudes.repository.DocumentoCertificadoVotacionRepository;
import com.maestria.gestionSolicitudes.repository.SolicitudesCertificadoVotacionRepository;
import com.maestria.gestionSolicitudes.repository.TiposSolicitudRepository;
import com.maestria.gestionSolicitudes.repository.EstudiantesPeriodoIngresoRepository;
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
    private EstudiantesPeriodoIngresoRepository estudiantesPeriodoIngresoRepository;

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

    //@Override
    public List<SolicitudPorFechaDto> registrarFechaSolicitud() {   
        List<TiposSolicitud> tiposSolicitudes = tipoSolicitudRepository.findByEstadoOrderByNombreAsc("ACTIVO");
        List<SolicitudPorFechaDto> solicitudFechas = new ArrayList<>();
        for (TiposSolicitud tipoSolicitud : tiposSolicitudes) {
            SolicitudPorFechaDto solicitudFecha = new SolicitudPorFechaDto();
            solicitudFecha.setIdSolicitud(tipoSolicitud.getId());
            solicitudFecha.setCodigo(tipoSolicitud.getCodigo());            
            if (tipoSolicitud.getCodigo().equals("CER_VOTO")) {
                solicitudFecha.setNombre("Otra");
            } else {
                solicitudFecha.setNombre(tipoSolicitud.getNombre());
            }            
            solicitudFechas.add(solicitudFecha);
        }
        return solicitudFechas;
    }

    public List<EstudiantesResponse> obtenerEstudiantesPeriodoIngreso() throws Exception{
    List<EstudiantesResponse> listaEstudiantes = new ArrayList<>();
        try {
            // Obtener todas las solicitudes de certificado de votación
            List<Estudiantes> estudiantes = estudiantesPeriodoIngresoRepository.findAllEstudiantesPeriodoIngresoOrderByFechaModificacion();

            if (estudiantes.isEmpty()) {
                throw new Exception("No se encontraron solicitudes de certificado de votación");
            }

            // Convertir cada solicitud a su response correspondiente
            for (Estudiantes estudiante : estudiantes) {
                EstudiantesResponse estudianteResponse = convertirAResponse(estudiante);
                listaEstudiantes.add(estudianteResponse);
            }
            
            return listaEstudiantes;
            
        } catch (Exception e) {
            throw new Exception("Error al obtener las solicitudes de certificado de votación: " + e.getMessage());
        }
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
        response.setEstado(solicitud.getEstado());
        response.setFecha_creacion(solicitud.getFechaCreacion());
        response.setFecha_modificacion(solicitud.getFechaModificacion());
        response.setId_tipo_solicitud(solicitud.getIdTipoSolicitud());
        return response;
    }

    private EstudiantesResponse convertirAResponse(Estudiantes estudiante){
        EstudiantesResponse response = new EstudiantesResponse();
        response.setId(estudiante.getId());
        response.setFecha_ingreso(estudiante.getPeriodo_ingreso());
        return response;
    }
}