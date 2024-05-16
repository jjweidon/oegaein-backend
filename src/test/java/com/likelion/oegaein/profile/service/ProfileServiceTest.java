package com.likelion.oegaein.profile.service;

import com.likelion.oegaein.domain.member.entity.profile.*;
import com.likelion.oegaein.domain.member.repository.ProfileRepository;
import com.likelion.oegaein.domain.member.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@ExtendWith(MockitoExtension.class)
public class ProfileServiceTest {

    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private ProfileService profileService;

    @Test
    public void ProfileService_CreateProfile_ReturnsProfileDto() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Profile profile = Profile.builder()
                .name("*정재웅*")
                .introduction("방가비~")
                .gender(Gender.M)
                .studentNo(19)
                .major(Major.EE)
                .birthdate(formatter.parse("1999-08-20"))
                .mbti(Mbti.ISFP)
                .lifePattern(LifePattern.NIGHT)
                .smoking(Smoking.NON_SMOKER)
                .cleaningCycle(CleaningCycle.EVERYDAY)
                .outing(Outing.HOMEBODY)
                .soundSensitivity(Sensitivity.INSENSITIVE)
                .build();

//        when(profileRepository.save(Mockito.any(Profile.class))).thenReturn(profile);

    }
}
