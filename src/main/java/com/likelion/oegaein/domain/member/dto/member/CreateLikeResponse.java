package com.likelion.oegaein.domain.member.dto.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateLikeResponse implements ResponseDto {
    @JsonProperty("like_id")
    private Long likeId;
}
