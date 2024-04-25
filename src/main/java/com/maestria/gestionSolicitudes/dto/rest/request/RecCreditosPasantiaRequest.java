package com.maestria.gestionSolicitudes.dto.rest.request;

import java.util.List;

import lombok.Data;

@Data
public class RecCreditosPasantiaRequest {
    private List<String> documentosAdjuntos;
}
