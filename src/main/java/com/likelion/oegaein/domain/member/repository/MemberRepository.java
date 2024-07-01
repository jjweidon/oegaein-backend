package com.likelion.oegaein.domain.member.repository;

import com.likelion.oegaein.domain.member.entity.member.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @EntityGraph(attributePaths = {"profile"})
    Optional<Member> findByEmail(String email);
}
