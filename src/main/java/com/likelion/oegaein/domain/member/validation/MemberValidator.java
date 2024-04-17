package com.likelion.oegaein.domain.member.validation;

import com.likelion.oegaein.domain.member.exception.MemberException;
import org.springframework.stereotype.Component;

@Component
public class MemberValidator {
    private final String IS_OWNER_MATCHING_POST_ERR_MSG = "올바르지 않은 사용자입니다.";
    public void validateIsOwnerMatchingPost(Long authenticatedMemberId, Long matchingPostAuthorId){
        if(!authenticatedMemberId.equals(matchingPostAuthorId)){
            throw new MemberException(IS_OWNER_MATCHING_POST_ERR_MSG);
        }
    }
}
