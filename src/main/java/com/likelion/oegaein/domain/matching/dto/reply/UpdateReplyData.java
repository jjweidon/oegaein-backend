package com.likelion.oegaein.domain.matching.dto.reply;

import com.likelion.oegaein.domain.matching.dto.comment.UpdateCommentData;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateReplyData {
    public String content;

    public static UpdateReplyData toUpdateReplyData(UpdateReplyRequest dto){
        return UpdateReplyData.builder()
                .content(dto.getContent())
                .build();
    }
}
