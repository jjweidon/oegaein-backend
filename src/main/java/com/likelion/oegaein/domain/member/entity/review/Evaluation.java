package com.likelion.oegaein.domain.member.entity.review;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Evaluation {
    VERY_GOOD("최고예요"),
    GOOD("좋아요"),
    NORMAL("보통"),
    BAD("별로예요"),
    VERY_BAD("최악이에요");

    private final String value;

    @JsonCreator
    public static Evaluation deserializer(String value) {
        for(Evaluation evaluation : Evaluation.values()){
            if(evaluation.getValue().equals(value)) {
                return evaluation;
            }
        }
        return null;
    }

    @JsonValue
    public String serializer(){
        return value;
    }
}
