package com.fastcampus05.zillinks.core.util.dto.excel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class ExcelOutDTO {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class ContactUsOutDTO {
        private Long index;
        private String email;
        private String name;
        private String content;
        private String type;
        private LocalDateTime date;
    }
}
