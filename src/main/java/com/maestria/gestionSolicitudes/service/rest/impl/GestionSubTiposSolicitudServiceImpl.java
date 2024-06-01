package com.maestria.gestionSolicitudes.service.rest.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maestria.gestionSolicitudes.comun.enums.ABREVIATURA_SUBTIPOS;
import com.maestria.gestionSolicitudes.domain.SubTiposSolicitud;
import com.maestria.gestionSolicitudes.domain.TiposSolicitud;
import com.maestria.gestionSolicitudes.dto.rest.response.SubTiposSolicitudResponse;
import com.maestria.gestionSolicitudes.mapper.SubTiposSolicitudMapper;
import com.maestria.gestionSolicitudes.repository.SubTiposSolicitudRepository;
import com.maestria.gestionSolicitudes.repository.TiposSolicitudRepository;
import com.maestria.gestionSolicitudes.service.rest.GestionSubTiposSolicitudService;

@Service
public class GestionSubTiposSolicitudServiceImpl implements GestionSubTiposSolicitudService {

    private final SubTiposSolicitudMapper subTiposSolicitudMapper;

    public GestionSubTiposSolicitudServiceImpl(SubTiposSolicitudMapper subTiposSolicitudMapper){
        this.subTiposSolicitudMapper = subTiposSolicitudMapper;
    }

    @Autowired
    private TiposSolicitudRepository tiposSolicitudRepository;

    @Autowired
    private SubTiposSolicitudRepository subTiposSolicitudRepository;

    @Override
    public List<SubTiposSolicitudResponse> obtenerSubtiposPorTipoSolicitud(Integer id) {
        TiposSolicitud tiposSolicitud = tiposSolicitudRepository.findById(id).get();
        List<SubTiposSolicitud> listaSubTipos = subTiposSolicitudRepository.findByTipoSolicitud(tiposSolicitud);
        List<SubTiposSolicitudResponse> response = new ArrayList<>();
        for (SubTiposSolicitud subTiposSolicitud : listaSubTipos) {
            SubTiposSolicitudResponse subTiposSolicitudResponse = subTiposSolicitudMapper.entidadAdto(subTiposSolicitud);
            String codigo = subTiposSolicitud.getCodigo();
            subTiposSolicitudResponse.setAbreviatura(ABREVIATURA_SUBTIPOS.getDescripcionPorCodigo(codigo));
            List<String> documentos = Arrays.asList("Documento 1", "Documento 2");
            List<String> enlaces = Arrays.asList("Enlace 1");
            subTiposSolicitudResponse.setDocumentos(documentos);
            subTiposSolicitudResponse.setEnlaces(enlaces);
            response.add(subTiposSolicitudResponse);
        }
        return response;
    }
    
}
