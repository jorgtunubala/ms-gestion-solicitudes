package com.maestria.gestionSolicitudes.comun.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BooleanConverter implements AttributeConverter<Boolean, Character> {
    @Override
    public Character convertToDatabaseColumn(Boolean b) {
        if (b == null) {
            return null;
        }
        if (b.booleanValue()) {
            return 'T';
        }
        return 'F';
    }

    @Override
    public Boolean convertToEntityAttribute(Character s) {
        if (s == null) {
            return null;
        }
        if (s.equals('T')) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
