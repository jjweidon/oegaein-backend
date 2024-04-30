package com.likelion.oegaein.domain.member.dto.review;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class FindReviewResponse extends FindReviewData {

    public FindReviewResponse(FindReviewData data) {
        super(data.getWriterId(), data.getWriterName(), data.getWriterPhotoUrl(), data.getEvaluation(), data.getSemester(), data.getDormitory(), data.getContent());
    }
}

