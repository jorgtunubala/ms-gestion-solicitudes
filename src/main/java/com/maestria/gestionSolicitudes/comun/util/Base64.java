package com.maestria.gestionSolicitudes.comun.util;

import java.util.Map;
import java.util.HashMap;

public class Base64 {
    
    public static Map<String, String> obtenerBase64(String cadenaConPrefijo) {

        // Crear un Map para almacenar los resultados
        Map<String, String> map = new HashMap<>();

        // Dividir la cadena y almacenar en el Map
        String[] partes = cadenaConPrefijo.split(":");
        if (partes.length == 2) {
            String prefijo = partes[0];
            String base64 = partes[1];
            map.put(prefijo, base64);

            return map;
        } else {
            return null;
        }
    }
}
