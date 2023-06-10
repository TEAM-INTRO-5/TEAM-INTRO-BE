package com.fastcampus05.zillinks.core.util.dto.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotNull;

public class AuthRequest {

    @Getter
    public static class GenerateAccessTokenInDTO {
        @JsonProperty("remember_me")
        @NotNull(message = "true 나 false 값이 할당 되어야합니다.")
        private Boolean rememberMe;
    }
}
