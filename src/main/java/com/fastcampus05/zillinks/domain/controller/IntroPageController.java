package com.fastcampus05.zillinks.domain.controller;

import com.fastcampus05.zillinks.core.auth.session.MyUserDetails;
import com.fastcampus05.zillinks.core.util.Common;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import static com.fastcampus05.zillinks.domain.dto.intropage.IntroPageResponse.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
@Slf4j
public class IntroPageController {

    private final IntroPageService introPageService;

    /**
     * 일반 유저 사용 영역
     */
    @Operation(summary = "회사 소개 메인 페이지 조회 - 일반 유저 조회", description = "회사 소개 메인 페이지 조회 - 일반 유저 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = FindIntroPageOutDTO.class))),
    })
    @Parameters({
            @Parameter(name = "findIntroPageInDTO"),
    })
    @PostMapping("/introPage")
    public ResponseEntity<ResponseDTO<FindIntroPageOutDTO>> getIntroPage(
            HttpServletRequest request,
            @RequestBody @Valid IntroPageRequest.FindIntroPageInDTO findIntroPageInDTO,
            Errors errors
    ) {
        FindIntroPageOutDTO findIntroPageOutDTO = introPageService.getIntroPage(findIntroPageInDTO, Common.getDeviceType(request));
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", findIntroPageOutDTO);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

    /**
     * 회사 페이지 편집 영역
     */
    @Operation(summary = "회사 소개 페이지 만들기", description = "회사 소개 페이지 정보 만들기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
    })
    @Parameters({
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @PostMapping("/s/user/introPage")
    public ResponseEntity<ResponseDTO> saveIntroPage(
            @RequestBody @Valid IntroPageRequest.SaveIntroPageInDTO saveIntroPageInDTO,
            Errors errors,
            @AuthenticationPrincipal MyUserDetails myUserDetails) {
        introPageService.saveIntroPage(saveIntroPageInDTO, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", null);
        return ResponseEntity.ok(responseBody);
    }

    @Operation(summary = "회사 소개 페이지 조회 - 편집", description = "회사 소개 페이지 정보 조회 - 편집")
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

    @Operation(summary = "회사 소개 페이지 수정", description = "회사 소개 페이지 정보 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
    })
    @Parameters({
            @Parameter(name = "updateInDTO"),
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @PatchMapping("/s/user/introPage")
    public ResponseEntity<ResponseDTO> updateIntroPage(
            @RequestBody @Valid IntroPageRequest.UpdateInDTO updateInDTO,
            Errors errors,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        introPageService.updateIntroPage(updateInDTO, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(null);
        return ResponseEntity.ok(responseBody);
    }

    @Operation(summary = "테마 변경", description = "테마 변경")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
    })
    @Parameters({
            @Parameter(name = "updateThemeInDTO"),
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @PatchMapping("/s/user/introPage/theme")
    public ResponseEntity<ResponseDTO> updateTheme(
            @RequestBody @Valid IntroPageRequest.UpdateThemeInDTO updateThemeInDTO,
            Errors errors,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        introPageService.updateTheme(updateThemeInDTO, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", null);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

    @Operation(summary = "회사 기본 정보 수정", description = "회사 기본 정보 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
    })
    @Parameters({
            @Parameter(name = "updateCompanyInfoInDTO"),
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @PatchMapping("/s/user/introPage/companyInfo")
    public ResponseEntity<ResponseDTO> updateCompanyInfo(
            @RequestBody @Valid IntroPageRequest.UpdateCompanyInfoInDTO updateCompanyInfoInDTO,
            Errors errors,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        introPageService.updateCompanyInfo(updateCompanyInfoInDTO, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", null);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

    @Operation(summary = "사이트 정보 수정", description = "사이트 정보 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
    })
    @Parameters({
            @Parameter(name = "updateSiteInfoInDTO"),
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @PatchMapping("/s/user/introPage/siteInfo")
    public ResponseEntity<ResponseDTO> updateSiteInfo(
            @RequestBody @Valid IntroPageRequest.UpdateSiteInfoInDTO updateSiteInfoInDTO,
            Errors errors,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        introPageService.updateSiteInfo(updateSiteInfoInDTO, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(null);
        return ResponseEntity.ok(responseBody);
    }

    @Operation(summary = "서브 도메인 중복 체크", description = "서브 도메인 중복 체크")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
    })
    @Parameters({
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @GetMapping("/s/user/introPage/siteInfo/checkSubDomain")
    public ResponseEntity<ResponseDTO> checkSubDomain(
            @RequestParam
            @Pattern(regexp = "^[_a-zA-Z0-9]{1,16}$")
            String subDomain,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        introPageService.checkSubDomain(subDomain, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(null);
        return ResponseEntity.ok(responseBody);
    }

    @Operation(summary = "헤더풋터 설정", description = "헤더풋터 설정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
    })
    @Parameters({
            @Parameter(name = "myUserDetails", hidden = true)
    })
    @PatchMapping("/s/user/introPage/headerAndFooter")
    public ResponseEntity<ResponseDTO> updateHeaderAndFooter(
            @RequestBody @Valid IntroPageRequest.UpdateHeaderAndFooter updateHeaderAndFooter,
            Errors errors,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        introPageService.updateHeaderAndFooter(updateHeaderAndFooter, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(null);
        return ResponseEntity.ok(responseBody);
    }
}
