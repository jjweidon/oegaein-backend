package com.likelion.oegaein.domain.member.controller;

import com.likelion.oegaein.domain.member.dto.review.*;
import com.likelion.oegaein.domain.member.service.ReviewService;
import com.likelion.oegaein.domain.member.service.ReviewTestService;
import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@RestController
public class ReviewTestController {
    private final ReviewTestService reviewTestService;
    private static final String email = "test1@hufs.ac.kr";

    @GetMapping("api/v1/test/{memberId}/reviews") // 멤버의 전체 리뷰 조희
    public ResponseEntity<ResponseDto> getReviewsTest(@PathVariable("memberId") Long memberId) {
        log.info("Request to get member's reviews");
        FindMemberReviewsResponse response = reviewTestService.findMemberReviews(memberId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("api/v1/test/review/{reviewId}") // 특정 리뷰 조회
    public ResponseEntity<ResponseDto> getReviewTest(@PathVariable("reviewId") Long reviewId) {
        log.info("Request to get a review");
        FindReviewResponse response = reviewTestService.findReview(reviewId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("api/v1/test/review") // 리뷰 등록
    public ResponseEntity<ResponseDto> postReviewTest(@RequestBody CreateReviewRequest dto) {
        log.info("Request to post review");
        CreateReviewResponse response = reviewTestService.createReview(email, dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("api/v1/test/review/{reviewId}") // 리뷰 수정
    public ResponseEntity<ResponseDto> putReviewTest(@PathVariable("reviewId") Long reviewId, @RequestBody UpdateReviewRequest dto) {
        log.info("Request to put review");
        UpdateReviewResponse response = reviewTestService.updateReview(email, reviewId, dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("api/v1/test/review/{reviewId}") // 리뷰 삭제
    public ResponseEntity<ResponseDto> deleteReviewTest(@PathVariable("reviewId") Long reviewId) {
        log.info("Request to delete review");
        DeleteReviewResponse response = reviewTestService.removeReview(email, reviewId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
