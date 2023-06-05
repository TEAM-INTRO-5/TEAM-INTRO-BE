package com.fastcampus05.zillinks.core.util.dto.token;

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
