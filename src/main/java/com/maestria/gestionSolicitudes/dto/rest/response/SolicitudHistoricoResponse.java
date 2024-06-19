package com.maestria.gestionSolicitudes.dto.rest.response;

import lombok.Data;

@Data
public class SolicitudHistoricoResponse {
    private String radicado;
    private String estadoSolicitud;
    private String fechaHora;
    private String pdfBase64;
    private String descripcion;
    private String comentarios;
}
