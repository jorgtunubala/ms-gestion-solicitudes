package com.maestria.gestionSolicitudes.dto.rest.response;

import lombok.Data;

@Data
public class InfoAdicionAsignaturaResponse {
    private Integer id;
    private String nombreAsignatura;
    private String codigoAsignatura;
    private String nombreDocente;
    private String infoAsignatura;
}
