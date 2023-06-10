package com.fastcampus05.zillinks.core.util.dto.mail;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class MailRequest {

    @Getter
    @AllArgsConstructor
    public static class MailInDTO {
        @Schema(description = "이메일", example = "taeheoki@naver.com")
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요")
        @NotEmpty
        private String email;
        @Schema(description = "중복 체크", example = "false")
        @NotEmpty
        @JsonProperty("dup_check")
        private Boolean dupCheck;
    }

    @Getter
    public static class MailCheckInDTO {
        @Schema(description = "인증 코드")
        private String code;
    }
}
