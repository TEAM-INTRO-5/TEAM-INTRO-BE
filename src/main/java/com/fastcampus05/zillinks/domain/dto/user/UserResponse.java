package com.fastcampus05.zillinks.domain.dto.user;

import com.fastcampus05.zillinks.domain.model.user.User;
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
    public static class LoginOutDTO {
        private String refreshToken;
        private String accessToken;

        public LoginOutDTO(String refreshToken, String accessToken) {
            this.refreshToken = refreshToken;
            this.accessToken = accessToken;
        }
    }
}
