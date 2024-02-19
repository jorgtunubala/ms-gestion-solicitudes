package com.maestria.gestionSolicitudes.dto.rest.request;

import lombok.Data;

@Data
public class DatosAvalarSolicitudDto {
    private Integer idSolicitud;
    private String firmaTutor;
    private String firmaDirector;
    private String documentoPdfSolicitud;
}
