package com.likelion.oegaein.domain.member.dto.review;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.likelion.oegaein.domain.matching.entity.DongType;
import com.likelion.oegaein.domain.member.entity.review.Evaluation;
import com.likelion.oegaein.domain.member.entity.review.Review;
import com.likelion.oegaein.domain.member.entity.review.Semester;
import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FindReviewData implements ResponseDto {
    private Long writerId;
    private String writerName;
    private String writerPhotoUrl;
    private Evaluation evaluation;
    private Semester semester;
    private DongType dormitory;
    private String content;

    public static FindReviewData of(Review review) {
        return FindReviewData.builder()
                .writerId(review.getWriter().getId())
                .writerName(review.getWriter().getProfile().getName())
                .writerPhotoUrl(review.getWriter().getPhotoUrl())
                .evaluation(review.getEvaluation())
                .semester(review.getSemester())
                .dormitory(review.getDormitory())
                .content(review.getContent())
                .build();
    }
}
