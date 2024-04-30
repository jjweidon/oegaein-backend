package com.likelion.oegaein.domain.delivery.controller;


import com.likelion.oegaein.domain.delivery.dto.DeliveryBasicResponse;
import com.likelion.oegaein.domain.delivery.dto.LikeResponse;
import com.likelion.oegaein.domain.delivery.dto.deliveryPost.CreateDeliveryPostData;
import com.likelion.oegaein.domain.delivery.dto.deliveryPost.CreateDeliveryPostRequest;
import com.likelion.oegaein.domain.delivery.dto.deliveryPost.CreateDeliveryPostResponse;
import com.likelion.oegaein.domain.delivery.dto.deliveryPost.PutDeliveryPostRequest;
import com.likelion.oegaein.domain.delivery.service.DeliveryService;


import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DeliveryPostApiController {


    private final DeliveryService deliveryService;

    @PostMapping("/api/v1/deliverypost") // 글 작성
    public ResponseEntity<CreateDeliveryPostResponse> postDeliveryPost(@RequestBody CreateDeliveryPostRequest dto) {
        CreateDeliveryPostData createDeliveryPostData = CreateDeliveryPostData.toCreateDeliveryPostData(dto);
        CreateDeliveryPostResponse response = deliveryService.save(createDeliveryPostData);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/api/v1/delivery/basic-all") // 기본 배달 목록조회
    public ResponseEntity<List<DeliveryBasicResponse>> deliveryBasicAllList() {
        List<DeliveryBasicResponse> deliveryList = deliveryService.basicAllDeliveryList();
        return ResponseEntity.ok(deliveryList);
    }

    @GetMapping("/api/v1/delivery/deadline-all") // 마감 배달 목록 조회
    public ResponseEntity<List<DeliveryBasicResponse>> deliveryDeadlineAllList() {
        List<DeliveryBasicResponse> deliveryList = deliveryService.deadLineAllDeliveryList();
        return ResponseEntity.ok(deliveryList);
    }

    @GetMapping("/api/v1/delivery/deadline") // 곧 마감 예정 목록
    public ResponseEntity<List<DeliveryBasicResponse>> deliveryDeadLineList() {
        List<DeliveryBasicResponse> deliveryList = deliveryService.deadLineDeliveryList();
        return ResponseEntity.ok(deliveryList);
    }

    //    @PutMapping("/api/v1/devivery/liked")
//    public ResponseEntity<PutDeliveryPostRequest> deliveryLiked(@RequestBody PutDeliveryPostRequest dto) {
//        PutDeliveryPostRequest response = deliveryService.update(dto);
//    }
//    @PostMapping("/api/v1/delivery/like/{id}")
//    public ResponseEntity<LikeResponse> likeDelivery(@PathVariable Long id, Authentication authentication) {
//        LikeResponse likeResponse = deliveryService.likeDelivery(id, authentication.getName());
//        return ResponseEntity.ok(likeResponse);
//    }
}



