package com.likelion.oegaein.domain.member.entity.review;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Evaluation {
    VERY_GOOD("최고예요"),
    GOOD("좋아요"),
    NORMAL("보통"),
    BAD("별로예요"),
    VERY_BAD("최악이에요");

    private final String value;
}
