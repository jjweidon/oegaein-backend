package com.likelion.oegaein.domain.matching.dto.matchingpost;

import com.likelion.oegaein.domain.matching.entity.DongType;
import com.likelion.oegaein.domain.matching.entity.RoomSizeType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
public class UpdateMatchingPostData {
    private String title;
    private String content;
    private LocalDateTime deadline;
    private DongType dongType;
    private RoomSizeType roomSizeType;
    private int targetNumberOfPeople;

    public static UpdateMatchingPostData toUpdateMatchingPostData(UpdateMatchingPostRequest dto){
        return UpdateMatchingPostData.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .deadline(dto.getDeadline())
                .dongType(dto.getDongType())
                .roomSizeType(dto.getRoomSizeType())
                .targetNumberOfPeople(dto.getTargetNumberOfPeople())
                .build();
    }
}
