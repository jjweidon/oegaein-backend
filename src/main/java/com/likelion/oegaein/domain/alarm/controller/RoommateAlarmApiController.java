package com.likelion.oegaein.domain.alarm.controller;

import com.likelion.oegaein.domain.alarm.dto.roommate.DeleteRoommateAlarmResponse;
import com.likelion.oegaein.domain.alarm.dto.roommate.DeleteRoommateAlarmsResponse;
import com.likelion.oegaein.domain.alarm.dto.roommate.FindRoommateAlarmsResponse;
import com.likelion.oegaein.domain.alarm.service.RoommateAlarmService;
import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class RoommateAlarmApiController {
    private final RoommateAlarmService roommateAlarmService;

    @GetMapping("/api/v1/roommate-alarms")
    public ResponseEntity<ResponseDto> getRoommateAlarm(Authentication authentication){
        log.info("Request to get roommate-alarms");
        FindRoommateAlarmsResponse response = roommateAlarmService.findRoommateAlarms(authentication);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/roommate-alarms")
    public ResponseEntity<ResponseDto> deleteRoommateAlarms(Authentication authentication){
        log.info("Request to delete roommate-alarms");
        DeleteRoommateAlarmsResponse response = roommateAlarmService.removeRoommateAlarms(authentication);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/api/v1/roommate-alarms/{roommatealarmid}")
    public ResponseEntity<ResponseDto> deleteRoommateAlarm(@PathVariable("roommatealarmid") Long roommateAlarmId, Authentication authentication){
        log.info("Request to delete roommate-alarms {}", roommateAlarmId);
        DeleteRoommateAlarmResponse response = roommateAlarmService.removeRoommateAlarm(roommateAlarmId, authentication);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}