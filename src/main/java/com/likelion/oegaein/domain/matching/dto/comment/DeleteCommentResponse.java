package com.likelion.oegaein.domain.matching.dto.comment;

import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteCommentResponse implements ResponseDto {
    private Long commentId;
}
