package com.likelion.oegaein.domain.matching.service;

import com.likelion.oegaein.domain.matching.dto.reply.*;
import com.likelion.oegaein.domain.matching.entity.Comment;
import com.likelion.oegaein.domain.matching.entity.Reply;
import com.likelion.oegaein.domain.matching.repository.CommentRepository;
import com.likelion.oegaein.domain.matching.repository.ReplyRepository;
import com.likelion.oegaein.domain.member.entity.profile.Member;
import com.likelion.oegaein.domain.member.repository.MemberRepository;
import com.likelion.oegaein.domain.member.validation.MemberValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReplyService {
    // constants
    private final String NOT_FOUND_COMMENT_ERR_MSG = "찾을 수 없는 댓글입니다.";
    private final String NOT_FOUND_REPLY_ERR_MSG = "찾을 수 없는 대댓글입니다.";
    private final String NOT_FOUND_MEMBER_ERR_MSG = "찾을 수 없는 사용자입니다.";
    // repository
    private final ReplyRepository replyRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    // validators
    private final MemberValidator memberValidator;

    @Transactional
    public CreateReplyResponse saveReply(CreateReplyData dto, Authentication authentication) {
        Member author = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        Comment comment = commentRepository.findById(dto.getCommentId())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_COMMENT_ERR_MSG));
        // create new reply
        Reply reply = Reply.builder()
                .comment(comment)
                .content(dto.getContent())
                .author(author)
                .isDeleted(Boolean.FALSE)
                .build();
        replyRepository.save(reply);
        return new CreateReplyResponse(reply.getId());
    }

    @Transactional
    public UpdateReplyResponse updateReply(UpdateReplyData dto, Long replyId, Authentication authentication) {
        Member authenticatedMember = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_REPLY_ERR_MSG));
        memberValidator.validateIsOwnerReply(authenticatedMember.getId(), reply.getAuthor().getId());
        reply.updateContent(dto.getContent());
        return new UpdateReplyResponse(replyId);
    }

    @Transactional
    public DeleteReplyResponse removeReply(Long replyId, Authentication authentication) {
        Member authenticatedMember = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_REPLY_ERR_MSG));
        memberValidator.validateIsOwnerReply(authenticatedMember.getId(), reply.getAuthor().getId());
        Comment parentComment = reply.getComment();
        if (parentComment.getIsDeleted().equals(Boolean.FALSE)) {
            reply.updateDeleteStatus();
        } else {
            if (reply.canDirectlyDelete()) commentRepository.delete(parentComment);
            else reply.updateDeleteStatus();
        }
        return new DeleteReplyResponse(replyId);
    }
}