package com.likelion.oegaein.domain.email.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailMessage {
    private String to; // 수신자
    private String subject; // 제목
    private String message; // 내용
}