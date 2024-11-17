package com.maestria.gestionSolicitudes.exceptions;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.ResourceAccessException;

public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == HttpStatus.NOT_FOUND.value()) {
            String[] urlParts = response.request().url().split("/");
            String entidad = urlParts[urlParts.length - 2];
            String id = urlParts[urlParts.length - 1];
            String mensaje = entidad.substring(0, 1).toUpperCase() + entidad.substring(1) + " con id " + id + " no encontrado";
            return new ResourceNotFoundException(mensaje);
        }

        if (response.status() == HttpStatus.SERVICE_UNAVAILABLE.value()) {
            return new ResourceAccessException("No se pudo conectar con el servicio externo.");
        }

        return defaultErrorDecoder.decode(methodKey, response);
    }
}
