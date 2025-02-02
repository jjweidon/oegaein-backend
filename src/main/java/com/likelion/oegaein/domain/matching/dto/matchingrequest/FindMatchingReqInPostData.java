package com.likelion.oegaein.domain.matching.dto.matchingrequest;

import com.likelion.oegaein.domain.member.entity.profile.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FindMatchingReqInPostData {
    private String name;
    private Gender gender;
    private int studentNo;
    private Major major;
    private Date birthdate;
    private Mbti mbti;
    private Smoking smoking;
    private List<SleepingHabit> sleepingHabit;
    private LifePattern lifePattern;
    private Outing outing;
    private CleaningCycle cleaningCycle;
    private Sensitivity soundSensitivity;
    private String introduce;

    // convert to FindMatchingReqInPostData
    public static FindMatchingReqInPostData toFindMatchingReqInPostData(Profile profile){
        FindMatchingReqInPostData findMatchingReqInPostData = new FindMatchingReqInPostData();
        findMatchingReqInPostData.name = profile.getName();
        findMatchingReqInPostData.gender = profile.getGender();
        findMatchingReqInPostData.studentNo = profile.getStudentNo();
        findMatchingReqInPostData.major = profile.getMajor();
        findMatchingReqInPostData.birthdate = profile.getBirthdate();
        findMatchingReqInPostData.mbti = profile.getMbti();
        findMatchingReqInPostData.smoking = profile.getSmoking();
        // findMatchingReqInPostData.sleepingHabit = profile.getSleepingHabit();
        findMatchingReqInPostData.lifePattern = profile.getLifePattern();
        findMatchingReqInPostData.outing = profile.getOuting();
        findMatchingReqInPostData.cleaningCycle = profile.getCleaningCycle();
        findMatchingReqInPostData.soundSensitivity = profile.getSoundSensitivity();
        findMatchingReqInPostData.introduce = profile.getIntroduction();
        return findMatchingReqInPostData;
    }
}
