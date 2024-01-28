package com.maestria.gestionSolicitudes.dto.rest.response;

import lombok.Data;

@Data
public class DatosComunSolicitud {
    private String tipoSolicitud;
    private String nombreEstudiante;
    private String codigoEstudiante;
    private String emailEstudiante;
    private String celular;
    private String nombreTutor;
}
