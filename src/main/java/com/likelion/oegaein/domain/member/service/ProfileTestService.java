package com.likelion.oegaein.domain.member.service;

import com.likelion.oegaein.domain.member.dto.profile.*;
import com.likelion.oegaein.domain.member.entity.Member;
import com.likelion.oegaein.domain.member.entity.profile.Profile;
import com.likelion.oegaein.domain.member.entity.profile.SleepingHabit;
import com.likelion.oegaein.domain.member.entity.profile.SleepingHabitEntity;
import com.likelion.oegaein.domain.member.repository.MemberRepository;
import com.likelion.oegaein.domain.member.repository.ProfileRepository;
import com.likelion.oegaein.domain.member.repository.SleepingHabitRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfileTestService {
    private final ProfileRepository profileRepository;
    private final MemberRepository memberRepository;
    private final SleepingHabitRepository sleepingHabitRepository;

    public CreateProfileResponse createProfileTest(String email, CreateProfileRequest form) {

        // 사용자 찾기
        Member loginMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Not Found Member: " + email));

        // 닉네임 중복 확인
        isValidName(form.getName());

        // 내용 저장
        Profile profile = Profile.builder()
                .name(form.getName())
                .photoUrl(form.getPhotoUrl())
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

    public UpdateProfileResponse updateProfileTest(String email, UpdateProfileRequest form) {
        // 사용자 찾기
        Member loginMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Not Found Member: " + email));

        // 닉네임 중복 확인
        isValidName(form.getName());

        // 프로필 찾기
        Profile profile = profileRepository.findById(loginMember.getProfile().getId())
                .orElseThrow(() -> new EntityNotFoundException("Not Found Profile: " + loginMember.getId()));

        // 내용 저장
        profile.update(form);

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

    public FindProfileResponse findProfileTest(Long memberId) {
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

    private String extractMajor(String googleName) {
        // 정규표현식을 사용하여 전공 추출
        Pattern pattern = Pattern.compile("\\/\\s*(.*?)\\s*\\]");
        Matcher matcher = pattern.matcher(googleName);

        // 매칭되는 부분이 있으면 그 부분을 출력합니다.
        if (matcher.find()) {
            String extracted = matcher.group(1); // 첫 번째 그룹에 해당하는 문자열 추출
            System.out.println(extracted); // 전공 출력
            return extracted;
        } else {
            System.out.println("추출할 전공이 없습니다.");
            return null;
        }
    }
}
