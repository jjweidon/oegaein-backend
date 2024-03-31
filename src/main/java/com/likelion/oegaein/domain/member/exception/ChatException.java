package com.likelion.oegaein.domain.member.exception;

public class ChatException extends RuntimeException{
    public ChatException(String message) {
        super(message);
    }
    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
