package com.maestria.gestionSolicitudes.dto.rest.response;

import lombok.Data;

@Data
public class DatosComunSolicitud {
    private String tipoSolicitud;
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
    private String firmaSolicitante;
    private String firmaTutor;
    private String firmaDirector;
    private String estadoSolicitud;    
}
