package com.likelion.oegaein.domain.alarm.dto.roommate;

import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeleteRoommateAlarmsResponse implements ResponseDto {
    private int deletedRoommateAlarmCount;
}
