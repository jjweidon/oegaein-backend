package com.likelion.oegaein.domain.member.dto.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DeleteLikeRequest {
    @JsonProperty("receiver")
    private Long receiver;
}