package com.maestria.gestionSolicitudes.dto.rest.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InfoAdicionAsignaturaRequest {
    private String nombreAsignatura;
    private Integer idDocente;
    private String grupo;
}
