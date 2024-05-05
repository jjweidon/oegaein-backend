package com.likelion.oegaein.domain.member.repository;

import com.likelion.oegaein.domain.member.entity.member.Likey;
import com.likelion.oegaein.domain.member.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likey, Long> {
    Optional<List<Likey>> findBySender(Member sender);
//    Optional<Likey> findBySenderReceiver(Member sender, Member receiver);
    @Query(value = "SELECT l FROM Likey l WHERE l.sender = :sender AND l.receiver = :receiver", nativeQuery = true)
    Optional<Likey> findBySenderReceiver(@Param("sender") Member sender, @Param("receiver") Member receiver);
}
