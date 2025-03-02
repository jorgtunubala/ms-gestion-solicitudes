package com.maestria.gestionSolicitudes.dto.rest.response;


public class FechaActualResponse {
    private String year;
    private String month;
    private String day;

    public FechaActualResponse(String year, String month, String day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }
    
}
