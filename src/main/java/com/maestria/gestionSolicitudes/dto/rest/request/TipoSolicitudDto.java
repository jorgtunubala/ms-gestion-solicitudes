package com.maestria.gestionSolicitudes.dto.rest.request;

import lombok.Data;

@Data
public class TipoSolicitudDto {
    private Integer idSolicitud;
    private String codigoSolicitud;
    private String nombreSolicitud;    
}
