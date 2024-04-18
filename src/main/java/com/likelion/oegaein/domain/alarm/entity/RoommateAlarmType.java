package com.likelion.oegaein.domain.alarm.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum RoommateAlarmType {
    MATCHING_REQUEST("매칭 요청"),
    MATCHING_REQUEST_ACCEPT("매칭 수락"),
    MATCHING_REQUEST_REJECT("매칭 거부"),
    MATCHING_POST_COMPLETED("매칭 완료");

    RoommateAlarmType(String value){
        this.value = value;
    }
    private final String value;

    @JsonCreator
    public RoommateAlarmType deserializeAlarmType(String value){
        for(RoommateAlarmType alarmType : RoommateAlarmType.values()){
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
