package com.likelion.oegaein.domain.matching.repository;

import com.likelion.oegaein.domain.matching.entity.MatchingPostComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<MatchingPostComment, Long> {
}
