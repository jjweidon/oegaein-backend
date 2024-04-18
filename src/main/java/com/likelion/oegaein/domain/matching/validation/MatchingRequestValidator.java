package com.likelion.oegaein.domain.matching.validation;

import com.likelion.oegaein.domain.matching.entity.MatchingPost;
import com.likelion.oegaein.domain.matching.entity.MatchingRequest;
import com.likelion.oegaein.domain.matching.entity.MatchingStatus;
import com.likelion.oegaein.domain.matching.exception.MatchingRequestException;
import com.likelion.oegaein.domain.matching.repository.MatchingPostRepository;
import com.likelion.oegaein.domain.matching.repository.MatchingRequestRepository;
import com.likelion.oegaein.domain.member.entity.profile.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MatchingRequestValidator {
    // constants
    private final String IS_SELF_MATCHING_REQ_ERR_MSG = "자신이 작성한 글에는 신청할 수 없습니다.";
    private final String IS_ALREADY_MATCHING_REQ_ERR_MSG = "이미 신청한 매칭글입니다.";
    private final String IS_COMPLETED_OR_EXPIRED_MATCHING_POST_ERR_MSG = "이미 종료된 매칭글입니다.";
    // repository
    private final MatchingRequestRepository matchingRequestRepository;
    public void validateIsNotSelfRequest(Long authenticatedMemberId, Long matchingPostAuthorId){
        if(authenticatedMemberId.equals(matchingPostAuthorId)){
            throw new MatchingRequestException(IS_SELF_MATCHING_REQ_ERR_MSG);
        }
    }
    public void validateIsNotAlreadyRequest(Member participant, MatchingPost matchingPost){
        Optional<MatchingRequest> matchingRequest = matchingRequestRepository
                .findByParticipantAndMatchingPost(participant, matchingPost);
        if(matchingRequest.isPresent()){
            throw new MatchingRequestException(IS_ALREADY_MATCHING_REQ_ERR_MSG);
        }
    }
    public void validateIsNotCompletedOrExpiredMatchingPost(MatchingPost matchingPost){
        MatchingStatus matchingStatus = matchingPost.getMatchingStatus();
        if(matchingStatus.equals(MatchingStatus.COMPLETED) || matchingStatus.equals(MatchingStatus.EXPIRED)){
            throw new MatchingRequestException(IS_COMPLETED_OR_EXPIRED_MATCHING_POST_ERR_MSG);
        }
    }
}
