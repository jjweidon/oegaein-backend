package com.likelion.oegaein.domain.delivery.dto;

import com.likelion.oegaein.domain.delivery.Entity.FoodType;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class FindDeliveryPostsData {
    private Long id;
    private String title;
    private FoodType foodType;
    private String foodImageUrl;
    private int currentNumberOfPeople; // 현재 모집 인원수
    private int targetNumberOfPeople; // 목표 인원수
    private LocalDate deadLine; // 마감 기한
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
