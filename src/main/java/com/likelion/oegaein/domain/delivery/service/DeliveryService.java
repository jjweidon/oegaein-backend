package com.likelion.oegaein.domain.delivery.service;

import com.likelion.oegaein.domain.delivery.Entity.Delivery;
import com.likelion.oegaein.domain.delivery.dto.DeliveryBasicResponse;
import com.likelion.oegaein.domain.delivery.dto.deliveryPost.CreateDeliveryPostData;
import com.likelion.oegaein.domain.delivery.dto.deliveryPost.CreateDeliveryPostResponse;
import com.likelion.oegaein.domain.delivery.dto.deliveryPost.PutDeliveryPostRequest;
import com.likelion.oegaein.domain.delivery.repository.DeliveryRepository;

import com.likelion.oegaein.domain.member.repository.MemberRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

@Service
@RequiredArgsConstructor
@Transactional
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final MemberRepository memberRepository;

    public CreateDeliveryPostResponse save(CreateDeliveryPostData request) {

        String restaurantImagesFolderPath = "images/restaurants/" + request.getRestaurantName() + "/";

        File restaurantDir = new File(restaurantImagesFolderPath);
        String[] files = restaurantDir.list();

        String imagePath = "";
        if (files != null && files.length > 0) {
            imagePath = restaurantImagesFolderPath + files[0];
        }

        Delivery newDelivery = Delivery.builder()
                .restaurantName(request.getRestaurantName())
                .locate(request.getLocate())
                .maxApplicants(request.getMaxApplicants())
                .nowApplicants(0)
                .deadLine(request.getDeadLine())
                .content(request.getContent())
                .restaurantImageUrl(imagePath)
                .heart(false)
                .build();
        deliveryRepository.save(newDelivery);
        Long deliveryPostId = newDelivery.getId();
        return new CreateDeliveryPostResponse(deliveryPostId);
    }

    @Transactional(readOnly = true)
    public List<DeliveryBasicResponse> basicAllDeliveryList() {
        List<Delivery> deliveryList = deliveryRepository.findAll();

        List<Delivery> sortedDeliveries = deliveryList.stream()
                .sorted(Comparator.comparing(Delivery::getCreatedAt).reversed())
                .collect(Collectors.toList());

        return sortedDeliveries.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }


    private DeliveryBasicResponse convertToResponse(Delivery delivery) {
        return DeliveryBasicResponse.builder()
                .id(delivery.getId())
                .restaurantName(delivery.getRestaurantName())
                .nowApplicants(delivery.getNowApplicants())
                .maxApplicants(delivery.getMaxApplicants())
                .like(delivery.isHeart())
                .deadLine(delivery.getDeadLine())
                .build();
    }
    @Transactional(readOnly = true)
    public List<DeliveryBasicResponse> deadLineDeliveryList() {
        List<DeliveryBasicResponse> resultList = new ArrayList<>();

        List<DeliveryBasicResponse> urgentDeliveries = findUrgentDeliveries();
        resultList.addAll(urgentDeliveries);
        int remainingCapacity = 20 - resultList.size();
        if (remainingCapacity > 0) {
            List<DeliveryBasicResponse> restOfDeliveries = findRemainingDeliveries(remainingCapacity);
            resultList.addAll(restOfDeliveries);
        }

        return resultList;
    }

    private List<DeliveryBasicResponse> findUrgentDeliveries() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return deliveryRepository.findAll().stream()
                .sorted(Comparator.comparing(delivery -> delivery.getDeadLine().minusHours(currentDateTime.getHour())))
                .limit(20)  // Limit to 20 deliveries
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private List<DeliveryBasicResponse> findRemainingDeliveries(int limit) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return deliveryRepository.findAll().stream()
                .filter(delivery -> currentDateTime.isBefore(delivery.getDeadLine()))
                .sorted(Comparator.comparing(Delivery::getDeadLine))
                .limit(limit)  // Limit to the remaining capacity
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DeliveryBasicResponse> deadLineAllDeliveryList() {
        // 현재 시간을 가져옵니다.
        LocalDateTime currentDateTime = LocalDateTime.now();

        // 모든 배송 정보를 가져옵니다.
        List<Delivery> deliveryList = deliveryRepository.findAll();

        // 마감 시간이 얼마 안 남은 순서대로 배송을 정렬합니다.
        List<Delivery> sortedDeliveries = deliveryList.stream()
                .sorted(Comparator.comparing(delivery -> {
                    Duration timeUntilDeadline = Duration.between(currentDateTime, delivery.getDeadLine());
                    return timeUntilDeadline.getSeconds(); // 현재 시간부터 마감 시간까지의 시간 차이를 반환
                }))
                .collect(Collectors.toList());

        // Delivery 객체를 DeliveryBasicResponse로 변환하고 이를 리스트로 반환합니다.
        return sortedDeliveries.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public PutDeliveryPostRequest update(PutDeliveryPostRequest dto) {
        Optional<Delivery> optionalDelivery = deliveryRepository.findById(dto.getId());

        if(optionalDelivery.isPresent()) {
            Delivery delivery = optionalDelivery.get();
            delivery.setHeart(true);
            return dto;
        } else {
            return null;
        }
    }

//    public LikeResponse likeDelivery(Long id, String name) {
//        Member member = memberRepository.findByEmail(name);
//        Optional<Delivery> delivery = deliveryRepository.findById(id);
//        LikeResponse goodResponse = new LikeResponse();
//        //if(member.) -> member 수정 필요
//    }
}
