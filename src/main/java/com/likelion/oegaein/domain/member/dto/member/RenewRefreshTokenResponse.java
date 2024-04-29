package com.likelion.oegaein.domain.member.dto.member;

import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RenewRefreshTokenResponse implements ResponseDto {
    private String accessToken;
}