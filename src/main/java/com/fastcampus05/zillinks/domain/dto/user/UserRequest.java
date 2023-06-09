package com.fastcampus05.zillinks.domain.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRequest {
    @Setter
    @Getter
    public static class LoginInDTO {

        @Pattern(regexp = "^[a-zA-Z0-9]{4,14}$", message = "영문/숫자 4~14자 이내로 작성해주세요")
        @NotEmpty
        @JsonProperty("login_id")
        private String loginId;

        @NotEmpty
        @Size(min = 4, max = 20)
        private String password;
    }

    @Getter
    @Setter
    public static class BizNumInDTO{
        @Pattern(regexp = "^[0-9]{10}$", message = "숫자 10자리로 입력해 주세요.")
        private String bizNum;
    }

//    @Setter
//    @Getter
//    public static class JoinInDTO {
//        @Pattern(regexp = "^[a-zA-Z0-9]{2,20}$", message = "영문/숫자 2~20자 이내로 작성해주세요")
//        @NotEmpty
//        private String username;
//
//        @NotEmpty
//        @Size(min = 4, max = 20)
//        private String password;
//
//        @NotEmpty
//        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요")
//        private String email;
//
//        @NotEmpty
//        @Pattern(regexp = "^[a-zA-Z가-힣]{1,20}$", message = "한글/영문 1~20자 이내로 작성해주세요")
//        private String fullName;
//
//        public User toEntity() {
//            return User.builder()
//                    .password(password)
//                    .email(email)
//                    .role("USER")
//                    .build();
//        }
//    }
}
