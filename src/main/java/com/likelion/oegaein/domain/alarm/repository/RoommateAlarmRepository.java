package com.likelion.oegaein.domain.alarm.repository;

import com.likelion.oegaein.domain.alarm.entity.RoommateAlarm;
import com.likelion.oegaein.domain.member.entity.profile.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoommateAlarmRepository extends JpaRepository<RoommateAlarm, Long> {
}