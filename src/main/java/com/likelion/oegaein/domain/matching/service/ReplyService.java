package com.likelion.oegaein.domain.matching.service;

import com.likelion.oegaein.domain.matching.dto.reply.*;
import com.likelion.oegaein.domain.matching.entity.Comment;
import com.likelion.oegaein.domain.matching.entity.Reply;
import com.likelion.oegaein.domain.matching.repository.CommentRepository;
import com.likelion.oegaein.domain.matching.repository.ReplyRepository;
import com.likelion.oegaein.domain.member.entity.Member;
import com.likelion.oegaein.domain.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public CreateReplyResponse saveReply(CreateReplyData dto) {
        Comment comment = commentRepository.findById(dto.getCommentId())
                .orElseThrow(() -> new EntityNotFoundException("Not Found: comment"));
        Member author = memberRepository.findById(2L)
                .orElseThrow(() -> new EntityNotFoundException("Not Found: member"));
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
    public UpdateReplyResponse updateReply(UpdateReplyData dto, Long replyId) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found: reply"));
        reply.updateContent(dto.getContent());
        return new UpdateReplyResponse(replyId);
    }

    @Transactional
    public DeleteReplyResponse removeReply(Long replyId) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found: reply"));
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