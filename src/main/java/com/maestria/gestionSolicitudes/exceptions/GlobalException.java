package com.maestria.gestionSolicitudes.exceptions;

import java.time.LocalDate;
import org.springframework.http.HttpStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GlobalException {

	private String mensaje;
	private HttpStatus estado;
	private LocalDate marcaTiempo;
	private String descripcionUrl;
}
