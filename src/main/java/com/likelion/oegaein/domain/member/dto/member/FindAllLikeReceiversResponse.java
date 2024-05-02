package com.likelion.oegaein.domain.member.dto.member;

import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class FindAllLikeReceiversResponse implements ResponseDto {
    private List<FindLikeReceiverData> data;
}
