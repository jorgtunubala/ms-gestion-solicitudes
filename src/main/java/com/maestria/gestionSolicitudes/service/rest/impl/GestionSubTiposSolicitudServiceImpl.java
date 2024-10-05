package com.maestria.gestionSolicitudes.service.rest.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maestria.gestionSolicitudes.comun.enums.ABREVIATURA_SUBTIPOS;
import com.maestria.gestionSolicitudes.domain.DocumentosSubtipos;
import com.maestria.gestionSolicitudes.domain.EnlacesSubtipos;
import com.maestria.gestionSolicitudes.domain.SubTiposSolicitud;
import com.maestria.gestionSolicitudes.domain.TiposSolicitud;
import com.maestria.gestionSolicitudes.dto.rest.response.SubTiposSolicitudResponse;
import com.maestria.gestionSolicitudes.mapper.SubTiposSolicitudMapper;
import com.maestria.gestionSolicitudes.repository.DocumentoSubtipoRepository;
import com.maestria.gestionSolicitudes.repository.EnlaceSubtipoRepository;
import com.maestria.gestionSolicitudes.repository.SubTiposSolicitudRepository;
import com.maestria.gestionSolicitudes.repository.TiposSolicitudRepository;
import com.maestria.gestionSolicitudes.service.rest.GestionSubTiposSolicitudService;

@Service
public class GestionSubTiposSolicitudServiceImpl implements GestionSubTiposSolicitudService {

    private final SubTiposSolicitudMapper subTiposSolicitudMapper;

    @Autowired
    private DocumentoSubtipoRepository documentoSubtipoRepository;

    @Autowired
    private EnlaceSubtipoRepository enlaceSubtipoRepository;

    public GestionSubTiposSolicitudServiceImpl(SubTiposSolicitudMapper subTiposSolicitudMapper){
        this.subTiposSolicitudMapper = subTiposSolicitudMapper;
    }

    @Autowired
    private TiposSolicitudRepository tiposSolicitudRepository;

    @Autowired
    private SubTiposSolicitudRepository subTiposSolicitudRepository;

    @Override
    public List<SubTiposSolicitudResponse> obtenerSubtiposPorTipoSolicitud(String proceso) {
        TiposSolicitud tiposSolicitud = tiposSolicitudRepository.findByCodigo("RE_CRED_PR_DOC");
        List<SubTiposSolicitud> listaSubTipos = subTiposSolicitudRepository.findByTipoSolicitud(tiposSolicitud);
        List<SubTiposSolicitudResponse> response = new ArrayList<>();
        for (SubTiposSolicitud subTiposSolicitud : listaSubTipos) {
            SubTiposSolicitudResponse subTiposSolicitudResponse = subTiposSolicitudMapper.entidadAdto(subTiposSolicitud);
            String codigo = subTiposSolicitud.getCodigo();
            subTiposSolicitudResponse.setAbreviatura(ABREVIATURA_SUBTIPOS.getDescripcionPorCodigo(codigo));
            List<String> documentos = obtenerDocumentosSubtipos(subTiposSolicitud.getId());
            List<String> enlaces = obtenerEnlacesSubtipos(subTiposSolicitud.getId());
            subTiposSolicitudResponse.setDocumentos(documentos);
            subTiposSolicitudResponse.setEnlaces(enlaces);
            if (proceso.equals("aval") && subTiposSolicitud.getRequiereAval() == true) {
                response.add(subTiposSolicitudResponse);
            } else if (proceso.equals("creditos")){
                response.add(subTiposSolicitudResponse);
            }
        }
        return response;
    }
    

    private List<String> obtenerDocumentosSubtipos(Integer idSubtipo) {
        List<String> documentos = documentoSubtipoRepository.findBySubtipoSolicitud(idSubtipo)
            .stream()
            .map(DocumentosSubtipos::getDocumento)
            .collect(Collectors.toList());
        return documentos;
    }

    private List<String> obtenerEnlacesSubtipos(Integer idSubtipo) {
        List<String> enlaces = enlaceSubtipoRepository.findBySubtipoSolicitud(idSubtipo)
            .stream()
            .map(EnlacesSubtipos::getNombreRequisito)
            .collect(Collectors.toList());
        return enlaces;
    }
}
