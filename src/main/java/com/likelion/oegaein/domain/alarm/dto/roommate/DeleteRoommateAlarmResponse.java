package com.likelion.oegaein.domain.alarm.dto.roommate;

import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeleteRoommateAlarmResponse implements ResponseDto {
    private Long roommateAlarmId;
}
