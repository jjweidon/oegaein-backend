package com.likelion.oegaein.domain.matching.dto.reply;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateReplyRequest {
    @NotNull
    @Positive
    private Long commentId;

    @NotEmpty
    private String content;
}