package com.example.demae.global.message;

public enum ErrorMessage {
    NOT_EXIST_TOKEN_ERROR_MESSAGE("토큰을 찾을 수 없습니다.");
    private final String errorMessage;

    ErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return "[ERROR] " + errorMessage;
    }
}
