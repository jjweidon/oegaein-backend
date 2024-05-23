package com.likelion.oegaein.domain.matching.dto.matchingrequest;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RejectMatchingReqResponse implements ResponseDto {
    private Long matchingRequestId; // 매칭 요청 ID
}
