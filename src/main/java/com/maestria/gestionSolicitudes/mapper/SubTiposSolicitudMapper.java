package com.maestria.gestionSolicitudes.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.maestria.gestionSolicitudes.domain.SubTiposSolicitud;
import com.maestria.gestionSolicitudes.dto.rest.response.SubTiposSolicitudResponse;

@Mapper(componentModel = "spring")
public interface SubTiposSolicitudMapper {

    @Mappings({
        @Mapping(source = "tipoSolicitud.id", target = "idTipoSolicitud")
    })
    SubTiposSolicitudResponse entidadAdto(SubTiposSolicitud subTiposSolicitud);
    
}
