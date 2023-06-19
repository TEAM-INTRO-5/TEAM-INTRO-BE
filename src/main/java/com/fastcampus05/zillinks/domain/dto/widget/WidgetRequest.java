package com.fastcampus05.zillinks.domain.dto.widget;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

public class WidgetRequest {

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class UpdateProductsAndServicesInDTO {
        @Schema(description = "제품/서비스 사용 여부", example = "true")
        private Boolean widgetStatus;

        @Schema(description = "제품/서비스들의 순서 리스트", example = "[10, 9, 2, 4, 8, 6]")
        private List<Long> orderList;

        @Schema(description = "Call To Action 사용 여부", example = "true")
        private Boolean callToActionStatus;

        @Schema(description = "버튼 설명", example = "이 상품이 궁금하세요?")
        private String description;
        @Schema(description = "버튼 텍스트", example = "상품 보러가기")
        private String text;
        @Schema(description = "버튼 링크", example = "https://zillinks.com")
        private String link;
    }

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

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class DeleteProductsAndServicesElementsInDTO {
        @Schema(description = "제품/서비스 삭제 리스트", example = "[1, 2, 3]")
        private List<Long> deleteList;
    }
}
