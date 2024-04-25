package com.likelion.oegaein.domain.delivery.dto.deliveryPost;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CreateDeliveryPostData {
    private String restaurantName;
    private String locate;
    private int maxApplicants;
    private LocalDateTime deadLine;
    private String content;

    public static CreateDeliveryPostData toCreateDeliveryPostData(CreateDeliveryPostRequest dto) {
        CreateDeliveryPostData createDeliveryPostData = new CreateDeliveryPostData();
        createDeliveryPostData.setRestaurantName(dto.getRestaurantName());
        createDeliveryPostData.setLocate(dto.getLocate());
        createDeliveryPostData.setMaxApplicants(dto.getMaxApplicants());
        createDeliveryPostData.setDeadLine(dto.getDeadLine());
        createDeliveryPostData.setContent(dto.getContent());
        return createDeliveryPostData;
    }


}
