package com.maestria.gestionSolicitudes.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import com.maestria.gestionSolicitudes.domain.ApoyoEconomicoCongreso;
import com.maestria.gestionSolicitudes.dto.rest.request.ApoyoEconomicoCongresoRequest;

@Mapper(componentModel = "spring")
public interface ApoyoEconomicoCongresoMapper {

    @Mappings({
        @Mapping(target = "solicitud", ignore = true),
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "idDirector", source = "idDirectorGrupo"),
        @Mapping(target = "fechaInicio", source = "fechaInicio", qualifiedByName = "stringToLocalDate"),
        @Mapping(target = "fechaFin", source = "fechaFin", qualifiedByName = "stringToLocalDate")
    })
    ApoyoEconomicoCongreso dtoToEntity(ApoyoEconomicoCongresoRequest apoyoEconomicoCongresoRequest);

    @Mapping(target = "idDirectorGrupo", source = "idDirector")
    @Mapping(target = "documentosAdjuntos", ignore = true)
    @Mapping(target = "nombreDirectorGrupo", ignore = true)
    @Mapping(target = "fechaInicio", source = "fechaInicio", qualifiedByName = "localDateToString")
    @Mapping(target = "fechaFin", source = "fechaFin", qualifiedByName = "localDateToString") 
    ApoyoEconomicoCongresoRequest entidadAdto(ApoyoEconomicoCongreso apoyoEconomicoCongreso);

    @Named("stringToLocalDate")
    default LocalDate stringToLocalDate(String fecha) {
        return LocalDate.parse(fecha);
    }

    @Named("localDateToString")
    default String localDateToString(LocalDate fecha) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        // Convertir LocalDate a String en formato "dd/MM/yyyy"
        return fecha.format(formatter);
    }
}
