package com.maestria.gestionSolicitudes.dto.rest.request;


import lombok.Data;

@Data
public class SolicitudPorFechaDto {
    private String codigo;
    private String fechaInicio;
    private String fechaFinal;
}
