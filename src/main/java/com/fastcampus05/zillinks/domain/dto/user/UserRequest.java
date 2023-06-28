package com.fastcampus05.zillinks.domain.dto.user;

import com.fastcampus05.zillinks.domain.model.user.GoogleAccount;
import com.fastcampus05.zillinks.domain.model.user.Marketing;
import com.fastcampus05.zillinks.domain.model.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserRequest {
    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class LoginInDTO {

        @Schema(description = "로그인 아이디", example = "taeheoki")
        @Pattern(regexp = "^[a-zA-Z0-9]{4,14}$", message = "영문/숫자 4~14자 이내로 작성해주세요")
        private String loginId;

        @Schema(description = "패스워드", example = "1234")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=(.*\\d)|(.*\\W)).{10,}$", message = "10자 이상 입력, 영문/숫자/특수문자(공백 제외) 2개 이상 조합해야합니다.")
        private String password;

        @Schema(description = "자동로그인 유무", example = "false")
        @NotBlank(message = "true 나 false 값이 할당 되어야합니다.")
        private Boolean rememberMe;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class JoinInDTO {
        @Schema(description = "로그인 아이디", example = "taeheoki11")
        @Pattern(regexp = "^[a-zA-Z0-9]{4,14}$", message = "영문/숫자 4~14자 이내로 작성해주세요")
        private String loginId;

        @Schema(description = "이메일", example = "taeheoki11@naver.com")
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요")
        private String email;

        @Schema(description = "패스워드", example = "@123456789")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=(.*\\d)|(.*\\W)).{10,}$", message = "10자 이상 입력, 영문/숫자/특수문자(공백 제외) 2개 이상 조합해야합니다.")
        private String password;

        @Schema(description = "사업자 등록 번호", example = "1234567890")
        @Pattern(regexp = "^[0-9]{10}$", message = "숫자 10자리로 입력해 주세요.")
        private String bizNum;

        public User toEntity(String password) {
            return User.builder()
                    .loginId(this.getLoginId())
                    .email(this.getEmail())
                    .password(password)
                    .bizNum(this.getBizNum())
                    .role("USER")
                    .marketing(Marketing.builder()
                            .marketingEmail(false)
                            .build())
                    .build();
        }

    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class OauthJoinInDTO {
        @Schema(description = "소셜 아이디", example = "google_12341234123")
        @NotEmpty
        private String loginId;

        @Schema(description = "소셜 이메일", example = "taeheoki11@gmail.com")
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요")
        private String email;

        @Schema(description = "소셜 이름", example = "taeheoki")
        @NotEmpty
        private String name;

        @Schema(description = "사업자 등록 번호", example = "1234567890")
        @Pattern(regexp = "^[0-9]{10}$", message = "숫자 10자리로 입력해 주세요.")
        private String bizNum;

        public User toEntity(String password) {
            return User.builder()
                    .loginId(this.getLoginId())
                    .email(this.getEmail())
                    .password(password)
                    .bizNum(this.getBizNum())
                    .role("USER")
                    .marketing(Marketing.builder()
                            .marketingEmail(false)
                            .build())
                    .build();
        }

        public GoogleAccount toEntity(User user) {
            return GoogleAccount.builder()
                    .user(user)
                    .oAuthId(this.getLoginId())
                    .oAuthEmail(this.getEmail())
                    .name(this.getName())
                    .build();
        }
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class BizNumInDTO{
        @Schema(description = "사업자 등록 번호", example = "2258701327")
        @Pattern(regexp = "^[0-9]{10}$", message = "숫자 10자리로 입력해 주세요.")
        private String bizNum;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class FindPasswordInDTO {
        @Schema(description = "로그인 아이디", example = "taeheoki")
        @Pattern(regexp = "^[a-zA-Z0-9]{4,14}$", message = "영문/숫자 4~14자 이내로 작성해주세요")
        private String loginId;

        @Schema(description = "이메일", example = "taeheoki@naver.com")
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요")
        private String email;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class UpdatePasswordInDTO {
        @Schema(description = "기존 비밀번호", example = "0ss123erljow!")
        private String password;

        @Schema(description = "새 비밀번호", example = "0ss123erljow!")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=(.*\\d)|(.*\\W)).{10,}$", message = "10자 이상 입력, 영문/숫자/특수문자(공백 제외) 2개 이상 조합해야합니다.")
        private String newPassword;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class CheckLoginIdInDTO {
        @Schema(description = "로그인 아이디", example = "taeheoki")
        @Pattern(regexp = "^[a-zA-Z0-9]{4,14}$", message = "영문/숫자 4~14자 이내로 작성해주세요")
        private String loginId;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class FindIdByEmailInDTO {
        @Schema(description = "이메일", example = "taeheoki@naver.com")
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요")
        private String email;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)

    public static class FindIdByBizNumInDTO {
        @Schema(description = "사업자 등록 번호", example = "2258701327")
        @Pattern(regexp = "^[0-9]{10}$", message = "숫자 10자리로 입력해 주세요.")
        private String bizNum;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class UserInfoUpdateInDTO {
        @Schema(description = "이메일", example = "taeheoki11@gmail.com")
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요")
        private String email;
        @Schema(description = "유저 프로필", example = "https://taeheoki-bucket.s3.ap-northeast-2.amazonaws.com/upload/506b4c3a-53de-4cee-b571-ffa074f73ea9.jpg")
        private String profile;
        @NotNull(message = "marketing 필드는 반드시 true 또는 false 값을 가져야 합니다.")
        private Boolean marketing;
    }
}
