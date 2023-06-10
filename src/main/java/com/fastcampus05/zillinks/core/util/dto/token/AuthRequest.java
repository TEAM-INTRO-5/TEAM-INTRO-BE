package com.fastcampus05.zillinks.core.util.dto.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;

public class AuthRequest {

    @Getter
    public static class GenerateAccessTokenInDTO {
        @Schema(description = "자동 로그인 유무", example = "false")
        @JsonProperty("remember_me")
        @NotNull(message = "true 나 false 값이 할당 되어야합니다.")
        private Boolean rememberMe;
    }
}
