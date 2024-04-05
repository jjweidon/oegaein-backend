package com.likelion.oegaein.domain.matching.entity;

import com.likelion.oegaein.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
public class Comment {
    @Id @GeneratedValue
    private Long id;
    private String content; // 댓글 내용
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matchingpost_id")
    private MatchingPost matchingPost; // 게시글 FK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member author; // 작성자 FK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment parentComment; // 부모 댓글 FK
    @ManyToOne(fetch = FetchType.LAZY)
    private Member receiver;
    @OneToMany(mappedBy = "parentComment", orphanRemoval = true)
    private final List<Comment> childrenComment = new ArrayList<>();
    @CreationTimestamp
    private LocalDateTime createdAt; // 작성일
    @UpdateTimestamp
    private LocalDateTime modifiedAt; // 수정일

    public void updateContent(String content){
        this.content = content;
    }

    public void updateReceiver(Member receiver){
        this.receiver = receiver;
    }
}
