package com.likelion.oegaein.domain.member.dto.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateProfileRequest extends ProfileData{
    @JsonProperty("photo_url")
    private String photoUrl;
}