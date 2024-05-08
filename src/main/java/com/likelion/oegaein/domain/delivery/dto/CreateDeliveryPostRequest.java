package com.likelion.oegaein.domain.delivery.dto;


import java.time.LocalDate;

import com.likelion.oegaein.domain.delivery.Entity.FoodType;
import lombok.Data;

@Data
public class CreateDeliveryPostRequest {
    private String title; // 제목
    private String address; // 위치
    private FoodType foodType; // 음식 종류
    private int targetNumberOfPeople; // 목표 인원수
    private LocalDate deadLine; // 마감 기한
    private String content; // 상세 내용
}