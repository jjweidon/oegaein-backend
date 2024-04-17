package com.likelion.oegaein.domain.member.dto.profile;

import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateProfileResponse implements ResponseDto {
    private Long profileId;
}