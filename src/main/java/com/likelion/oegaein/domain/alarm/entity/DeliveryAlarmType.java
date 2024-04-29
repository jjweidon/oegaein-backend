package com.likelion.oegaein.domain.alarm.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum DeliveryAlarmType {
    DELIVERY_REQUEST("공동 배달 요청"),
    DELIVERY_REQUEST_ACCEPT("공동 배달 수락"),
    DELIVERY_REQUEST_REJECT("공동 배달 거부"),
    DELIVERY_POST_COMPLETED("공동 배달 완료");

    DeliveryAlarmType(String value){
        this.value = value;
    }
    private final String value;

    @JsonCreator
    public DeliveryAlarmType deserializeAlarmType(String value){
        for(DeliveryAlarmType alarmType : DeliveryAlarmType.values()){
            if(alarmType.getValue().equals(value)){
                return alarmType;
            }
        }
        return null;
    }

    @JsonValue
    public String serializeAlarmType(){
        return this.value;
    }
}
