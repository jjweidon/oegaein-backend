package com.likelion.oegaein.domain.member.service;

import com.likelion.oegaein.domain.member.dto.review.*;
import com.likelion.oegaein.domain.member.entity.member.Member;
import com.likelion.oegaein.domain.member.entity.review.Review;
import com.likelion.oegaein.domain.member.repository.MemberRepository;
import com.likelion.oegaein.domain.member.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewTestService {
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;

    public FindReviewResponse findReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found Review: " + reviewId));
        return new FindReviewResponse(FindReviewData.of(review));
    }

    public FindMemberReviewsResponse findMemberReviews(Long memberId) {
        Member receiver = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found Member: " + memberId));
        List<Review> reviews = reviewRepository.findAllByReceiver(receiver)
                .orElseThrow(() -> new EntityNotFoundException("Not Found Reviews: " + memberId));
        List<FindReviewData> findMemberReviewsData = reviews.stream()
                .map(FindReviewData::of)
                .toList();
        return FindMemberReviewsResponse.builder()
                .data(findMemberReviewsData)
                .build();
    }

    public CreateReviewResponse createReview(String email, CreateReviewRequest form) {
        Member writer = findAuthenticatedMember(email);
        Member receiver = memberRepository.findById(form.getReceiverId())
                .orElseThrow(() -> new EntityNotFoundException("Not Found Receiver: " + form.getReceiverId()));
        Review review = Review.builder()
                .writer(writer)
                .evaluation(form.getEvaluation())
                .semester(form.getSemester())
                .dormitory(form.getDormitory())
                .content(form.getContent())
                .receiver(receiver)
                .build();
        reviewRepository.save(review);

        return new CreateReviewResponse(review.getId());
    }

    public UpdateReviewResponse updateReview(String email, Long reviewId, UpdateReviewRequest form) {
        Member writer = findAuthenticatedMember(email);
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found Review: " + reviewId));
        review.update(form);

        return new UpdateReviewResponse(review.getId());
    }

    public DeleteReviewResponse removeReview(String email, Long reviewId) {
        Member writer = findAuthenticatedMember(email);
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found Review: " + reviewId));
        reviewRepository.delete(review);

        return new DeleteReviewResponse(reviewId);
    }

    private Member findAuthenticatedMember(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Not Found Member: " + email));
    }
}
