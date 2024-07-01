package com.likelion.oegaein.domain.member.dto.member;


import com.likelion.oegaein.global.dto.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LogoutResponse implements ResponseDto {
    Boolean message;
}
