package com.maestria.gestionSolicitudes.mapper;

import org.mapstruct.Mapper;

import com.maestria.gestionSolicitudes.domain.ApoyoEconomicoPublicacionEvento;
import com.maestria.gestionSolicitudes.dto.rest.request.ApoyoEconomicoPublicacionEventoRequest;

@Mapper(componentModel = "spring")
public interface ApoyoEconomicoPublicacionEventoMapper extends GenericMapper<ApoyoEconomicoPublicacionEventoRequest, ApoyoEconomicoPublicacionEvento> {

}
