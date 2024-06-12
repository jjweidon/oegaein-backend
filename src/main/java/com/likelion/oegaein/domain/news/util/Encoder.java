package com.likelion.oegaein.domain.news.util;

import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class Encoder {
    public String encode(String input){
        return Base64.getEncoder().encodeToString(input.getBytes());
    }
}
