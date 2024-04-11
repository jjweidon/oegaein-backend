package com.likelion.oegaein.domain.matching.dto.reply;

import lombok.Data;

@Data
public class CreateReplyRequest {
    private Long commentId;
    private String content;
}
