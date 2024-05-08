package com.likelion.oegaein.domain.delivery.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FindDeliveryPostsResponse {
    private Long id;
    private String restaurantName;
    private int nowApplicants;
    private int maxApplicants;
    private boolean like;
    private LocalDateTime deadLine;
}
