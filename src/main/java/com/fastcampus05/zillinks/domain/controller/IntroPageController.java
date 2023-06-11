package com.fastcampus05.zillinks.domain.controller;

import com.fastcampus05.zillinks.core.auth.session.MyUserDetails;
import com.fastcampus05.zillinks.domain.dto.ResponseDTO;
import com.fastcampus05.zillinks.domain.dto.intropage.IntroPageRequest;
import com.fastcampus05.zillinks.domain.dto.intropage.IntroPageResponse;
import com.fastcampus05.zillinks.domain.service.IntroPageService;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.fastcampus05.zillinks.domain.dto.intropage.IntroPageResponse.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class IntroPageController {

    private final IntroPageService introPageService;

    // check-point, 초기값 세팅에 대한 논의
    @Operation(summary = "회사 소개 페이지 만들기", description = "회사 소개 페이지 정보 만들기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
    })
    @Parameters({
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @PostMapping("/s/user/introPage")
    public ResponseEntity<ResponseDTO<SaveIntroPageOutDTO>> saveIntroPage(
            @AuthenticationPrincipal MyUserDetails myUserDetails) {
        IntroPageResponse.SaveIntroPageOutDTO saveIntroPageOutDTO = introPageService.saveIntroPage(myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(saveIntroPageOutDTO);
        return ResponseEntity.ok(responseBody);
    }

    @Operation(summary = "회사 소개 페이지 조회", description = "회사 소개 페이지 정보 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = IntroPageOutDTO.class))),
    })
    @Parameters({
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @GetMapping("/s/user/introPage")
    public ResponseEntity<ResponseDTO<IntroPageOutDTO>> findIntroPage(
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        IntroPageOutDTO findOutDTO = introPageService.findIntroPage(myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", findOutDTO);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }


    // check-point, mediaKitFile, trackingCode 저장시 기존 데이터 삭제 후 연결
    @Operation(summary = "회사 소개 페이지 수정", description = "회사 소개 페이지 정보 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
    })
    @Parameters({
            @Parameter(name = "updateInDTO"),
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @PutMapping("/s/user/introPage")
    public ResponseEntity<ResponseDTO<UpdateIntroPageOutDTO>> updateIntroPage(
            @RequestBody @Valid IntroPageRequest.UpdateInDTO updateInDTO,
            Errors errors,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        UpdateIntroPageOutDTO updateIntroPageOutDTO = introPageService.updateIntroPage(updateInDTO, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(updateIntroPageOutDTO);
        return ResponseEntity.ok(responseBody);
    }

    @Operation(summary = "사이트 정보 조회", description = "사이트 정보 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = InfoOutDTO.class))),
    })
    @Parameters({
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @GetMapping("/s/user/introPage/info")
    public ResponseEntity<ResponseDTO<InfoOutDTO>> findInfo(
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        InfoOutDTO infoOutDTO = introPageService.findInfo(myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", infoOutDTO);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

    @Operation(summary = "사이트 정보 수정", description = "사이트 정보 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = UpdateInfoOutDTO.class))),
    })
    @Parameters({
            @Parameter(name = "updateInfoInDTO"),
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @PutMapping("/s/user/introPage/info")
    public ResponseEntity<ResponseDTO<UpdateInfoOutDTO>> updateInfo(
            @RequestBody @Valid IntroPageRequest.UpdateInfoInDTO updateInfoInDTO,
            Errors errors,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        UpdateInfoOutDTO updateIntroPageOutDTO = introPageService.updateInfo(updateInfoInDTO, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(updateIntroPageOutDTO);
        return ResponseEntity.ok(responseBody);
    }

    @Operation(summary = "회사 기본 정보 조회", description = "회사 기본 정보 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = CompanyInfoOutDTO.class))),
    })
    @Parameters({
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @GetMapping("/s/user/companyInfo")
    public ResponseEntity<ResponseDTO<CompanyInfoOutDTO>> findCompanyInfo(
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        CompanyInfoOutDTO companyInfoOutDTO = introPageService.findCompanyInfo(myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", companyInfoOutDTO);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

    @Operation(summary = "회사 기본 정보 수정", description = "회사 기본 정보 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = UpdateCompanyInfoOutDTO.class))),
    })
    @Parameters({
            @Parameter(name = "updateCompanyInfoInDTO"),
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @PatchMapping("/s/user/companyInfo")
    public ResponseEntity<ResponseDTO<UpdateCompanyInfoOutDTO>> updateCompanyInfo(
            @RequestBody @Valid IntroPageRequest.UpdateCompanyInfoInDTO updateCompanyInfoInDTO,
            Errors errors,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        UpdateCompanyInfoOutDTO updateCompanyInfoOutDTO = introPageService.updateCompanyInfo(updateCompanyInfoInDTO, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", updateCompanyInfoOutDTO);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

    // check-point 와이어 프레임 정리가 되어 있지 않음, 이후 정리가 필요한 내용
    @Operation(summary = "Contact-us 요청", description = "Contact-us 요청")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
    })
    @Parameters({
            @Parameter(name = "contactUsInDTO")
    })
    @PostMapping("/contactUs")
    public ResponseEntity<ResponseDTO> saveContactUs(
            @RequestBody @Valid IntroPageRequest.ContactUsInDTO contactUsInDTO,
            Errors errors
    ) {
        introPageService.saveContactUs(contactUsInDTO);
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", null);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

    @Operation(summary = "회사소개서/미디어킷 다운로드 요청", description = "회사소개서/미디어킷 다운로드 요청")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
    })
    @Parameters({
            @Parameter(name = "downloadFileInDTO")
    })
    @PostMapping("/downloadFile")
    public ResponseEntity<ResponseDTO> downloadFile(
            @RequestBody @Valid IntroPageRequest.DownloadFileInDTO downloadFileInDTO,
            Errors errors
    ) {
        introPageService.downloadFile(downloadFileInDTO);
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", null);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }
}
