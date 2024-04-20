package com.likelion.oegaein.domain.member.entity.review;

import com.likelion.oegaein.domain.matching.entity.DongType;
import com.likelion.oegaein.domain.member.dto.review.UpdateReviewRequest;
import com.likelion.oegaein.domain.member.entity.Member;
import com.likelion.oegaein.domain.member.dto.review.UpdateReviewRequest;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Transactional
public class Review {
    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Member writer;
    @Enumerated(EnumType.STRING)
    private Evaluation evaluation;
    @Enumerated(EnumType.STRING)
    private Semester semester;
    @Enumerated(EnumType.STRING)
    private DongType dormitory;
    @Size(max = 50)
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Member receiver;

    public void update(UpdateReviewRequest request) {
        this.evaluation = request.getEvaluation();
        this.semester = request.getSemester();
        this.dormitory = request.getDormitory();
        this.content = request.getContent();
    }
}
