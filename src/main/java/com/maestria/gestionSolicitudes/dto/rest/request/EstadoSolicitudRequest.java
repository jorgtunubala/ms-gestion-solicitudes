package com.maestria.gestionSolicitudes.dto.rest.request;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EstadoSolicitudRequest {
    private Integer codigo;
    private String estado;
}
