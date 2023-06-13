package com.fastcampus05.zillinks.domain.model.user;

import com.fastcampus05.zillinks.core.util.TimeBaseEntity;
import com.fastcampus05.zillinks.domain.model.intropage.CompanyInfo;
import com.fastcampus05.zillinks.domain.model.intropage.IntroPage;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

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

    @Embedded
    private Marketing marketing;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private IntroPage introPage;

    public void setMarketing(Marketing marketing) {
        this.marketing = marketing;
    }

    public void setIntroPage(IntroPage introPage) {
        this.introPage = introPage;
    }

    // 비밀번호 찾기, 재설정 과정
    public void updatePassword(String password) {
        this.password = password;
    }
}