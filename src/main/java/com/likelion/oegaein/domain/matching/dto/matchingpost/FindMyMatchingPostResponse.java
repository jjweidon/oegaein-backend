package com.likelion.oegaein.domain.matching.dto.matchingpost;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.Builder;

import java.util.List;

@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FindMyMatchingPostResponse implements ResponseDto {
    private final List<FindMyMatchingPostData> data;
}
