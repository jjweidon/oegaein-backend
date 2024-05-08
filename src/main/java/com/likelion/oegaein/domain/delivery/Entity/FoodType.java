package com.likelion.oegaein.domain.delivery.Entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.likelion.oegaein.domain.member.entity.review.Evaluation;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FoodType {
    JOK_BO("족발-보쌈"),
    MEAT("고기-구이"),
    MIDNIGHT_MEAL("야식"),
    KOREAN("백반-죽-국수"),
    JAPANESE("일식"),
    CHINESE("중식"),
    WESTERN("양식"),
    ASIAN("아시안"),
    CHICKEN("치킨"),
    PIZZA("피자"),
    FAST_FOOD("패스트푸드"),
    CAFE("카페"),
    DESSERT("디저트"),
    LUNCH_BOX("도시락"),
    SCHOOL_FOOD("분식");

    private final String value;

    @JsonCreator
    public static FoodType deserializer(String value) {
        for(FoodType foodType: FoodType.values()){
            if(foodType.getValue().equals(value)) {
                return foodType;
            }
        }
        return null;
    }

    @JsonValue
    public String serializer(){
        return value;
    }
}
