package com.likelion.oegaein.domain.member.validation;

import com.likelion.oegaein.domain.member.exception.MemberException;
import org.springframework.stereotype.Component;

@Component
public class MemberValidator {
    private final String IS_OWNER_MATCHING_POST_ERR_MSG = "올바른 매칭글 작성자가 아닙니다.";
    private final String IS_OWNER_COMMENT_ERR_MSG = "올바른 댓글 작성자가 아닙니다.";
    private final String IS_OWNER_REPLY_ERR_MSG = "올바른 대댓글 작성자가 아닙니다.";
    private final String IS_OWNER_MATCHING_REQ_ERR_MSG = "올바른 매칭 신청자가 아닙니다.";
    private final String IS_OWNER_COME_MATCHING_REQ_ERR_MSG = "올바른 매칭 수락/거부자가 아닙니다.";
    public void validateIsOwnerMatchingPost(Long authenticatedMemberId, Long matchingPostAuthorId){
        if(!authenticatedMemberId.equals(matchingPostAuthorId)){
            throw new MemberException(IS_OWNER_MATCHING_POST_ERR_MSG);
        }
    }
    public void validateIsOwnerComment(Long authenticatedMemberId, Long commentAuthorId){
        if(!authenticatedMemberId.equals(commentAuthorId)){
            throw new MemberException(IS_OWNER_COMMENT_ERR_MSG);
        }
    }
    public void validateIsOwnerReply(Long authenticatedMemberId, Long replyAuthorId){
        if(!authenticatedMemberId.equals(replyAuthorId)){
            throw new MemberException(IS_OWNER_REPLY_ERR_MSG);
        }
    }
    public void validateIsOwnerMatchingRequest(Long authenticatedMemberId, Long matchingRequestParticipantId){
        if(!authenticatedMemberId.equals(matchingRequestParticipantId)){
            throw new MemberException(IS_OWNER_MATCHING_REQ_ERR_MSG);
        }
    }
    public void validateIsOwnerComeMatchingRequest(Long authenticatedMemberId, Long comeMatchingRequestMatchingPostAuthorId){
        if(!authenticatedMemberId.equals(comeMatchingRequestMatchingPostAuthorId)){
            throw new MemberException(IS_OWNER_COME_MATCHING_REQ_ERR_MSG);
        }
    }
}