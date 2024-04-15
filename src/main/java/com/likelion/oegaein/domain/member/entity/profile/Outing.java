package com.likelion.oegaein.domain.member.entity.profile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Outing {
    HOMEBODY("집돌이", "집순이"),
    ITCHY_FEET("밖돌이", "밖순이");

    private final String maleValue;
    private final String femaleValue;

    @JsonCreator
    public static Outing deserializer(String value) {
        for(Outing outing : Outing.values()){
            if(outing.getMaleValue().equals(value)) {
                return outing;
            }
        }
        return null;
    }

    @JsonValue
    public String serializer(){
        return maleValue;
    }
}
