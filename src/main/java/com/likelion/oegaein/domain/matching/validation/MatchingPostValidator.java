package com.likelion.oegaein.domain.matching.validation;

import com.likelion.oegaein.domain.matching.entity.RoomSizeType;
import com.likelion.oegaein.domain.matching.exception.MatchingPostException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MatchingPostValidator {
    // constants
    private final String ROOM_SIZE_TWO_ENUM_VALUE = "2인실";
    private final String ROOM_SIZE_FOUR_ENUM_VALUE = "4인실";
    private final String ROOM_SIZE_AND_TARGET_NUM_OF_PEOPLE_ERR_MSG = "올바르지 않은 모집 인원 입니다.";

    public void validateRoomSizeAndTargetNumOfPeople(RoomSizeType roomSizeType, int targetNumOfPeople){
        List<Integer> availableNumOfPeopleForTwoRoom = List.of(1);
        List<Integer> availableNumOfPeopleForFourRoom = List.of(1,2,3);
        if(roomSizeType.getValueName().equals(ROOM_SIZE_TWO_ENUM_VALUE)){
            if(!availableNumOfPeopleForTwoRoom.contains(targetNumOfPeople)){
                throw new MatchingPostException(ROOM_SIZE_AND_TARGET_NUM_OF_PEOPLE_ERR_MSG);
            }
        }else if(roomSizeType.getValueName().equals(ROOM_SIZE_FOUR_ENUM_VALUE)){
            if(!availableNumOfPeopleForFourRoom.contains(targetNumOfPeople)){
                throw new MatchingPostException(ROOM_SIZE_AND_TARGET_NUM_OF_PEOPLE_ERR_MSG);
            }
        }
    }
}
