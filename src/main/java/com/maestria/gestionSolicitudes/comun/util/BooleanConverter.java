package com.maestria.gestionSolicitudes.comun.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.maestria.gestionSolicitudes.comun.constant.Constantes;

@Converter
public class BooleanConverter implements AttributeConverter<Boolean, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Boolean valor) {
        if (valor == null) {
            return Constantes.CERO;
        }
        if (valor.booleanValue()) {
            return Constantes.UNO;
        }
        return Constantes.CERO;
    }

    @Override
    public Boolean convertToEntityAttribute(Integer valor) {
        if (valor == null) {
            return Boolean.FALSE;
        }
        if (valor.equals(Constantes.UNO)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
