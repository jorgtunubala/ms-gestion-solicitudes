package com.maestria.gestionSolicitudes.dto.rest.request;



import lombok.Data;

@Data
public class SolicitudPorFechaDto {
    private Integer idSolicitud;
    private String codigo;
    private String nombre;
    private String fechaInicio;
    private String fechaFinal;
}
