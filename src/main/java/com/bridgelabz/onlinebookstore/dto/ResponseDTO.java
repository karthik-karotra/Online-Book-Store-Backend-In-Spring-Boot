package com.bridgelabz.onlinebookstore.dto;

public class ResponseDTO {
    public String message;
    public Object data;

    public ResponseDTO(String message) {
        this.message = message;
    }

    public ResponseDTO(Object data, String message) {
        this.message = message;
        this.data = data;
    }
}
