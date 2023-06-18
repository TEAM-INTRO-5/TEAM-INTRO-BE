package com.fastcampus05.zillinks.domain.controller;

import com.fastcampus05.zillinks.core.auth.session.MyUserDetails;
import com.fastcampus05.zillinks.domain.dto.ResponseDTO;
import com.fastcampus05.zillinks.domain.dto.dashboard.DashboardRequest;
import com.fastcampus05.zillinks.domain.dto.dashboard.DashboardResponse;
import com.fastcampus05.zillinks.domain.dto.widget.WidgetRequest;
import com.fastcampus05.zillinks.domain.dto.widget.WidgetResponse;
import com.fastcampus05.zillinks.domain.service.DashboardService;
import com.fastcampus05.zillinks.domain.service.WidgetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/s/user/introPage")
public class WidgetController {

    private final WidgetService widgetService;

    @Operation(summary = "팀 멤버 요소 추가", description = "팀 멤버 요소 추가")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = WidgetResponse.SaveProductsAndServicesElementOutDTO.class))),
    })
    @Parameters({
            @Parameter(name = "saveProductsAndServicesElement"),
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @PostMapping("/productsAndServices/detail")
    public ResponseEntity<ResponseDTO<WidgetResponse.SaveProductsAndServicesElementOutDTO>> saveProductsAndServicesElement(
            @RequestBody @Valid WidgetRequest.SaveProductsAndServicesElementInDTO saveProductsAndServicesElement,
            Errors errors,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        WidgetResponse.SaveProductsAndServicesElementOutDTO saveProductsAndServicesElementOutDTO = widgetService.saveProductsAndServicesElement(saveProductsAndServicesElement, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", null);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }
}
