package com.likelion.oegaein.domain.member.entity;

import com.likelion.oegaein.domain.member.entity.profile.Profile;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    @Column(unique = true)
    private String email;
    @Column(name = "google_name")
    private String googleName;
    @Column(name = "refresh_token")
    private String refreshToken;
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;
    private Boolean profileSetUpStatus;

    public void renewRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
