package com.fastcampus05.zillinks.core.util.dto.mail;

import lombok.Getter;

public class MailRequest {

    @Getter
    public static class MailInDTO {
        private String email;
        private String type;
    }

    @Getter
    public static class MailCheckInDTO {
        private String code;
    }
}
