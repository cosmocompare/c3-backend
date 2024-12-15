package c3.login.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Id 자동 생성
    private Long id;

    @Column(length = 500, nullable = false)
    private Long socialId;

    @Column(length = 500, nullable = false)
    private String profile_nickname;

    @Column(length = 500, nullable = false)
    private String profile_img;

    @Builder
    public User(Long socialId, String profile_nickname, String profile_img) {
        this.socialId = socialId;
        this.profile_nickname = profile_nickname;
        this.profile_img = profile_img;
    }
}
