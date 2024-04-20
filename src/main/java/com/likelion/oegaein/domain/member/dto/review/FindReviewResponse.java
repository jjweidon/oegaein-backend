package com.likelion.oegaein.domain.member.dto.review;

import com.likelion.oegaein.domain.matching.entity.DongType;
import com.likelion.oegaein.domain.member.entity.review.Evaluation;
import com.likelion.oegaein.domain.member.entity.review.Review;
import com.likelion.oegaein.domain.member.entity.review.Semester;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class FindReviewResponse extends FindReviewData {

    public FindReviewResponse(FindReviewData data) {
        super(data.getWriterName(), data.getEvaluation(), data.getSemester(), data.getDormitory(), data.getContent());
    }
}

