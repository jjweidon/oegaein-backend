package com.likelion.oegaein.domain.member.dto;

import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GoogleOauthLoginResponse implements ResponseDto {
    private String email;
    private String accessToken;
    private String refreshToken;
}
