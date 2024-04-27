package com.cashrich.CashRichAssessment.DTO;

public class ApiResponse {
    // Define properties for the response from the third-party API
    private String message;
    private Object data;

    // Constructors, getters, and setters
    public ApiResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
