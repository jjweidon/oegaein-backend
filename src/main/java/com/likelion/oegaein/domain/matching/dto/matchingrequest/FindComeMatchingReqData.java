package com.likelion.oegaein.domain.matching.dto.matchingrequest;

import com.likelion.oegaein.domain.matching.entity.MatchingPost;
import com.likelion.oegaein.domain.matching.entity.MatchingRequest;
import com.likelion.oegaein.domain.member.entity.Member;
import com.likelion.oegaein.domain.member.entity.profile.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindComeMatchingReqData {
    private Long matchingRequestId; // 매칭 요청 ID
    private LocalDateTime createdAt; // 매칭 요청 생성일

    private String photoUrl; // 신청자 프로필 사진
    private String name; // 신청자 이름
    private String introduction; // 신청자 자기소개

    private Long matchingPostId; // 매칭글 ID
    private String title; // 매칭글 제목

    public static FindComeMatchingReqData toFindComeMatchingReqData(MatchingRequest matchingRequest){
        Member participant = matchingRequest.getParticipant();
        Profile profile = participant.getProfile();
        MatchingPost matchingPost = matchingRequest.getMatchingPost();
        return FindComeMatchingReqData.builder()
                .matchingRequestId(matchingRequest.getId())
                .createdAt(matchingPost.getCreatedAt())
                .photoUrl(participant.getPhotoUrl())
                .name(profile.getName())
                .introduction(profile.getIntroduction())
                .matchingPostId(matchingPost.getId())
                .title(matchingPost.getTitle())
                .build();
    }
}
