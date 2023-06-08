package com.fastcampus05.zillinks.core.util.dto.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class OAuthResponse {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class LoginOutDTO {
        @JsonProperty("access_token")
        private String accessToken;
        @JsonProperty("refresh_token")
        private String refreshToken;
        @JsonProperty("google_profile")
        private GoogleProfile googleProfile;

        public static class GoogleProfile {
            @JsonProperty("login_id")
            private String loginId;
            private String email;
        }
    }
}
