package com.likelion.oegaein.domain.alarm.service;

import com.likelion.oegaein.domain.alarm.dto.delivery.DeleteDeliveryAlarmResponse;
import com.likelion.oegaein.domain.alarm.dto.delivery.DeleteDeliveryAlarmsResponse;
import com.likelion.oegaein.domain.alarm.dto.delivery.FindDeliveryAlarmData;
import com.likelion.oegaein.domain.alarm.dto.delivery.FindDeliveryAlarmsResponse;
import com.likelion.oegaein.domain.alarm.dto.roommate.DeleteRoommateAlarmResponse;
import com.likelion.oegaein.domain.alarm.entity.DeliveryAlarm;
import com.likelion.oegaein.domain.alarm.repository.DeliveryAlarmRepository;
import com.likelion.oegaein.domain.alarm.repository.query.DeliveryAlarmQueryRepository;
import com.likelion.oegaein.domain.member.entity.Member;
import com.likelion.oegaein.domain.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DeliveryAlarmService {
    // constants
    private final String NOT_FOUND_MEMBER_ERR_MSG = "찾을 수 없는 사용자입니다.";
    private final String NOT_FOUND_DELIVERY_ALARM_ERR_MSG = "찾을 수 없는 공동배달 알림입니다.";
    // repository
    private final DeliveryAlarmRepository deliveryAlarmRepository;
    private final DeliveryAlarmQueryRepository deliveryAlarmQueryRepository;
    private final MemberRepository memberRepository;

    public FindDeliveryAlarmsResponse findDeliveryAlarms(Authentication authentication){
        Member authenticatedMember = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        List<DeliveryAlarm> deliveryAlarms = deliveryAlarmQueryRepository.findByMemberOrderByCreatedAtDesc(authenticatedMember.getId());
        List<FindDeliveryAlarmData> deliveryAlarmsData = deliveryAlarms.stream()
                .map(FindDeliveryAlarmData::toFindDeliveryAlarmData).toList();
        return FindDeliveryAlarmsResponse.builder()
                .count(deliveryAlarmsData.size())
                .data(deliveryAlarmsData)
                .build();
    }

    @Transactional
    public DeleteDeliveryAlarmsResponse removeDeliveryAlarms(Authentication authentication){
        Member authenticatedMember = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        int deletedDeliveryAlarmCount = deliveryAlarmQueryRepository.deleteAllByMember(authenticatedMember.getId());
        return new DeleteDeliveryAlarmsResponse(deletedDeliveryAlarmCount);
    }

    @Transactional
    public DeleteDeliveryAlarmResponse removeDeliveryAlarm(Long deliveryAlarmId, Authentication authentication){
        Member authenticatedMember = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        DeliveryAlarm deliveryAlarm = deliveryAlarmRepository.findById(deliveryAlarmId)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_DELIVERY_ALARM_ERR_MSG));
        // validation code
        deliveryAlarmRepository.delete(deliveryAlarm);
        return new DeleteDeliveryAlarmResponse(deliveryAlarmId);
    }
}
