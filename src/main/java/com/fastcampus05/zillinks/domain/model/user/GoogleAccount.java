package com.fastcampus05.zillinks.domain.model.user;

import com.fastcampus05.zillinks.core.util.TimeBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "google_account_id")
@Entity
public class GoogleAccount extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "google_account_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    @NotNull
    private User user;

    @Column(name = "o_auth_id", unique = true)
    @NotNull
    private String oAuthId;

    @Column(name = "o_auth_email", unique = true)
    @NotNull
    private String oAuthEmail;
    @NotNull
    private String name;

    @Column(name = "logined_at")
    private LocalDateTime loginedAt;

    public void updateLoginedAt(LocalDateTime loginedAt) {
        this.loginedAt = loginedAt;
    }
}
