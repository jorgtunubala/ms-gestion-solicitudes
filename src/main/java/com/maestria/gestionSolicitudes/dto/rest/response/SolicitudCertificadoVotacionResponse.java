package com.maestria.gestionSolicitudes.dto.rest.response;


import lombok.Data;
import java.sql.Timestamp;

@Data
public class SolicitudCertificadoVotacionResponse {
    private Integer id;
    private Integer id_Estudiante;
    private String estado;
    private Timestamp fecha_creacion;
    private Timestamp fecha_modificacion;
    private String id_tipo_solicitud;
}
