package com.fastcampus05.zillinks.core.auth.token.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TokenResponse {
    private String refreshToken;
    private String accessToken;
}
