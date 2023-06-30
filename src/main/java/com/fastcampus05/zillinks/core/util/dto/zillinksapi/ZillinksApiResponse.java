package com.fastcampus05.zillinks.core.util.dto.zillinksapi;

import com.fastcampus05.zillinks.domain.model.intropage.ZillinksData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
public class ZillinksApiResponse {

    private Query query;
    private Result result;

    @Getter
    @Setter
    public static class Query {
        private String bizNum;
    }

    @Getter
    @Setter
    public static class Result {
        private String name;                           // 기업명
        private Address address;                       // 기업 주소
        private String homepage;                       // 홈페이지
        private String intro;                          // 기업 소개글
        private String logo;                           // 로고 주소
        private String representative;                 // 대표자
        private List<String> tags = Collections.emptyList();    // 기업 키워드 (Optional)
        private List<History> history;                 // 기업 연혁
        private List<Team> teams;                      // 팀 정보
        private String regNum;
        @JsonProperty("mainColor")
        private String mainColor;                      // 기업 고유 색상
        @JsonProperty("isCorporation")
        private boolean isCorporation;                 // 법인 여부
        @JsonProperty("startDate")
        private String startDate;                          // 설립일 (Optional)
        @JsonProperty("introFile")
        private String introFile;                      // 소개 파일
        @JsonProperty("mainBackground")
        private String mainBackground;                 // 기업 메인 배경 이미지 주소
        @JsonProperty("tagline")
        private String tagline;                            // 기업 한줄 소개 (Optional)
        @JsonProperty("bizArea")
        private List<String> bizArea = Collections.emptyList(); // 산업 분야 (Optional)
        @JsonProperty("bizStatus")
        private String bizStatus;                      // 휴폐업 상태
        @JsonProperty("contactEmail")
        private String contactEmail;                   // 담당자 이메일
        private Employment employment;                 // 고용 정보
        private String nationality;                    // 국적
    }

    @Getter
    @Setter
    public static class Address {
        private String zonecode;          // 추가된 zonecode 필드
        private String sido;              // 시도
        private String sigungu;          // 시군구
        private String address1;         // 기본 주소
        private String address2;         // 상세 주소
    }

    @Getter
    @Setter
    public static class History {
        private String date;             // 날짜
        private String title;            // 제목
    }

    @Getter
    @Setter
    public static class Team {
        private String name;             // 팀원 이름
        private String position;         // 부서
        private String appointment;      // 직급
        @JsonProperty("sns_url")
        private String snsUrl;           // sns 주소
        @JsonProperty("join_date")
        private String joinDate;         // 합류일
        @JsonProperty("detail_content")
        private String detailContent;    // 소개
        @JsonProperty("profile_img")
        private String profileImg;       // 프로필 이미지
    }

    @Getter
    @Setter
    public static class Employment {
        @JsonProperty("latestStaffCount")
        private int latestStaffCount;                  // 최근 직원수
        @JsonProperty("variationRateOf1Year")
        private double variationRateOf1Year;           // 1년 대비 직원수 증감률(%)
        private Map<String, Integer> staff;            // 월별 직원수
        private Map<String, Integer> hire;             // 월별 고용수
        private Map<String, Integer> leave;            // 월별 퇴직수
    }

    public ZillinksData toZillinksData() {
        return ZillinksData.builder()
                .name(result.name)
                .startDate(result.startDate)
                .representative(result.representative)
                .contactEmail(result.contactEmail)
                .bizNum(query.bizNum)
                .build();
    }
}
