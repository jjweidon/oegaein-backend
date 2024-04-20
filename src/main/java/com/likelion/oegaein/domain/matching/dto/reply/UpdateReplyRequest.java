package com.likelion.oegaein.domain.matching.dto.reply;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UpdateReplyRequest {
    @NotEmpty
    private String content;
}
