package com.fastcampus05.zillinks.domain.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class UserResponse {
//    @Getter @Setter
//    public static class DetailOutDTO{
//        private Long id;
//        private String username;
//        private String email;
//        private String fullName;
//        private String role;
//
//        public DetailOutDTO(User user) {
//            this.id = user.getId();
//            this.username = user.getUsername();
//            this.email = user.getEmail();
//            this.fullName = user.getFullName();
//            this.role = user.getRole();
//        }
//    }
//
//    @Setter
//    @Getter
//    public static class JoinOutDTO {
//        private Long id;
//        private String username;
//        private String fullName;
//
//        public JoinOutDTO(User user) {
//            this.id = user.getId();
//            this.username = user.getUsername();
//            this.fullName = user.getFullName();
//        }
//    }

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
        private String accessToken;
        private String refreshToken;
        private GoogleProfile googleProfile;

        @Getter
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
}
