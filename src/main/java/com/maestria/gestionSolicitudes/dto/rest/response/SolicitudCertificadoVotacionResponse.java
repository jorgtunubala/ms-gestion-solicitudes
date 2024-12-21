package com.maestria.gestionSolicitudes.dto.rest.response;


import com.maestria.gestionSolicitudes.dto.rest.request.SolicitudCertificadoVotacionRequest;

import lombok.Data;

@Data
public class SolicitudCertificadoVotacionResponse {
    private Integer id;
    private Integer id_Estudiante;
    private String estado;
    private String fecha_creacion;
    private String fecha_modificacion;
    private String documento_Firmado;
}
