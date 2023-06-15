package com.fastcampus05.zillinks.domain.dto.user;

import com.fastcampus05.zillinks.domain.model.user.Marketing;
import com.fastcampus05.zillinks.domain.model.user.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class UserResponse {
    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class LoginOutDTO {
        private String refreshToken;
        private String accessToken;

        public LoginOutDTO(String refreshToken, String accessToken) {
            this.refreshToken = refreshToken;
            this.accessToken = accessToken;
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class OAuthLoginOutDTO {
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String accessToken;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String refreshToken;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private GoogleProfile googleProfile;

        @Getter
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        public static class GoogleProfile {
            private String oAuthId;
            private String email;
            private String name;

            public GoogleProfile(String oAuthId, String email, String name) {
                this.oAuthId = oAuthId;
                this.email = email;
                this.name = name;
            }
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class FindIdByEmailOutDTO {
        private String loginId;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class FindIdByBizNumOutDTO {
        private String loginId;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class UserInfoOutDTO{
        private String loginId;
        private String email;
        private String bizNum;
        private String profile;
        private Marketing marketing;

        public UserInfoOutDTO(User user) {
            this.loginId = user.getLoginId();
            this.email = user.getEmail();
            this.bizNum = user.getBizNum();
            this.profile = user.getProfile();
            this.marketing = user.getMarketing();
        }
    }
}
