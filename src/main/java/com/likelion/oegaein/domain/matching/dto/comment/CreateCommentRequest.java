package com.likelion.oegaein.domain.matching.dto.comment;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateCommentRequest {
    @NotNull
    @Positive
    private Long matchingPostId; // 매칭글 ID

    @NotEmpty
    private String content; // 댓글 내용
}
