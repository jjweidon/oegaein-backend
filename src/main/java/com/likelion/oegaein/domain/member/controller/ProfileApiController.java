package com.likelion.oegaein.domain.member.controller;

import com.likelion.oegaein.domain.member.dto.profile.*;
import com.likelion.oegaein.domain.member.service.ProfileService;
import com.likelion.oegaein.domain.member.util.JwtUtil;
import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.devtools.v116.audits.model.FederatedAuthUserInfoRequestIssueDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.awt.image.AreaAveragingScaleFilter;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProfileApiController {
    private final ProfileService profileService;

    @GetMapping("api/v1/member/profile/{memberId}")
    public ResponseEntity<ResponseDto> getProfile(@PathVariable Long memberId) {
        log.info("Request to get profile");
        FindProfileResponse response = profileService.findProfile(memberId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("api/v1/member/profile")
    public ResponseEntity<ResponseDto> postProfile(Authentication authentication, @RequestBody CreateProfileRequest dto){
        log.info("Request to post profile");
        CreateProfileResponse response = profileService.createProfile(authentication, dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("api/v1/member/profile")
    public ResponseEntity<ResponseDto> putProfile(Authentication authentication, @RequestBody UpdateProfileRequest dto){
        log.info("Request to put profile");
        UpdateProfileResponse response = profileService.updateProfile(authentication, dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
