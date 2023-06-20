package com.fastcampus05.zillinks.domain.controller;

import com.fastcampus05.zillinks.core.auth.session.MyUserDetails;
import com.fastcampus05.zillinks.core.util.Common;
import com.fastcampus05.zillinks.core.util.dto.excel.ExcelOutDTO;
import com.fastcampus05.zillinks.domain.dto.ResponseDTO;
import com.fastcampus05.zillinks.domain.dto.dashboard.DashboardRequest;
import com.fastcampus05.zillinks.domain.dto.dashboard.DashboardResponse;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class DashboardController {

    private final DashboardService dashboardService;

    @Operation(summary = "Contact-us 요청", description = "Contact-us 요청")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
    })
    @Parameters({
            @Parameter(name = "contactUsLogInDTO")
    })
    @PostMapping("/dashboard/contactUs")
    public ResponseEntity<ResponseDTO> saveContactUs(
            @RequestBody @Valid DashboardRequest.ContactUsLogInDTO contactUsLogInDTO,
            Errors errors
    ) {
        dashboardService.saveContactUs(contactUsLogInDTO);
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", null);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

    @Operation(summary = "회사소개서/미디어킷 다운로드 요청", description = "회사소개서/미디어킷 다운로드 요청")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
    })
    @Parameters({
            @Parameter(name = "downloadInDTO")
    })
    @PostMapping("/dashboard/download")
    public ResponseEntity<ResponseDTO> download(
            @RequestBody @Valid DashboardRequest.DownloadInDTO downloadInDTO,
            Errors errors
    ) {
        dashboardService.download(downloadInDTO);
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", null);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

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
    @PutMapping("/s/user/dashboard/contactUs")
    public ResponseEntity<ResponseDTO<DashboardResponse.FindContactUsOutDTO>> updateContactUsDetail(
            @RequestBody @Valid DashboardRequest.UpdateContactUsDetailInDTO updateContactUsDetailInDTO,
            Errors errors,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        dashboardService.updateContactUsDetail(updateContactUsDetailInDTO, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", null);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

    @Operation(summary = "다운로드 관리 내역 조회 - 전체/회사소개서/미디어킷", description = "다운로드 관리 내역 조회 - 전체/회사소개서/미디어킷")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = DashboardResponse.FindDownloadOutDTO.class))),
    })
    @Parameters({
            @Parameter(name = "type"),
            @Parameter(name = "page"),
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @GetMapping("/s/user/dashboard/download")
    public ResponseEntity<ResponseDTO<DashboardResponse.FindDownloadOutDTO>> findDownload (
            @RequestParam
            @Pattern(regexp = "ALL|INTROFILE|MEDIAKIT")
            String type,
            @RequestParam(defaultValue = "0") Integer page,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        DownloadType downloadType = null;
        if (!type.equals("ALL"))
            downloadType = DownloadType.valueOf(type);
        DashboardResponse.FindDownloadOutDTO findDownloadOutDTO = dashboardService.findDownload(downloadType, page, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", findDownloadOutDTO);
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

    @Operation(summary = "연락 관리 내역 엑셀 다운로드 - 획인 필요/완료", description = "연락 관리 내역 엑셀 다운로드 - 획인 필요/완료")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
    })
    @Parameters({
            @Parameter(name = "excelContactUsInDTO"),
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @PostMapping("/s/user/dashboard/contactUs/excel")
//    @PostMapping("/dashboard/contactUs/excel")
    public ResponseEntity<byte[]> excelContactUs(
            @RequestBody @Valid DashboardRequest.ExcelContactUsInDTO excelContactUsInDTO,
            Errors errors,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        List<ExcelOutDTO.ContactUsOutDTO> contactUsOutDTOList = dashboardService.excelContactUs(excelContactUsInDTO, myUserDetails.getUser());
//        List<ExcelOutDTO.ContactUsOutDTO> contactUsOutDTOList = dashboardService.excelContactUs(excelContactUsInDTO, null);
        return Common.excelGenerator(contactUsOutDTOList, ExcelOutDTO.ContactUsOutDTO.class, "ContactUs");
//        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", null);
//        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

    @Operation(summary = "연락 관리 내역 엑셀 다운로드 - 획인 필요/완료", description = "연락 관리 내역 엑셀 다운로드 - 획인 필요/완료")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
    })
    @Parameters({
            @Parameter(name = "excelDownloadInDTO"),
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @PostMapping("/s/user/dashboard/download/excel")
//    @PostMapping("/dashboard/download/excel")
    public ResponseEntity<byte[]> excelDownload(
            @RequestBody @Valid DashboardRequest.ExcelDownloadInDTO excelDownloadInDTO,
            Errors errors,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        DownloadType downloadType = null;
        if (!excelDownloadInDTO.getType().equals("ALL"))
            downloadType = DownloadType.valueOf(excelDownloadInDTO.getType());
        List<ExcelOutDTO.DownloadOutDTO> downloadOutDTOList = dashboardService.excelDownload(downloadType, myUserDetails.getUser());
//        List<ExcelOutDTO.DownloadOutDTO> downloadOutDTOList = dashboardService.excelDownload(downloadType, null);
        return Common.excelGenerator(downloadOutDTOList, ExcelOutDTO.DownloadOutDTO.class, "Download");
//        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", null);
//        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

    @Operation(summary = "방문자 기록 내역 엑셀 다운로드 - 조회/공유", description = "방문자 기록 내역 엑셀 다운로드 - 조회/공유")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
    })
    @Parameters({
            @Parameter(name = "excelVisitorInDTO"),
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @PostMapping("/s/user/dashboard/visitor/excel")
//    @PostMapping("/dashboard/visitor/excel")
    public ResponseEntity<byte[]> excelVisitor(
            @RequestBody @Valid DashboardRequest.ExcelVisitorInDTO excelVisitorInDTO,
            Errors errors,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        List<ExcelOutDTO.VisitorOutDTO> visitorOutDTOList = dashboardService.excelVisitor(excelVisitorInDTO, myUserDetails.getUser());
//        List<ExcelOutDTO.VisitorOutDTO> visitorOutDTOList = dashboardService.excelVisitor(excelVisitorInDTO, null);
        return Common.excelGenerator(visitorOutDTOList, ExcelOutDTO.VisitorOutDTO.class, "Visitor");
//        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", null);
//        return new ResponseEntity(responseBody, HttpStatus.OK);
    }
}
