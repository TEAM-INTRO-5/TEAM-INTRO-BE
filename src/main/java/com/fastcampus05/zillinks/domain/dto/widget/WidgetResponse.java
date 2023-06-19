package com.fastcampus05.zillinks.domain.dto.widget;

import com.fastcampus05.zillinks.domain.model.widget.ProductsAndServices;
import com.fastcampus05.zillinks.domain.model.widget.ProductsAndServicesElement;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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

    @Getter
    @Builder
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class UpdateProductsAndServicesOutDTO {
        private Long productsAndServicesId;
        //        private List<Long> orderList;
        private List<ProductsAndServicesElementOutDTO> productsAndServicesElements;
        private Boolean callToActionStatus;
        private CallToActionOutDTO callToAction;

        @Getter
        @Builder
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        public static class ProductsAndServicesElementOutDTO {
            private Long productsAndServicesElementId;
            private String image;
            private String name;
            private String title;
            private String description;

            private static ProductsAndServicesElementOutDTO toOutDTO(ProductsAndServicesElement productsAndServicesElement) {
                return ProductsAndServicesElementOutDTO.builder()
                        .productsAndServicesElementId(productsAndServicesElement.getId())
                        .image(productsAndServicesElement.getImage())
                        .name(productsAndServicesElement.getName())
                        .title(productsAndServicesElement.getTitle())
                        .description(productsAndServicesElement.getDescription())
                        .build();
            }
        }

        @Getter
        @Builder
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        public static class CallToActionOutDTO {
            private String description;
            private String text;
            private String link;
        }

        public static UpdateProductsAndServicesOutDTO toOutDTO(
                ProductsAndServices productsAndServices,
                List<Long> orderList
        ) {
            List<ProductsAndServicesElement> productsAndServicesElements = productsAndServices.getProductsAndServicesElements();
            List<ProductsAndServicesElementOutDTO> productsAndServicesElementOutDTOs = new ArrayList<>();
            for (int i = 0; i < orderList.size(); i++) {
                for (ProductsAndServicesElement productsAndServicesElement : productsAndServicesElements) {
                    if (productsAndServicesElement.getOrder() != i + 1)
                        continue;
                    productsAndServicesElementOutDTOs.add(ProductsAndServicesElementOutDTO.toOutDTO(productsAndServicesElement));
                }
            }
            return UpdateProductsAndServicesOutDTO.builder()
                    .productsAndServicesId(productsAndServices.getId())
                    .productsAndServicesElements(productsAndServicesElementOutDTOs)
                    .callToActionStatus(productsAndServices.getCallToActionStatus())
                    .callToAction(productsAndServices.getCallToActionStatus() ? CallToActionOutDTO.builder()
                            .description(productsAndServices.getCallToAction().getDescription())
                            .text(productsAndServices.getCallToAction().getText())
                            .link(productsAndServices.getCallToAction().getLink())
                            .build() : null)
                    .build();
        }
    }
}
