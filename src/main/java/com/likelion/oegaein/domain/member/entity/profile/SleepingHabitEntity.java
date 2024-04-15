package com.likelion.oegaein.domain.member.entity.profile;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SleepingHabitEntity {
    @Id
    @GeneratedValue
    @Column(name = "sleeping_habit_id")
    private Long id;
    private SleepingHabit sleepingHabit;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;
}
