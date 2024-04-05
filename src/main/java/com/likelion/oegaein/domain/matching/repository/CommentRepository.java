package com.likelion.oegaein.domain.matching.repository;

import com.likelion.oegaein.domain.matching.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
