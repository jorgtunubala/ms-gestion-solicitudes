package com.maestria.gestionSolicitudes.service.rest.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maestria.gestionSolicitudes.domain.DocumentoRequisitoSolicitud;
import com.maestria.gestionSolicitudes.domain.NotaDocumentoRequerido;
import com.maestria.gestionSolicitudes.domain.RequisitoSolicitud;
import com.maestria.gestionSolicitudes.domain.Solicitud;
import com.maestria.gestionSolicitudes.dto.client.InformacionPersonalDto;
import com.maestria.gestionSolicitudes.dto.rest.DocumentoRequeridoSolicitudDto;
import com.maestria.gestionSolicitudes.dto.rest.TipoSolicitudDto;
import com.maestria.gestionSolicitudes.dto.rest.TutorDto;
import com.maestria.gestionSolicitudes.mapper.TipoSolicitudMapper;
import com.maestria.gestionSolicitudes.repository.DocumentoRequisitoSolicitudRepository;
import com.maestria.gestionSolicitudes.repository.NotaDocumentoRequeridoRepository;
import com.maestria.gestionSolicitudes.repository.RequisitoSolicitudRepository;
import com.maestria.gestionSolicitudes.repository.SolicitudRepository;
import com.maestria.gestionSolicitudes.service.client.GestionDocentesEstudiantesService;
import com.maestria.gestionSolicitudes.service.rest.GestionSolicitudesService;

@Service
public class GestionSolicitudesServiceImpl implements GestionSolicitudesService {

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private RequisitoSolicitudRepository requisitoSolicitudRepository;

    @Autowired
    private DocumentoRequisitoSolicitudRepository dSolicitudRepository;

    @Autowired
    private NotaDocumentoRequeridoRepository notaDocumentoRequeridoRepository;

    @Autowired
    private GestionDocentesEstudiantesService gestionDocentesEstudiantesService;

    private final TipoSolicitudMapper tipoSolicitudMapper = TipoSolicitudMapper.INSTANCE;

    @Override
    public List<TipoSolicitudDto> obtenerTiposSolicitudes() {
        List<Solicitud> solicitudes = solicitudRepository.findAll();
        List<TipoSolicitudDto> solicitudDtos = new ArrayList<>();
        for (Solicitud solicitud : solicitudes) {
            solicitudDtos.add(tipoSolicitudMapper.solicitudToTipoSolicitudDTO(solicitud));
        }
        return solicitudDtos;
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
            List<NotaDocumentoRequerido> lNotaDocumentoRequeridos = notaDocumentoRequeridoRepository.findByRequisitoSolicitudId(requisitoSolicitud.getSolicitud().getId());
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
    
}
