package com.likelion.oegaein.domain.matching.dto.comment;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UpdateCommentRequest {
    @NotEmpty
    private String content; // 댓글 내용
}