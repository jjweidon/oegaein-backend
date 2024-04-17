package com.likelion.oegaein.domain.matching.service;

import com.likelion.oegaein.domain.matching.dto.matchingpost.*;
import com.likelion.oegaein.domain.matching.entity.MatchingPost;
import com.likelion.oegaein.domain.matching.entity.MatchingStatus;
import com.likelion.oegaein.domain.matching.validation.MatchingPostValidator;
import com.likelion.oegaein.domain.member.entity.profile.Member;
import com.likelion.oegaein.domain.matching.repository.MatchingPostRepository;
import com.likelion.oegaein.domain.matching.repository.query.MatchingPostQueryRepository;
import com.likelion.oegaein.domain.member.repository.MemberRepository;
import com.likelion.oegaein.domain.member.validation.MemberValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MatchingPostService {
    // constants
    private final String NOT_FOUND_MEMBER_ERR_MSG = "찾을 수 없는 사용자입니다.";
    private final String NOT_FOUND_MATCHING_POST_ERR_MSG = "찾을 수 없는 매칭글입니다.";
    // repository & validator
    private final MatchingPostRepository matchingPostRepository;
    private final MatchingPostQueryRepository matchingPostQueryRepository;
    private final MemberRepository memberRepository;
    // validators
    private final MatchingPostValidator matchingPostValidator;
    private final MemberValidator memberValidator;

    // 모든 매칭글 조회
    public FindMatchingPostsResponse findAllMatchingPosts(){
        // find matchingPosts
        List<MatchingPost> matchingPosts = matchingPostRepository.findAll();
        // create matchingPostsData
        List<FindMatchingPostsData> matchingPostsData = matchingPosts.stream()
                .map(FindMatchingPostsData::toFindMatchingPostsData)
                .toList();
        return new FindMatchingPostsResponse(matchingPostsData);
    }

    // 매칭글 작성
    @Transactional
    public CreateMatchingPostResponse saveMatchingPost(Authentication authentication, CreateMatchingPostData dto){
        matchingPostValidator.validateRoomSizeAndTargetNumOfPeople(dto.getRoomSizeType(), dto.getTargetNumberOfPeople());
        // Member author = memberRepository.findByEmail(authentication.getName())
        //         .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        // Member author = memberRepository.findById(); // 로그인 구현 완료시 사용
        Member author = new Member();
        // Create MatchingPost Entity
        MatchingPost newMatchingPost = MatchingPost.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .deadline(dto.getDeadline())
                .targetNumberOfPeople(dto.getTargetNumberOfPeople())
                .dongType(dto.getDongType())
                .roomSizeType(dto.getRoomSizeType())
                .matchingStatus(MatchingStatus.WAITING)
                .author(author)
                .build();
        // persist entity
        matchingPostRepository.save(newMatchingPost);
        return new CreateMatchingPostResponse(newMatchingPost.getId());
    }

    // 특정 매칭글 조회(ID)
    public FindMatchingPostResponse findByIdMatchingPost(Long matchingPostId){
        MatchingPost matchingPost = matchingPostRepository.findById(matchingPostId)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_MATCHING_POST_ERR_MSG));
        return FindMatchingPostResponse.toFindMatchingPostResponse(matchingPost);
    }

    // 특정 매칭글 삭제
    @Transactional
    public void removeMatchingPost(Long matchingPostId, Authentication authentication) {
        Member authenticatedMember = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        MatchingPost findMatchingPost = matchingPostRepository.findById(matchingPostId)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_MATCHING_POST_ERR_MSG));
        memberValidator.validateIsOwnerMatchingPost(authenticatedMember.getId(), findMatchingPost.getAuthor().getId());
        matchingPostRepository.delete(findMatchingPost);
    }

    // 매칭글 수정
    @Transactional
    public UpdateMatchingPostResponse updateMatchingPost(Long matchingPostId, UpdateMatchingPostData dto, Authentication authentication){
        Member authenticatedMember = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        MatchingPost findMatchingPost = matchingPostRepository.findById(matchingPostId)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_MATCHING_POST_ERR_MSG));
        memberValidator.validateIsOwnerMatchingPost(authenticatedMember.getId(), findMatchingPost.getAuthor().getId());
        matchingPostValidator.validateRoomSizeAndTargetNumOfPeople(dto.getRoomSizeType(), dto.getTargetNumberOfPeople());
        findMatchingPost.updateMatchingPost(dto); // dirty checking
        return new UpdateMatchingPostResponse(matchingPostId);
    }

    // 내 매칭글 조회
    public FindMyMatchingPostResponse findMyMatchingPosts(Authentication authentication){
        Member author = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MEMBER_ERR_MSG));
        List<MatchingPost> findMatchingPosts = matchingPostRepository.findByAuthor(author);
        List<FindMyMatchingPostData> findMyMatchingPostData = findMatchingPosts.stream()
                .map(FindMyMatchingPostData::toFindMyMatchingPostData
                ).toList();
        return FindMyMatchingPostResponse.builder()
                .data(findMyMatchingPostData)
                .build();
    }

    // 베스트 룸메이트 매칭글 조회
    public FindBestRoomMateMatchingPostsResponse findBestRoomMateMatchingPosts(){
        List<MatchingPost> findMatchingPosts = matchingPostQueryRepository.findBestRoomMateMatchingPosts();
        List<FindBestRoomMateMatchingPostsData> bestRoomMateMatchingPostsData = findMatchingPosts.stream()
                .map(FindBestRoomMateMatchingPostsData::toFindBestRoomMateMatchingPostData)
                .toList();
        return new FindBestRoomMateMatchingPostsResponse(bestRoomMateMatchingPostsData);
    }

    public FindDeadlineImminentMatchingPostsResponse findDeadlineImminentMatchingPosts(){
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime beforeOneDayDate = LocalDateTime.now().plusDays(1);
        List<MatchingPost> findMatchingPosts = matchingPostQueryRepository.findMatchingPostsBetweenTwoDates(
                beforeOneDayDate,
                currentDate
        );
        List<FindDeadlineImminentMatchingPostsData> deadlineImminentMatchingPostsData = findMatchingPosts.stream()
                .map(FindDeadlineImminentMatchingPostsData::toFindDeadlineImminentMatchingPostsData)
                .toList();
        return new FindDeadlineImminentMatchingPostsResponse(deadlineImminentMatchingPostsData);
    }
}