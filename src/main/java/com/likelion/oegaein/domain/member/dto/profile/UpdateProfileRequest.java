package com.likelion.oegaein.domain.member.dto.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.likelion.oegaein.domain.member.entity.profile.*;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateProfileRequest extends ProfileData{
    @JsonProperty("photo_url")
    private String photoUrl;
}