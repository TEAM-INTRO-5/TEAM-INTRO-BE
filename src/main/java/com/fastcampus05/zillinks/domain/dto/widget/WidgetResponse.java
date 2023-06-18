package com.fastcampus05.zillinks.domain.dto.widget;

import com.fastcampus05.zillinks.domain.model.widget.ProductsAndServicesElement;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class WidgetResponse {

    @Getter
    @Builder
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class SaveProductsAndServicesElementOutDTO {
        private Long productsAndServicesElementId;
        private String image;
        private String name;
        private String title;
        private String description;

        public static SaveProductsAndServicesElementOutDTO toOutDTO(
                ProductsAndServicesElement productsAndServicesElement
        ) {
            return SaveProductsAndServicesElementOutDTO.builder()
                    .productsAndServicesElementId(productsAndServicesElement.getId())
                    .image(productsAndServicesElement.getImage())
                    .name(productsAndServicesElement.getName())
                    .title(productsAndServicesElement.getTitle())
                    .description(productsAndServicesElement.getDescription())
                    .build();
        }
    }
}
