package com.fastcampus05.zillinks.domain.controller;

import com.fastcampus05.zillinks.domain.dto.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Swagger 테스트", description = "Swagger 문법을 공부하기 위한 컨트롤러입니다.")
@RestController
@RequestMapping("/api")
public class SwaggerTestController {

    @GetMapping("/hello1")
    public String hello1() {
        return "hello";
    }

    @GetMapping("/hello2")
    public String hello2(@RequestParam String param) {
        return param;
    }

    @Operation(summary = "get posts", description = "지역에 대한 posts들 가져오기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @Parameters({
            @Parameter(name = "province", description = "시", example = "경기도"),
            @Parameter(name = "city", description = "도", example = "고양시"),
            @Parameter(name = "hashtag", description = "검색한 해시태그", example = "['#자장면', '#중국집']")
    })
    @GetMapping( "/test")
    public ResponseEntity<?> getPosts(
            @RequestParam(value = "province") String province,
            @RequestParam(value = "city") String city,
            @RequestParam(value = "hashtag", required = false) String hashtag
    ) {
        String tmp = province + " " + city;
        if (StringUtils.hasText(hashtag))
            tmp += " " + hashtag;
        ResponseDTO responseBody = new ResponseDTO(tmp);
        return ResponseEntity.ok(responseBody);
    }
}
