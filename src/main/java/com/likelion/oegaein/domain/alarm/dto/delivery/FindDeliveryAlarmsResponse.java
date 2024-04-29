package com.likelion.oegaein.domain.alarm.dto.delivery;

import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class FindDeliveryAlarmsResponse implements ResponseDto {
    private int count;
    private List<FindDeliveryAlarmData> data;
}
