package com.fastcampus05.zillinks.domain.model.user;

import com.fastcampus05.zillinks.core.util.TimeBaseEntity;
import com.fastcampus05.zillinks.domain.model.intropage.IntroPage;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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

    @OneToOne
    @JoinColumn(name = "google_id")
    private GoogleAccount googleAccount; // 구글 연동 유무 체크

    // /{intropageId}/작업
    // -> Intropage -> user_id /JWT - id => 접근권한있다.

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    List<Marketing> marketings = new ArrayList<>();

    //== 연관관계 메서드==//
//    public void addMargeting(Marketing marketing) {
//        marketings.add(marketing);
//        marketing.setUser(this);
//    }
}