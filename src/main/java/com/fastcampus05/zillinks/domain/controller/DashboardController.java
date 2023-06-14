package com.fastcampus05.zillinks.domain.controller;

import com.fastcampus05.zillinks.core.auth.session.MyUserDetails;
import com.fastcampus05.zillinks.domain.dto.ResponseDTO;
import com.fastcampus05.zillinks.domain.dto.dashboard.DashboardResponse;
import com.fastcampus05.zillinks.domain.dto.intropage.IntroPageRequest;
import com.fastcampus05.zillinks.domain.model.dashboard.ContactUsStatus;
import com.fastcampus05.zillinks.domain.model.dashboard.DownloadType;
import com.fastcampus05.zillinks.domain.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class DashboardController {

    private final DashboardService dashboardService;

    @Operation(summary = "연락 관리 내역 조회 - 획인 필요/완료", description = "연락 관리 내역 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = DashboardResponse.FindContactUsOutDTO.class))),
    })
    @Parameters({
            @Parameter(name = "status"),
            @Parameter(name = "page"),
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @GetMapping("/s/user/dashboard/contactUs")
    public ResponseEntity<ResponseDTO<DashboardResponse.FindContactUsOutDTO>> findContactUs(
            @RequestParam
            @Pattern(regexp = "UNCONFIRMED|CONFIRM")
            String status,
            @RequestParam(defaultValue = "0") Integer page,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        ContactUsStatus contactUsStatus = ContactUsStatus.valueOf(status);
        DashboardResponse.FindContactUsOutDTO findContactUsOutDTO = dashboardService.findContactUs(contactUsStatus, page, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", findContactUsOutDTO);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

    @Operation(summary = "연락 관리 내역 조회 - detail", description = "단일 연락 관리 내역 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = DashboardResponse.FindContactUsDetailOutDTO.class))),
    })
    @Parameters({
            @Parameter(name = "contactUsId"),
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @GetMapping("/s/user/dashboard/contactUs/{contactUsId}")
    public ResponseEntity<ResponseDTO<DashboardResponse.FindContactUsOutDTO>> findContactUsDetail(
            @PathVariable Long contactUsId,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        DashboardResponse.FindContactUsDetailOutDTO findContactUsDetailOutDTO = dashboardService.findContactUsDetail(contactUsId, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", findContactUsDetailOutDTO);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

    @Operation(summary = "연락 관리 내역 - detail 확인/삭제", description = "단일 연락 관리 내역 확인/삭제 상태변경")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
    })
    @Parameters({
            @Parameter(name = "contactUsId"),
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @PutMapping("/s/user/dashboard/contactUs/{contactUsId}")
    public ResponseEntity<ResponseDTO<DashboardResponse.FindContactUsOutDTO>> updateContactUsDetail(
            @PathVariable Long contactUsId,
            @RequestBody @Valid IntroPageRequest.UpdateContactUsDetailInDTO updateContactUsDetailInDTO,
            Errors errors,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        dashboardService.updateContactUsDetail(contactUsId, updateContactUsDetailInDTO, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", null);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

    @Operation(summary = "다운로드 관리 내역 조회 - 전체/회사소개서/미디어킷", description = "다운로드 관리 내역 조회 - 전체/회사소개서/미디어킷")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = DashboardResponse.FindDownloadFileOutDTO.class))),
    })
    @Parameters({
            @Parameter(name = "type"),
            @Parameter(name = "page"),
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @GetMapping("/s/user/dashboard/downloadFile")
    public ResponseEntity<ResponseDTO<DashboardResponse.FindDownloadFileOutDTO>> findDownloadFile (
            @RequestParam
            @Pattern(regexp = "ALL|INTROFILE|MEDIAKIT")
            String type,
            @RequestParam(defaultValue = "0") Integer page,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        DownloadType downloadType = null;
        if (!type.equals("ALL"))
            downloadType = DownloadType.valueOf(type);
        DashboardResponse.FindDownloadFileOutDTO findDownloadFileOutDTO  = dashboardService.findDownloadFile(downloadType, page, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", findDownloadFileOutDTO);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

    @Operation(summary = "방문자 기록 내역 조회 - 조회/공유", description = "방문자 기록 내역 조회 - 조회/공유")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = DashboardResponse.FindVisitorOutDTO.class))),
    })
    @Parameters({
            @Parameter(name = "type"),
            @Parameter(name = "page"),
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @GetMapping("/s/user/dashboard/visitor")
    public ResponseEntity<ResponseDTO<?>> findVisitor (
            @RequestParam
            @Pattern(regexp = "VIEW|SHARING")
            String type,
            @RequestParam(defaultValue = "0") Integer page,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        DashboardResponse.FindVisitorOutDTO findVisitorOutDTO  = dashboardService.findVisitor(type, page, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", findVisitorOutDTO);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }
}
