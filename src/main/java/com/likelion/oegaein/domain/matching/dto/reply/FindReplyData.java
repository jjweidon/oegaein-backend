package com.likelion.oegaein.domain.matching.dto.reply;

import com.likelion.oegaein.domain.matching.entity.Reply;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class FindReplyData {
    private Long id;
    private String content;
    private Long authorId;
    private String authorName;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Boolean isDeleted;

    public static FindReplyData toFindReplyData(Reply reply){
        return FindReplyData.builder()
                .id(reply.getId())
                .content(reply.getContent())
                .authorId(reply.getAuthor().getId())
                .authorName(reply.getAuthor().getProfile().getName())
                .createdAt(reply.getCreatedAt())
                .modifiedAt(reply.getModifiedAt())
                .isDeleted(reply.getIsDeleted())
                .build();
    }
}
