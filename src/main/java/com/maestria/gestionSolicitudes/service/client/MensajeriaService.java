package com.maestria.gestionSolicitudes.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.maestria.gestionSolicitudes.dto.client.EmailRequest;

@FeignClient(value="mensajeriaService", url="${mensajeria.url}")
public interface MensajeriaService {

    @PostMapping("${mensajeria.resources.enviar-email}")
    Boolean enviarEmail(EmailRequest emailRequest);

}
