package com.likelion.oegaein.domain.delivery.dto.deliveryPost;

import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class CreateDeliveryPostResponse implements ResponseDto {
    private Long deliveryPostId;


}