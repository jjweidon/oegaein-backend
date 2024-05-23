package com.likelion.oegaein.domain.member.dto.profile;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UpdateProfileResponse implements ResponseDto {
    private Long profileId;
}