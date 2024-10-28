package com.maestria.gestionSolicitudes.dto.rest.request;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class DatosActividadDocenteRequest {
    private String codigoSubtipo;
    private Integer intensidadHoraria;
    private BigDecimal horasReconocer;
    private List<String> documentosAdjuntos;
    private List<String> enlacesAdjuntos;
}
