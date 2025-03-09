package com.maestria.gestionSolicitudes.dto.rest.response;

import lombok.Data;

@Data
public class DocumentoCertificadoVotacionResponse {
    private Integer id;
    private byte[] documentoPDF;
    
}
