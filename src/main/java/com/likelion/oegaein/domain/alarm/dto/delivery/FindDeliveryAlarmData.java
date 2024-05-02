package com.likelion.oegaein.domain.alarm.dto.delivery;

import com.likelion.oegaein.domain.alarm.entity.DeliveryAlarm;
import com.likelion.oegaein.domain.alarm.entity.DeliveryAlarmType;
import com.likelion.oegaein.domain.member.entity.member.Member;
import com.likelion.oegaein.domain.member.entity.profile.Profile;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindDeliveryAlarmData {
    private Long deliveryAlarmId; // 공동 배달 알림 ID
    private String name;
    private String photoUrl;
    private DeliveryAlarmType deliveryAlarmType;
    private Long deliveryPostId; // 공동 배달 매칭글 ID
    private String title; // 매칭글 제목

    public static FindDeliveryAlarmData toFindDeliveryAlarmData(DeliveryAlarm deliveryAlarm){
        Member member = deliveryAlarm.getMember();
        Profile profile = member.getProfile();
        return FindDeliveryAlarmData.builder()
                .deliveryAlarmId(deliveryAlarm.getId())
                .name(profile.getName())
                .photoUrl(member.getPhotoUrl())
                .deliveryAlarmType(deliveryAlarm.getAlarmType())
                .build();
    }
}
