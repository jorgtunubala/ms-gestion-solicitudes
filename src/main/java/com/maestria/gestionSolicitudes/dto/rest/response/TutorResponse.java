package com.maestria.gestionSolicitudes.dto.rest.response;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TutorResponse extends RespuestaBase {
    List<TutorDto> tutores;
}
