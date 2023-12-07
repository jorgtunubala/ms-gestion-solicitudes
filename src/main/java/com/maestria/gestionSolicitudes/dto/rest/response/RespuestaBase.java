package com.maestria.gestionSolicitudes.dto.rest.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class RespuestaBase implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private String mensaje;

    private String codigo;
    
    private boolean respuesta;

    private int estado;

    public RespuestaBase() {
        this.setMensaje("La solicitud se completó con éxito.");
        this.setCodigo("OK");
        this.setEstado(200);
        this.setRespuesta(true);
    }
}
