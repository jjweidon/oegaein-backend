package com.likelion.oegaein.domain.member.repository;

import com.likelion.oegaein.domain.member.entity.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<Block, Long> {
}