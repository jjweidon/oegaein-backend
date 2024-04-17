package com.likelion.oegaein.domain.matching.service;

import com.likelion.oegaein.domain.matching.dto.comment.*;
import com.likelion.oegaein.domain.matching.entity.Comment;
import com.likelion.oegaein.domain.matching.entity.MatchingPost;
import com.likelion.oegaein.domain.matching.repository.CommentRepository;
import com.likelion.oegaein.domain.matching.repository.MatchingPostRepository;
import com.likelion.oegaein.domain.member.entity.Member;
import com.likelion.oegaein.domain.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MatchingPostRepository matchingPostRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public CreateCommentResponse saveComment(CreateCommentData dto){
        // find author, parentComment, matchingPost and receiver
        MatchingPost matchingPost = matchingPostRepository.findById(dto.getMatchingPostId())
                .orElseThrow(() -> new EntityNotFoundException("Not Found: matchingPost"));
        Member author = memberRepository.findById(2L).orElseThrow(() -> new EntityNotFoundException("Not Found: member")); // 임시 작성자

        // create new comment
        Comment comment = Comment.builder()
                .content(dto.getContent())
                .matchingPost(matchingPost)
                .author(author)
                .isDeleted(Boolean.FALSE)
                .build();
        commentRepository.save(comment);
        return new CreateCommentResponse(comment.getId());
    }

    @Transactional
    public UpdateCommentResponse updateComment(UpdateCommentData dto, Long commentId){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found: comment"));
        comment.updateContent(dto.getContent());
        return new UpdateCommentResponse(commentId);
    }

    @Transactional
    public DeleteCommentResponse removeComment(Long commentId){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found: comment"));
        if(comment.getReplies().isEmpty()){ // no child comments
            commentRepository.delete(comment);
        }else{ // has child comments
            comment.updateDeleteStatus();
            if(comment.canDirectlyDelete()) commentRepository.delete(comment);
        }
        return new DeleteCommentResponse(commentId);
    }
}