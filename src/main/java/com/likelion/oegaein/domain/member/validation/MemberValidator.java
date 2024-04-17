package com.likelion.oegaein.domain.member.validation;

import com.likelion.oegaein.domain.member.exception.MemberException;
import org.springframework.stereotype.Component;

@Component
public class MemberValidator {
    private final String IS_OWNER_MATCHING_POST_ERR_MSG = "올바른 매칭글 작성자가 아닙니다.";
    private final String IS_OWNER_COMMENT_ERR_MSG = "올바른 댓글 작성자가 아닙니다.";
    public void validateIsOwnerMatchingPost(Long authenticatedMemberId, Long matchingPostAuthorId){
        if(!authenticatedMemberId.equals(matchingPostAuthorId)){
            throw new MemberException(IS_OWNER_MATCHING_POST_ERR_MSG);
        }
    }

    public void validateIsOwnerComment(Long authenticatedMemberId, Long commentId){
        if(!authenticatedMemberId.equals(commentId)){
            throw new MemberException(IS_OWNER_COMMENT_ERR_MSG);
        }
    }
}