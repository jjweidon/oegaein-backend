package com.likelion.oegaein.domain.alarm.dto.roommate;

import com.likelion.oegaein.domain.alarm.entity.RoommateAlarm;
import com.likelion.oegaein.domain.alarm.entity.RoommateAlarmType;
import com.likelion.oegaein.domain.matching.entity.MatchingPost;
import com.likelion.oegaein.domain.member.entity.member.Member;
import com.likelion.oegaein.domain.member.entity.profile.Profile;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindRoommateAlarmsData {
    private Long roommateAlarmId; // 알림 ID
    private String name; // 이름
    private String photoUrl; // 프로필 사진
    private RoommateAlarmType roommateAlarmType; // 알림 타입
    private Long matchingPostId; // 매칭글 ID
    private String title; // 매칭글 제목

    public static FindRoommateAlarmsData toFindRoommateAlarmsData(RoommateAlarm roommateAlarm){
        Member member = roommateAlarm.getMember();
        Profile profile = member.getProfile();
        MatchingPost matchingPost = roommateAlarm.getMatchingPost();
        return FindRoommateAlarmsData.builder()
                .roommateAlarmId(roommateAlarm.getId())
                .name(profile.getName())
                .photoUrl(member.getPhotoUrl())
                .roommateAlarmType(roommateAlarm.getAlarmType())
                .matchingPostId(matchingPost.getId())
                .title(matchingPost.getTitle())
                .build();
    }
}