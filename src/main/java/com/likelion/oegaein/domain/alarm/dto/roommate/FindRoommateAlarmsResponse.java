package com.likelion.oegaein.domain.alarm.dto.roommate;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FindRoommateAlarmsResponse implements ResponseDto {
    private int count;
    private List<FindRoommateAlarmsData> data;
}
