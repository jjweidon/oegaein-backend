package com.likelion.oegaein.domain.matching.dto.comment;

import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DeleteCommentResponse implements ResponseDto {
    private Long commentId;
}
