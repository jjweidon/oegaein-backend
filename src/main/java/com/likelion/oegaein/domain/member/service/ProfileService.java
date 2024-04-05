package com.likelion.oegaein.domain.member.service;

import com.likelion.oegaein.domain.member.dto.profile.*;
import com.likelion.oegaein.domain.member.entity.Member;
import com.likelion.oegaein.domain.member.entity.profile.Profile;
import com.likelion.oegaein.domain.member.repository.MemberRepository;
import com.likelion.oegaein.domain.member.repository.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final MemberRepository memberRepository;

    public CreateProfileResponse createProfile(Authentication authentication, CreateProfileRequest form) {
        // 사용자 찾기
        Member loginMember = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException("Not Found Member: " + authentication.getName()));
        
        // 닉네임 중복 확인
        isValidName(form.getName());
        
        // 내용 저장
        Profile profile = Profile.builder()
                .name(form.getName())
                .photoUrl(form.getPhotoUrl())
                .introduction(form.getIntroduction())
                .gender(form.getGender())
                .studentNo(form.getStudentNo())
                .birthdate(form.getBirthdate())
                .mbti(form.getMbti())
                .sleepingHabit(form.getSleepingHabit())
                .lifePattern(form.getLifePattern())
                .smoking(form.getSmoking())
                .cleaningCycle(form.getCleaningCycle())
                .outing(form.getOuting())
                .soundSensitivity(form.getSoundSensitivity())
                .build();
        profileRepository.save(profile);
        loginMember.setProfile(profile);

        return new CreateProfileResponse(profile.getId());
    }

    public UpdateProfileResponse updateProfile(Authentication authentication, UpdateProfileRequest form) {
        // 사용자 찾기
        Member loginMember = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException("Not Found Member: " + authentication.getName()));

        // 닉네임 중복 확인
        isValidName(form.getName());

        // 프로필 찾기
        Profile profile = profileRepository.findById(loginMember.getProfile().getId())
                .orElseThrow(() -> new EntityNotFoundException("Not Found Profile: " + loginMember.getId()));

        // 내용 저장
        profile.update(form);
        loginMember.setProfile(profile);

        return new UpdateProfileResponse(profile.getId());
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
}
