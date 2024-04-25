package com.likelion.oegaein.domain.member.dto.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateReviewResponse implements ResponseDto {
    private Long reviewId;
}
