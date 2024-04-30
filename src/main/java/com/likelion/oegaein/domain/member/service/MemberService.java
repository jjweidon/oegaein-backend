package com.likelion.oegaein.domain.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.likelion.oegaein.domain.member.dto.member.CreateBlockRequest;
import com.likelion.oegaein.domain.member.dto.member.CreateBlockResponse;
import com.likelion.oegaein.domain.member.dto.member.RenewRefreshTokenResponse;
import com.likelion.oegaein.domain.member.dto.oauth.GoogleOauthLoginResponse;
import com.likelion.oegaein.domain.member.dto.oauth.GoogleOauthToken;
import com.likelion.oegaein.domain.member.dto.oauth.GoogleOauthUserInfo;
import com.likelion.oegaein.domain.member.entity.Block;
import com.likelion.oegaein.domain.member.entity.Member;
import com.likelion.oegaein.domain.member.exception.RefreshTokenException;
import com.likelion.oegaein.domain.member.repository.MemberRepository;
import com.likelion.oegaein.domain.member.util.GoogleOauthUtil;
import com.likelion.oegaein.domain.member.util.JwtUtil;
import com.likelion.oegaein.domain.member.validation.MemberValidator;
import com.likelion.oegaein.domain.member.validation.RefreshTokenValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    // constant
    private final String REFRESH_TOKEN_COOKIE_NAME = "refreshToken";
    private final String REFRESH_TOKEN_NOT_EXIST_ERR_MSG = "Not exist refresh token";
    private final String REFRESH_TOKEN_INVALID_ERR_MSG = "Invalid refresh token";
    private final String NOT_FOUND_MEMBER_ERR_MSG = "찾을 수 없는 사용자입니다.";
    // di
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final GoogleOauthUtil googleOauthUtil;
    private final MemberValidator memberValidator;
    private final RefreshTokenValidator refreshTokenValidator;

    @Transactional
    public GoogleOauthLoginResponse googleLogin(String code) throws JsonProcessingException {
        // get data from google
        ResponseEntity<String> accessTokenResponse = googleOauthUtil.requestAccessToken(code);
        GoogleOauthToken oauthToken = googleOauthUtil.getAccessToken(accessTokenResponse);
        ResponseEntity<String> userInfoResponse = googleOauthUtil.requestUserInfo(oauthToken);
        GoogleOauthUserInfo userInfo = googleOauthUtil.getUserInfo(userInfoResponse);
        // validation
        memberValidator.validateIsUnivEmailDomain(userInfo.getEmail());
        // find user or save user
        Member member = memberRepository.findByEmail(userInfo.getEmail()).orElseGet(() -> {
            Member newMember = Member.builder()
                    .email(userInfo.getEmail())
                    .photoUrl(userInfo.getPicture())
                    .build();
            memberRepository.save(newMember);
            return newMember;
        });
        // generate tokens
        String accessToken = jwtUtil.generateAccessToken(member);
        String refreshToken = jwtUtil.generateRefreshToken(member);
        // setting refresh token;
        member.renewRefreshToken(refreshToken);
        return GoogleOauthLoginResponse.builder()
                .email(member.getEmail())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public CreateBlockResponse createBlockMember(Authentication authentication, CreateBlockRequest form) {
        Member blockingMember = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException("Not Found Member: " + authentication.getName()));
        Member blockedMember = memberRepository.findById(form.getBlockedId())
                .orElseThrow(() -> new EntityNotFoundException("Not Found Member: " + form.getBlockedId()));
        Block block = Block.builder()
                .blocking(blockingMember)
                .blocked(blockedMember)
                .build();
        return new CreateBlockResponse(block.getId());
    }

    public RenewRefreshTokenResponse renewRefreshToken(HttpServletRequest request){
        Cookie refreshTokenCookie = findRefreshTokenCookie(request);
        if(refreshTokenCookie == null){ // refreshToken cookie X
            throw new RefreshTokenException(REFRESH_TOKEN_NOT_EXIST_ERR_MSG);
        }
        String refreshToken = refreshTokenCookie.getValue();
        if(!jwtUtil.validateToken(refreshToken)){
            throw new RefreshTokenException(REFRESH_TOKEN_INVALID_ERR_MSG);
        }
        String email = jwtUtil.extractEmail(refreshToken);
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        refreshTokenValidator.validateIsEqualToRefreshTokenInDb(refreshToken, member.getRefreshToken());
        String newAccessToken = jwtUtil.generateAccessToken(member);
        return RenewRefreshTokenResponse.builder()
                .accessToken(newAccessToken)
                .build();
    }

    private Cookie findRefreshTokenCookie(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(REFRESH_TOKEN_COOKIE_NAME.equals(cookie.getName())){
                    return cookie;
                }
            }
        }
        return null;
    }
}
