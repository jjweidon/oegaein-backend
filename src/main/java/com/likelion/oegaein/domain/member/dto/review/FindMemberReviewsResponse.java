package com.likelion.oegaein.domain.member.dto.review;

import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Builder
public class FindMemberReviewsResponse implements ResponseDto {
    private final List<FindReviewData> data;
}
