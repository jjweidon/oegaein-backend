package com.likelion.oegaein.domain.matching.controller;

import com.likelion.oegaein.domain.matching.dto.comment.*;
import com.likelion.oegaein.domain.matching.service.CommentService;
import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentApiController {
    private final CommentService commentService;

    @PostMapping("/api/v1/comments")
    public ResponseEntity<ResponseDto> postComments(@RequestBody CreateCommentRequest dto){
        log.info("Request to post comments");
        CreateCommentResponse response = commentService.saveComment(CreateCommentData.toCreateCommentData(dto));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/api/v1/comments/{commentid}")
    public ResponseEntity<ResponseDto> putComment(@RequestBody UpdateCommentRequest dto, @PathVariable("commentid") Long commentId){
        log.info("Request to put comment");
        UpdateCommentResponse response = commentService.updateComment(UpdateCommentData.toUpdateCommentData(dto), commentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/comments/{commentid}")
    public ResponseEntity<ResponseDto> deleteComment(@PathVariable("commentid") Long commentId){
        log.info("Request to delete comment");
        DeleteCommentResponse response = commentService.removeComment(commentId);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
