package com.maestria.gestionSolicitudes.dto.rest.response;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class DatosActividadDocenteResponse {
    private String nombreActividad;
    private BigDecimal horasReconocer;
    private List<String> documentos;
    private List<String> enlaces;
}
