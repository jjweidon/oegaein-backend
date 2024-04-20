package com.likelion.oegaein.domain.member.exception;

public class AuthException extends RuntimeException{
    public AuthException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
