package com.likelion.oegaein.domain.matching.dto.matchingpost;

import com.likelion.oegaein.domain.matching.entity.DongType;
import com.likelion.oegaein.domain.matching.entity.MatchingPost;
import com.likelion.oegaein.domain.matching.entity.MatchingStatus;
import com.likelion.oegaein.domain.matching.entity.RoomSizeType;
import com.likelion.oegaein.domain.member.entity.profile.Gender;
import com.likelion.oegaein.domain.member.entity.profile.Profile;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Builder
public class FindMatchingPostsData {
    private String major; // major
    private int studentNo; // studentNo
    private String name; // name
    private Gender gender; // gender
    private Long matchingPostId; // matchingPost ID
    private String title; // Title
    private Long dDay; // D-day
    private DongType dong; // dorm dong
    private RoomSizeType roomSize; // dorm roomSize
    private MatchingStatus matchingStatus; // matching status
    private LocalDateTime createdAt;

    public static FindMatchingPostsData toFindMatchingPostsData(MatchingPost matchingPost){
        Profile profile = matchingPost.getAuthor().getProfile();
        return FindMatchingPostsData.builder()
                .major(profile.getMajor())
                .studentNo(profile.getStudentNo())
                .name(profile.getName())
                .gender(profile.getGender())
                .matchingPostId(matchingPost.getId())
                .title(matchingPost.getTitle())
                .dDay(Duration.between(LocalDateTime.now(), matchingPost.getDeadline()).toDays())
                .dong(matchingPost.getDongType())
                .roomSize(matchingPost.getRoomSizeType())
                .matchingStatus(matchingPost.getMatchingStatus())
                .createdAt(matchingPost.getCreatedAt())
                .build();
    }
}
