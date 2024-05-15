package com.likelion.oegaein.domain.member.entity.member;

import com.likelion.oegaein.domain.member.entity.profile.Profile;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
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
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "member")
    private Profile profile;
    private Boolean profileSetUpStatus;

    public void renewRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }
}
