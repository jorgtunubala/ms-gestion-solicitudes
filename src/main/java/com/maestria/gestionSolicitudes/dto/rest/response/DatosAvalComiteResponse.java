package com.maestria.gestionSolicitudes.dto.rest.response;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DatosAvalComiteResponse {
    private String nombreActividad;
    private BigDecimal horasReconocer;   
}
