package com.maestria.gestionSolicitudes.dto.client;

import lombok.Data;

@Data
public class DocentesAsignaturasResponse {
    private Integer id;
    private String nombreAsignatura;
    private String codigoAsignatura;
    private Integer idDocente;
}
