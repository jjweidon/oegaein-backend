package com.likelion.oegaein.domain.matching.dto.comment;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.likelion.oegaein.domain.matching.dto.reply.FindReplyData;
import com.likelion.oegaein.domain.matching.entity.MatchingPostComment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FindCommentData {
    private Long id;
    private String content;
    private Long authorId;
    private String authorName;
    private String photoUrl;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Boolean isDeleted;
    private List<FindReplyData> replies;

    public static FindCommentData toFindCommentData(MatchingPostComment comment){
        List<FindReplyData> repliesData = comment.getReplies().stream()
                .map(FindReplyData::toFindReplyData).toList();
        return FindCommentData.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .authorId(comment.getAuthor().getId())
                .authorName(comment.getAuthor().getProfile().getName())
                .photoUrl(comment.getAuthor().getPhotoUrl())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .isDeleted(comment.getIsDeleted())
                .replies(repliesData)
                .build();
    }
}
