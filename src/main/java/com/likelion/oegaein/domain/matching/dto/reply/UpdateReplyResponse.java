package com.likelion.oegaein.domain.matching.dto.reply;

import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateReplyResponse implements ResponseDto {
    private Long replyId;
}
