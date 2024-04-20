package com.likelion.oegaein.domain.member.entity;

import com.likelion.oegaein.domain.member.entity.profile.Profile;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    private String googleName;
    @Setter
    private String photoUrl;
    private String refreshToken;
    @CreationTimestamp
    private LocalDateTime createdAt = LocalDateTime.now();
    @UpdateTimestamp
    private LocalDateTime updatedAt = LocalDateTime.now();
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "member")
    private Profile profile;
    private Boolean profileSetUpStatus;

    public void renewRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }
}
