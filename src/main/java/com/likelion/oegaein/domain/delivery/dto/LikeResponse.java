package com.likelion.oegaein.domain.delivery.dto;

import com.likelion.oegaein.domain.delivery.Entity.Delivery;
import com.likelion.oegaein.domain.member.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeResponse {
    private Long id;
    private Member member;
    private Delivery delivery;
}
