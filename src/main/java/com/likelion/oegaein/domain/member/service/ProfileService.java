package com.likelion.oegaein.domain.member.service;

import com.likelion.oegaein.domain.member.dto.profile.*;
import com.likelion.oegaein.domain.member.entity.profile.Member;
import com.likelion.oegaein.domain.member.entity.profile.Profile;
import com.likelion.oegaein.domain.member.entity.profile.SleepingHabit;
import com.likelion.oegaein.domain.member.entity.profile.SleepingHabitEntity;
import com.likelion.oegaein.domain.member.repository.MemberRepository;
import com.likelion.oegaein.domain.member.repository.ProfileRepository;
import com.likelion.oegaein.domain.member.repository.SleepingHabitRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final MemberRepository memberRepository;
    private final SleepingHabitRepository sleepingHabitRepository;

    public CreateProfileResponse createProfile(Authentication authentication, CreateProfileRequest form) {
        // 사용자 찾기
        Member loginMember = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException("Not Found Member: " + authentication.getName()));
        
        // 닉네임 중복 확인
        isValidName(form.getName());
        
        // 내용 저장
        Profile profile = Profile.builder()
                .name(form.getName())
                .introduction(form.getIntroduction())
                .gender(form.getGender())
                .studentNo(form.getStudentNo())
                .major(extractMajor(loginMember.getGoogleName()))
                .birthdate(form.getBirthdate())
                .mbti(form.getMbti())
                .lifePattern(form.getLifePattern())
                .smoking(form.getSmoking())
                .cleaningCycle(form.getCleaningCycle())
                .outing(form.getOuting())
                .soundSensitivity(form.getSoundSensitivity())
                .build();
        profileRepository.save(profile);
        loginMember.setProfile(profile);

        // 수면 습관 엔티티 생성
        updateSleepingHabit(form.getSleepingHabit(), profile);

        return new CreateProfileResponse(profile.getId());
    }

    public UpdateProfileResponse updateProfile(Authentication authentication, UpdateProfileRequest form) {
        // 사용자 찾기
        Member loginMember = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException("Not Found Member: " + authentication.getName()));

        // 닉네임이 바뀌었으면 중복 확인
        if (!loginMember.getProfile().getName().equals(form.getName())) {
            isValidName(form.getName());
        }

        // 프로필 찾기
        Profile profile = profileRepository.findById(loginMember.getProfile().getId())
                .orElseThrow(() -> new EntityNotFoundException("Not Found Profile: " + loginMember.getId()));

        // 내용 저장
        profile.update(form);
        loginMember.setPhotoUrl(form.getPhotoUrl());

        // 수면 습관 업데이트
        updateSleepingHabit(form.getSleepingHabit(), profile);

        return new UpdateProfileResponse(profile.getId());
    }

    public void updateSleepingHabit(List<SleepingHabit> newSleepingHabits, Profile profile) {
        // 기존 내용 delete
        List<SleepingHabitEntity> findSleepingHabitEntities = sleepingHabitRepository.findAllByProfile(profile);
        for (SleepingHabitEntity sleepingHabitEntity : findSleepingHabitEntities) {
            sleepingHabitRepository.delete(sleepingHabitEntity);
        }

        // 새로운 내용 생성
        for (SleepingHabit sleepingHabit : newSleepingHabits) {
            SleepingHabitEntity sleepingHabitEntity = SleepingHabitEntity.builder()
                    .sleepingHabit(sleepingHabit)
                    .profile(profile)
                    .build();
            sleepingHabitRepository.save(sleepingHabitEntity);
        }
    }

    public FindProfileResponse findProfile(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found Member: " + memberId));
        Profile profile = profileRepository.findById(member.getProfile().getId())
                .orElseThrow(() -> new EntityNotFoundException("Not Found Profile: " + memberId));
        return FindProfileResponse.of(profile);
    }

    // 유효 닉네임 검사
    private void isValidName(String name) {
        Optional<Profile> member = profileRepository.findByName(name);
        if (member.isPresent()) {
            throw new IllegalStateException("이미 존재하는 닉네임입니다.");
        }
    }

    // 전공 추출
    private String extractMajor(String googleName) {
        // 정규표현식으로 전공 추출
        Pattern pattern = Pattern.compile("\\/\\s*(.*?)\\s*\\]");
        Matcher matcher = pattern.matcher(googleName);

        // 추출된 전공을 반환
        if (matcher.find()) {
            String extracted = matcher.group(1); // 첫 번째 그룹에 해당하는 문자열 추출
            log.info("전공: " + extracted);
            return extracted;
        } else {
            log.info("추출할 전공이 없습니다.");
            return null;
        }
    }
}
