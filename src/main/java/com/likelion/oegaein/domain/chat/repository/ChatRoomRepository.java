package com.likelion.oegaein.domain.chat.repository;

import com.likelion.oegaein.domain.chat.entity.ChatRoom;
import com.likelion.oegaein.domain.matching.entity.MatchingPost;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from ChatRoom c where c.id = :id")
    Optional<ChatRoom> findByIdWithPessimisticLock(@Param("id") Long id);
    Optional<ChatRoom> findByMatchingPost(MatchingPost matchingPost);
}
