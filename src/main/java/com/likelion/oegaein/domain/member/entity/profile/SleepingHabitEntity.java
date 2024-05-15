package com.likelion.oegaein.domain.member.entity.profile;

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
public class SleepingHabitEntity {
    @Id
    @GeneratedValue
    @Column(name = "sleeping_habit_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    private SleepingHabit sleepingHabit;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;
}
