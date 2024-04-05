package com.likelion.oegaein.domain.matching.dto.comment;

import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateCommentResponse implements ResponseDto {
    private Long commentId;
}
