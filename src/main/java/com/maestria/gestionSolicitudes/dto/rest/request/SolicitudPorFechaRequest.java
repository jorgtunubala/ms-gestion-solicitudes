package com.maestria.gestionSolicitudes.dto.rest.request;


import lombok.Data;

@Data
public class SolicitudPorFechaRequest {
    private String codigo;
    private String fechaInicio;
    private String fechaFinal;
}
