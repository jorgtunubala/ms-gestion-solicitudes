package com.maestria.gestionSolicitudes.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.maestria.gestionSolicitudes.domain.ApoyoEconomicoInvestigacion;
import com.maestria.gestionSolicitudes.dto.rest.request.ApoyoEconomicoRequest;

@Mapper(componentModel = "spring")
public interface ApoyoEconomicoMapper {

    @Mapping(target = "idDirector", source = "idDirectorGrupo")
    @Mapping(target = "solicitud", ignore = true)
    @Mapping(target = "id", ignore = true) 
    ApoyoEconomicoInvestigacion dtoToEntity(ApoyoEconomicoRequest apoyoEconomicoRequest);

    @Mapping(target = "idDirectorGrupo", source = "idDirector")
    @Mapping(target = "documentosAdjuntos", ignore = true) 
    ApoyoEconomicoRequest entidadAdto(ApoyoEconomicoInvestigacion apoyoEconomicoInvestigacion);
}
