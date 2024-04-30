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
public class UpdateMatchingPostRequest {
    @NotEmpty
    private String title;

    private String content;

    @EnumFormat(enumClass = DongType.class)
    private DongType dongType;

    @EnumFormat(enumClass = RoomSizeType.class)
    private RoomSizeType roomSizeType;

    @NotNull
    @Positive
    private int targetNumberOfPeople;

    @NotNull
    @FutureOrPresent
    private LocalDate deadline;
}
