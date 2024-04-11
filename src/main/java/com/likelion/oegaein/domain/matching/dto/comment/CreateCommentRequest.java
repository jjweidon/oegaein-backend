package com.likelion.oegaein.domain.matching.dto.comment;

import lombok.Data;

@Data
public class CreateCommentRequest {
    private Long matchingPostId; // 매칭글 ID
    private String content; // 댓글 내용
}
