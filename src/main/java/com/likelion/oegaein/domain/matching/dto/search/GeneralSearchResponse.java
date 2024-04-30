package com.likelion.oegaein.domain.matching.dto.search;

import com.likelion.oegaein.domain.matching.dto.matchingpost.FindMatchingPostsData;
import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GeneralSearchResponse implements ResponseDto {
    List<FindMatchingPostsData> matchingPostsData;
    // List<> deliveryPostsData;
}
