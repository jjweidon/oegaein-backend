package com.likelion.oegaein.domain.matching.dto.comment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateCommentData {
    private String content;

    public static UpdateCommentData toUpdateCommentData(UpdateCommentRequest dto){
        return UpdateCommentData.builder()
                .content(dto.getContent())
                .build();
    }
}
