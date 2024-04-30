package com.likelion.oegaein.domain.member.exception;

public class RefreshTokenException extends RuntimeException{
    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public RefreshTokenException(String message) {
        super(message);
    }
}
