package com.likelion.oegaein.domain.matching.repository;

import com.likelion.oegaein.domain.matching.entity.MatchingPostReply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<MatchingPostReply, Long> {
}
