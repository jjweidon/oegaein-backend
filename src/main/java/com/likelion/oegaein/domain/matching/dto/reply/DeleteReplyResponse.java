package com.likelion.oegaein.domain.matching.dto.reply;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DeleteReplyResponse implements ResponseDto {
    private Long replyId;
}