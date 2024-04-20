package com.likelion.oegaein.domain.email.exception;

public class EmailException extends RuntimeException{
    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public EmailException(String message) {
        super(message);
    }
}
