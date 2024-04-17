package com.likelion.oegaein.domain.member.service;

import com.likelion.oegaein.domain.member.dto.review.*;
import com.likelion.oegaein.domain.member.entity.Member;
import com.likelion.oegaein.domain.member.entity.review.Review;
import com.likelion.oegaein.domain.member.repository.MemberRepository;
import com.likelion.oegaein.domain.member.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
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

    public CreateReviewResponse createReview(Authentication authentication, CreateReviewRequest form) {
        Member writer = findAuthenticatedMember(authentication);
        Review review = Review.builder()
                .writer(writer)
                .evaluation(form.getEvaluation())
                .semester(form.getSemester())
                .dormitory(form.getDormitory())
                .content(form.getContent())
                .receiver(form.getReceiver())
                .build();
        reviewRepository.save(review);

        return new CreateReviewResponse(review.getId());
    }

    public UpdateReviewResponse updateReview(Authentication authentication, Long reviewId, UpdateReviewRequest form) {
        Member writer = findAuthenticatedMember(authentication);
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found Review: " + reviewId));
        review.update(form);

        return new UpdateReviewResponse(review.getId());
    }

    public DeleteReviewResponse removeReview(Authentication authentication, Long reviewId) {
        Member writer = findAuthenticatedMember(authentication);
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found Review: " + reviewId));
        reviewRepository.delete(review);

        return new DeleteReviewResponse(reviewId);
    }

    private Member findAuthenticatedMember(Authentication authentication) {
        return memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException("Not Found Member: " + authentication.getName()));
    }
}
