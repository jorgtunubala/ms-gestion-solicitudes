package com.maestria.gestionSolicitudes.dto.rest.response;

import java.util.List;

import lombok.Data;

@Data
public class DocumentoRequeridoSolicitudDto {
    private String tituloDocumento;
    private String descripcion;
    private String articulo;
    private String tenerEnCuenta;
    private List<DocumentoRequerido> documentosRequeridos;
    private List<String> notas; 
}
