package com.likelion.oegaein.domain.member.validation;

import com.likelion.oegaein.domain.member.exception.RefreshTokenException;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenValidator {
    private final String NOT_EQUAL_REFRESH_TOKEN_ERR_MSG = "Not equal to refresh token in database";
    public void validateIsEqualToRefreshTokenInDb(String receivedToken, String inDbToken){
        if(!receivedToken.equals(inDbToken)){
            throw new RefreshTokenException(NOT_EQUAL_REFRESH_TOKEN_ERR_MSG);
        }
    }
}