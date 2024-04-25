package com.likelion.oegaein.domain.matching.service;

import com.likelion.oegaein.domain.alarm.entity.RoommateAlarm;
import com.likelion.oegaein.domain.alarm.entity.RoommateAlarmType;
import com.likelion.oegaein.domain.alarm.repository.RoommateAlarmRepository;
import com.likelion.oegaein.domain.alarm.repository.query.RoommateAlarmQueryRepository;
import com.likelion.oegaein.domain.email.dto.EmailMessage;
import com.likelion.oegaein.domain.email.service.EmailService;
import com.likelion.oegaein.domain.matching.dto.matchingrequest.*;
import com.likelion.oegaein.domain.matching.entity.MatchingPost;
import com.likelion.oegaein.domain.matching.entity.MatchingRequest;
import com.likelion.oegaein.domain.matching.repository.query.MatchingRequestQueryRepository;
import com.likelion.oegaein.domain.matching.validation.MatchingRequestValidator;
import com.likelion.oegaein.domain.member.entity.Member;
import com.likelion.oegaein.domain.matching.repository.MatchingPostRepository;
import com.likelion.oegaein.domain.matching.repository.MatchingRequestRepository;
import com.likelion.oegaein.domain.member.repository.MemberRepository;
import com.likelion.oegaein.domain.member.validation.BlockValidator;
import com.likelion.oegaein.domain.member.validation.MemberValidator;
import com.likelion.oegaein.global.dto.ResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MatchingRequestService {
    // constants
    private final String NOT_FOUND_MEMBER_ERR_MSG = "찾을 수 없는 사용자입니다.";
    private final String NOT_FOUND_MATCHING_POST_ERR_MSG = "찾을 수 없는 매칭글입니다.";
    private final String NOT_FOUND_MATCHING_REQ_ERR_MSG = "찾을 수 없는 매칭요청입니다.";
    private final String EMAIL_MATCHING_REQUEST_SUBJECT = "[외개인] 매칭 요청이 도착했습니다.";
    private final String EMAIL_MATCHING_REQUEST_ACCEPT_SUBJECT = "[외개인] 매칭 요청이 수락되었습니다.";
    private final String EMAIL_MATCHING_REQUEST_REJECT_SUBJECT = "[외개인] 매칭 요청이 거부되었습니다.";
    private final String EMAIL_MATCHING_REQUEST_COMPLETE_SUBJECT = "[외개인] 매칭 요청이 완료되었습니다.";
    private final String EMAIL_MATCHING_REQUEST_TYPE = "matchingrequest";
    private final String EMAIL_MATCHING_REQUEST_ACCEPT_TYPE = "matchingrequestaccept";
    private final String EMAIL_MATCHING_REQUEST_REJECT_TYPE = "matchingrequestreject";
    private final String EMAIL_MATCHING_COMPLETE_TYPE = "matchingrequestcomplete";

    // repository
    private final MatchingRequestRepository matchingRequestRepository;
    private final MatchingRequestQueryRepository matchingRequestQueryRepository;
    private final MatchingPostRepository matchingPostRepository;
    private final MemberRepository memberRepository;
    private final RoommateAlarmRepository roommateAlarmRepository;
    private final RoommateAlarmQueryRepository roommateAlarmQueryRepository;
    // service
    private final EmailService emailService;
    // validators
    private final MatchingRequestValidator matchingRequestValidator;
    private final MemberValidator memberValidator;
    private final BlockValidator blockValidator;

    public FindMyMatchingReqsResponse findMyMatchingRequest(Authentication authentication){
        Member participant = memberRepository.findByEmail(authentication.getName()) // 인증 유저 조회
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        // 내 매칭 요청 목록 조회
        List<MatchingRequest> matchingRequests = matchingRequestRepository.findByParticipant(participant);
        List<FindMyMatchingReqData> matchingReqDatas = matchingRequests.stream()
                .map(FindMyMatchingReqData::toFindMatchingReqData)
                .toList();
        return new FindMyMatchingReqsResponse(matchingReqDatas.size(), matchingReqDatas);
    }

    public FindComeMatchingReqsResponse findComeMatchingRequest(Authentication authentication){
        Member author = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG)); // 인증 유저 조회
        List<MatchingRequest> matchingRequests = matchingRequestQueryRepository.findComeMatchingRequests(author);
        List<FindComeMatchingReqData> matchingReqDatas = matchingRequests.stream()
                .map(FindComeMatchingReqData::toFindComeMatchingReqData)
                .toList();
        return new FindComeMatchingReqsResponse(matchingReqDatas.size(), matchingReqDatas);
    }

    @Transactional
    public CreateMatchingReqResponse createMatchingRequest(CreateMatchingReqData dto, Authentication authentication){
        // find participant
        Member findParticipant = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        // find matchingPost
        MatchingPost findMatchingPost = matchingPostRepository.findById(dto.getMatchingPostId())
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_MATCHING_POST_ERR_MSG));
        // validation
        matchingRequestValidator.validateIsNotSelfRequest(findParticipant.getId(), findMatchingPost.getAuthor().getId());
        matchingRequestValidator.validateIsNotAlreadyRequest(findParticipant, findMatchingPost);
        matchingRequestValidator.validateIsNotCompletedOrExpiredMatchingPost(findMatchingPost);
        blockValidator.validateBlockedMember(findParticipant.getId(), findMatchingPost.getAuthor().getId());
        // create new matching request
        MatchingRequest newMatchingRequest = new MatchingRequest(findMatchingPost, findParticipant);
        matchingRequestRepository.save(newMatchingRequest);
        RoommateAlarm roommateAlarm = RoommateAlarm.builder()
                .matchingPost(findMatchingPost)
                .member(findMatchingPost.getAuthor())
                .alarmType(RoommateAlarmType.MATCHING_REQUEST)
                .build();
        roommateAlarmRepository.save(roommateAlarm);
        EmailMessage emailMessage = EmailMessage.builder()
                .to(findMatchingPost.getAuthor().getEmail())
                .subject(EMAIL_MATCHING_REQUEST_SUBJECT)
                .message(findMatchingPost.getTitle())
                .build();
        emailService.sendMail(emailMessage,EMAIL_MATCHING_REQUEST_TYPE);
        return new CreateMatchingReqResponse(newMatchingRequest.getId());
    }

    @Transactional
    public void removeMatchingRequest(Long matchingRequestId, Authentication authentication){
        Member authenticatedMember = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        MatchingRequest matchingRequest = matchingRequestRepository.findById(matchingRequestId)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_MATCHING_REQ_ERR_MSG));
        memberValidator.validateIsOwnerMatchingRequest(authenticatedMember.getId(), matchingRequest.getParticipant().getId());
        matchingRequestValidator.validateIsNotAcceptOrRejectMatchingRequest(matchingRequest);
        matchingRequestRepository.delete(matchingRequest);
    }

    @Transactional
    public ResponseDto acceptMatchingRequest(Long matchingRequestId, Authentication authentication){
        Member authenticatedMember = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        MatchingRequest matchingRequest = matchingRequestRepository.findById(matchingRequestId)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_MATCHING_REQ_ERR_MSG));
        MatchingPost matchingPost = matchingRequest.getMatchingPost();
        // validation
        memberValidator.validateIsOwnerComeMatchingRequest(authenticatedMember.getId(), matchingPost.getAuthor().getId());
        matchingRequestValidator.validateIsNotAcceptOrRejectMatchingRequest(matchingRequest);
        // change matchingAcceptance
        matchingRequest.acceptMatchingRequest();
        // create alarm
        RoommateAlarm acceptRoommateAlarm = RoommateAlarm.builder()
                .member(matchingRequest.getParticipant())
                .matchingPost(matchingPost)
                .alarmType(RoommateAlarmType.MATCHING_REQUEST_ACCEPT)
                .build();
        roommateAlarmRepository.save(acceptRoommateAlarm);
        EmailMessage emailMessage = EmailMessage.builder()
                .to(matchingRequest.getParticipant().getEmail())
                .subject(EMAIL_MATCHING_REQUEST_ACCEPT_SUBJECT)
                .message(matchingPost.getTitle())
                .build();
        emailService.sendMail(emailMessage,EMAIL_MATCHING_REQUEST_TYPE);
        // check matching is completed
        if(isCompletedMatching(matchingPost)){
            updateFailedMatchingRequests(matchingPost);
            updateCompletedMatchingRequests(matchingPost);
            // generate uuid
            String chatRoomNo = UUID.randomUUID().toString();
            // return matchingReqResponse
            return new CompletedMatchingResponse(
                    chatRoomNo
            );
        }
        // not completed matching
        return new AcceptMatchingReqResponse(matchingRequestId);
    }

    @Transactional
    public RejectMatchingReqResponse rejectMatchingRequest(Long matchingRequestId, Authentication authentication){
        Member authenticatedMember = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        MatchingRequest matchingRequest = matchingRequestRepository.findById(matchingRequestId)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_MATCHING_REQ_ERR_MSG));
        MatchingPost matchingPost = matchingRequest.getMatchingPost();
        // validation
        memberValidator.validateIsOwnerComeMatchingRequest(authenticatedMember.getId(), matchingPost.getAuthor().getId());
        matchingRequestValidator.validateIsNotAcceptOrRejectMatchingRequest(matchingRequest);
        // create alarm
        RoommateAlarm roommateAlarm = RoommateAlarm.builder()
                .member(matchingRequest.getParticipant())
                .matchingPost(matchingPost)
                .alarmType(RoommateAlarmType.MATCHING_REQUEST_REJECT)
                .build();
        roommateAlarmRepository.save(roommateAlarm);
        // change matchingAcceptance
        matchingRequest.rejectMatchingRequest();
        EmailMessage emailMessage = EmailMessage.builder()
                .to(matchingRequest.getParticipant().getEmail())
                .subject(EMAIL_MATCHING_REQUEST_REJECT_SUBJECT)
                .message(matchingPost.getTitle())
                .build();
        emailService.sendMail(emailMessage,EMAIL_MATCHING_REQUEST_REJECT_TYPE);
        // return matchingReqResponse
        return new RejectMatchingReqResponse(
                matchingRequest.getId()
        );
    }

    // 사용자 정의 메서드
    private Boolean isCompletedMatching(MatchingPost matchingPost){
        int targetNumberOfPeople = matchingPost.getTargetNumberOfPeople();
        int completedMatchingRequest = matchingRequestQueryRepository
                .countCompletedMatchingRequest(matchingPost);
        return targetNumberOfPeople == completedMatchingRequest;
    }

    private void updateFailedMatchingRequests(MatchingPost matchingPost){
        // change other requests of status
        List<MatchingRequest> failedMatchingRequests = matchingPost.getMatchingRequests().stream()
                .filter((mr) -> mr.getFailedMatchingRequestId().isPresent()).toList();
        List<Long> failedMatchingRequestsId = failedMatchingRequests.stream().map(MatchingRequest::getId).toList();
        if(!failedMatchingRequests.isEmpty()){
            matchingRequestQueryRepository.bulkUpdateFailedMatchingRequest(failedMatchingRequestsId);
            // create alarms
            List<RoommateAlarm> failedRoommateAlarms = failedMatchingRequests.stream().map((fmr) -> RoommateAlarm.builder()
                    .member(fmr.getParticipant())
                    .matchingPost(matchingPost)
                    .alarmType(RoommateAlarmType.MATCHING_REQUEST_REJECT)
                    .build()).toList();
            roommateAlarmQueryRepository.bulkSaveAll(failedRoommateAlarms);
            List<EmailMessage> emailMessages = failedMatchingRequests.stream().map((fmr) -> EmailMessage.builder()
                    .to(fmr.getParticipant().getEmail())
                    .subject(EMAIL_MATCHING_REQUEST_REJECT_SUBJECT)
                    .message(fmr.getMatchingPost().getTitle())
                    .build()).toList();
            emailMessages.forEach((emailMessage) -> emailService.sendMail(emailMessage, EMAIL_MATCHING_REQUEST_REJECT_TYPE));
        }
    }

    private void updateCompletedMatchingRequests(MatchingPost matchingPost){
        // change matchingPost of status
        matchingPost.completeMatchingPost();
        // create alarms
        List<MatchingRequest> succeedMatchingRequests = matchingPost.getMatchingRequests().stream()
                .filter((mr) -> mr.getSucceedMatchingRequestId().isPresent()).toList();
        List<RoommateAlarm> succeedRoommateAlarms = new java.util.ArrayList<>(succeedMatchingRequests.stream().map((smr) -> RoommateAlarm.builder()
                .member(smr.getParticipant())
                .matchingPost(matchingPost)
                .alarmType(RoommateAlarmType.MATCHING_POST_COMPLETED)
                .build()).toList());
        RoommateAlarm succeedRoommateAlarm = RoommateAlarm.builder()
                        .member(matchingPost.getAuthor())
                                .matchingPost(matchingPost)
                                        .alarmType(RoommateAlarmType.MATCHING_POST_COMPLETED)
                                                .build();
        succeedRoommateAlarms.add(succeedRoommateAlarm);
        roommateAlarmQueryRepository.bulkSaveAll(succeedRoommateAlarms);
        List<EmailMessage> emailMessages = succeedMatchingRequests.stream().map((fmr) -> EmailMessage.builder()
                .to(fmr.getParticipant().getEmail())
                .subject(EMAIL_MATCHING_REQUEST_COMPLETE_SUBJECT)
                .message(fmr.getMatchingPost().getTitle())
                .build()).toList();
        emailMessages.forEach((emailMessage) -> emailService.sendMail(emailMessage, EMAIL_MATCHING_COMPLETE_TYPE));
    }
}
