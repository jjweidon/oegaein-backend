package com.likelion.oegaein.domain.member.dto.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.likelion.oegaein.domain.member.entity.profile.*;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProfileData {
    private String name;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @JsonProperty("student_no")
    private int studentNo;
    private Date birthdate;
    private String introduction;
    @Enumerated(EnumType.STRING)
    private Mbti mbti;
    @JsonProperty("sleeping_habit")
    private List<SleepingHabit> sleepingHabit;
    @Enumerated(EnumType.STRING)
    @JsonProperty("life_pattern")
    private LifePattern lifePattern;
    @Enumerated(EnumType.STRING)
    private Smoking smoking;
    @Enumerated(EnumType.STRING)
    @JsonProperty("cleaning_cycle")
    private CleaningCycle cleaningCycle;
    @Enumerated(EnumType.STRING)
    private Outing outing;
    @Enumerated(EnumType.STRING)
    @JsonProperty("sound_sensitivity")
    private Sensitivity soundSensitivity;
}
