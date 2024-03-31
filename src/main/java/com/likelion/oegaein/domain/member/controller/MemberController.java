package com.likelion.oegaein.domain.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.likelion.oegaein.domain.member.dto.GoogleOauthLoginResponse;
import com.likelion.oegaein.domain.member.entity.Member;
import com.likelion.oegaein.domain.member.dto.UpdateProfileDto;
import com.likelion.oegaein.domain.member.service.MemberService;
import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/members")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/profile")
    public void getMemberProfile(@RequestHeader(value = "Authorization") String token) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Member member = memberService.findByEmail(auth.getPrincipal().toString()).get();
    }

    @PutMapping("/profile")
    public void updateMemberProfile(@RequestHeader(value = "Authorization") String token, @RequestBody UpdateProfileDto form) {

    }

    @GetMapping("/api/v1/auth/google/callback")
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
}
