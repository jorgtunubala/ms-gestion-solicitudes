package com.maestria.gestionSolicitudes.dto.rest.response;

import lombok.Data;

@Data
public class SolicitudEnComiteResponse {
    private Integer idSolicitud;
    private Boolean enComite;
    private Boolean avaladoComite;
    private String conceptoComite;
    private String numeroActa;
    private String fechaAval;
}
