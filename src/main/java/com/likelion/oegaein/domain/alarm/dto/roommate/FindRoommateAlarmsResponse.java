package com.likelion.oegaein.domain.alarm.dto.roommate;

import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class FindRoommateAlarmsResponse implements ResponseDto {
    private int count;
    private List<FindRoommateAlarmsData> data;
}
