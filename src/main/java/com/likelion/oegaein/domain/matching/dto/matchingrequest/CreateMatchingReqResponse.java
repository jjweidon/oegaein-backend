package com.likelion.oegaein.domain.matching.dto.matchingrequest;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateMatchingReqResponse implements ResponseDto {
    private final Long matchingRequestId;
}
