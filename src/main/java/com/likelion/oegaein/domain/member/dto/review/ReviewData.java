package com.likelion.oegaein.domain.member.dto.review;

import com.likelion.oegaein.domain.matching.entity.DongType;
import com.likelion.oegaein.domain.member.entity.review.Evaluation;
import com.likelion.oegaein.domain.member.entity.review.Semester;
import lombok.Data;

@Data
public class ReviewData {
    private Evaluation evaluation;
    private Semester semester;
    private DongType dormitory;
    private String content;
}