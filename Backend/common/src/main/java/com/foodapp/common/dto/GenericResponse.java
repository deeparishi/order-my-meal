package com.foodapp.common.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class GenericResponse<T> {

    private boolean success;
    private int statusCode;
    private String status;
    private T data;
    private List<T> datum;
    private final Date timeStamp;

    private GenericResponse() {
        this.timeStamp = new Date();
    }

    public static <T> GenericResponse<T> success(int statusCode, String status, T data) {
        GenericResponse<T> response = new GenericResponse<>();
        response.success = true;
        response.statusCode = statusCode;
        response.status = status;
        response.data = data;
        return response;
    }

    public static <T> GenericResponse<T> success(int statusCode, String status, List<T> datum) {
        GenericResponse<T> response = new GenericResponse<>();
        response.success = true;
        response.statusCode = statusCode;
        response.status = status;
        response.datum = datum;
        return response;
    }

    public static <T> GenericResponse<T> error(int statusCode, String status, T data) {
        GenericResponse<T> response = new GenericResponse<>();
        response.success = false;
        response.statusCode = statusCode;
        response.status = status;
        response.data = data;
        return response;
    }

}
