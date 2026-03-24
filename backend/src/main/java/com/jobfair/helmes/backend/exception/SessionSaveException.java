package com.jobfair.helmes.backend.exception;

public class SessionSaveException extends RuntimeException {
    public SessionSaveException(String message) {
        super(message);
    }

    public SessionSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}