package com.maestria.gestionSolicitudes.dto.rest.request;

import lombok.Data;

@Data
public class RechazarSolicitudRequest {
    
    private Integer idSolicitud;
    private String emailRevisor;
    private String estado;
    private String comentario;
}
