package com.maestria.gestionSolicitudes.service.client;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maestria.gestionSolicitudes.comun.util.Base64;
import com.maestria.gestionSolicitudes.dto.client.EmailRequest;
import com.maestria.gestionSolicitudes.dto.rest.request.EnvioCorreoRequest;

@Service
public class GestionEnvioCorreoServiceImpl implements GestionEnvioCorreoService {

    @Autowired
    private MensajeriaService mensajeriaService;

    @Override
    public Boolean enviarCorreo(EnvioCorreoRequest envioCorreoRequest) {
        String destino = envioCorreoRequest.getDestinatario();
        String oficio = envioCorreoRequest.getOficio();
        EmailRequest emailRequest = new EmailRequest();
        ArrayList<String> destinatarios = new ArrayList<>();
        if (destino.equals("concejo")) {
            emailRequest.setAsunto("Asunto concejo");
            emailRequest.setMensaje("Enviando mensaje concejo");
            destinatarios.add("jorgtunubala@gmail.com");
            emailRequest.setCorreos(destinatarios);
            emailRequest.setDocumentos(Base64.obtenerBase64(oficio));
            mensajeriaService.enviarEmail(emailRequest);
            return true;
        } else if (destino.equals("solicitante")) {
            emailRequest.setAsunto("Asunto Solicitante");
            emailRequest.setMensaje("Enviando mensaje solicitante");
            destinatarios.add("jorgtunubala@gmail.com");
            emailRequest.setCorreos(destinatarios);
            emailRequest.setDocumentos(Base64.obtenerBase64(oficio));
            mensajeriaService.enviarEmail(emailRequest);
            return true;
        }
        return false;
    }
    
}
