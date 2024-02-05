package com.maestria.gestionSolicitudes.dto.rest.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TutorDto {
    private Integer id;
    private String codigoTutor;
    private String nombreTutor;
}
