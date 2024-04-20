package com.likelion.oegaein.domain.matching.dto.matchingrequest;

import com.likelion.oegaein.domain.matching.entity.*;
import com.likelion.oegaein.domain.member.entity.profile.Gender;
import com.likelion.oegaein.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindMyMatchingReqData {
    private Long matchingRequestId; // 매칭 요청 ID
    private LocalDateTime createdAt;

    private String photoUrl; // 작성자 프로필 사진
    private String name; // 작성자 이름
    private Gender gender; // 작성자 성별

    private Long matchingPostId; // 매칭글 ID
    private String title; // 제목
    private DongType dongType; // 동
    private RoomSizeType roomSizeType; // 호실
    private int targetNumberOfPeople; // 모집인원
    private MatchingAcceptance matchingAcceptance; // 매칭 상태

    public static FindMyMatchingReqData toFindMatchingReqData(MatchingRequest matchingRequest){
        MatchingPost matchingPost = matchingRequest.getMatchingPost();
        Member author = matchingPost.getAuthor();
        return FindMyMatchingReqData.builder()
                .matchingRequestId(matchingRequest.getId())
                .createdAt(matchingRequest.getCreatedAt())
                .photoUrl(author.getPhotoUrl())
                .name(author.getProfile().getName())
                .gender(author.getProfile().getGender())
                .matchingPostId(matchingPost.getId())
                .title(matchingPost.getTitle())
                .dongType(matchingPost.getDongType())
                .roomSizeType(matchingPost.getRoomSizeType())
                .targetNumberOfPeople(matchingPost.getTargetNumberOfPeople())
                .matchingAcceptance(matchingRequest.getMatchingAcceptance())
                .build();
    }
}
