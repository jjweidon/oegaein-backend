package com.likelion.oegaein.domain.member.entity.profile;

import com.likelion.oegaein.domain.member.dto.profile.UpdateProfileRequest;
import com.likelion.oegaein.domain.member.entity.Member;
import com.likelion.oegaein.domain.member.entity.review.Review;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Transactional
public class Profile {
    @Id @GeneratedValue
    @Column(name = "profile_id")
    private Long id;
    @Column(unique = true)
    private String name;
    private String photoUrl;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private int studentNo;
//    private String major;
    private Date birthdate;
    @Enumerated(EnumType.STRING)
    private Mbti mbti;
    @Enumerated(EnumType.STRING)
    private Smoking smoking;
    private SleepingHabitEntity sleepingHabit;
    @Enumerated(EnumType.STRING)
    private LifePattern lifePattern;
    @Enumerated(EnumType.STRING)
    private Outing outing;
    @Enumerated(EnumType.STRING)
    private CleaningCycle cleaningCycle;
    @Enumerated(EnumType.STRING)
    private Sensitivity soundSensitivity;
    private String introduction;
    private Review review;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "profile")
    private Member member;

    public void update(UpdateProfileRequest request) {
        this.name = request.getName();
        this.photoUrl = request.getPhotoUrl();
        this.gender = request.getGender();
        this.studentNo = request.getStudentNo();
        this.birthdate = request.getBirthdate();
        this.mbti = request.getMbti();
        this.smoking = request.getSmoking();
        this.sleepingHabit = request.getSleepingHabit();
        this.lifePattern = request.getLifePattern();
        this.outing = request.getOuting();
        this.cleaningCycle = request.getCleaningCycle();
        this.soundSensitivity = request.getSoundSensitivity();
        this.introduction = request.getIntroduction();
    }
}
