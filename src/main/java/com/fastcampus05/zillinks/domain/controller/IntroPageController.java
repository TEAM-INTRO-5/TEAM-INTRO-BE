package com.fastcampus05.zillinks.domain.controller;

import com.fastcampus05.zillinks.core.auth.session.MyUserDetails;
import com.fastcampus05.zillinks.core.exception.Exception403;
import com.fastcampus05.zillinks.domain.dto.ResponseDTO;
import com.fastcampus05.zillinks.domain.dto.intropage.IntroPageRequest;
import com.fastcampus05.zillinks.domain.dto.intropage.IntroPageResponse;
import com.fastcampus05.zillinks.domain.service.IntroPageService;
import io.swagger.v3.oas.annotations.Operation;
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
    @GetMapping("/s/user/introPage")
    public ResponseEntity<IntroPageOutDTO> findIntroPage(
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        IntroPageOutDTO findOutDTO = introPageService.findIntroPage(myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", findOutDTO);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }


    // check-point, mediaKitFile, trackingCode 저장시 기존 데이터 삭제 후 연결
    @Operation(summary = "회사 소개 페이지 수정", description = "회사 소개 페이지 정보 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = UpdateIntroPageOutDTO.class))),
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
    @GetMapping("/s/user/introPage/info")
    public ResponseEntity<InfoOutDTO> findInfo(
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
    @PutMapping("/s/user/introPage/info")
    public ResponseEntity<ResponseDTO<UpdateInfoOutDTO>> updateIntroPage(
            @RequestBody @Valid IntroPageRequest.UpdateInfoInDTO updateInfoInDTO,
            Errors errors,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ) {
        UpdateInfoOutDTO updateIntroPageOutDTO = introPageService.updateInfo(updateInfoInDTO, myUserDetails.getUser());
        ResponseDTO responseBody = new ResponseDTO(updateIntroPageOutDTO);
        return ResponseEntity.ok(responseBody);
    }
}
