package com.likelion.oegaein.domain.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.likelion.oegaein.domain.member.dto.member.CreateBlockResponse;
import com.likelion.oegaein.domain.member.dto.oauth.GoogleOauthLoginResponse;
import com.likelion.oegaein.domain.member.dto.oauth.GoogleOauthToken;
import com.likelion.oegaein.domain.member.dto.oauth.GoogleOauthUserInfo;
import com.likelion.oegaein.domain.member.entity.Block;
import com.likelion.oegaein.domain.member.entity.Member;
import com.likelion.oegaein.domain.member.repository.MemberRepository;
import com.likelion.oegaein.domain.member.util.GoogleOauthUtil;
import com.likelion.oegaein.domain.member.util.JwtUtil;
import com.likelion.oegaein.domain.member.validation.MemberValidator;
import jakarta.persistence.EntityNotFoundException;
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
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final GoogleOauthUtil googleOauthUtil;
    private final MemberValidator memberValidator;

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
    public CreateBlockResponse createBlockMember(Authentication authentication, Long blockedId) {
        Member blockingMember = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException("Not Found Member: " + authentication.getName()));
        Member blockedMember = memberRepository.findById(blockedId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found Member: " + blockedId));
        Block block = Block.builder()
                .blocking(blockingMember)
                .blocked(blockedMember)
                .build();
        return null;
    }
}
