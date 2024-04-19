package com.likelion.oegaein.domain.member.entity.profile;

import com.likelion.oegaein.domain.member.dto.profile.UpdateProfileRequest;
import com.likelion.oegaein.domain.member.entity.Member;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

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
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private int studentNo;
    private String major;
    private Date birthdate;
    @Enumerated(EnumType.STRING)
    private Mbti mbti;
    @Enumerated(EnumType.STRING)
    private Smoking smoking;
    @OneToMany(mappedBy = "profile")
    private List<SleepingHabitEntity> sleepingHabit;
    @Enumerated(EnumType.STRING)
    private LifePattern lifePattern;
    @Enumerated(EnumType.STRING)
    private Outing outing;
    @Enumerated(EnumType.STRING)
    private CleaningCycle cleaningCycle;
    @Enumerated(EnumType.STRING)
    private Sensitivity soundSensitivity;
    @Size(max = 20)
    private String introduction;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void update(UpdateProfileRequest request) {
        this.name = request.getName();
        this.gender = request.getGender();
        this.studentNo = request.getStudentNo();
        this.birthdate = request.getBirthdate();
        this.mbti = request.getMbti();
        this.smoking = request.getSmoking();
        this.lifePattern = request.getLifePattern();
        this.outing = request.getOuting();
        this.cleaningCycle = request.getCleaningCycle();
        this.soundSensitivity = request.getSoundSensitivity();
        this.introduction = request.getIntroduction();
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
