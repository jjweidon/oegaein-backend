package com.likelion.oegaein.domain.matching.dto.matchingpost;

import com.likelion.oegaein.domain.matching.entity.DongType;
import com.likelion.oegaein.domain.matching.entity.MatchingPost;
import com.likelion.oegaein.domain.matching.entity.MatchingStatus;
import com.likelion.oegaein.domain.matching.entity.RoomSizeType;
import com.likelion.oegaein.domain.member.entity.profile.*;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Getter
@Builder
public class FindMatchingPostsData {
    private Major major; // major
    private int studentNo; // studentNo
    private String name; // name
    private Gender gender; // gender
    private String photoUrl; // profile photo
    private Date birthdate;
    private Mbti mbti;
    private List<SleepingHabit> sleepingHabits;
    private LifePattern lifePattern;
    private Smoking smoking;
    private CleaningCycle cleaningCycle;
    private Outing outing;
    private Sensitivity sensitivity;
    private Long matchingPostId; // matchingPost ID
    private String title; // Title
    private Long dDay; // D-day
    private DongType dong; // dorm dong
    private RoomSizeType roomSize; // dorm roomSize
    private int targetNumberOfPeople; // 모집 인원
    private MatchingStatus matchingStatus; // matching status
    private LocalDateTime createdAt;

    public static FindMatchingPostsData toFindMatchingPostsData(MatchingPost matchingPost){
        Profile profile = matchingPost.getAuthor().getProfile();
        List<SleepingHabit> sleepingHabitDataList = profile.getSleepingHabit().stream()
                .map(SleepingHabitEntity::getSleepingHabit)
                .toList();
        return FindMatchingPostsData.builder()
                .major(profile.getMajor())
                .studentNo(profile.getStudentNo())
                .name(profile.getName())
                .gender(profile.getGender())
                .photoUrl(matchingPost.getAuthor().getPhotoUrl())
                .birthdate(profile.getBirthdate())
                .mbti(profile.getMbti())
                .sleepingHabits(sleepingHabitDataList)
                .lifePattern(profile.getLifePattern())
                .smoking(profile.getSmoking())
                .cleaningCycle(profile.getCleaningCycle())
                .outing(profile.getOuting())
                .sensitivity(profile.getSoundSensitivity())
                .matchingPostId(matchingPost.getId())
                .title(matchingPost.getTitle())
                .dDay(ChronoUnit.DAYS.between(LocalDate.now(), matchingPost.getDeadline()))
                .dong(matchingPost.getDongType())
                .roomSize(matchingPost.getRoomSizeType())
                .targetNumberOfPeople(matchingPost.getTargetNumberOfPeople())
                .matchingStatus(matchingPost.getMatchingStatus())
                .createdAt(matchingPost.getCreatedAt())
                .build();
    }
}
