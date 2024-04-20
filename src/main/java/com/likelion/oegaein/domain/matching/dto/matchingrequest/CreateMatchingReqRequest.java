package com.likelion.oegaein.domain.matching.dto.matchingrequest;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateMatchingReqRequest {
    @NotNull
    @Positive
    private Long matchingPostId; // 매칭글 ID
}
