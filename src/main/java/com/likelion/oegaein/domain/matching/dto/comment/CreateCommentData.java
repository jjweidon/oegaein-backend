package com.likelion.oegaein.domain.matching.dto.comment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCommentData {
    private Long matchingPostId;
    private String content;

    public static CreateCommentData toCreateCommentData(CreateCommentRequest dto){
        return CreateCommentData.builder()
                .matchingPostId(dto.getMatchingPostId())
                .content(dto.getContent())
                .build();
    }
}