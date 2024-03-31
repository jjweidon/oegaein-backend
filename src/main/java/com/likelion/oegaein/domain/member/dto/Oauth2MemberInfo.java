package com.likelion.oegaein.domain.member.dto;

import com.likelion.oegaein.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Builder
public class Oauth2MemberInfo {
    private String name;
    private String email;
    private String profile;

    public static Oauth2MemberInfo toOauth2MemberInfo(Map<String, Object> attributes){
        return Oauth2MemberInfo.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .profile((String) attributes.get("profile"))
                .build();
    }

    public Map<String, Object> convertToMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("email", email);
        map.put("profile", profile);
        return map;
    }
}
