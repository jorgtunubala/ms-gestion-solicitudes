package com.maestria.gestionSolicitudes.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.maestria.gestionSolicitudes.domain.Solicitud;
import com.maestria.gestionSolicitudes.dto.rest.TipoSolicitudDto;

@Mapper
public interface TipoSolicitudMapper {
    
    TipoSolicitudMapper INSTANCE = Mappers.getMapper(TipoSolicitudMapper.class);

    @Mapping(source = "id", target = "idSolicitud")
    @Mapping(source = "codigo", target = "codigoSolicitud")
    @Mapping(source = "tipoSolicitud", target = "nombreSolicitud")
    TipoSolicitudDto solicitudToTipoSolicitudDTO(Solicitud solicitud);
}
