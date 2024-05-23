package com.likelion.oegaein.domain.member.dto.member;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RenewRefreshTokenResponse implements ResponseDto {
    private String accessToken;
    private boolean profileSetUpStatus;
}