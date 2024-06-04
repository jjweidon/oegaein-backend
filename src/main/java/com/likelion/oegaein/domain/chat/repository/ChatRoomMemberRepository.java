package com.likelion.oegaein.domain.chat.repository;

import com.likelion.oegaein.domain.chat.entity.ChatRoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {
}
