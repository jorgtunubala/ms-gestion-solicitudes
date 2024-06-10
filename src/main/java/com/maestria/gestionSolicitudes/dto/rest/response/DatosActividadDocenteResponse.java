package com.maestria.gestionSolicitudes.dto.rest.response;

import java.util.List;

import lombok.Data;

@Data
public class DatosActividadDocenteResponse {
    private String nombreActividad;
    private Integer horasReconocer;
    private List<String> documentos;
    private List<String> enlaces;
}
