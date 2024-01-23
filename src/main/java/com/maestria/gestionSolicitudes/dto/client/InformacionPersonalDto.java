package com.maestria.gestionSolicitudes.dto.client;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InformacionPersonalDto {
    private Integer id;
    private String nombres;
    private String apellidos;
    private String correo;
    private String celular;
    private String codigoAcademico;
    private String tipoDocumento;
    private String numeroDocumento;

    public String obtenerNombreCompleto() {
        return nombres + " " + apellidos;
    }
}
