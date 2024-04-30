package com.likelion.oegaein.domain.matching.dto.matchingpost;

import com.likelion.oegaein.domain.matching.entity.DongType;
import com.likelion.oegaein.domain.matching.entity.RoomSizeType;
import com.likelion.oegaein.global.validation.EnumFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CreateMatchingPostRequest {
    @NotEmpty
    private String title; // 제목

    private String content; // 상세 설명

    @EnumFormat(enumClass = DongType.class)
    private DongType dongType; // 기숙사 동

    @EnumFormat(enumClass = RoomSizeType.class)
    private RoomSizeType roomSizeType; // 기숙사 호실

    @NotNull
    @Positive
    private int targetNumberOfPeople; // 목표 인원 수

    @NotNull
    @FutureOrPresent
    private LocalDate deadline; // 마감 기한
}