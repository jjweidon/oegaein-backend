package com.likelion.oegaein.domain.member.dto.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateLikeRequest {
    @JsonProperty("receiver_id")
    private Long receiverId;
}
