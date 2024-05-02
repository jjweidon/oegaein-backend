package com.likelion.oegaein.domain.member.dto.review;

import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class FindMemberReviewsResponse implements ResponseDto {
    private final List<FindReviewData> data;
}
