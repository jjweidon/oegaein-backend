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
        Member receiver = null; // memberRepository.findById(1L).orElseThrow(); // 임시 수신자
        Comment parentComment = dto.getParentCommentId() == null ? null : commentRepository.findById(dto.getParentCommentId())
                .orElseThrow(() -> new EntityNotFoundException("Not Found: comment"));

        // create new comment
        Comment comment = Comment.builder()
                .content(dto.getContent())
                .matchingPost(matchingPost)
                .parentComment(parentComment)
                .author(author)
                .receiver(receiver)
                .build();
        commentRepository.save(comment);
        return new CreateCommentResponse(comment.getId());
    }

    @Transactional
    public UpdateCommentResponse updateComment(UpdateCommentData dto, Long commentId){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found: comment"));
        Member receiver = null;
        comment.updateContent(dto.getContent());
        comment.updateReceiver(receiver);
        return new UpdateCommentResponse(commentId);
    }

    @Transactional
    public DeleteCommentResponse removeComment(Long commentId){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found: comment"));
        commentRepository.delete(comment);
        return new DeleteCommentResponse(commentId);
    }
}