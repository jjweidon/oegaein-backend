package com.likelion.oegaein.domain.member.entity.review;

import com.likelion.oegaein.domain.matching.entity.DongType;
import com.likelion.oegaein.domain.member.entity.Member;
import com.likelion.oegaein.domain.member.entity.profile.Profile;
import com.likelion.oegaein.domain.member.entity.review.Evaluation;
import com.likelion.oegaein.domain.member.entity.review.Semester;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Transactional
public class Review {
    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE) // 변경 필요
    @JoinColumn(name = "member_id")
    private Member writer;
    private Evaluation evaluation;
    private Semester semester;
    private DongType dormitory;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Profile profile;
}
