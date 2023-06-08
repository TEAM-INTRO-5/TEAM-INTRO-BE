package com.fastcampus05.zillinks.domain.model.user;

import com.fastcampus05.zillinks.core.util.TimeBaseEntity;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "google_account_id")
@Getter
public class GoogleAccount extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "google_account_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "o_auth_id", unique = true)
    private String oAuthId;

    @Column(name = "o_auth_email", unique = true)
    private String oAuthEmail;
    private String name;

    @Column(name = "logined_at")
    private LocalDateTime loginedAt;
}
