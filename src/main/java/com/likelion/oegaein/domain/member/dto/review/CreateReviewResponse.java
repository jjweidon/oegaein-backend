package com.likelion.oegaein.domain.member.dto.review;

import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateReviewResponse implements ResponseDto {
    private Long reviewId;
}
