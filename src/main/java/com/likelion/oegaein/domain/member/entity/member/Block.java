package com.likelion.oegaein.domain.member.entity.member;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Block {
    @Id @GeneratedValue
    @Column(name = "block_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member blocking;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member blocked;
}
