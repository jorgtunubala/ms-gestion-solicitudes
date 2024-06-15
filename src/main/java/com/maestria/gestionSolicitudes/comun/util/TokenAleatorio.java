package com.maestria.gestionSolicitudes.comun.util;

import java.util.Random;

public class TokenAleatorio {
    public static String generarCodigoAleatorio() {
        Random random = new Random();
        StringBuilder codigo = new StringBuilder();

        // Generar 4 caracteres en mayúscula
        for (int i = 0; i < 4; i++) {
            char letraMayuscula = (char) (random.nextInt(26) + 'A');
            codigo.append(letraMayuscula);
        }

        // Generar 2 dígitos numéricos
        for (int i = 0; i < 2; i++) {
            int numero = random.nextInt(10);
            codigo.append(numero);
        }

        return codigo.toString();
    }
}
