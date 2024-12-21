package com.maestria.gestionSolicitudes.mapper;

import org.mapstruct.Mapper;

import com.maestria.gestionSolicitudes.domain.AvalPasantiaInvestigacion;
import com.maestria.gestionSolicitudes.dto.rest.request.AvalPasantiaInvRequest;

@Mapper(componentModel = "spring")
public interface AvalPasantiaInvMapper extends GenericMapper<AvalPasantiaInvRequest, AvalPasantiaInvestigacion> {

}
