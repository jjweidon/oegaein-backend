package com.likelion.oegaein.domain.matching.dto.reply;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.likelion.oegaein.domain.matching.entity.MatchingPostReply;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FindReplyData {
    private Long id;
    private String content;
    private Long authorId;
    private String authorName;
    private String photoUrl;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Boolean isDeleted;

    public static FindReplyData toFindReplyData(MatchingPostReply reply){
        return FindReplyData.builder()
                .id(reply.getId())
                .content(reply.getContent())
                .authorId(reply.getAuthor().getId())
                .authorName(reply.getAuthor().getProfile().getName())
                .photoUrl(reply.getAuthor().getPhotoUrl())
                .createdAt(reply.getCreatedAt())
                .modifiedAt(reply.getModifiedAt())
                .isDeleted(reply.getIsDeleted())
                .build();
    }
}
