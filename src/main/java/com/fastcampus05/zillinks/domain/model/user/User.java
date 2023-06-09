package com.fastcampus05.zillinks.domain.model.user;

import com.fastcampus05.zillinks.core.util.TimeBaseEntity;
import com.fastcampus05.zillinks.domain.model.intropage.IntroPage;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "user_tb")
@Entity
public class User extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    @NotEmpty
    private String loginId;

    @Column(unique = true)
    @NotEmpty
    private String email;

    @NotEmpty
    @Column(length = 60)
    private String password;

    @Column(unique = true) // checkpoint
    private String bizNum;

    private String role; // USER|ADMIN

    // 비밀번호 찾기, 재설정 과정
    public void updatePassword(String password) {
        this.password = password;
    }
}