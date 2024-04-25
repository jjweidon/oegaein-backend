package com.likelion.oegaein.domain.matching.service;

import com.likelion.oegaein.domain.matching.dto.comment.*;
import com.likelion.oegaein.domain.matching.entity.MatchingPostComment;
import com.likelion.oegaein.domain.matching.entity.MatchingPost;
import com.likelion.oegaein.domain.matching.repository.CommentRepository;
import com.likelion.oegaein.domain.matching.repository.MatchingPostRepository;
import com.likelion.oegaein.domain.member.entity.Member;
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
public class CommentService {
    // constants
    private final String NOT_FOUND_MATCHING_POST_ERR_MSG = "찾을 수 없는 매칭글입니다.";
    private final String NOT_FOUND_MEMBER_ERR_MSG = "찾을 수 없는 사용자입니다.";
    private final String NOT_FOUND_COMMENT_ERR_MSG = "찾을 수 없는 댓글입니다.";
    // repository
    private final CommentRepository commentRepository;
    private final MatchingPostRepository matchingPostRepository;
    private final MemberRepository memberRepository;
    // validator
    private final MemberValidator memberValidator;

    @Transactional
    public CreateCommentResponse saveComment(CreateCommentData dto, Authentication authentication){
        // find author, parentComment, matchingPost and receiver
        MatchingPost matchingPost = matchingPostRepository.findById(dto.getMatchingPostId())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MATCHING_POST_ERR_MSG));
        Member author = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG)); // 임시 작성자

        // create new comment
        MatchingPostComment comment = MatchingPostComment.builder()
                .content(dto.getContent())
                .matchingPost(matchingPost)
                .author(author)
                .isDeleted(Boolean.FALSE)
                .build();
        commentRepository.save(comment);
        return new CreateCommentResponse(comment.getId());
    }

    @Transactional
    public UpdateCommentResponse updateComment(UpdateCommentData dto, Long commentId, Authentication authentication){
        Member authenticatedMember = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        MatchingPostComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_COMMENT_ERR_MSG));
        memberValidator.validateIsOwnerComment(authenticatedMember.getId(), comment.getAuthor().getId());
        comment.updateContent(dto.getContent());
        return new UpdateCommentResponse(commentId);
    }

    @Transactional
    public DeleteCommentResponse removeComment(Long commentId, Authentication authentication){
        Member authenticatedMember = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        MatchingPostComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_COMMENT_ERR_MSG));
        memberValidator.validateIsOwnerComment(authenticatedMember.getId(), comment.getAuthor().getId());
        if(comment.getReplies().isEmpty()){ // no child comments
            commentRepository.delete(comment);
        }else{ // has child comments
            comment.updateDeleteStatus();
            if(comment.canDirectlyDelete()) commentRepository.delete(comment);
        }
        return new DeleteCommentResponse(commentId);
    }
}