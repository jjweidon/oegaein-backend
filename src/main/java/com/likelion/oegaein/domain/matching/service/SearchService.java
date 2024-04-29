package com.likelion.oegaein.domain.matching.service;

import com.likelion.oegaein.domain.matching.dto.matchingpost.FindMatchingPostsData;
import com.likelion.oegaein.domain.matching.dto.search.GeneralSearchResponse;
import com.likelion.oegaein.domain.matching.entity.MatchingPost;
import com.likelion.oegaein.domain.matching.repository.MatchingPostRepository;
import com.likelion.oegaein.domain.matching.repository.query.MatchingPostQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchService {
    private final MatchingPostQueryRepository matchingPostQueryRepository;
    // 공동배달 레포지토리

    public GeneralSearchResponse searchGeneralPosts(String content){
        List<MatchingPost> findMatchingPosts = matchingPostQueryRepository.searchMatchingPost(content);
        List<FindMatchingPostsData> findMatchingPostsData = findMatchingPosts.stream()
                .map(FindMatchingPostsData::toFindMatchingPostsData).toList();
        return GeneralSearchResponse.builder()
                .matchingPostsData(findMatchingPostsData)
                .build();
    }
}
