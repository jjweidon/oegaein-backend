package com.likelion.oegaein.domain.matching.dto.comment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCommentData {
    private Long matchingPostId;
    private Long parentCommentId;
    private String receiverName;
    private String content;

    public static CreateCommentData toCreateCommentData(CreateCommentRequest dto){
        return CreateCommentData.builder()
                .matchingPostId(dto.getMatchingPostId())
                .parentCommentId(dto.getParentCommentId())
                .receiverName(dto.getReceiverName())
                .content(dto.getContent())
                .build();
    }
}