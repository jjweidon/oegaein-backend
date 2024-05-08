package com.likelion.oegaein.domain.delivery.controller;


import com.likelion.oegaein.domain.delivery.dto.FindDeliveryPostsResponse;
import com.likelion.oegaein.domain.delivery.dto.CreateDeliveryPostData;
import com.likelion.oegaein.domain.delivery.dto.CreateDeliveryPostRequest;
import com.likelion.oegaein.domain.delivery.dto.CreateDeliveryPostResponse;
import com.likelion.oegaein.domain.delivery.service.DeliveryService;


import java.util.List;

import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DeliveryPostApiController {
    private final DeliveryService deliveryService;
    @PostMapping("/api/v1/delivery") // 공동 배달 글 작성
    public ResponseEntity<ResponseDto> postDelivery(@RequestBody CreateDeliveryPostRequest dto) {
        log.info("Request to post delivery");
        CreateDeliveryPostData createDeliveryPostData = CreateDeliveryPostData.toCreateDeliveryPostData(dto);
        CreateDeliveryPostResponse response = deliveryService.saveDelivery(createDeliveryPostData); // CreateDeliveryPostResponse response =
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}



