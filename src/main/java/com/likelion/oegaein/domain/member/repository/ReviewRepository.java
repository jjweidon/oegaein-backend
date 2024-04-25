package com.likelion.oegaein.domain.member.repository;

import com.likelion.oegaein.domain.member.entity.Member;
import com.likelion.oegaein.domain.member.entity.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<List<Review>> findAllByReceiver(Member receiver);
}
