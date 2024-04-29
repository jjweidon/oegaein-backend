package com.likelion.oegaein.domain.alarm.dto.delivery;

import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DeleteDeliveryAlarmResponse implements ResponseDto {
    private Long deliveryAlarmId;
}
