package com.maestria.gestionSolicitudes.service.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
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
import com.maestria.gestionSolicitudes.dto.rest.response.DocumentoCertificadoVotacionResponse;
import com.maestria.gestionSolicitudes.dto.rest.response.SolicitudCertificadoVotacionResponse;
import com.maestria.gestionSolicitudes.dto.rest.response.EstudiantesResponse;
import com.maestria.gestionSolicitudes.repository.DocumentoCertificadoVotacionRepository;
import com.maestria.gestionSolicitudes.repository.SolicitudesCertificadoVotacionRepository;
import com.maestria.gestionSolicitudes.repository.EstudiantesPeriodoIngresoRepository;
import com.maestria.gestionSolicitudes.service.rest.GestionSolicitudesCertificadoVotacionService;

@Service
public class GestionSolicitudesCertificadoVotacionImpl implements GestionSolicitudesCertificadoVotacionService {

    @Autowired
    private SolicitudesCertificadoVotacionRepository solicitudesCertificadoVotacionRepository;

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
    public byte[] obtenerTodosDocumentosZip() throws Exception {
        List<DocumentosCertificadoVotacion> documentos = documentoCertificadoVotacionRepository.findAllDocmentosSolicitudesCer_votOrderByFechaModificacion();
        
        if (documentos.isEmpty()) {
            throw new Exception("No se encontraron documentos para procesar");
        }
    
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        
        int documentosValidos = 0;
        
        try {
            for (DocumentosCertificadoVotacion doc : documentos) {
                byte[] documentoPDF = doc.getDocumentoPDF();
                
                if (documentoPDF != null && documentoPDF.length > 0) {
                    try {
                        // Convertir bytes a String para procesar el contenido
                        String contenido = new String(documentoPDF, StandardCharsets.UTF_8);
                        
                        // Eliminar el prefijo del nombre del archivo si existe
                        int indexSeparador = contenido.indexOf(":"); // Buscar el separador ':'
                        if (indexSeparador != -1) {
                            contenido = contenido.substring(indexSeparador + 1);
                        }
                        
                        // Decodificar el contenido base64
                        byte[] pdfDecodificado = Base64.getDecoder().decode(contenido.trim());
                        
                        // Verificar que sea un PDF válido
                        if (isPDF(pdfDecodificado)) {
                            // Crear entrada en el ZIP
                            String nombreArchivo = "Certificado_votacion_" + doc.getId() + ".pdf";
                            ZipEntry entry = new ZipEntry(nombreArchivo);
                            zos.putNextEntry(entry);
                            zos.write(pdfDecodificado);
                            zos.closeEntry();
                            documentosValidos++;
                            
                            System.out.println("Documento " + nombreArchivo + " agregado exitosamente al ZIP");
                        } else {
                            System.out.println("El documento " + doc.getId() + " no es un PDF válido después de decodificar");
                            logPDFInfo(pdfDecodificado, doc.getId());
                        }
                        
                    } catch (IllegalArgumentException e) {
                        System.err.println("Error decodificando base64 para documento " + doc.getId() + ": " + e.getMessage());
                    } catch (Exception e) {
                        System.err.println("Error procesando documento " + doc.getId() + ": " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
            
            zos.close();
            
            if (documentosValidos == 0) {
                throw new Exception("No se encontraron documentos PDF válidos para procesar");
            }
            
            return baos.toByteArray();
            
        } catch (Exception e) {
            throw new Exception("Error al procesar los documentos: " + e.getMessage());
        }
    }
    
    private boolean isPDF(byte[] data) {
        if (data == null || data.length < 5) return false;
        
        // Verificar la firma del PDF (%PDF-)
        return data[0] == 0x25 && // %
               data[1] == 0x50 && // P
               data[2] == 0x44 && // D
               data[3] == 0x46 && // F
               data[4] == 0x2D;   // -
    }
    
    private void logPDFInfo(byte[] documentoPDF, long docId) {
        try {
            System.out.println("\n=== Documento " + docId + " información ===");
            System.out.println("Tamaño: " + documentoPDF.length + " bytes");
            
            // Imprimir los primeros 50 bytes en hex y ASCII
            StringBuilder hexBuilder = new StringBuilder();
            StringBuilder asciiBuilder = new StringBuilder();
            
            for (int i = 0; i < Math.min(50, documentoPDF.length); i++) {
                byte b = documentoPDF[i];
                hexBuilder.append(String.format("%02X ", b));
                
                // Para visualización ASCII, mostrar solo caracteres imprimibles
                if (b >= 32 && b < 127) {
                    asciiBuilder.append((char)b);
                } else {
                    asciiBuilder.append('.');
                }
            }
            
            System.out.println("Hex: " + hexBuilder.toString());
            System.out.println("ASCII: " + asciiBuilder.toString());
            System.out.println("=====================================\n");
        } catch (Exception e) {
            System.err.println("Error al logear información del PDF: " + e.getMessage());
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