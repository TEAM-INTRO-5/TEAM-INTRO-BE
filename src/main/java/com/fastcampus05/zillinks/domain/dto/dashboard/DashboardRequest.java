package com.fastcampus05.zillinks.domain.dto.dashboard;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class DashboardRequest {
    @Getter
    @Builder
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class FindContactUsOutDTO {
        private Long introPageId;
        private List<ContactUsOutDTO> content;
        private Long totalElements;
        private Integer totalPage;
        private Integer size;
        private Integer number;
        private Integer numberOfElements;
        private Boolean hasPrevious;
        private Boolean hasNext;
        private Boolean isFirst;
        private Boolean isLast;

        @Getter
        @Builder
        @AllArgsConstructor
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        public static class ContactUsOutDTO {
            private Long contactUsLogId;
            private String email;
            private String name;
            private String content;
            private String type;
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy.MM.dd", timezone = "UTC")
            private LocalDateTime date;
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class FindContactUsDetailOutDTO {
        private Long contactUsLogId;
        private String email;
        private String name;
        private String type;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy.MM.dd", timezone = "UTC")
        private LocalDateTime date;
        private String status;
        private String content;
    }
}
