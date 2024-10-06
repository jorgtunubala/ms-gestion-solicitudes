package com.maestria.gestionSolicitudes.dto.rest.response;

import lombok.Data;

@Data
public class DocumentoRequerido {
    private String nombre;
    private Boolean adjuntarDocumento;
    private String nombreAcortado;
}
