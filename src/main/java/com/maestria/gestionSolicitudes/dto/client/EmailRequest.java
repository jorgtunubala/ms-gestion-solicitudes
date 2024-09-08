package com.maestria.gestionSolicitudes.dto.client;

import java.util.ArrayList;
import java.util.Map;

import lombok.Data;

@Data
public class EmailRequest {
    
    private ArrayList<String> correos;
    private String asunto;
    private String mensaje;
    private Map<String, String> documentos;
}
