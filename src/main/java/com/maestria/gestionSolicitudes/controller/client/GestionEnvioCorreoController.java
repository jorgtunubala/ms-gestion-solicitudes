package com.maestria.gestionSolicitudes.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maestria.gestionSolicitudes.dto.rest.request.EnvioCorreoRequest;
import com.maestria.gestionSolicitudes.service.client.GestionEnvioCorreoService;

@RestController
@RequestMapping("/gestionEnvioCorreo")
public class GestionEnvioCorreoController {
    
    @Autowired
    private GestionEnvioCorreoService mensajeriaService;    

    @PostMapping("/send-email")
    public Boolean enviarEmail(@RequestBody EnvioCorreoRequest envioCorreoRequest) throws Exception {
        return mensajeriaService.enviarCorreo(envioCorreoRequest);
    }
}
