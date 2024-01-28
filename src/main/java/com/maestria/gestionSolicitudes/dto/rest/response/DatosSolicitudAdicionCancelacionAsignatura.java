package com.maestria.gestionSolicitudes.dto.rest.response;

import lombok.Data;

@Data
public class DatosSolicitudAdicionCancelacionAsignatura {
    private String nombreAsignatura;
    private String grupo;
    private String motivo;
}
