package com.maestria.gestionSolicitudes.service.client;

import com.maestria.gestionSolicitudes.dto.rest.request.EnvioCorreoRequest;

public interface GestionEnvioCorreoService {
    
    Boolean enviarCorreo(EnvioCorreoRequest envioCorreoRequest);
}
