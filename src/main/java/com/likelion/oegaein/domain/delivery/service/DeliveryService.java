package com.likelion.oegaein.domain.delivery.service;

import com.likelion.oegaein.domain.delivery.Entity.Delivery;
import com.likelion.oegaein.domain.delivery.dto.CreateDeliveryPostData;
import com.likelion.oegaein.domain.delivery.dto.CreateDeliveryPostResponse;
import com.likelion.oegaein.domain.delivery.repository.DeliveryRepository;

import com.likelion.oegaein.domain.member.entity.member.Member;
import com.likelion.oegaein.domain.member.repository.MemberRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@PropertySource("classpath:application.yaml")
public class DeliveryService {
    private final String NOT_FOUND_MEMBER_ERR_MSG = "찾을 수 없는 사용자입니다.";
    private final DeliveryRepository deliveryRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public CreateDeliveryPostResponse saveDelivery(CreateDeliveryPostData dto) {
        Delivery delivery = Delivery.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .address(dto.getAddress())
                .foodType(dto.getFoodType())
                .foodImageUrl("www.naver.com")
                .targetNumberOfPeople(dto.getTargetNumberOfPeople())
                .deadLine(dto.getDeadLine())
                .content(dto.getContent())
                //.author(authenticatedMember)
                .build();
        deliveryRepository.save(delivery);
        return new CreateDeliveryPostResponse(delivery.getId());
    }
}
