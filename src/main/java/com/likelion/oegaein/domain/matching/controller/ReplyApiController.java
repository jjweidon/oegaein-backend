package com.likelion.oegaein.domain.matching.controller;

import com.likelion.oegaein.domain.matching.dto.reply.*;
import com.likelion.oegaein.domain.matching.service.ReplyService;
import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReplyApiController {
    private final ReplyService replyService;

    @PostMapping("/api/v1/replies")
    public ResponseEntity<ResponseDto> postReplies(@RequestBody CreateReplyRequest dto){
        log.info("Request to post replies");
        CreateReplyResponse response = replyService.saveReply(CreateReplyData.toCreateReplyData(dto));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/api/v1/replies/{replyid}")
    public ResponseEntity<ResponseDto> putReply(@RequestBody UpdateReplyRequest dto, @PathVariable("replyid") Long replyId){
        log.info("Request to put reply");
        UpdateReplyResponse response = replyService.updateReply(UpdateReplyData.toUpdateReplyData(dto), replyId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/replies/{replyid}")
    public ResponseEntity<ResponseDto> deleteReply(@PathVariable("replyid") Long replyId){
        log.info("Request to delete reply");
        DeleteReplyResponse response = replyService.removeReply(replyId);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
