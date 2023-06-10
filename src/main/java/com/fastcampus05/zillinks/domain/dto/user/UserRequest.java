package com.fastcampus05.zillinks.domain.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

public class UserRequest {
    @Getter
    public static class LoginInDTO {

        @Pattern(regexp = "^[a-zA-Z0-9]{4,14}$", message = "영문/숫자 4~14자 이내로 작성해주세요")
        @NotBlank(message = "login_id는 비어있을 수 없습니다.")
        @JsonProperty("login_id")
        private String loginId;

        @NotBlank(message = "password는 비어있을 수 없습니다.")
        @Size(min = 4, max = 20)
        private String password;

        @NotBlank(message = "true 나 false 값이 할당 되어야합니다.")
        @JsonProperty("remember_me")
        private Boolean rememberMe;
    }

    @Getter
    public static class BizNumInDTO{
        @Pattern(regexp = "^[0-9]{10}$", message = "숫자 10자리로 입력해 주세요.")
        @NotEmpty
        private String bizNum;
    }

    @Getter
    public static class FindPasswordInDTO {
        @JsonProperty("login_id")
        @Pattern(regexp = "^[a-zA-Z0-9]{4,14}$", message = "영문/숫자 4~14자 이내로 작성해주세요")
        @NotEmpty
        private String loginId;

        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요")
        @NotEmpty
        private String email;
    }

    @Getter
    public static class UpdatePasswordInDTO {
        @Size(min = 4, max = 20)
        @NotBlank(message = "password는 비어있을 수 없습니다.")
        private String password;
    }

    @Getter
    public static class CheckLoginIdInDTO {
        @Pattern(regexp = "^[a-zA-Z0-9]{4,14}$", message = "영문/숫자 4~14자 이내로 작성해주세요")
        @NotBlank(message = "login_id는 비어있을 수 없습니다.")
        @JsonProperty("login_id")
        private String loginId;
    }

    @Getter
    public static class FindIdByEmailInDTO {
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요")
        @NotEmpty
        private String email;
    }

    @Getter
    public static class FindIdByBizNumInDTO {
        @Pattern(regexp = "^[0-9]{10}$", message = "숫자 10자리로 입력해 주세요.")
        @NotEmpty
        private String bizNum;
    }
}
