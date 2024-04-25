package com.likelion.oegaein.domain.member.validation;

import com.likelion.oegaein.domain.member.exception.BlockException;
import com.likelion.oegaein.domain.member.repository.BlockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BlockValidator {
    private final String BLOCKED_ERR_MSG = "해당 글 작성자로부터 차단된 사용자입니다.";
    private final BlockRepository blockRepository;
    public void validateBlockedMember(Long blockingMemberId, Long blockedMemberId){
        boolean isBlocked = blockRepository.isBlocked(blockedMemberId, blockingMemberId);
        if(isBlocked){
            throw new BlockException(BLOCKED_ERR_MSG);
        }
    }
}
