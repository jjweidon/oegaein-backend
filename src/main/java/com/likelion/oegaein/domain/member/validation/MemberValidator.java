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
    private final String IS_OWNER_ROOMMATE_ALARM_ERR_MSG = "올바른 룸메이트 알림 수신자가 아닙니다.";
    private final String IS_HUFS_EMAIL_DOMAIN_ERR_MSG = "한국외국어대학교 계정이 아닙니다.";
    private final String HUFS_EMAIL_DOMAIN = "hufs.ac.kr";
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
    public void validateIsOwnerRoommateAlarm(Long authenticatedMemberId, Long roommateAlarmAuthorId){
        if(!authenticatedMemberId.equals(roommateAlarmAuthorId)){
            throw new MemberException(IS_OWNER_ROOMMATE_ALARM_ERR_MSG);
        }
    }
    public void validateIsUnivEmailDomain(String email){
        String emailDomain = email.substring(email.indexOf('@') + 1);
        if(!emailDomain.equals(HUFS_EMAIL_DOMAIN)){
            throw new MemberException(IS_HUFS_EMAIL_DOMAIN_ERR_MSG);
        }
    }
}