package com.maestria.gestionSolicitudes.mapper;

import org.mapstruct.Mapper;

import com.maestria.gestionSolicitudes.domain.SubTiposSolicitud;
import com.maestria.gestionSolicitudes.dto.rest.response.SubTiposSolicitudResponse;

@Mapper(componentModel = "spring")
public interface SubTiposSolicitudMapper extends GenericMapper<SubTiposSolicitudResponse, SubTiposSolicitud> {
   
}
