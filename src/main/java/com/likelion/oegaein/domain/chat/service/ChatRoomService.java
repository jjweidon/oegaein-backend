package com.likelion.oegaein.domain.chat.service;

import com.likelion.oegaein.domain.chat.entity.ChatRoom;
import com.likelion.oegaein.domain.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public void increaseMemberCount(Long id){
        ChatRoom chatRoom = chatRoomRepository.findById(id).orElseThrow();
        chatRoom.upMemberCount();
    }

    @Transactional
    public void increaseMemberCountPessimisticLock(Long id){
        ChatRoom chatRoom = chatRoomRepository.findByIdWithPessimisticLock(id).orElseThrow();
        chatRoom.upMemberCount();
    }
}
