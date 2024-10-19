package com.maestria.gestionSolicitudes.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import com.maestria.gestionSolicitudes.domain.ApoyoEconomicoPublicacionEvento;
import com.maestria.gestionSolicitudes.dto.rest.request.ApoyoEconomicoPublicacionEventoRequest;

@Mapper(componentModel = "spring")
public interface ApoyoEconomicoPublicacionEventoMapper {

    @Mappings({
        @Mapping(target = "solicitud", ignore = true),
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "idDirector", source = "idDirectorGrupo"),
        @Mapping(target = "fechaInicio", ignore = true),
        @Mapping(target = "fechaFin", ignore = true)
    })
    ApoyoEconomicoPublicacionEvento dtoToEntity(ApoyoEconomicoPublicacionEventoRequest apoyoEconomicoPublicacionEventoRequest);

    @Mapping(target = "idDirectorGrupo", source = "idDirector")
    @Mapping(target = "documentosAdjuntos", ignore = true)
    @Mapping(target = "nombreDirectorGrupo", ignore = true)
    @Mapping(target = "fechaInicio", ignore = true)
    @Mapping(target = "fechaFin", ignore = true)
    ApoyoEconomicoPublicacionEventoRequest entidadAdto(ApoyoEconomicoPublicacionEvento apoyoEconomicoPublicacionEvento);

}
