package com.likelion.oegaein.domain.alarm.repository;

import com.likelion.oegaein.domain.alarm.entity.DeliveryAlarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryAlarmRepository extends JpaRepository<DeliveryAlarm, Long> {
}
