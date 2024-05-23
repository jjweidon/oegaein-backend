package com.likelion.oegaein.domain.matching.dto.comment;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DeleteCommentResponse implements ResponseDto {
    private Long commentId;
}