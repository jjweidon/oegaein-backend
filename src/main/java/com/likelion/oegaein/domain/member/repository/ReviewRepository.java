package com.likelion.oegaein.domain.member.repository;

import com.likelion.oegaein.domain.member.entity.member.Member;
import com.likelion.oegaein.domain.member.entity.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<List<Review>> findAllByReceiver(Member receiver);

    @Query(value = "SELECT COUNT(r) FROM Review r WHERE r.receiver = :member", nativeQuery = true)
    int countByReceiver(@Param("member") Member member);

    @Query(value = "SELECT AVG(r.evaluation.score) FROM Review r WHERE r.receiver = :member", nativeQuery = true)
    double averageScoreByReceiver(@Param("member") Member member);
}