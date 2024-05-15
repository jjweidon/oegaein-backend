package com.likelion.oegaein.domain.member.service;

import com.likelion.oegaein.domain.member.dto.member.CheckDuplicateNameResponse;
import com.likelion.oegaein.domain.member.dto.profile.*;
import com.likelion.oegaein.domain.member.entity.member.Member;
import com.likelion.oegaein.domain.member.entity.profile.Profile;
import com.likelion.oegaein.domain.member.entity.profile.SleepingHabit;
import com.likelion.oegaein.domain.member.entity.profile.SleepingHabitEntity;
import com.likelion.oegaein.domain.member.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final MemberRepository memberRepository;
    private final SleepingHabitRepository sleepingHabitRepository;
    private final BlockRepository blockRepository;
    private final ReviewRepository reviewRepository;

    public CreateProfileResponse createProfile(String authentication, CreateProfileRequest form) {
        Member loginMember = findAuthenticatedMember(authentication);
        
        // 닉네임 중복 확인
        isValidName(form.getName());
        
        // 내용 저장
        Profile profile = Profile.builder()
                .name(form.getName())
                .introduction(form.getIntroduction())
                .gender(form.getGender())
                .studentNo(form.getStudentNo())
                .major(form.getMajor())
                .birthdate(form.getBirthdate())
                .mbti(form.getMbti())
                .lifePattern(form.getLifePattern())
                .smoking(form.getSmoking())
                .cleaningCycle(form.getCleaningCycle())
                .outing(form.getOuting())
                .soundSensitivity(form.getSoundSensitivity())
                .build();
        profile.setMember(loginMember);
        profileRepository.save(profile);

        // 수면습관 설정
        updateSleepingHabit(form.getSleepingHabit(), profile);

        return new CreateProfileResponse(profile.getId());
    }

    public UpdateProfileResponse updateProfile(String email, UpdateProfileRequest form) {
        Member loginMember = findAuthenticatedMember(email);// 사용자 찾기

        // 닉네임이 바뀌었으면 중복 확인
        if (!loginMember.getProfile().getName().equals(form.getName())) {
            isValidName(form.getName());
        }

        // 프로필 찾기
        Profile profile = profileRepository.findById(loginMember.getProfile().getId())
                .orElseThrow(() -> new EntityNotFoundException("Not Found Profile: " + loginMember.getId()));

        // 내용 저장
        profile.set(form);
        loginMember.setPhotoUrl(form.getPhotoUrl());

        // 수면습관 업데이트
        updateSleepingHabit(form.getSleepingHabit(), profile);

        return new UpdateProfileResponse(profile.getId());
    }

    public void updateSleepingHabit(List<SleepingHabit> newSleepingHabits, Profile profile) {
        // 기존 내용 delete
        Optional<List<SleepingHabitEntity>> findSleepingHabitEntities = sleepingHabitRepository.findAllByProfile(profile);
        findSleepingHabitEntities.ifPresent(sleepingHabitEntities -> {
            for (SleepingHabitEntity sleepingHabitEntity : sleepingHabitEntities) {
                sleepingHabitRepository.delete(sleepingHabitEntity);
            }
        });

        // 새로운 내용 생성
        Optional.ofNullable(newSleepingHabits)
                .ifPresent(habits -> habits.forEach(habit -> {
                    SleepingHabitEntity sleepingHabitEntity = SleepingHabitEntity.builder()
                            .sleepingHabit(habit)
                            .profile(profile)
                            .build();
                    sleepingHabitRepository.save(sleepingHabitEntity);
                }));
    }

    public FindProfileResponse findProfile(String email, Long memberId) {
        Member loginMember = findAuthenticatedMember(email);
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found Member: " + memberId));

        // 차단 확인
        isBlockedMember(loginMember, findMember);

        Profile profile = profileRepository.findById(findMember.getProfile().getId())
                .orElseThrow(() -> new EntityNotFoundException("Not Found Profile: " + memberId));
        return FindProfileResponse.of(profile);
    }

    public FindMyProfileResponse findMyProfile(Authentication authentication){
        Member loginMember = findAuthenticatedMember(authentication);
        Profile profile = loginMember.getProfile();
        return FindMyProfileResponse.of(profile);
    }

    // 유효 닉네임 검사
    public CheckDuplicateNameResponse isValidName(String nickname) {
        Optional<Profile> member = profileRepository.findByName(nickname);
        if (member.isPresent()) {
            return new CheckDuplicateNameResponse(true);
        }
        else {
            return new CheckDuplicateNameResponse(false);
        }
    }

    // 차단 확인
    public void isBlockedMember(Member loginMember, Member findMember) {
        if (blockRepository.isBlocked(loginMember.getId(), findMember.getId())) {
            throw new IllegalStateException("차단된 사용자입니다.");
        }
    }

    // 리뷰 평점 계산
    public void setAverageScore(Member member) {
        Profile profile = member.getProfile();
        double averageScore = reviewRepository.averageScoreByReceiver(member);
        profile.setScore(averageScore);
    }

    // 로그인한 사용자 찾기
    private Member findAuthenticatedMember(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Not Found Member: " + email));
    }

    // 전공 추출
//    private String extractMajor(String googleName) {
//        // 정규표현식으로 전공 추출
//        Pattern pattern = Pattern.compile("\\/\\s*(.*?)\\s*\\]");
//        Matcher matcher = pattern.matcher(googleName);
//
//        // 추출된 전공을 반환
//        if (matcher.find()) {
//            String extracted = matcher.group(1); // 첫 번째 그룹에 해당하는 문자열 추출
//            log.info("전공: " + extracted);
//            return extracted;
//        } else {
//            log.info("추출할 전공이 없습니다.");
//            return null;
//        }
//    }
}
