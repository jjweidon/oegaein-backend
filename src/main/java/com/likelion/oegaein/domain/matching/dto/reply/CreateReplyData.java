package com.likelion.oegaein.domain.matching.dto.reply;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateReplyData {
    private Long commentId;
    private String content;
    public static CreateReplyData toCreateReplyData(CreateReplyRequest dto){
        return CreateReplyData.builder()
                .commentId(dto.getCommentId())
                .content(dto.getContent())
                .build();
    }
}
