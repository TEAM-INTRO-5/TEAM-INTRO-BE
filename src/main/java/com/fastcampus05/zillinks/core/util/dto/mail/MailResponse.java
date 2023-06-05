package com.fastcampus05.zillinks.core.util.dto.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class MailResponse {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class MailOutDTO {
        private String code;
        private String type;
    }
}
