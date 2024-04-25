package com.likelion.oegaein.domain.alarm.controller;

import com.likelion.oegaein.domain.alarm.dto.delivery.FindDeliveryAlarmsResponse;
import com.likelion.oegaein.domain.alarm.service.DeliveryAlarmService;
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
public class DeliveryAlarmApiController {
    private final DeliveryAlarmService deliveryAlarmService;

    @GetMapping("/api/v1/delivery-alarms")
    public ResponseEntity<ResponseDto> getDeliveryAlarm(Authentication authentication){
        log.info("Request to get delivery-alarms");
        FindDeliveryAlarmsResponse response = deliveryAlarmService.findDeliveryAlarms(authentication);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/delivery-alarms")
    public ResponseEntity<ResponseDto> deleteDeliveryAlarms(Authentication authentication){
        log.info("Request to delete delivery-alarms");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/api/v1/delivery-alarms/{deliveryalarmid}")
    public ResponseEntity<ResponseDto> deleteDeliveryAlarm(@PathVariable("deliveryalarmid") Long deliveryAlarmId, Authentication authentication){
        log.info("Request to delete delivery-alarms {}", deliveryAlarmId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
