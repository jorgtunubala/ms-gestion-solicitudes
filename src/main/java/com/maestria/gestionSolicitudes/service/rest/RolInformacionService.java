package com.maestria.gestionSolicitudes.service.rest;

import com.maestria.gestionSolicitudes.dto.rest.response.Rolinformacion.RolInformacionResponse;

public interface RolInformacionService {
    
    RolInformacionResponse obtenerRolInformacion(String cargo);

    Boolean guardarRolInformacion(RolInformacionResponse rolInfo);
}
