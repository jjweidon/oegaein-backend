package com.likelion.oegaein.domain.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.likelion.oegaein.domain.member.dto.member.*;
import com.likelion.oegaein.domain.member.dto.oauth.GoogleOauthLoginResponse;
import com.likelion.oegaein.domain.member.service.MemberService;
import com.likelion.oegaein.global.dto.ResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            ResponseCookie responseCookie = ResponseCookie.from("refreshToken", response.getRefreshToken())
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
    public ResponseEntity<ResponseDto> postBlockMember(Authentication authentication, CreateBlockRequest dto) {
        log.info("Request to post block member");
        CreateBlockResponse response = memberService.createBlockMember(authentication, dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("api/v1/member/like")
    public ResponseEntity<ResponseDto> postLikeMember(Authentication authentication, CreateLikeRequest dto) {
        log.info("Request to post like member");
        CreateLikeResponse response = memberService.createLikeMember(authentication, dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("api/v1/member/like")
    public ResponseEntity<ResponseDto> getLikeMembers(Authentication authentication) {
        log.info("Request to get like members");
        FindAllLikeReceiversResponse response = memberService.findAllLikeReceivers(authentication);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/api/v1/member/refresh")
    public ResponseEntity<ResponseDto> renewRefreshToken(HttpServletRequest httpServletRequest){
        log.info("Request to renew refresh token");
        RenewRefreshTokenResponse response = memberService.renewRefreshToken(httpServletRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
