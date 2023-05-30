package com.fastcampus05.zillinks.domain.dto.intropage;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class IntellectualPropertyResponse {
    @JsonProperty("totalCount")
    private int totalCount;                      // 기업의 총 지식재산권 수
    @JsonProperty("itemsCount")
    private int itemsCount;                      // 호출시 반환된 지식재산권 수
    private List<IntellectualPropertyItem> itemsC; // 반환된 지식재산권 목록

    public static class IntellectualPropertyItem {
        private String type;                    // 지식재산권 종류
        @JsonProperty("legalStatus")
        private String legalStatus;             // 지식재산권 법적상태
        private String title;                   // 요약문
        @JsonProperty("appNum")
        private String appNum;                  // 출원번호
        @JsonProperty("appDate")
        private String appDate;                 // 출원일자
        @JsonProperty("regNum")
        private String regNum;                  // 등록번호
        @JsonProperty("regDate")
        private String regDate;                 // 등록일자
        private List<String> applicants;        // 출원인
        private String country;                 // 국적
        @JsonProperty("finalRightHolder")
        private List<String> finalRightHolder;  // 최종권리자
        private String link;                    // 지식재산권 상세주소 키프리스 주소
    }
}
