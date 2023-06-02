package com.fastcampus05.zillinks.core.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static org.hibernate.validator.internal.util.CollectionHelper.newArrayList;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi group1() {
        return GroupedOpenApi.builder()
                .group("그룹1")
                .pathsToMatch("/api/**")
                .build();
    }

    @Bean
    public GroupedOpenApi group2() {
        return GroupedOpenApi.builder()
                .group("그룹2")
                .pathsToMatch("/api/**")
                // .packagesToScan("com.example.swagger") // package 필터 설정
                .build();
    }

    @Bean
    public OpenAPI openAPI() {

        Info info = new Info()
                .title("Media Kit 관리 서비스 API 명세서")
                .description("API Description")
                .version("v1.0.0");

        // SecuritySecheme명
        String jwtSchemeName = "jwtAuth";
        // API 요청헤더에 인증정보 포함
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
        // SecuritySchemes 등록
        Components components = new Components()
                .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                        .name(jwtSchemeName)
                        .type(SecurityScheme.Type.HTTP) // HTTP 방식
                        .scheme("bearer")
                        .bearerFormat("JWT")); // 토큰 형식을 지정하는 임의의 문자(Optional)

        return new OpenAPI()
                .info(info)
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}