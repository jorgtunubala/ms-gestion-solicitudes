package com.maestria.gestionSolicitudes.dto.rest.request.AvalComite;

import lombok.Data;

@Data
public class AprobarAvalComiteRequest {
    private Integer idSubtipo;
    private String nombreActividad;
    private Boolean aprobado;
}
