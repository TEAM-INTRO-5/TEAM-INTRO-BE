package com.fastcampus05.zillinks.domain.dto.dashboard;

import com.fastcampus05.zillinks.domain.model.dashboard.*;
import com.fastcampus05.zillinks.domain.model.intropage.IntroPage;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DashboardResponse {
    @Getter
    @Builder
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class FindContactUsOutDTO {
        private Long introPageId;
        private String status;
        private List<ContactUsLogOutDTO> content;
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
        public static class ContactUsLogOutDTO {
            private Long contactUsLogId;
            private String email;
            private String name;
            private String content;
            private String type;
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy.MM.dd", timezone = "UTC")
            private LocalDateTime date;
        }

        public static FindContactUsOutDTO toOutDTO(
                IntroPage introPage,
                Page<ContactUsLog> contactUsLog,
                List<ContactUsLogOutDTO> contactUsLogOutDTOList,
                ContactUsStatus status
        ) {
            return FindContactUsOutDTO.builder()
                    .introPageId(introPage.getId())
                    .status(String.valueOf(status))
                    .content(contactUsLogOutDTOList)
                    .totalElements(contactUsLog.getTotalElements())
                    .totalPage(contactUsLog.getTotalPages())
                    .size(contactUsLog.getSize())
                    .number(contactUsLog.getNumber())
                    .numberOfElements(contactUsLog.getNumberOfElements())
                    .hasPrevious(contactUsLog.hasPrevious())
                    .hasNext(contactUsLog.hasNext())
                    .isFirst(contactUsLog.isFirst())
                    .isLast(contactUsLog.isLast())
                    .build();
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

    @Getter
    @Builder
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class FindDownloadOutDTO {
        private Long introPageId;
        private String type;
        private List<Integer> introFile;
        private List<Integer> mediaKitFile;
        private List<DownloadOutDTO> content;
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
        public static class DownloadOutDTO {
            private Long downloadLogId;
            private DownloadType type;
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy.MM.dd", timezone = "UTC")
            private LocalDateTime date;
        }

        public static FindDownloadOutDTO toOutDTO(
                IntroPage introPage,
                List<DownloadLog> downloadLogList,
                Page<DownloadLog> downloadLogPG,
                List<DownloadOutDTO> downloadOutDTOList,
                DownloadType type
        ) {
            String typeString = "ALL";
            if (type != null)
                typeString = String.valueOf(type);

            // 주간 다운로드 List 변환
            List<Integer> introFile = new ArrayList<>();
            List<Integer> mediaKitFile = new ArrayList<>();

            List<DownloadLog> introFileLogList = downloadLogList.stream().filter(s -> s.getDownloadType().equals(DownloadType.INTROFILE)).collect(Collectors.toList());
            List<DownloadLog> mediaKitFileLogList = downloadLogList.stream().filter(s -> s.getDownloadType().equals(DownloadType.MEDIAKIT)).collect(Collectors.toList());
            for (int i = 6; i >= 0; i--) {
                LocalDate date = LocalDate.now().minusDays(i);
                int size = introFileLogList.stream().filter(s -> s.getCreatedAt().toLocalDate().compareTo(date) == 0).collect(Collectors.toList()).size();
                introFile.add(size);
                size = mediaKitFileLogList.stream().filter(s -> s.getCreatedAt().toLocalDate().compareTo(date) == 0).collect(Collectors.toList()).size();
                mediaKitFile.add(size);
            }

            return FindDownloadOutDTO.builder()
                    .introPageId(introPage.getId())
                    .type(typeString)
                    .introFile(introFile)
                    .mediaKitFile(mediaKitFile)
                    .content(downloadOutDTOList)
                    .totalElements(downloadLogPG.getTotalElements())
                    .totalPage(downloadLogPG.getTotalPages())
                    .size(downloadLogPG.getSize())
                    .number(downloadLogPG.getNumber())
                    .numberOfElements(downloadLogPG.getNumberOfElements())
                    .hasPrevious(downloadLogPG.hasPrevious())
                    .hasNext(downloadLogPG.hasNext())
                    .isFirst(downloadLogPG.isFirst())
                    .isLast(downloadLogPG.isLast())
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class FindVisitorOutDTO {
        private Long introPageId;
        private String type;
        private List<Visitor> content;
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
        public static class Visitor {
            private Long visitorId;
            @JsonInclude(JsonInclude.Include.NON_NULL)
            private String keyword;
            @JsonInclude(JsonInclude.Include.NON_NULL)
            private String sharingCode;
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy.MM.dd", timezone = "UTC")
            private LocalDateTime date;
        }

        public static FindVisitorOutDTO toOutDTO(
                IntroPage introPage,
                Page<VisitorLog> visitorLogPG,
                List<Visitor> visitorList,
                String type
        ) {
            return FindVisitorOutDTO.builder()
                    .introPageId(introPage.getId())
                    .type(type)
                    .content(visitorList)
                    .totalElements(visitorLogPG.getTotalElements())
                    .totalPage(visitorLogPG.getTotalPages())
                    .size(visitorLogPG.getSize())
                    .number(visitorLogPG.getNumber())
                    .numberOfElements(visitorLogPG.getNumberOfElements())
                    .hasPrevious(visitorLogPG.hasPrevious())
                    .hasNext(visitorLogPG.hasNext())
                    .isFirst(visitorLogPG.isFirst())
                    .isLast(visitorLogPG.isLast())
                    .build();
        }
    }
}
