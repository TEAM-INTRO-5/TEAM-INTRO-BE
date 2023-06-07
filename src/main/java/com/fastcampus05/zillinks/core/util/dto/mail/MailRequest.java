package com.fastcampus05.zillinks.core.util.dto.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class MailRequest {

    @Getter
    @AllArgsConstructor
    public static class MailInDTO {
        private String email;
        private String type;
    }

    @Getter
    public static class MailCheckInDTO {
        private String code;
    }
}
