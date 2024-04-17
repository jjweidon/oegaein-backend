package com.likelion.oegaein.domain.member.dto.oauth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GoogleOauthUserInfo {
    private String name;
    private String picture;
    private String email;
}