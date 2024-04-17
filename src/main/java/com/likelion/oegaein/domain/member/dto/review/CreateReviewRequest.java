package com.likelion.oegaein.domain.member.dto.review;

import com.likelion.oegaein.domain.member.entity.Member;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateReviewRequest extends ReviewData {
    private Member receiver;
}
