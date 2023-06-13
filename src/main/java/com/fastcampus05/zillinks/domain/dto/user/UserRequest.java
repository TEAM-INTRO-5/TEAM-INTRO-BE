package com.fastcampus05.zillinks.domain.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class UserRequest {
    @Getter
    public static class LoginInDTO {

        @Schema(description = "로그인 아이디", example = "taeheoki")
        @Pattern(regexp = "^[a-zA-Z0-9]{4,14}$", message = "영문/숫자 4~14자 이내로 작성해주세요")
        @NotBlank(message = "login_id는 비어있을 수 없습니다.")
        @JsonProperty("login_id")
        private String loginId;

        @Schema(description = "패스워드", example = "1234")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=(.*\\d)|(.*\\W)).{10,}$", message = "10자 이상 입력, 영문/숫자/특수문자(공백 제외) 2개 이상 조합해야합니다.")
        private String password;

        @Schema(description = "자동로그인 유무", example = "false")
        @NotBlank(message = "true 나 false 값이 할당 되어야합니다.")
        @JsonProperty("remember_me")
        private Boolean rememberMe;
    }

    @Getter
    public static class JoinInDTO {
        @Schema(description = "로그인 아이디", example = "taeheoki11")
        @Pattern(regexp = "^[a-zA-Z0-9]{4,14}$", message = "영문/숫자 4~14자 이내로 작성해주세요")
        @NotBlank(message = "login_id는 비어있을 수 없습니다.")
        @JsonProperty("login_id")
        private String loginId;

        @Schema(description = "이메일", example = "taeheoki11@naver.com")
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요")
        @NotBlank(message = "이메일을 입력해 주세요.")
        private String email;

        @Schema(description = "패스워드", example = "@123456789")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=(.*\\d)|(.*\\W)).{10,}$", message = "10자 이상 입력, 영문/숫자/특수문자(공백 제외) 2개 이상 조합해야합니다.")
        private String password;

        @Schema(description = "사업자 등록 번호", example = "1234567890")
        @Pattern(regexp = "^[0-9]{10}$", message = "숫자 10자리로 입력해 주세요.")
        @NotBlank(message = "사업자 등록 번호를 입력해 주세요.")
        @JsonProperty("biz_num")
        private String bizNum;
    }

    @Getter
    public static class OauthJoinInDTO {
        @Schema(description = "소셜 아이디", example = "google_12341234")
        @JsonProperty("o_auth_id")
        private String oAuthId;
        
        @Schema(description = "소셜 이메일", example = "taeheoki11@gmail.com")
        @JsonProperty("o_auth_email")
        private String oAuthEmail;
        
        @Schema(description = "소셜 이름", example = "taeheoki")
        private String name;

        @Schema(description = "사업자 등록 번호", example = "1234567890")
        @Pattern(regexp = "^[0-9]{10}$", message = "숫자 10자리로 입력해 주세요.")
        @NotBlank(message = "사업자 등록 번호를 입력해 주세요.")
        @JsonProperty("biz_num")
        private String bizNum;
    }

    @Getter
    public static class BizNumInDTO{
        @Schema(description = "사업자 등록 번호", example = "2258701327")
        @Pattern(regexp = "^[0-9]{10}$", message = "숫자 10자리로 입력해 주세요.")
        @NotBlank(message = "사업자 등록 번호를 입력해 주세요.")
        @JsonProperty("biz_num")
        private String bizNum;
    }

    @Getter
    public static class FindPasswordInDTO {
        @Schema(description = "로그인 아이디", example = "taeheoki")
        @JsonProperty("login_id")
        @Pattern(regexp = "^[a-zA-Z0-9]{4,14}$", message = "영문/숫자 4~14자 이내로 작성해주세요")
        @NotEmpty
        private String loginId;

        @Schema(description = "이메일", example = "taeheoki@naver.com")
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요")
        @NotEmpty
        private String email;
    }

    @Getter
    public static class UpdatePasswordInDTO {
        @Schema(description = "기존 비밀번호", example = "0ss123erljow!")
        private String password;

        @Schema(description = "새 비밀번호", example = "0ss123erljow!")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=(.*\\d)|(.*\\W)).{10,}$", message = "10자 이상 입력, 영문/숫자/특수문자(공백 제외) 2개 이상 조합해야합니다.")
        @JsonProperty("new_password")
        private String newPassword;
    }

    @Getter
    public static class CheckLoginIdInDTO {
        @Schema(description = "로그인 아이디", example = "taeheoki")
        @Pattern(regexp = "^[a-zA-Z0-9]{4,14}$", message = "영문/숫자 4~14자 이내로 작성해주세요")
        @NotBlank(message = "login_id는 비어있을 수 없습니다.")
        @JsonProperty("login_id")
        private String loginId;
    }

    @Getter
    public static class FindIdByEmailInDTO {
        @Schema(description = "이메일", example = "taeheoki@naver.com")
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요")
        @NotEmpty
        private String email;
    }

    @Getter
    public static class FindIdByBizNumInDTO {
        @Schema(description = "사업자 등록 번호", example = "2258701327")
        @Pattern(regexp = "^[0-9]{10}$", message = "숫자 10자리로 입력해 주세요.")
        @NotEmpty
        private String bizNum;
    }
}
