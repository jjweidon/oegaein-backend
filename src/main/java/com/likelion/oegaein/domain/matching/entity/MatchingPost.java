package com.likelion.oegaein.domain.matching.entity;

import com.likelion.oegaein.domain.member.entity.profile.Member;
import com.likelion.oegaein.domain.matching.dto.matchingpost.UpdateMatchingPostData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchingPost {
    @Id @GeneratedValue
    @Column(name = "matching_post_id")
    private Long id; // PK

    private String title;
    private String content;

    @Enumerated(EnumType.STRING)
    private DongType dongType; // 기숙사 동(* A,B,C,D,E)
    @Enumerated(EnumType.STRING)
    private RoomSizeType roomSizeType; // 기숙사 방 사이즈(* 2인실/4인실)

    private int targetNumberOfPeople; // 목표 인원 수

    private LocalDateTime deadline;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private MatchingStatus matchingStatus; // 매칭 상태, WAITING, COMPLETED

    @ManyToOne(fetch = FetchType.LAZY) // 변경 필요
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "author_id")
    private Member author;

    @OneToMany(mappedBy = "matchingPost", orphanRemoval = true)
    @Builder.Default
    private List<MatchingRequest> matchingRequests = new ArrayList<>();

    @OneToMany(mappedBy = "matchingPost", orphanRemoval = true)
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    public void updateMatchingPost(UpdateMatchingPostData dto){
        title = dto.getTitle();
        content = dto.getContent();
        deadline = dto.getDeadline();
        dongType = dto.getDongType();
        roomSizeType = dto.getRoomSizeType();
    }

    public void completeMatchingPost(){
        this.matchingStatus = MatchingStatus.COMPLETED;
    }
}
