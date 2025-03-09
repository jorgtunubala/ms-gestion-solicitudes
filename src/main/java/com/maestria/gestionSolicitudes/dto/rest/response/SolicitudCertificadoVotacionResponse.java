package com.maestria.gestionSolicitudes.dto.rest.response;


import lombok.Data;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class SolicitudCertificadoVotacionResponse {
    private Integer id;
    private Integer id_Estudiante;
    private String estado;
    private String fecha_creacion;
    private String fecha_modificacion;
    private String id_tipo_solicitud;
}
