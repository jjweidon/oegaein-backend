package com.likelion.oegaein.domain.member.dto.review;

import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateReviewResponse implements ResponseDto {
    private Long reviewId;
}
