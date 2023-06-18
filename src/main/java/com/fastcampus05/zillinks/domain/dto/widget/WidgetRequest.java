package com.fastcampus05.zillinks.domain.dto.widget;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

public class WidgetRequest {

    @Getter
    public static class SaveProductsAndServicesElementInDTO {
        @Schema(description = "제품 서비스 이미지", example = "url 주소 경로")
        private String image;
        @Schema(description = "제품 서비스 이름", example = "빗코")
        private String name;
        @Schema(description = "제품 서비스 타이틀", example = "회사인베스트인베스트인베스트인베스트")
        private String title;
        @Schema(description = "제품 서비스 설명", example = "예: 빗코는 디나래 아슬라 아름드리 이플 옅구름 함초롱하다 가온누리 가온누리 소록소록 노트북 미쁘다 함초롱하다 책방 노트북 안녕 책방 노틉구 안녕")
        private String description;
    }
}
