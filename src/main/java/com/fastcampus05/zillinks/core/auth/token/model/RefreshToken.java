package com.fastcampus05.zillinks.core.auth.token.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@RedisHash(value = "refreshToken", timeToLive = 60 * 60 * 24 * 20)
@AllArgsConstructor
@Getter
public class RefreshToken {

    @Id
    private String refreshToken;
    private Long userId;
    private List<String> validList;

}
