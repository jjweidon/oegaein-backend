package com.likelion.oegaein.domain.alarm.repository;

import com.likelion.oegaein.domain.alarm.entity.RoommateAlarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoommateAlarmRepository extends JpaRepository<RoommateAlarm, Long> {
}