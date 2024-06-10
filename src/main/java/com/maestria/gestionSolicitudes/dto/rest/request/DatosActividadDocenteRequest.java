package com.maestria.gestionSolicitudes.dto.rest.request;

import java.util.List;

import lombok.Data;

@Data
public class DatosActividadDocenteRequest {
    private String codigoSubtipo;
    private Integer intensidadHoraria;
    private Integer horasReconocer;
    private List<String> documentosAdjuntos;
    private List<String> enlacesAdjuntos;
}
