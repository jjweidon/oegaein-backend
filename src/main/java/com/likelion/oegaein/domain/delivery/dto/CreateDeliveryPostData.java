package com.likelion.oegaein.domain.delivery.dto;
import java.time.LocalDate;

import com.likelion.oegaein.domain.delivery.Entity.FoodType;
import lombok.Data;

@Data
public class CreateDeliveryPostData {
    private String title; // 제목
    private String address; // 위치
    private FoodType foodType; // 음식 종류
    private int targetNumberOfPeople; // 목표 인원수
    private LocalDate deadLine; // 마감 기한
    private String content; // 상세 내용

    public static CreateDeliveryPostData toCreateDeliveryPostData(CreateDeliveryPostRequest dto) {
        CreateDeliveryPostData createDeliveryPostData = new CreateDeliveryPostData();
        createDeliveryPostData.setTitle(dto.getTitle());
        createDeliveryPostData.setAddress(dto.getAddress());
        createDeliveryPostData.setFoodType(dto.getFoodType());
        createDeliveryPostData.setTargetNumberOfPeople(dto.getTargetNumberOfPeople());
        createDeliveryPostData.setDeadLine(dto.getDeadLine());
        createDeliveryPostData.setContent(dto.getContent());
        return createDeliveryPostData;
    }
}
