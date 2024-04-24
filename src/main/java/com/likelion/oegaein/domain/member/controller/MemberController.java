package com.likelion.oegaein.domain.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.likelion.oegaein.domain.member.dto.member.CreateBlockResponse;
import com.likelion.oegaein.domain.member.dto.oauth.GoogleOauthLoginResponse;
import com.likelion.oegaein.domain.member.service.MemberService;
import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/api/v1/member/auth/google/callback")
    public ResponseEntity<ResponseDto> googleLoginCallbackRequest(@RequestParam("code") String code){
        try {
            log.info("Request to login google oauth2");
            GoogleOauthLoginResponse response = memberService.googleLogin(code);
            ResponseCookie responseCookie = ResponseCookie.from("refresh_token", response.getRefreshToken())
                    .httpOnly(true)
                    .maxAge(60*60*24)
                    .path("/")
                    .build();
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                    .body(response);
        }catch (JsonProcessingException e){
            log.error("JsonProcessingException");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("api/v1/member/block")
    public ResponseEntity<ResponseDto> postBlockMember(Authentication authentication, @RequestParam("blocked") Long blockedId) {
        log.info("Request to post block member");
        CreateBlockResponse response = memberService.createBlockMember(authentication, blockedId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
