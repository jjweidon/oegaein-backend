package com.likelion.oegaein.domain.member.repository;

import com.likelion.oegaein.domain.member.entity.profile.Profile;
import com.likelion.oegaein.domain.member.entity.profile.SleepingHabit;
import com.likelion.oegaein.domain.member.entity.profile.SleepingHabitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SleepingHabitRepository extends JpaRepository<SleepingHabitEntity, Long> {
    List<SleepingHabitEntity> findAllByProfile(Profile profile);
}
