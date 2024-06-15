package com.maestria.gestionSolicitudes.dto.rest.request;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class AvalComiteRequest {
    private String codigoSubtipo;
    private Integer intensidadHoraria;
    private BigDecimal horasReconocer;
}
