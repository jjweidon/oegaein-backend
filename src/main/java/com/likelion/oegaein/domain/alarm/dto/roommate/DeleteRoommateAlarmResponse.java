package com.likelion.oegaein.domain.alarm.dto.roommate;

import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DeleteRoommateAlarmResponse implements ResponseDto {
    private Long roommateAlarmId;
}