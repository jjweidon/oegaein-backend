package com.likelion.oegaein.domain.delivery.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FindDeliveryPostsResponse {
    private Long id;
    private String restaurantName;
    private int nowApplicants;
    private int maxApplicants;
    private boolean like;
    private LocalDateTime deadLine;
}
