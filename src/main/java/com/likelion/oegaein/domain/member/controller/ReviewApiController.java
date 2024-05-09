package com.likelion.oegaein.domain.member.controller;

import com.likelion.oegaein.domain.member.dto.review.*;
import com.likelion.oegaein.domain.member.service.ProfileService;
import com.likelion.oegaein.domain.member.service.ReviewService;
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
public class ReviewApiController {
    private final ReviewService reviewService;
    private final ProfileService profileService;

    @GetMapping("api/v1/{memberId}/reviews") // 멤버의 전체 리뷰 조희
    public ResponseEntity<ResponseDto> getReviews(@PathVariable("memberId") Long memberId) {
        log.info("Request to get reviews");
        FindMemberReviewsResponse response = reviewService.findMemberReviews(memberId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("api/v1/review/{reviewId}") // 특정 리뷰 조회
    public ResponseEntity<ResponseDto> getReview(@PathVariable("reviewId") Long reviewId) {
        log.info("Request to get a review");
        FindReviewResponse response = reviewService.findReview(reviewId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("api/v1/review") // 리뷰 등록
    public ResponseEntity<ResponseDto> postReview(Authentication authentication, @RequestBody CreateReviewRequest dto) {
        log.info("Request to post review");
        CreateReviewResponse response = reviewService.createReview(authentication, dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

//    @PutMapping("api/v1/review/{reviewId}") // 리뷰 수정
//    public ResponseEntity<ResponseDto> putReview(Authentication authentication, @PathVariable("reviewId") Long reviewId, @RequestBody UpdateReviewRequest dto) {
//        log.info("Request to put review");
//        UpdateReviewResponse response = reviewService.updateReview(authentication, reviewId, dto);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//    @DeleteMapping("api/v1/review/{reviewId}") // 리뷰 삭제
//    public ResponseEntity<ResponseDto> deleteReview(Authentication authentication, @PathVariable("reviewId") Long reviewId) {
//        log.info("Request to delete review");
//        DeleteReviewResponse response = reviewService.removeReview(authentication, reviewId);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
}
