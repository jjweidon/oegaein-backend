package com.likelion.oegaein.domain.chat.repository;

import com.likelion.oegaein.domain.chat.entity.ChatRoom;
import com.likelion.oegaein.domain.matching.entity.MatchingPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByMatchingPost(MatchingPost matchingPost);
}
