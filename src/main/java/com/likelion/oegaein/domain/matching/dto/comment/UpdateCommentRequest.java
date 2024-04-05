package com.likelion.oegaein.domain.matching.dto.comment;

import lombok.Data;

@Data
public class UpdateCommentRequest {
    private String receiverName; // 수신자 이름
    private String content; // 댓글 내용
}
