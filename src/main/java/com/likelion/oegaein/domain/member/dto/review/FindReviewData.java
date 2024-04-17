package com.likelion.oegaein.domain.member.dto.review;

import com.likelion.oegaein.domain.matching.entity.DongType;
import com.likelion.oegaein.domain.member.entity.review.Evaluation;
import com.likelion.oegaein.domain.member.entity.review.Review;
import com.likelion.oegaein.domain.member.entity.review.Semester;
import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindReviewData implements ResponseDto {
    private String writerName;
    private Evaluation evaluation;
    private Semester semester;
    private DongType dormitory;
    private String content;

    public static FindReviewData of(Review review) {
        return FindReviewData.builder()
                .writerName(review.getWriter().getProfile().getName())
                .evaluation(review.getEvaluation())
                .semester(review.getSemester())
                .dormitory(review.getDormitory())
                .content(review.getContent())
                .build();
    }
}
