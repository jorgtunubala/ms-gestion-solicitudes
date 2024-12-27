package com.maestria.gestionSolicitudes.mapper;

import org.mapstruct.Mapper;

import com.maestria.gestionSolicitudes.domain.ApoyoEconomicoInvestigacion;
import com.maestria.gestionSolicitudes.dto.rest.request.ApoyoEconomicoRequest;

@Mapper(componentModel = "spring")
public interface ApoyoEconomicoMapper extends GenericMapper<ApoyoEconomicoRequest, ApoyoEconomicoInvestigacion> {

}
