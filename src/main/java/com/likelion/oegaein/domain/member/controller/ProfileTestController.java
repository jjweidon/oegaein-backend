package com.likelion.oegaein.domain.member.controller;

import com.likelion.oegaein.domain.member.dto.profile.*;
import com.likelion.oegaein.domain.member.service.ProfileService;
import com.likelion.oegaein.domain.member.service.ProfileTestService;
import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProfileTestController {
    private final ProfileTestService profileTestService;

    @PostMapping("api/v1/test/profile")
    public ResponseEntity<ResponseDto> postProfileTest(@RequestBody CreateProfileRequest dto){
        log.info("Request to post profile");
        String email = "test@hufs.ac.kr";
        CreateProfileResponse response = profileTestService.createProfileTest(email, dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("api/v1/test/profile")
    public ResponseEntity<ResponseDto> putProfileTest(@RequestBody UpdateProfileRequest dto){
        log.info("Request to put profile");
        String email = "test@hufs.ac.kr";
        UpdateProfileResponse response = profileTestService.updateProfileTest(email, dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
