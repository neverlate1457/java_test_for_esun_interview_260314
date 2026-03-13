package com.esun.voting.dto;

public class ApiResponse<T> {
    private String message;
    private T data;

    public ApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    // Jackson 需要 Getter 才能把物件轉成 JSON
    public String getMessage() { return message; }
    public T getData() { return data; }
    
    public void setMessage(String message) { this.message = message; }
    public void setData(T data) { this.data = data; }
}