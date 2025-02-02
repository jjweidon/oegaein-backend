package com.likelion.oegaein.domain.member.repository;

import com.likelion.oegaein.domain.member.entity.member.Block;
import com.likelion.oegaein.domain.member.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlockRepository extends JpaRepository<Block, Long> {
    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END " +
            "FROM Block b " +
            "WHERE b.blocked.id = :blockedId AND b.blocking.id = :blockingId")
    boolean isBlocked(@Param("blockedId") Long blockedId, @Param("blockingId") Long blockingId);

    List<Block> findByBlocking(Member blockingMember);
    List<Block> findByBlocked(Member blockedMember);
}