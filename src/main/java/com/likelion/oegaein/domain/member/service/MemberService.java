package com.likelion.oegaein.domain.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.likelion.oegaein.domain.member.dto.GoogleOauthLoginResponse;
import com.likelion.oegaein.domain.member.dto.GoogleOauthToken;
import com.likelion.oegaein.domain.member.dto.GoogleOauthUserInfo;
import com.likelion.oegaein.domain.member.entity.Member;
import com.likelion.oegaein.domain.member.repository.MemberRepository;
import com.likelion.oegaein.domain.member.repository.ProfileRepository;
import com.likelion.oegaein.domain.member.util.GoogleOauthUtil;
import com.likelion.oegaein.domain.member.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final ProfileRepository profileRepository;
    private final GoogleOauthUtil googleOauthUtil;
    public Optional<Member> findByEmail(String email){
        return memberRepository.findByEmail(email);
    }

    @Transactional
    public GoogleOauthLoginResponse googleLogin(String code) throws JsonProcessingException {
        // get data from google
        ResponseEntity<String> accessTokenResponse = googleOauthUtil.requestAccessToken(code);
        GoogleOauthToken oauthToken = googleOauthUtil.getAccessToken(accessTokenResponse);
        ResponseEntity<String> userInfoResponse = googleOauthUtil.requestUserInfo(oauthToken);
        GoogleOauthUserInfo userInfo = googleOauthUtil.getUserInfo(userInfoResponse);
        // validation
        // find user or save user
        Member member = memberRepository.findByEmail(userInfo.getEmail()).orElseGet(() -> {
            Member newMember = Member.builder()
                    .email(userInfo.getEmail())
                    .photoUrl(userInfo.getPicture())
                    .profileSetUpStatus(Boolean.FALSE)
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
}
