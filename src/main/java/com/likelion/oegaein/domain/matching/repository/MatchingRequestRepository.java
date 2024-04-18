package com.likelion.oegaein.domain.matching.repository;

import com.likelion.oegaein.domain.matching.entity.MatchingPost;
import com.likelion.oegaein.domain.matching.entity.MatchingRequest;
import com.likelion.oegaein.domain.member.entity.profile.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MatchingRequestRepository extends JpaRepository<MatchingRequest, Long> {
    @EntityGraph(attributePaths = {"matchingPost", "participant"})
    List<MatchingRequest> findByParticipant(Member participant);

    Optional<MatchingRequest> findByParticipantAndMatchingPost(Member participant, MatchingPost matchingPost);
}
