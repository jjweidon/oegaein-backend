package com.likelion.oegaein.domain.member.controller;

import com.likelion.oegaein.domain.member.dto.member.CheckDuplicateNameRequest;
import com.likelion.oegaein.domain.member.dto.member.CheckDuplicateNameResponse;
import com.likelion.oegaein.domain.member.dto.profile.*;
import com.likelion.oegaein.domain.member.service.ProfileService;
import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProfileApiController {
    private final ProfileService profileService;

    @GetMapping("api/v1/member/profile/{memberId}")
    public ResponseEntity<ResponseDto> getProfile(String email, @PathVariable("memberId") Long memberId) {
        log.info("Request to get profile by member id-{}", memberId);
        FindProfileResponse response = profileService.findProfile(email, memberId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("api/v1/member/profile")
    public ResponseEntity<ResponseDto> postProfile(String email, @RequestBody CreateProfileRequest dto){
        log.info("Request to post profile");
        CreateProfileResponse response = profileService.createProfile(email, dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("api/v1/member/profile")
    public ResponseEntity<ResponseDto> putProfile(String email, @RequestBody UpdateProfileRequest dto){
        log.info("Request to put profile");
        UpdateProfileResponse response = profileService.updateProfile(email, dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("api/v1/member/nickname/duplicate")
    public ResponseEntity<ResponseDto> checkDuplicateName(@RequestBody CheckDuplicateNameRequest dto) {
        log.info("Request to check duplicate name");
        CheckDuplicateNameResponse response = profileService.isValidName(dto.getNickname());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
