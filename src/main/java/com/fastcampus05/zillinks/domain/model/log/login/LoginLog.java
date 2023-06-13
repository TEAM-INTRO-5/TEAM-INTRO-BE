package com.fastcampus05.zillinks.domain.model.log.login;

import com.fastcampus05.zillinks.core.util.TimeBaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Setter // DTO 만들면 삭제해야됨
@Getter
@Table(name = "login_log_tb")
@Entity
public class LoginLog extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "login_log_id")
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "user_agent")
    private String userAgent;
    @Column(name = "client_ip")
    private String clientIp;
    @Column(name = "o_auth_user")
    private LoginPath loginPath;

    @Builder
    public LoginLog(Long id, Long userId, String userAgent, String clientIp, LoginPath loginPath) {
        this.id = id;
        this.userId = userId;
        this.userAgent = userAgent;
        this.clientIp = clientIp;
        this.loginPath = loginPath;
    }

    public static LoginLog toEntity(Long userId, List<String> validList, LoginPath loginPath) {
        return LoginLog.builder()
                .userId(userId)
                .clientIp(validList.get(0))
                .userAgent(validList.get(1))
                .loginPath(loginPath)
                .build();
    }
}
