package com.maestria.gestionSolicitudes.dto.rest.response;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DatosComunSolicitud {
    private String tipoSolicitud;
    private String radicado;
    private String fechaEnvioSolicitud;
    private String nombreSolicitante;
    private String apellidoSolicitante;
    private String codigoSolicitante;
    private String emailSolicitante;
    private String celularSolicitante;
    private String tipoIdentSolicitante;
    private String numeroIdentSolicitante;
    private String nombreTutor;
    private String nombreDirector;
    private Boolean requiereFirmaDirector;
    private Boolean firmaSolicitante;
    private Boolean firmaTutor;
    private Boolean firmaDirector;
    private Integer numPaginaTutor;
    private Integer numPaginaDirector;
    private BigDecimal posXTutor;
    private BigDecimal posYTutor;
    private BigDecimal posXDirector;
    private BigDecimal posYDirector;
    private String estadoSolicitud;
    private String oficioPdf;
}
