package com.likelion.oegaein.domain.delivery.dto.deliveryPost;


import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CreateDeliveryPostRequest {
    private String restaurantName;
    private String locate;
    private int maxApplicants;
    private LocalDateTime deadLine;
    private String content;
}
