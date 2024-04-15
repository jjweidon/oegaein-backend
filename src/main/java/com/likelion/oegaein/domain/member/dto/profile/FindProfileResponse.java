package com.likelion.oegaein.domain.member.dto.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.likelion.oegaein.domain.member.entity.profile.*;
import com.likelion.oegaein.global.dto.ResponseDto;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;

import java.util.Date;
import java.util.List;

@Builder
public class FindProfileResponse implements ResponseDto {
    private String name; // 닉네임
    private String introduction; // 소개글
    @Enumerated(EnumType.STRING)
    private Gender gender; // 성별
    @JsonProperty("student_no")
    private int studentNo; // 학번
    private String major;
    private Date birthdate; // 생일
    @Enumerated(EnumType.STRING)
    private Mbti mbti; // MBTI
    @JsonProperty("sleeping_habit")
    private List<SleepingHabit> sleepingHabit; // 수면 습관
    @Enumerated(EnumType.STRING)
    @JsonProperty("lifePattern")
    private LifePattern lifePattern; // 생활 패턴
    @Enumerated(EnumType.STRING)
    private Smoking smoking; // 흡연 여부
    @Enumerated(EnumType.STRING)
    @JsonProperty("cleaning_cycle")
    private CleaningCycle cleaningCycle; // 청소 주기
    @Enumerated(EnumType.STRING)
    private Outing outing; // 외출 빈도
    @Enumerated(EnumType.STRING)
    @JsonProperty("sound_sensitivity")
    private Sensitivity soundSensitivity; // 소리 민감도
    // 후기

    public static FindProfileResponse of(Profile profile) {
        return FindProfileResponse.builder()
                .name(profile.getName())
                .introduction(profile.getIntroduction())
                .gender(profile.getGender())
                .studentNo(profile.getStudentNo())
                .birthdate(profile.getBirthdate())
                .mbti(profile.getMbti())
//                .sleepingHabit(profile.getSleepingHabit())
                .lifePattern(profile.getLifePattern())
                .smoking(profile.getSmoking())
                .cleaningCycle(profile.getCleaningCycle())
                .outing(profile.getOuting())
                .soundSensitivity(profile.getSoundSensitivity())
                .build();
    }


}
