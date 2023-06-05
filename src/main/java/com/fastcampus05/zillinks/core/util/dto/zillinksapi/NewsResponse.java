package com.fastcampus05.zillinks.core.util.dto.zillinksapi;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class NewsResponse {

    @JsonProperty("totalCount")
    private int totalCount;                // 기업의 총 뉴스 수
    @JsonProperty("itemsCount")
    private int itemsCount;                // 호출시 반환된 뉴스 수
    private List<NewsItem> itemsC;         // 반환된 뉴스 목록

    public static class NewsItem {
        private String title;            // 제목
        private String originallink;     // 주소
        private String description;      // 요약문
        private String press;            // 언론사
        private String pubDate;          // 작성일
        @JsonProperty("imageUri")
        private String imageUri;         // 썸네일 주소
    }
}
