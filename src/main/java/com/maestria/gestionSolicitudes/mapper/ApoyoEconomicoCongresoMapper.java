package com.maestria.gestionSolicitudes.mapper;

import org.mapstruct.Mapper;

import com.maestria.gestionSolicitudes.domain.ApoyoEconomicoCongreso;
import com.maestria.gestionSolicitudes.dto.rest.request.ApoyoEconomicoCongresoRequest;

@Mapper(componentModel = "spring")
public interface ApoyoEconomicoCongresoMapper extends GenericMapper<ApoyoEconomicoCongresoRequest, ApoyoEconomicoCongreso> {

}
