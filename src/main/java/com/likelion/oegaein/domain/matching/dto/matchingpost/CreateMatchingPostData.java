package com.likelion.oegaein.domain.matching.dto.matchingpost;

import com.likelion.oegaein.domain.matching.entity.DongType;
import com.likelion.oegaein.domain.matching.entity.RoomSizeType;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CreateMatchingPostData {
    private String title;
    private String content;
    private LocalDateTime deadline;
    private DongType dongType;
    private RoomSizeType roomSizeType;
    private int targetNumberOfPeople;

    public static CreateMatchingPostData toCreateMatchingPostData(CreateMatchingPostRequest dto){
        CreateMatchingPostData createMatchingPostData = new CreateMatchingPostData();
        createMatchingPostData.setTitle(dto.getTitle());
        createMatchingPostData.setContent(dto.getContent());
        createMatchingPostData.setDeadline(dto.getDeadline());
        createMatchingPostData.setDongType(dto.getDongType());
        createMatchingPostData.setRoomSizeType(dto.getRoomSizeType());
        createMatchingPostData.setTargetNumberOfPeople(dto.getTargetNumberOfPeople());
        return createMatchingPostData;
    }
}
